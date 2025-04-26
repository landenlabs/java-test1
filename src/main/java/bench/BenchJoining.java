package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Benchmark                                          (SIZE)   Mode  Cnt     Score    Error   Units
BenchJoining.loop1_jdk                                100  thrpt    6  1059.605 ± 13.862  ops/ms
BenchJoining.loop1_jdk                               1000  thrpt    6    79.394 ±  0.479  ops/ms
BenchJoining.loop1_jdk                              10000  thrpt    6     6.240 ±  0.192  ops/ms
BenchJoining.loop2_jdk                                100  thrpt    6  1047.802 ± 11.123  ops/ms
BenchJoining.loop2_jdk                               1000  thrpt    6    76.332 ±  0.204  ops/ms
BenchJoining.loop2_jdk                              10000  thrpt    6     7.379 ±  1.072  ops/ms
BenchJoining.parallel_lazy_mapToStringJoining_jdk     100  thrpt    6    34.643 ±  4.748  ops/ms
BenchJoining.parallel_lazy_mapToStringJoining_jdk    1000  thrpt    6    32.629 ±  0.805  ops/ms
BenchJoining.parallel_lazy_mapToStringJoining_jdk   10000  thrpt    6    12.577 ±  0.062  ops/ms
BenchJoining.serial_lazy_mapToStringJoining_jdk       100  thrpt    6   506.511 ± 10.921  ops/ms
BenchJoining.serial_lazy_mapToStringJoining_jdk      1000  thrpt    6    55.667 ±  0.196  ops/ms
BenchJoining.serial_lazy_mapToStringJoining_jdk     10000  thrpt    6     5.119 ±  0.035  ops/ms
 */

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 6, time = 2)
public class BenchJoining {
    @Param({"100", "1000", "10000"})
    private  int SIZE;
    private List<Integer> integersJDK;
    private ExecutorService executorService;

    @Setup
    public void setUp()
    {
        Stream<Integer> INTEGERS = IntStream.range(0, SIZE).boxed();
        integersJDK = INTEGERS.limit(SIZE).collect(Collectors.toList());
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Collections.shuffle(integersJDK);

        // Verify that tests produce identical outputs.
        String s1 = loop1_jdk();
        String s2 = loopPreAllocated_jdk();
        String s3 = serial_lazy_mapToStringJoining_jdk();
        String s4 = parallel_lazy_mapToStringJoining_jdk();
        if (!s1.equals(s2)) {
            System.out.println("S1=" + s1 + "\nS2=" + s2);
            throw new InvalidParameterException("tests 1 & 2 generate different strings");
        }
        if (!s1.equals(s3)) {
            System.out.println("S1=" + s1 + "\nS3=" + s3);
            throw new InvalidParameterException("tests 1 & 3 generate different strings");
        }
        if (!s1.equals(s4)) {
            System.out.println("S1=" + s1 + "\nS4=" + s4);
            throw new InvalidParameterException("tests 1 & 4 generate different strings");
        }
    }

    @TearDown
    public void tearDown() throws InterruptedException
    {
        this.executorService.shutdownNow();
        this.executorService.awaitTermination(1L, TimeUnit.SECONDS);
    }

    @Benchmark
    public String loop1_jdk()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < integersJDK.size(); i++)
        {
            if (i > 0) builder.append(",");
            builder.append(integersJDK.get(i));
        }
        return builder.toString();
    }

    @Benchmark
    public String loopPreAllocated_jdk()
    {
        final int NUM_DIGITS = (int)Math.floor(Math.log10(integersJDK.size()) + 1);
        final String SEPARATOR = ",";
        final int DECORATION = SEPARATOR.length();
        StringBuilder builder = new StringBuilder(integersJDK.size() * (NUM_DIGITS + DECORATION));
        builder.append(integersJDK.get(0));
        for (int i = 1; i < integersJDK.size(); i++)
        {
            builder.append(SEPARATOR);
            builder.append(integersJDK.get(i));
        }
        return builder.toString();
    }

    @Benchmark
    public String serial_lazy_mapToStringJoining_jdk()
    {
        return integersJDK.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    @Benchmark
    public String parallel_lazy_mapToStringJoining_jdk()
    {
        return integersJDK.parallelStream().map(Object::toString).collect(Collectors.joining(","));
    }

    public static void main(String[] args) throws IOException, RunnerException {
        // org.openjdk.jmh.Main.main(args);
        Options opt = new OptionsBuilder()
                .include(".*" + "BenchJoining" + ".*")
                .build();

        new Runner(opt).run();
    }
}