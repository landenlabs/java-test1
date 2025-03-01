import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConstructorOrder {

    static class Base {

        static String STATIC_STR1 = "base";
        String str1 = "base";
        final List<String> list1 = new ArrayList<>();

        Base(String str) {
            setStr(str);
        }

        void setStr(String str) {
            str1 = str;
            STATIC_STR1 = str;
            list1.add(str);
        }

        void dump() {
            System.out.println("Base ");
            System.out.println("  STATIC_STR1=" + STATIC_STR1);
            System.out.println("         str1=" + str1);
            System.out.println("        list1=" + Arrays.toString(list1.toArray()));
            System.out.println(" ");
        }
    }

    static class Derived extends Base {
        static String STATIC_STR2 = "derived";
        String str2 = "derived";
        final List<String> list2 = new ArrayList<>();

        Derived(String str) {
            super(str);
            setStr(str);
        }

        @Override
        void setStr(String str) {
            super.setStr(str);
            str2 = str;
            STATIC_STR2 = str;
            list2.add(str);     // Crash - list2 not initialized if setStr() called from Base constructor.
        }

        @Override
        void dump() {
            super.dump();
            System.out.println("Derived ");
            System.out.println("  STATIC_STR2=" + STATIC_STR2);
            System.out.println("         str2=" + str2);
            System.out.println("        list2=" + Arrays.toString(list1.toArray()));
            System.out.println(" ");
        }
    }

    public static void test1() {
        Base base1 = new Base("TEST1");
        base1.dump();

        Derived derived2 = new Derived("TEST2");
        derived2.dump();
    }

// =================================================================================

    static class  FooBase {
        FooBase() {
            doFoo();
        }
        void doFoo() {
        }
    }

    static class FooDerived extends FooBase {
        private final  List<String> list = new ArrayList<>();

        @Override
        void doFoo() {
            list.add("crash");    // Crashes because list is null.
        }
    }

    public static void test2() {
        FooDerived fooDerived = new FooDerived();
    }
}
