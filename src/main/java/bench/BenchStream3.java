package bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// https://medium.com/@daniel.las/speed-of-java-stream-1cc3a94b44c2

/*

Benchmark                (size)   Mode  Cnt     Score     Error   Units
BenchStream3.forEach       1000  thrpt    5  1698.061 ±  30.824  ops/ms
BenchStream3.forEach      10000  thrpt    5   157.788 ±  15.677  ops/ms
BenchStream3.forEach     100000  thrpt    5    15.934 ±   2.002  ops/ms
BenchStream3.forEach    1000000  thrpt    5     1.607 ±   0.011  ops/ms
BenchStream3.forLoop       1000  thrpt    5  1617.111 ± 222.853  ops/ms
BenchStream3.forLoop      10000  thrpt    5   158.548 ±   1.948  ops/ms
BenchStream3.forLoop     100000  thrpt    5    15.337 ±   1.393  ops/ms
BenchStream3.forLoop    1000000  thrpt    5     1.528 ±   0.098  ops/ms
BenchStream3.reduce        1000  thrpt    5   221.895 ±   1.456  ops/ms
BenchStream3.reduce       10000  thrpt    5    24.020 ±   0.083  ops/ms
BenchStream3.reduce      100000  thrpt    5     2.308 ±   0.005  ops/ms
BenchStream3.reduce     1000000  thrpt    5     0.224 ±   0.001  ops/ms
BenchStream3.reducePar     1000  thrpt    5    35.374 ±   2.594  ops/ms
BenchStream3.reducePar    10000  thrpt    5    28.758 ±   1.262  ops/ms
BenchStream3.reducePar   100000  thrpt    5     8.265 ±   0.038  ops/ms
BenchStream3.reducePar  1000000  thrpt    5     1.025 ±   0.004  ops/ms
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 2)

@State(Scope.Benchmark)
public class BenchStream3 {
    // @Param({"1000",  "1000000"})
    @Param({"1000", "10000", "100000", "1000000"})
    public int size;
    public List<Double> items;

    // This is our calculation, takes double type number, calculates
    // logarithm, then sine and then square root
    private static double calculate(double value) {
        // return Math.sqrt(Math.sin(Math.log(value)));
        return value;
    }

    @Setup
    public void setUp() {
        // items = random.doubles(size).mapToObj(i -> i).collect(Collectors.toList());
        items = IntStream.range(0, size).mapToObj(Double::valueOf).collect(Collectors.toList());
    }

    // Using forEach
    @Benchmark
    public Double forEach() {
        double res = 0d;
        for (double item : items) {
            res += calculate(item);
        }
        return res;
    }

    /* NOT A REAL USE CASE
    // Using collect with summing collector
    @Benchmark
    public Double collect() {
        return items.stream()
                .map(BenchStream3::calculate)
                .collect(Collectors.summingDouble(i -> i));
    }
    // Using collect with summing collector on parallel stream
    @Benchmark
    public Double collectPar() {
        return items.parallelStream()
                .map(BenchStream3::calculate)
                .collect(Collectors.summingDouble(i -> i));
    }
     */

    // Using reduce
    @Benchmark
    public Double reduce() {
        return items.stream()
                .map(BenchStream3::calculate)
                .reduce(0d, Double::sum);
    }
    // Using reduce on parallel stream
    @Benchmark
    public Double reducePar() {
        return items.parallelStream()
                .map(BenchStream3::calculate)
                .reduce(0d, Double::sum);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        // org.openjdk.jmh.Main.main(args);
        Options opt = new OptionsBuilder()
                .include(".*" + "BenchStream3" + ".*")
                .build();

        new Runner(opt).run();
    }
}
