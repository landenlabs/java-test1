import java.util.ArrayList;

public class TestForLoops {

    //
    // Use javap -c TestForLoops.class to inspect the generated code
    //
    //  See article
    // https://medium.com/javarevisited/java-compiler-optimization-for-string-concatenation-7f5237e5e6ed


    private final static ArrayList<String> list = new ArrayList<>(10);

    static void forTest1a() {
        for (int i = 0, size = list.size(); i < size; i++) {
            System.out.println(list.get(i));
        }
    }

    // compiled code does call the list.size() on every loop.
    static void forTest1b() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    // string += is convert to makeConcatWithConstants
    static String stringCat1a() {
        String out = "";
        for (int i = 0; i < list.size(); i++) {
            out += list.get(i);
        }
        return out;
    }

    // unclear if above is better or worse than using StringBuilder directly.
    static String stringCat1b() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            out.append(list.get(i));
        }
        return out.toString();
    }

    public static void test1() {

        forTest1a();
        forTest1b();

        System.out.println(stringCat1a());
        System.out.println(stringCat1b());
    }
}
