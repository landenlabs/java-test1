
import java.util.concurrent.*;

/**
 * Dennis Lang - test Java virtual thread startup overhead.
 */
public class TestVirtualJavaThreads {

    volatile static  long total = 0L;
    static final int MAX_THREADS = 200000;
    static final long SLEEP_MILLI = 5000;
    static Runnable threadJob = () -> {
        try {
            total++;
            Thread.sleep(SLEEP_MILLI);
            total++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    public static void test1() {
        final int repeatCnt = 2;
        repeatIt("testA", TestVirtualJavaThreads::testA, repeatCnt);
        repeatIt("testB", TestVirtualJavaThreads::testB, repeatCnt);
    }

    interface Job {
        void doJob() throws Exception;;
    }
    static void repeatIt(String name, Job job, int repeatCnt) {
        var startTime = System.currentTimeMillis();
        try {
            for (int idx = 0; idx < repeatCnt; idx++) job.doJob();
            System.out.printf("%s duration: %d ms, total %d \n", name,
                    (System.currentTimeMillis() - startTime)/repeatCnt, total/repeatCnt);
        } catch (Exception ex) {
            System.err.printf("%s failed with %s", name, ex.toString());
        }
        total = 0;
    }

    public static void testA() throws InterruptedException {
        Thread[] threads = new Thread[MAX_THREADS];
        for (var i = 0; i < MAX_THREADS; i++) {
            threads[i] = Thread.ofVirtual().start(threadJob);
        }
        for(Thread thread : threads) thread.join();
    }

    public static void testB() throws ExecutionException, InterruptedException {
        Thread[] threads = new Thread[MAX_THREADS];
        ThreadFactory factory = Thread.ofVirtual().factory();
        for (var i = 0; i < MAX_THREADS; i++) {
            (threads[i] = factory.newThread(threadJob)).start();
        }
        for(Thread thread  : threads) thread.join();
    }
}
