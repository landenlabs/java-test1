
package bench2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// https://medium.com/@vikas.taank_40391/stop-using-string-concatenation-4459aa544f14
/*
Benchmark                   (DUPS)    (N)  Mode  Cnt   Score   Error  Units
BenchStrCat.strCat1            N/A  10000  avgt    2  13.841          ms/op
BenchStrCat.strCat2            N/A  10000  avgt    2   0.044          ms/op
BenchStrCat.strCat3            N/A  10000  avgt    2   0.039          ms/op
 */


// @BenchmarkMode(Mode.AverageTime)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 0, time = 2000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 4000, timeUnit = TimeUnit.MILLISECONDS)
// @Warmup(iterations = 0)
// @Measurement(iterations = 1)
public class BenchStrCat {

    private static final Map<String, Integer> resultSizes = new HashMap<>();

    // @Param({"50000"})
    @Param({ "100000"})
    private int N;

    @Setup
    public void setup() {
    }

    @Benchmark
    public void strCatenate(Blackhole blackhole) {
        String result = "";
        for (int i = 0; i < N; i++) {
            result += i; // Each iteration creates a new String object
        }

        resultSizes.put("strCatenate", result.length());
        blackhole.consume(result.length());
    }

    @Benchmark
    public void strBuilder(Blackhole blackhole) {
        // Using StringBuilder for efficient concatenation
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            result.append(i);
        }

        resultSizes.put("strBuilder", result.length());
        blackhole.consume(result.length());
    }

    @Benchmark
    public void strBuilderAlloc(Blackhole blackhole) {
        // Using StringBuilder for efficient concatenation
        StringBuilder result = new StringBuilder(N*5);
        for (int i = 0; i < N; i++) {
            result.append(i);
        }

        resultSizes.put("strBuilderAlloc", result.length());
        blackhole.consume(result.length());
    }


    @Benchmark
    public void strBuffer(Blackhole blackhole) {
        // Using StringBuilder for efficient concatenation
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < N; i++) {
            result.append(i);
        }

        resultSizes.put("strBuffer", result.length());
        blackhole.consume(result.length());
    }


    @Benchmark
    public void strStream(Blackhole blackhole) {
        // Use streams to build the string
        String result = IntStream.range(0, 100000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        resultSizes.put("strStream", result.length());
        blackhole.consume(result.length());
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        for (String key : resultSizes.keySet()) {
            System.out.printf("%20s ----- %d\n", key, resultSizes.get(key));
        }
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

}
