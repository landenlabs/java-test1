import java.util.*;

/**
 * Created by ldennis on 3/22/17.
 */
public class TestInterface {

    public interface I1 {
        void foo1();
    }
    public interface  I2 {
        void foo2();
    }
    public static class C1 implements  I1, I2 {
        @Override
        public void foo1() {
        }
        @Override
        public void foo2() {
        }
    }

    private static void takeC1list(Set<C1> list)  {
    }

    private static void takeI1list(Set<? extends I1> list)  {
    }

    private static void takeI2list(Set<? extends I2> list)  {
    }
    private static void takeI1(I1 item)  {
    }
    private static void takeI2(I2 item)  {
    }

    public static void Test1() {

        Map<C1, String> map1 = new HashMap<>();
        Map<I1, String> map2 = new HashMap<>();
        Map<I2, String> map3 = new HashMap<>();

        C1 c1 = new C1();
        C1 c2 = new C1();
        C1 c3 = new C1();

        map1.put(c1, "c1");
        map1.put(c2, "c2");
        map1.put(c3, "c3");

        map2.put(c1, "c1");
        map2.put(c2, "c2");
        map2.put(c3, "c3");

        map3.put(c1, "c1");
        map3.put(c2, "c2");
        map3.put(c3, "c3");

        takeI1(c1);
        takeI2(c1);
        takeC1list(map1.keySet());
        takeI2list( map1.keySet());

        System.out.println("\nInterface I1 mapping to class");
        for (I1 i1 : map2.keySet()) {
            String gotForI1 = map1.get((C1) i1);
            System.out.println("Map found for " + map2.get(i1) + "=" + gotForI1);
        }

        System.out.println("\nInterface I2 mapping to class");
        for (I2 i2 : map3.keySet()) {
            String gotForI2 = map1.get((C1) i2);
            System.out.println("Map found for " + map3.get(i2) + "=" + gotForI2);
        }
    }
}
