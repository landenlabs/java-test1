/**
 * Created by ldennis on 4/27/17.
 */
public class CLogger {
    // FLogger flogger;
    String prefix = "abc";

    public void d(String msg) {
        FLogger.println(FLogger.F_DEBUG, prefix + msg);
    }

    public void e(String msg) {
        FLogger.println(FLogger.F_ERROR, prefix + msg);
    }

}
