/**
 * Created by ldennis on 4/27/17.
 */
final public class FLogger {
    public static final int F_DEBUG = 0;
    public static final int F_ERROR = 1;

    public static void d(String msg) {
        println(F_DEBUG, msg);
    }

    public static void e(String msg) {
        println(F_ERROR, msg);
    }

    public static void println(int level, String msg) {
        switch (level) {
            case F_DEBUG:
                System.out.println(msg);
                break;
            case F_ERROR:
                System.err.println(msg);
                break;
        }
    }
}
