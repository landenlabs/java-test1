
package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*
  Bench test some stream functions from article
  https://betterprogramming.pub/modern-java-methods-for-processing-text-with-streams-and-lambdas-135273475057
 */


/*
  Java Microbench Test -
  https://github.com/melix/jmh-gradle-plugin#configuration-options


  Benchmark                 (N)  Mode  Cnt  Score   Error  Units
TestStream2.joinWords1  10000  avgt       0.102          ms/op
TestStream2.joinWords2  10000  avgt       0.117          ms/op
TestStream2.joinWords3  10000  avgt       0.148          ms/op
TestStream2.joinWords4  10000  avgt       0.189          ms/op
 */

@BenchmarkMode(Mode.AverageTime) // @BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 0, time = 2000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1)
public class TestStream2 {

    private static final boolean BENCHTEST = true;
    private static final boolean VALIDATE = false;
    volatile public static long join1Size = 0;
    volatile public static long join2Size = 0;
    volatile public static long join3Size = 0;
    volatile public static long join4Size = 0;
    volatile public static long join5Size = 0;
    volatile public static long join6Size = 0;
    static   List<String> words = new ArrayList<>();

    @Param({ /*"1000",*/ "10000" })
    private int N;

    private static final String TEST_STR = "Hello World";  // length=11, prime number
    @Setup // @Setup(Level.Trial)
    public void setup() {
        if (words.size() != N) {
            words.clear();
            for (int idx = 0; idx < N; idx++) {
                words.add(TEST_STR);
            }
        }
    }

    private String StringJoin1(List<String> strings) {
        int totalLen = 0;
        for (String str : strings) totalLen += str.length();
        char[] out = new char[totalLen];
        int idx = 0;
        for (String str : strings) {
            // Fastest
            str.getChars(0, str.length(), out, idx);
            idx += str.length();

            /*  2nd fastest
            char[] newChr = str.toCharArray();
            System.arraycopy(newChr, 0, out, idx, newChr.length);
            idx += newChr.length;
             */

            /* Slowest
            for (char c : str.toCharArray())   out[idx++] = c;
             */
        }
        return new String(out);
    }

    private String StringJoin2(List<String> strings) {
        int totalLen = 0;
        for (String str : strings) totalLen += str.length();
        StringBuilder stringBuilder = new StringBuilder(totalLen);
        for (String str : strings) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    @Benchmark
    public void joinWords1(Blackhole blackhole) {
        String bigStr = StringJoin1(words);
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join1Size) {
            join1Size = bigStr.length();
            System.out.println("join1 size=" + join1Size + " hash=" + bigStr.hashCode());
        }
    }
    @Benchmark
    public void joinWords2(Blackhole blackhole) {
        String bigStr = StringJoin2(words);
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join2Size) {
            join2Size = bigStr.length();
            System.out.println("join2 size=" + join2Size + " hash=" + bigStr.hashCode());
        }
    }

    @Benchmark
    // @BenchmarkMode(Mode.SingleShotTime)
    public void joinWords3(Blackhole blackhole) {
        String bigStr = words.stream().collect(Collectors.joining());
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join3Size) {
            join3Size = bigStr.length();
            System.out.println("join3 size=" + join3Size + " hash=" + bigStr.hashCode());
        }
    }

    @Benchmark
    public void joinWords4(Blackhole blackhole) {
        String bigStr = String.join("", words);
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join4Size) {
            join4Size = bigStr.length();
            System.out.println("join4 size=" + join4Size + " hash=" + bigStr.hashCode());
        }
    }

    /*
    @Benchmark
    // @BenchmarkMode(Mode.SingleShotTime)
    public void joinWords3(Blackhole blackhole) {
        String bigStr = persons.stream().collect(Collectors.joining("\t"));
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join3Size) {
            join3Size = bigStr.length();
            System.out.println("join3 size=" + join3Size + " hash=" + bigStr.hashCode());
        }
    }

    @Benchmark
    // @BenchmarkMode(Mode.SingleShotTime)
    public void joinWords4(Blackhole blackhole) {
        String bigStr = persons.stream().collect(Collectors.joining("\t", "{", "}"));
        blackhole.consume(bigStr.length());
        if (VALIDATE && bigStr.length() != join4Size) {
            join4Size = bigStr.length();
            System.out.println("join4 size=" + join4Size + " hash=" + bigStr.hashCode());
        }
    }
    */


    public static void main(String[] args) throws IOException, RunnerException {
        System.out.println("==== TestStream2 ====");
        if (BENCHTEST) {
            Options opt = new OptionsBuilder()
                    .include(".*" + "TestStream2" + ".*")
                    .build();

            new Runner(opt).run();
            // org.openjdk.jmh.Main.main(args);
        } else {
            // Debug test
            TestStream2 t2 = new TestStream2();
            t2.setup();
            System.out.println("Test strings=" + t2.words.size());
        }
    }
}
