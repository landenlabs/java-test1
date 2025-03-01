
package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

// https://medium.com/javarevisited/3-advanced-collectors-tips-to-refactor-better-in-java-44fe78d53a36
/*
dup=5
TestStream1.mergePersons1  50000  avgt    3  10.357 ± 3.762  ms/op
TestStream1.mergePersons2  50000  avgt    3  25.258 ± 2.030  ms/op
TestStream1.mergePersons3  50000  avgt    3   9.466 ± 1.899  ms/op

dups=2
Benchmark                    (N)  Mode  Cnt  Score    Error  Units
TestStream1.mergePersons1    100  avgt    3  0.007 ±  0.001  ms/op
TestStream1.mergePersons1   1000  avgt    3  0.070 ±  0.031  ms/op
TestStream1.mergePersons1  10000  avgt    3  0.719 ±  0.116  ms/op
TestStream1.mergePersons2    100  avgt    3  0.010 ±  0.001  ms/op
TestStream1.mergePersons2   1000  avgt    3  0.095 ±  0.016  ms/op
TestStream1.mergePersons2  10000  avgt    3  1.063 ±  0.211  ms/op
TestStream1.mergePersons3    100  avgt    3  0.006 ±  0.001  ms/op
TestStream1.mergePersons3   1000  avgt    3  0.064 ±  0.016  ms/op
TestStream1.mergePersons3  10000  avgt    3  0.677 ±  0.067  ms/op

Forks=3, Dups=4
Benchmark                  (DUPS)    (N)  Mode  Cnt  Score    Error  Units
TestStream1.mergePersons1       4    100  avgt    6  0.009 ±  0.001  ms/op
TestStream1.mergePersons1       4   1000  avgt    6  0.097 ±  0.003  ms/op
TestStream1.mergePersons1       4  10000  avgt    6  1.003 ±  0.055  ms/op
TestStream1.mergePersons2       4    100  avgt    6  0.020 ±  0.001  ms/op
TestStream1.mergePersons2       4   1000  avgt    6  0.208 ±  0.008  ms/op
TestStream1.mergePersons2       4  10000  avgt    6  2.159 ±  0.178  ms/op
TestStream1.mergePersons3       4    100  avgt    6  0.009 ±  0.001  ms/op
TestStream1.mergePersons3       4   1000  avgt    6  0.092 ±  0.007  ms/op
TestStream1.mergePersons3       4  10000  avgt    6  0.937 ±  0.028  ms/op
 */

@BenchmarkMode(Mode.AverageTime)
// @BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1, time = 2000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 4000, timeUnit = TimeUnit.MILLISECONDS)
// @Warmup(iterations = 3)
// @Measurement(iterations = 20)
public class BenchStream1 {

    volatile public static long merge1Size = 0;
    volatile public static long merge2Size = 0;
    volatile public static long merge3Size = 0;
    volatile public static long merge4Size = 0;

    static class CollectivePerson {
        public long id;
        public final List<String> addresses = new ArrayList<>();
        public final List<String> phones = new ArrayList<>();

        CollectivePerson(long id) {
            this.id = id;
        }

        long getId() {
            return id;
        }

        List<String> getAddresses() {
            return addresses;
        }

        List<String> getPhones() {
            return phones;
        }
    }

    static class Person {
        public long id;
        public String address;
        public String phone;

        long getId() { return id; }
        String getAddress() { return  address; }
        String getPhone() { return  phone; }
    }

    List<Person> persons = new ArrayList<>();

    // @Param({"50000"})
    @Param({"100", "1000", "10000"})
    private int N;

    @Param({"4"})
    private int DUPS;

    @Setup
    public void setup() {
        persons.clear();
        int cnt = Math.max(100, N);
        for (int idx = 0; idx < cnt; idx++) {
            int dupCnt = 1 + (idx % DUPS);
            for (int dup = 0; dup < dupCnt; dup++) {
                Person person = new Person();
                person.id = idx;
                person.address = "address " + dup;
                person.phone = "123-456-" + dup;
                persons.add(person);
            }
        }
    }

    @Benchmark
    public void mergePersons1(Blackhole blackhole) {
        Map<Long, CollectivePerson> map = new HashMap<>();
        List<CollectivePerson> collectivePersons = persons.stream()
                .map((Person person) -> {
                    CollectivePerson collectivePerson = map.get(person.getId());
                    if (Objects.isNull(collectivePerson)) {
                        collectivePerson = new CollectivePerson(person.getId());
                        map.put(person.getId(), collectivePerson);

                        collectivePerson.getAddresses().add(person.getAddress());
                        collectivePerson.getPhones().add(person.getPhone());

                        return collectivePerson;
                    } else {
                        collectivePerson.getAddresses().add(person.getAddress());
                        collectivePerson.getPhones().add(person.getPhone());

                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.<CollectivePerson>toList());

        blackhole.consume(collectivePersons.size());
        if (collectivePersons.size() != merge1Size) {
            System.out.println("merge1 size=" + collectivePersons.size());
            merge1Size = collectivePersons.size();
        }
        // collectivePersons.forEach(System.out::println);
    }

    @Benchmark
    public void mergePersons2(Blackhole blackhole) {
        List<CollectivePerson> collectivePersons = persons.stream()
                .map(p -> {
                    CollectivePerson cp = new CollectivePerson(p.getId());
                    cp.getAddresses().add(p.getAddress());
                    cp.getPhones().add(p.getPhone());
                    return cp;
                })
                .collect(Collectors.collectingAndThen(Collectors.toMap(
                                CollectivePerson::getId, Function.identity(),
                                (cp1, cp2) -> {
                                    cp1.getAddresses().addAll(cp2.getAddresses());
                                    cp1.getPhones().addAll(cp2.getPhones());
                                    return cp1;
                                }),
                        m -> new ArrayList<>(m.values())
                ));
        blackhole.consume(collectivePersons.size());
        if (collectivePersons.size() != merge2Size) {
            System.out.println("merge2 size=" + collectivePersons.size());
            merge2Size = collectivePersons.size();
        }
    }

    @Benchmark
    public void mergePersons3(Blackhole blackhole) {
        Map<Long, CollectivePerson> map = new HashMap<>();
        // List<CollectivePerson> collectivePersons = new ArrayList<>();
        for (Person person : persons) {
            CollectivePerson collectivePerson = map.get(person.id);
            if (collectivePerson == null) {
                collectivePerson = new CollectivePerson(person.id);
                map.put(person.id, collectivePerson);
                // collectivePersons.add(collectivePerson);
            }
            collectivePerson.addresses.add(person.address);
            collectivePerson.phones.add(person.phone);
        }
        Collection<CollectivePerson> collectivePersons = map.values();
        blackhole.consume(collectivePersons.size());
        if (collectivePersons.size() != merge3Size) {
            System.out.println("merge3 size=" + collectivePersons.size());
            merge3Size = collectivePersons.size();
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

}
