
package bench2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
@Warmup(iterations = 1, time = 2000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 4000, timeUnit = TimeUnit.MILLISECONDS)
// @Warmup(iterations = 0)
// @Measurement(iterations = 1)
public class BenchStrCat {

    volatile public static long merge1Size = 0;
    volatile public static long merge2Size = 0;
    volatile public static long merge3Size = 0;
    volatile public static long merge4Size = 0;


    // @Param({"50000"})
    @Param({ "10000"})
    private int N;

    @Setup
    public void setup() {
    }

    @Benchmark
    public void strCat1(Blackhole blackhole) {
        String result = "";
        for (int i = 0; i < N; i++) {
            result += i; // Each iteration creates a new String object
        }

        merge1Size = result.length();
        blackhole.consume(merge1Size);
    }

    @Benchmark
    public void strCat2(Blackhole blackhole) {
        // Using StringBuilder for efficient concatenation
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            result.append(i);
        }

        merge2Size = result.length();
        blackhole.consume(merge1Size);
    }

    @Benchmark
    public void strCat3(Blackhole blackhole) {
        // Using StringBuilder for efficient concatenation
        StringBuilder result = new StringBuilder(N*4);
        for (int i = 0; i < N; i++) {
            result.append(i);
        }

        merge3Size = result.length();
        blackhole.consume(merge1Size);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

}
