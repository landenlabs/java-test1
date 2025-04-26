import org.joda.time.DateTime;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestCompletable {

    //
    // https://www.callicoder.com/java-8-completablefuture-tutorial/
    // https://levelup.gitconnected.com/completablefuture-a-new-era-of-asynchronous-programming-86c2fe23e246
    //
    // http://blog.tremblay.pro/2017/08/supply-async.html
    //
    public static void test1() {
        final CompletableFuture<String> future1 = CompletableFuture.supplyAsync(new Supplier<String> () {
            @Override
            public String get() {
                try {
                    // while (!future1.isCancelled()) { // <-- Does not compile, - future1 not initialized ?
                        TimeUnit.SECONDS.sleep(1);
                    // }
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return "Result of the asynchronous computation";
            }
        });

        // future.join();  // compiles with out try/catch, but still throws exception

        // Block and get the result of the Future
        try {
            future1.cancel(true);
            String result = future1.get();
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getSimpleName() + " " + ex.getMessage());
        }
        System.out.println("[Test Math done]");
    }

    public static void test2() {
        final CompletableFuture<String> future5 = CompletableFuture.supplyAsync(new Supplier<String> () {
            @Override
            public String get() {
                try {
                    if (true) {
                        int idx = 0;
                        while (true) {
                            idx++;
                            System.out.println("Sleep " + idx + " future5 ID=" + Thread.currentThread().getId());
                        }
                    } else {
                        for (int idx = 0; idx < 5; idx++) {
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println("Sleep " + idx + " future5 ID=" + Thread.currentThread().getId());
                        }
                    }
                } catch (InterruptedException e) {
                    System.err.println("future5 ex=" + e.getMessage());
                    throw new IllegalStateException(e);
                }
                return "Result of the asynchronous computation";
            }
        });

        final CompletableFuture<String> future10 = CompletableFuture.supplyAsync(new Supplier<String> () {
            @Override
            public String get() {
                try {
                    for (int idx = 0; idx < 10; idx++) {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Sleep " + idx + " future10 ID=" + Thread.currentThread().getId());
                    }
                } catch (InterruptedException e) {
                    System.err.println("future10 ex=" + e.getMessage());
                    throw new IllegalStateException(e);
                }
                return "Result of the asynchronous computation";
            }
        });

        CompletableFuture<String> twoFuture = future5.thenCombine(future10, (a,b) -> { return "done"; });
        try {
            Thread.sleep(2000);
            System.out.println("canceled =" + twoFuture.cancel(true));
            System.out.println("two result=" + twoFuture.get());
        } catch (Exception ex) {
            System.err.println("Exception " + ex.getMessage());
        }

    }

    public static void test3() {
        final CompletableFuture<String> future1 = new CompletableFuture<>();

        future1.thenApply( xx -> {
            System.out.println("test3 - first");
            return xx;
        });

        future1.complete("mark completed");

        future1.thenApply( xx -> {
            System.out.println("test3 - second");
            return xx;
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    // From article
    //    https://medium.com/@viraj_63415/java-the-flaws-in-completablefuture-allof-0e3e454c23c4

    private static Supplier<String> task(String name, int secs, boolean fail) {

        return () -> {
            try {
                log("Start task %s(%d)".formatted(name, secs));
                Thread.sleep(TimeUnit.SECONDS.toMillis(secs));

                if (fail) {
                    String msg = "task %s(%s) failed".formatted(name, secs);
                    log(msg);
                    throw new RuntimeException(msg);
                }

                log("End task %s(%d)".formatted(name, secs));
                return name;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

    }

    private static void log(String msg) {
        String thread = Thread.currentThread().getName();
        System.out.printf("%s => %s%n", thread, msg);
    }

    public static void test4() {

        // Create the tasks to run
        var subtask1 = task("subtask1", 3, false);
        var subtask2 = task("subtask2", 5, false);
        var subtask3 = task("subtask3", 7, false);

        // Start subtasks in parallel
        var f1 = CompletableFuture.supplyAsync(subtask1);
        var f2 = CompletableFuture.supplyAsync(subtask2);
        var f3 = CompletableFuture.supplyAsync(subtask3);

        // Call allOf(..) to create a combined Future
        CompletableFuture<Void> combFuture =
                CompletableFuture.allOf(f1, f2, f3);

        // Wait till all tasks completed
        List<String> results
                = combFuture
                .thenApply(unused -> {
                    log("All subtasks successful");
                    List<String> result = Stream.of(f1, f2, f3)
                            .map(f -> f.getNow(null))
                            .toList();
                    return result;
                })
                .join();

        // Handle results here
        log("Results = %s%n".formatted(results));

        // Join() is called just so the program
        // does not terminate
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static Supplier<String> job(CompletableFuture<String> future, String name, int secs, boolean fail) {

        return () -> {
            try {
                log("Start task %s(%d)".formatted(name, secs));
                if (future.isCancelled())
                    return name;

                Thread.sleep(TimeUnit.SECONDS.toMillis(secs));

                if (fail) {
                    String msg = "task %s(%s) failed".formatted(name, secs);
                    log(msg);
                    future.cancel(true);
                }

                log("End task %s(%d)".formatted(name, secs));
                future.complete(name);
                return name;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

    }

    public static void test5() {

        CompletableFuture<String> future = new CompletableFuture<>();

        // Create the tasks to run
        var subtask1 = task("subtask1", 3, false);
        var subtask2 = task("subtask2", 5, true);
        var subtask3 = task("subtask3", 7, false);

        // Start subtasks in parallel
        var f1 = CompletableFuture.supplyAsync(subtask1);
        var f2 = CompletableFuture.supplyAsync(subtask2);
        var f3 = CompletableFuture.supplyAsync(subtask3);

        // Call allOf(..) to create a combined Future
        CompletableFuture<Void> combFuture =
                CompletableFuture.allOf(f1, f2, f3);

        // Wait till all tasks completed
        List<String> results
                = combFuture
                .thenApply(unused -> {
                    log("All subtasks successful");
                    List<String> result = Stream.of(f1, f2, f3)
                            .map(f -> f.getNow(null))
                            .toList();
                    return result;
                })
                .join();

        // Handle results here
        log("Results = %s%n".formatted(results));

        // Join() is called just so the program
        // does not terminate
    }

}
