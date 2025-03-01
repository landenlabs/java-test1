
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TestEnum {

    enum ColorsE {
        RED(0xff0000), GREEN(0x00ff00), BLUE(0x0000ff);

        int color;
        ColorsE(int color) {
            this.color = color;
        }

    }

    static class ColorsC {
        public static ColorsC RED = new ColorsC(0xff0000);
        public static ColorsC GREEN = new ColorsC(0x00ff00);
        public static ColorsC BLUE = new ColorsC(0x0000ff);

        public static ColorsC[] values = new ColorsC[] { RED, GREEN, BLUE };

        int color;
        public ColorsC(int color) {
            this.color = color;
        }

        public static List<ColorsC> getValues() {
            List<ColorsC> values = new ArrayList<>();
            for (Field field : ColorsC.RED.getClass().getDeclaredFields()) {
                ColorsC c = new ColorsC(0);
                try {
                    values.add((ColorsC) field.get(c));
                } catch (Exception ex) {
                    // System.out.println("Exception=" + ex.getMessage());
                }
            }
            return values;
        }

    }

    static public void test1() {

        for (ColorsE color : ColorsE.values()) {
            System.out.println("ColorE " + color.name() + " value=" + color.color);
        }

        for (ColorsC color : ColorsC.values) {
            Class clazz =  color.getClass();
            System.out.println("ColorC " + color.getClass().getName() + " value=" + color.color);
        }

        for (ColorsC color : ColorsC.getValues() ) {
            Class clazz =  color.getClass();
            System.out.println("ColorC " + color.getClass().getName() + " value=" + color.color);
        }

        System.out.println("[TestEnum]");
    }

    static interface enum2 {
        static int rain2 = 1;
        static int cloudy2 = 2;
        static int clear2 = 3;
        int value();
    }

    @interface enum3 {
        static int rain3 = 1;
        static int cloudy3 = 2;
        static int clear3 = 3;
    }

    static final int rain4 = 1;
    static final int cloudy4 = 2;
    static final int clear4 = 3;
    // @IntDef({rain4, clear4, clear4})
    @Retention(RetentionPolicy.SOURCE)
    @interface enum4 {
    }

    // Does not really work
    private static void test2_enum2(enum2  value) {
        switch (value.value()) {    // <---- can't get at value without method.
            case enum2.rain2:
                System.out.println(value);
                break;
            case enum2.clear2:
                System.out.println(value);
                break;
            case enum2.cloudy2:
                System.out.println(value);
                break;
        }
    }

    private static void test2_enum3(@enum3 int value) {
        switch (value) {
            case enum3.rain3:
                System.out.println(value);
                break;
            case enum3.clear3:
                System.out.println(value);
                break;
            case enum3.cloudy3:
                System.out.println(value);
                break;
        }
    }

    private static void test2_enum4(@enum4 int value) {
        switch (value) {
            case rain4:
                System.out.println(value);
                break;
            case clear4:
                System.out.println(value);
                break;
            case cloudy4:
                System.out.println(value);
                break;
        }
    }

    static public void test2() {
        // test2_enum2(enum2.rain2);    // Can't call
        test2_enum3(enum3.rain3);
        test2_enum4(rain4);
    }
}
