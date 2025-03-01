import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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
}
