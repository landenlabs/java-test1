import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by ldennis on 3/31/16.
 */
public class TestString {

    // From Android TextUtils
    public static CharSequence concat(CharSequence... text) {
        if (text.length == 0) {
            return "";
        }

        return text[0];
    }

    public static void test1(final String s0) {
        // String test

        String s1 = (String)concat("",s0);
        System.out.println("empty length=" + s1.length() + " value=" + s1);
    }

    public static void test2() {

        Map<String, Object> objMap = new HashMap<String, Object>();

        objMap.put("string", "string");
        objMap.put("long", 123L);
        objMap.put("float", 12.3f);

        System.out.println("Raw map keys");
        for(Map.Entry<String,?> entry : objMap.entrySet()) {
            System.out.print(String.format("  %s : %s\n", entry.getKey(), entry.getValue()));
        }

        System.out.println("Sorted map keys");
        TreeSet<String> sortedKeys = new TreeSet<>(objMap.keySet());
        for (String key : sortedKeys) {
            System.out.print(String.format("  %s : %s\n", key, objMap.get(key)));
        }

        System.out.println("TestString Test2 [Done]");
    }

    /**
     * Measure performance of String.format verses concatenation
     * TODO - add StringBuilder and my custom StringBuilder
     */
    public static void test3() {
        final long maxIter = 1000000;
        long start;

        System.gc();
        start = System.currentTimeMillis();
        String sFmt1 = "";
        for( int idx=0; idx < maxIter; idx++) {
            sFmt1 = String.format( "Hi %d; Hi to you %d", idx, idx*2);
        }
        System.out.println("Format1 = " + (System.currentTimeMillis() - start) + " millisecond");

        System.gc();
        start = System.currentTimeMillis();
        String sFmt2 = "";
        for( int idx=0; idx < maxIter; idx++) {
            sFmt2 = String.format( "Hi %s; Hi to you %s", "hello", "world");
        }
        System.out.println("Format2 = " + (System.currentTimeMillis() - start) + " millisecond");

        System.gc();
        start = System.currentTimeMillis();
        String sCat1 = "";
        for( int idx=0; idx < maxIter; idx++) {
            sCat1 = "Hi " + idx + "; Hi to you " + idx*2;
        }
        System.out.println("Concat1 = " + (System.currentTimeMillis() - start) + " millisecond") ;

        System.out.println();
        System.out.println("Format1 = " + sFmt1);
        System.out.println("Format2 = " + sFmt2);
        System.out.println("Concat1 = " + sCat1);

        System.out.println("\nTestString Test3 [Done]");
    }

    /**
     * Test string.format syntax
     */
    public static void test4() {

        String word = "Hello";
        String word10 = "0123456789";
        System.out.printf("%10s, %-10s, %s\n", word10, word10, word10);
        System.out.printf("%10s, %-10s, %s\n", word, word, word);
        System.out.println(String.format("%10s, %-10s, %s", word, word, word));


        System.out.println("\nTestString Test4 [Done]");
    }

    public static void test5() {
        double d1 = 123.456;
        double d2 = 987.654;
        double l1 = 1234567890;
        double i1 = 1234567;

        // Use %s for double
        System.out.println(String.format("d1=%s d2=%s l=%s i=%s", d1, d2, l1, i1));
    }


    public static void testSplit() {
        final String dataStr = "[123.45,56.789]";

        String[] patterns = new String[] {
            "[|,|]",
            "\\[|,|\\]",
            "([|,|])",
            "(\\[|,|\\])",
        };

        for (String patStr : patterns) {
            String[] parts = dataStr.split(patStr);
            System.out.println(" Pattern=" + patStr + " Split into=" + parts.length + " Array=" + StringUtilsEssentials.join(parts, ", "));
        }

        System.out.println("\nTestString testSplit [Done]");
    }
}
