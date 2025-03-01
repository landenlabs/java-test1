import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppendStringTest {

    static final int APPEND_COUNT = 50;
    static final int USE_TEST_COUNT = 1000;
    static final String APPENDER = "HelloWorld;";

    public interface StrBuilder {
        String getString();
    }

    static class UseStringBuilder implements StrBuilder {
        public String test(String str1, String str2) {
            return new StringBuilder().append(str1).append(str2).toString();
        }

        public String getString() {
            StringBuilder strBld = new StringBuilder();
            for (int idx = 0; idx < APPEND_COUNT; idx++) {
                // String str = "temp" + idx + ".ext";
                strBld.append(APPENDER);
            }
            return strBld.toString();
        }
    }

    static class UseStringBuilder2 implements StrBuilder {
        public String test(String str1, String str2) {
            return new StringBuilder2().append(str1).append(str2).toString();
        }

        public String getString() {
            StringBuilder2 strBld = new StringBuilder2();
            for (int idx = 0; idx < APPEND_COUNT; idx++) {
                // String str = "temp" + idx + ".ext";
                strBld.append(APPENDER);
            }
            return strBld.toString();
        }
    }

    static class UseStringAppender implements StrBuilder {
        public String test(String str1, String str2) {
            return new StringAppender().append(str1).append(str2).toString();
        }

        public String getString() {
            StringAppender strApp = new StringAppender();
            for (int idx = 0; idx < APPEND_COUNT; idx++) {
                // String str = "temp" + idx + ".ext";
                strApp.append(APPENDER);
            }
            return strApp.toString();
        }
    }

    static class UseStringAppend2 implements StrBuilder {
        public String test(String str1, String str2) {
            return new StringAppender().append(str1).append(str2).toString();
        }

        public String getString() {
            StringAppender strApp = new StringAppender();
            for (int idx = 0; idx < APPEND_COUNT; idx++) {
                // String str = "temp" + idx + ".ext";
                strApp.append(APPENDER);
            }
            return strApp.toString();
        }
    }

    static class UseStringList implements StrBuilder {
        public String getString() {
            ArrayList<String> strApp = new ArrayList<String>();
            for (int idx = 0; idx < APPEND_COUNT; idx++) {
                // String str = "temp" + idx + ".ext";
                strApp.add(APPENDER);
            }
            return APPENDER + strApp.size();
        }
    }


    public static void test1() {

        final int LOOP_CNT = 3;

        System.out.println("\ntestStringAppend2\n");
        System.out.println(new UseStringAppend2().test("hello", "world"));
        for (int idx = 0; idx < LOOP_CNT; idx++) {
            testString(new UseStringAppend2());
        }

        System.out.println("\ntestStringAppender\n");
        System.out.println(new UseStringAppender().test("hello", "world"));
        for (int idx = 0; idx < LOOP_CNT; idx++) {
            testString(new UseStringAppender());
        }

        /*
        System.out.println("\ntestStringList");
        for (int idx = 0; idx < LOOP_CNT; idx++) {
            testString(new UseStringList());
        }
        */

        System.out.println("\ntestStringBuilder\n");
        System.out.println(new UseStringBuilder().test("hello", "world"));
        for (int idx = 0; idx < LOOP_CNT; idx++) {
            testString(new UseStringBuilder());
        }

        System.out.println("\ntestStringBuilder2\n");
        System.out.println(new UseStringBuilder2().test("hello", "world"));
        for (int idx = 0; idx < LOOP_CNT; idx++) {
            testString(new UseStringBuilder2());
        }
    }

    static void testString(StrBuilder strBuilder) {

        List<String> results = new ArrayList<String>(USE_TEST_COUNT);
        long nanoSec1 = System.nanoTime();
        int total = 0;


        for (int idx = 0; idx < USE_TEST_COUNT; idx++) {
            results.add(strBuilder.getString());
            total += results.get(idx).length();
        }
        long nanoSec2 = System.nanoTime();

        System.out.println(String.format("Total items in lists %d %s", total, results.get(10)));
        System.out.println(String.format("Delta = %.4f", ((nanoSec2 - nanoSec1) / 1e9)));
    }

    static long memoryUsed(boolean runGc) {
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();

        if (runGc) {
            // Run the garbage collector
            runtime.gc();
        }

        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        return memory;
    }

    static class TestResult {
        String name;
        long milli;
        long memUsed;
        long hash;
        long len;

        TestResult(String name, long milli, long memUsed, String toHash) {
            this.name = name;
            this.milli = milli;
            this.memUsed = memUsed;
            this.hash = toHash.hashCode();
            this.len = toHash.length();
        }

        static Map<String, List<TestResult>> testResults = new HashMap<>();

        static void add(String name, long milli, long memStart, String toHash) {
            long memUsed = memoryUsed(false) - memStart;
            // System.out.printf("%10d seconds, %s\n", milli / 1000, name);
            if (!testResults.containsKey(name))
                testResults.put(name, new ArrayList<TestResult>());

            testResults.get(name).add(new TestResult(name, milli, memUsed, toHash));
        }
    }

    public static void test2() {
        final int LOOPS = 100;
        final int ITERATION = 1000;
        final String Astr = "A2345";
        String s = "";
        long memused;
        long startTime;
        long durMilli;

        // String Concatenation  + operator
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            for (int i = 0; i < ITERATION; i++) {
                s = s + Astr;
            }
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("+ operator", durMilli, memused, s);
        }

        // String concat() method
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            for (int i = 0; i < ITERATION; i++) {
                s = s.concat(Astr);
            }
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("concat", durMilli, memused, s);
        }

        // StringBuffer example to concate String in Java, default size 16
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < ITERATION; i++) {
                buffer.append(Astr);
            }
            s = buffer.toString();
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("StringBuffer", durMilli, memused, s);
        }

        // StringBuilder
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ITERATION; i++) {
                builder.append(Astr);
            }
            s = builder.toString();
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("StringBuilder", durMilli, memused, s);
        }

        // StringBuilder2
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            StringBuilder2 builder = new StringBuilder2();
            for (int i = 0; i < ITERATION; i++) {
                builder.append(Astr);
            }
            s = builder.toString();
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("StringBuilder2", durMilli, memused, s);
        }

        // StringAppender
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            StringAppender appender = new StringAppender(ITERATION);
            for (int i = 0; i < ITERATION; i++) {
                appender.append(Astr);
            }
            s = appender.toString();
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("StringAppender", durMilli, memused, s);
        }

        /*
        // StringAppender
        for (int loopIdx = 0; loopIdx < LOOPS; loopIdx++) {
            s = "";
            memused = memoryUsed(true);
            startTime = System.nanoTime();
            StringAppender append2 = new StringAppender();
            for (int i = 0; i < ITERATION; i++) {
                append2.append(Astr);
            }
            s = append2.toString();
            durMilli = (System.nanoTime() - startTime) / 1000;
            TestResult.add("StringAppender", durMilli, memused, s);
        }
        */

        // Summarize test results
        long minMilli = Long.MAX_VALUE;
        for (String key : TestResult.testResults.keySet()) {
            long totalMilli = 0;
            for (TestResult testResult : TestResult.testResults.get(key)) {
                totalMilli += testResult.milli;
            }
            minMilli = Math.min(minMilli, totalMilli);
        }

        System.out.printf("\n   Scale MemoryUsed         TestType  HashCode  Length Seconds\n");
        for (String key : TestResult.testResults.keySet()) {
            long totalMilli = 0;
            for (TestResult testResult : TestResult.testResults.get(key)) {
                totalMilli += testResult.milli;
            }
            int sampleIdx = TestResult.testResults.get(key).size() / 2;
            TestResult testResult = TestResult.testResults.get(key).get(sampleIdx);
            System.out.printf("%8.2f %10d, %15s %10x %4d %.6f\n",
                    (double) totalMilli / minMilli, testResult.memUsed,
                    testResult.name, testResult.hash, testResult.len, totalMilli / 1e6);
        }
    }

    static String testJoin() {
        String[] array = {"Hello", "World"};
        String out = String.join(",", array);
        return out;
    }
}
