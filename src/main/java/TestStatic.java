public class TestStatic {
    public static class Applog {
        public  static void d(String msg) {
            System.out.println("Applog.d " + msg);
        }
        public  static void w(String msg) {
            System.out.println("Applog.w " + msg);
        }
        public  static void e(String msg) {
            System.out.println("Applog.e " + msg);
        }
    }

    // static derived class.
    public static class NoLog extends Applog {
        public  static void d(String msg) {
            System.out.println("NoLog.d " + msg);
        }
        public  static void w(String msg) {
            System.out.println("NoLog.w " + msg);
        }
        public  static void e(String msg) {
            System.out.println("NoLog.e " + msg);
        }
    }
    public static class YesLog extends Applog {
        public  static void d(String msg) {
            System.out.println("YesLog.d " + msg);
        }
        public  static void w(String msg) {
            System.out.println("YesLog.w " + msg);
        }
        public  static void e(String msg) {
            System.out.println("YesLog.e " + msg);
        }
    }

    private static void passLog(Applog log1, Applog log2) {
        log1.d("log1");
        log2.d("log2");

    }
    public static void test1() {
        // Get warning if you don't assign a value even thou static and no data in class.
        final Applog DEBUG1_FOO = new NoLog();
        // Get warning about accessing static method via instance
        DEBUG1_FOO.d( "debug1");

        final Applog DEBUG2_FOO = new YesLog();
        DEBUG2_FOO.d( "debug2");

        passLog(DEBUG1_FOO, DEBUG2_FOO);
    }
}
