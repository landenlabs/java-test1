
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestTime {

    public static void test1() {
        fromHoursToSeconds(10.0);
        fromHoursToSeconds(10.3);

        fromMinutesToSeconds(10.0);
        fromMinutesToSeconds(10.3);

        fromTo(WTimeUnit.FromUnits.fromHours, 10.6, WTimeUnit.ToUnits.toHours);
        fromTo(WTimeUnit.FromUnits.fromMinutes, 30.6, WTimeUnit.ToUnits.toHours);

        System.out.println("Milli = " + WTimeUnit.ToUnits.toMilli.fromHours(10.5));
        System.out.println("Milli = " + WTimeUnit.ToUnits.toMilli.fromMinutes(10.3*60));
        System.out.println("Milli = " + WTimeUnit.ToUnits.toMilli.fromSeconds(10.3*3600));

        double start = 10.3;
        {
            double milli = WTimeUnit.ToUnits.toMilli.fromHours(start);
            double hours = WTimeUnit.ToUnits.toHours.fromMillis(milli);
            System.out.println(String.valueOf(start) + " hours to milli back to hours = " + hours);
        }
        {
            long lngMilli = TimeUnit.HOURS.toMillis(Math.round(start));
            long lngHours = TimeUnit.MILLISECONDS.toHours(lngMilli);
            System.out.println(String.valueOf(start) + " hours to milli back to hours = " + lngHours);
        }

    }

    public static void fromTo(WTimeUnit.FromUnits fromUnits, double val, WTimeUnit.ToUnits toUnits) {
        long result = WTimeUnit.round(fromUnits, val, toUnits);
        System.out.println("round(From " + fromUnits + "  " + val + " " + toUnits + ") = " + result);
    }

    public static void fromHoursToSeconds(double hours) {

        double seconds1 = WTimeUnit.ToUnits.toSeconds.fromHours(hours);
        double seconds2 = WTimeUnit.FromUnits.fromHours.toSeconds(hours);
        double seconds3 = WTimeUnit.dbl(WTimeUnit.FromUnits.fromHours, hours, WTimeUnit.ToUnits.toSeconds);
        long seconds4 = WTimeUnit.round(WTimeUnit.FromUnits.fromHours, hours, WTimeUnit.ToUnits.toSeconds);
        long seconds5 = WTimeUnit.trunc(WTimeUnit.FromUnits.fromHours, hours, WTimeUnit.ToUnits.toSeconds);
        double seconds6 = WTimeUnit.fromHours(hours, WTimeUnit.ToUnits.toSeconds);
        double seconds7 = WTimeUnit.toSeconds(WTimeUnit.FromUnits.fromHours, hours);

        System.out.println(" Hours=" + hours);
        System.out.println("  Seconds=" + TimeUnit.HOURS.toSeconds((long)hours));

        System.out.println(String.format("  1 Seconds= %.4f", seconds1));
        System.out.println(String.format("  2 Seconds= %.4f", seconds2));
        System.out.println(String.format("  3 Seconds= %.4f", seconds3));
        System.out.println(String.format("  4 Seconds= %d", seconds4));
        System.out.println(String.format("  5 Seconds= %d", seconds5));
        System.out.println(String.format("  6 Seconds= %.4f", seconds6));
        System.out.println(String.format("  7 Seconds= %.4f", seconds7));
    }

    public static void fromMinutesToSeconds(double minutes) {

        double seconds1 = WTimeUnit.ToUnits.toSeconds.fromMinutes(minutes);
        double seconds2 = WTimeUnit.FromUnits.fromMinutes.toSeconds(minutes);
        double seconds3 = WTimeUnit.dbl(WTimeUnit.FromUnits.fromMinutes, minutes, WTimeUnit.ToUnits.toSeconds);
        long seconds4 = WTimeUnit.round(WTimeUnit.FromUnits.fromMinutes, minutes, WTimeUnit.ToUnits.toSeconds);
        long seconds5 = WTimeUnit.trunc(WTimeUnit.FromUnits.fromMinutes, minutes, WTimeUnit.ToUnits.toSeconds);
        double seconds6 = WTimeUnit.fromMinutes(minutes, WTimeUnit.ToUnits.toSeconds);
        double seconds7 = WTimeUnit.toSeconds(WTimeUnit.FromUnits.fromMinutes, minutes);

        System.out.println(" Minutes=" + minutes);
        System.out.println("  Seconds=" + TimeUnit.MINUTES.toSeconds((long)minutes));

        System.out.println(String.format("  1 Seconds= %.4f", seconds1));
        System.out.println(String.format("  2 Seconds= %.4f", seconds2));
        System.out.println(String.format("  3 Seconds= %.4f", seconds3));
        System.out.println(String.format("  4 Seconds= %d", seconds4));
        System.out.println(String.format("  5 Seconds= %d", seconds5));
        System.out.println(String.format("  6 Seconds= %.4f", seconds6));
        System.out.println(String.format("  7 Seconds= %.4f", seconds7));
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static void test2() {
        // <pubDate>Thu, 10 Feb 2022 04:05:55 -0800</pubDate>  = 4:05am PST / 7:05am EST
        // <pubDate>Thu, 10 Feb 2022 09:38:48 -0800</pubDate>  = 9:38am PST / 12:38pm EST

        Date date;
        date = parseRssDate("Thu, 10 Feb 2022 04:05:55 -0800");
        date = parseRssDate("Thu, 10 Feb 2022 09:38:48 -0800");
    }

    public static Date parseRssDate(String pubDate) {
        // "2017-07-06 16:39:12" or "Thu, 06 Jul 2017 16:39:12"
        // or "Thu, 06 Jul 17 16:39:12"
        Date date;

        if (pubDate.matches("[1-2][0-9]+-[0-1][0-9]-[0-3][0-9] [0-9:]+")) {
            date = parseDate(pubDate, "yyyy-MM-dd HH:mm:ss");
        } else {
            String pubDateLocalized = pubDate.replaceAll(" Z$", " +0000").replaceAll(" UT$", " +0000");

            String dateFormat;
            String alternativeDateFormat;
            if (pubDateLocalized.matches("[A-Za-z]+, [0-9]+ [A-Za-z]+ 20[0-9][0-9].*")) {
                // date = Sat, 18 Jun 2016 13:25:00 +0000
                // fmt  = EEE, dd MMM yyyy HH:mm:ss z
                dateFormat ="EEE, dd MMM yyyy HH:mm:ss z";
                alternativeDateFormat = "EEE, dd MMM yyyy HH:mm:ss a";
            } else {
                // date = Sat, 18 Jun 16 13:25:00 +0000
                // fmt  = EEE, dd MMM yy HH:mm:ss z
                dateFormat = "EEE, dd MMM yy HH:mm:ss z";
                alternativeDateFormat = "EEE, dd MMM yy HH:mm:ss a";
            }
            date = parseDate(pubDateLocalized, dateFormat, alternativeDateFormat, "EEE, dd MMM yyyy HH:mm:ss'Z'");
        }
        return date;
    }

    public static Date parseDate(String date, String... patterns) {
        if (patterns == null || patterns.length == 0) {
            // ALog.e.tag("You have to provide at least one pattern to parse date!");
            return null;
        }

        Date result;
        for (String pattern : patterns) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            try {
                result = format.parse(date);
                if (result != null) {
                    return result;
                }
            } catch (ParseException ex) {
                // ignore, try to parse with next pattern
            }
        }
        return null;
    }
}
