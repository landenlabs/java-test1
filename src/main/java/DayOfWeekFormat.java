import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ldennis on 3/30/17.
 */
public class DayOfWeekFormat {

    /*
    ENGLISH {
        @Override
        public String formatDay(Date date, TimeZone timeZone, boolean shortForm) {
            String result = formatDay(date, timeZone, shortForm, "en");
            if (!shortForm) {
                result = result.toUpperCase(Locale.getDefault());
            }
            return result;
        }
    } ,
    SPANISH {
        @Override
        public String formatDay(Date date, TimeZone timeZone, boolean shortForm) {
            String result = formatDay(date, timeZone, shortForm, "es");
            result = result.toUpperCase(Locale.getDefault());
            if (shortForm) {
                result = result.replaceAll("\\.", "");
            }
            return result;
        }
    };
    */

    /*
    private static final Map<String, DayOfWeekFormat> mapping = new HashMap<String, DayOfWeekFormat>();

    static {
        mapping.put("en", ENGLISH);
        mapping.put("es", SPANISH);
    }


    public abstract String formatDay(Date date, TimeZone timeZone, boolean shortForm);


    public static DayOfWeekFormat fromLocale(Locale locale) {
        DayOfWeekFormat result = mapping.get(locale.getLanguage());
        if (null == result) {
            result = ENGLISH;
        }
        return result;
    }
 */
    protected static String formatDay(Date date, TimeZone timeZone, boolean shortForm, String language) {
        String formatPattern = shortForm ? "EE" : "EEEE";
        SimpleDateFormat format = new SimpleDateFormat(formatPattern, new Locale(language));
        format.setTimeZone(timeZone);
        return  format.format(date).replaceAll("\\.", "");
    }
}
