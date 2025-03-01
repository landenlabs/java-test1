import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Dennis Lang on 2/22/17.
 */
public class TestJoda {


    // =================================================================================================================

    public static void TestJoda1() {

        DateTime dt00 = new DateTime(2017, 1, 2, 3, 0, 0, 0);

        testMinutes(dt00, 29);
        testMinutes(dt00, 30);
        testMinutes(dt00, 31);

        testMinutes(dt00, 59);
        testMinutes(dt00, 60);
        testMinutes(dt00, 61);
    }

    static void testMinutes(DateTime dt1, int minutes) {
        DateTime dt2 = dt1.plus(Period.minutes(minutes));
        Hours hoursBetween = Hours.hoursBetween(dt1, dt2);
        System.out.println("Hours between " + minutes + " hrs=" + hoursBetween);
        System.out.println("Hours between " + minutes + " hrs=" + hoursBetween.getHours());
        System.out.println("Hours between " + minutes + " min=" + hoursBetween.toStandardMinutes());

        Minutes minutesBetween = Minutes.minutesBetween(dt1, dt2);
        System.out.println("Minutes between " + minutes + " min=" + minutesBetween);
        System.out.println("Minutes between " + minutes + " hrs=" + minutesBetween.dividedBy(60));
        System.out.println("Minutes between " + minutes + " hrs=" + Math.round(minutesBetween.getMinutes()/60.0f));

        System.out.println();
    }


    // =================================================================================================================

    public static void TestJoda2() {

        DateTime today = new DateTime().withTimeAtStartOfDay();
        for (int hour = 0; hour <= 48; hour++) {
            DateTime dateTime = today.plusHours(hour);
            Date date = dateTime.toDate();
            System.out.printf("Hour:%2d %-26s %s\n", hour, date.toLocaleString(), getDay(date));
        }
    }

    public static String getDay(Date date) {
        return isTomorrow(date) ? "Tomorrow" : "Today";
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return new DateTime(date1).withTimeAtStartOfDay().isEqual(new DateTime(date2).withTimeAtStartOfDay());
    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static boolean isToday(long when) {
        return (new DateTime(when).toLocalDate()).equals(new LocalDate());
    }

    public static boolean isTomorrow(Date date) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
        DateTime secondDay = today.plusDays(2).withTimeAtStartOfDay();
        // return tomorrow.isBefore(date.getTime()+1) && secondDay.isAfter(date.getTime());
        DateTime dateTime = new DateTime(date);
        // date >= tomorrow and  date < secondDay
        return !dateTime.isBefore(tomorrow) && dateTime.isBefore(secondDay);
    }

    public static boolean isEqualorAfter(DateTime dt1, DateTime dt2) {
        return !dt1.isBefore(dt2);  // same as dt1.isEqual(dt2) || dt1.isAfter(dt2);
    }

    // =================================================================================================================

    public static void TestJoda3() {

        boolean is24HourFormat = true;
        TimeZone timeZone= TimeZone.getDefault();
        DateTime now = DateTime.now();
        int nowHour = now.hourOfDay().get();
        int endOffset = 2;

        System.out.printf("Today hour=%d, end offset hours=%d\n\n", nowHour, endOffset);
        System.out.printf("%8s %s\n", "Beg Hour", "Range");

        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime startDt = today;
        for (int hour = nowHour; hour <= 48; hour++) {
            startDt = today.plusHours(hour);
            DateTime endDt = today.plusHours(hour+endOffset);
            System.out.printf("%8d %s\n",
                     hour,
                    getFormatedAlertDuration(is24HourFormat, startDt.toDate(), endDt.toDate(), timeZone)
                    );
        }
    }

    public static String getFormatedAlertDuration(boolean is24HourFormat,
           Date startDate, Date endDate, TimeZone zone) {
        if (startDate != null && endDate != null && !endDate.equals(startDate)) {

            DateTime now = DateTime.now();
            DateTime today = new DateTime().withTimeAtStartOfDay();
            DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
            DateTime secondDay = today.plusDays(2).withTimeAtStartOfDay();

            if (startDate == null && endDate == null)
                return "";

            if (startDate == null)
                startDate = today.toDate();
            if (endDate == null)
                endDate = today.toDate();

            long durationMillis = endDate.getTime() - startDate.getTime();
            DateTime startDT = new DateTime(startDate);
            DateTime endDT = new DateTime(endDate);

            // 24 hours = H:mm   0:00 to 23:59
            // 24 hours = k:mm   1:00 to 24:59
            String hhmmStr = is24HourFormat ? "H:mm" : "h:mmaa";
            final SimpleDateFormat hourMinAm = new SimpleDateFormat(hhmmStr);      // 1:20pm  11:20am
            final SimpleDateFormat dayFmt = new SimpleDateFormat("EEE");

            SimpleDateFormat startFmt = hourMinAm;
            SimpleDateFormat endFmt = hourMinAm;
            String startPrefix = "";
            String endPrefix = "";

            if (isToday(startDate) && isToday(endDate)) {
                //  1:00p - 3:00pm
            } else {
                startPrefix =
                        (isToday(startDate) ? "Today" :
                                (isTomorrow(startDate) ? "Tomorrow" : "")).toLowerCase();
                endPrefix =  (
                        isToday(endDate) ? "Today" :
                                (isTomorrow(endDate) ? "Tomorrow" : "")).toLowerCase();
                if (!startDT.isBefore(secondDay)) { // startDT >= secondDay
                    startPrefix = dayFmt.format(startDate);
                }
                if (!endDT.isBefore(secondDay)) {   // endDT >= secondDay
                    endPrefix = dayFmt.format(endDate);
                }
            }

            if (startDT.isBefore(now) ){
                if (isToday(endDate))
                    endPrefix = "";
                startFmt = null;
                endPrefix = ("Until").toLowerCase() + " " + endPrefix;
            }
            else if (Duration.millis(durationMillis).getStandardHours() <= 1) {
                endFmt = null;
            }

            String startStr = "";
            String endStr = "";
            if (startFmt != null) {
                startFmt.setTimeZone(zone);
                startStr =  startPrefix + " " + startFmt.format(startDate).toLowerCase();
            }
            if (endFmt != null) {
                endFmt.setTimeZone(zone);
                endStr = endPrefix  + " " +  endFmt.format(endDate).toLowerCase();
            }

            String sepStr =  (startStr.isEmpty() || endStr.isEmpty()) ? "" : " - ";
            return (startStr.trim() + sepStr + endStr.trim()).replaceAll(" +", " "); // fix multiple spaces
        }

        return "";
    }


    // =================================================================================================================
    private static Date parseDate(DateTimeFormatter formatDate, String dateStr) {

        try {
            // Remove any trailing Thh:mm:ssZ
            dateStr = dateStr.replaceAll("T.*", "");
            return formatDate.parseDateTime(dateStr).toDate();
        } catch (Exception e) {
            System.err.println("Failed to parse date " + e.getMessage() + e.getCause());
            return null;
        }
    }

    private static Date parseDate(DateFormat formatDate, String dateStr) {

        try {
            return formatDate.parse(dateStr);
        } catch (Exception e) {
            System.err.println("Failed to parse date " + e.getMessage() + e.getCause());
            return null;
        }
    }

    public static TimeZone getTimeZone(float timeZoneOffsetInHours) {
        String sign = timeZoneOffsetInHours < 0.0F?"":"+";
        int hours = (int)timeZoneOffsetInHours;
        int mins = (int)((timeZoneOffsetInHours - (float)hours) * 60.0F);
        String tzStr = String.format("GMT%s%d:%02d", new Object[]{sign, Integer.valueOf(hours), Integer.valueOf(Math.abs(mins))});
        TimeZone tz = TimeZone.getTimeZone(tzStr);
        return tz;
    }

    public static void TestJoda4() {

        if (false) {

            DateFormat REPORT_LOCAL_DATE_FORMAT = new SimpleDateFormat("M/d/yyyy h:m:ss a",
                    Locale.US);

            String[] date2Strings = {
                    "1/27/2018 12:00:00 AM",
                    "1/27/2018 1:00:00 AM",
                    "1/27/2018 11:00:00 AM",

                    "1/27/2018 12:00:00 PM",
                    "1/27/2018 1:00:00 PM",
                    "1/27/2018 11:00:00 PM",
            };

            TimeZone tz = getTimeZone(-6);  // Central time.
            REPORT_LOCAL_DATE_FORMAT.setTimeZone(tz);

            for (String dateString : date2Strings) {

                System.out.println("   Input=" + dateString);
                Date date = parseDate(REPORT_LOCAL_DATE_FORMAT, dateString);
                System.out.println("  Parsed=" + REPORT_LOCAL_DATE_FORMAT.format(date) + " time=" + date.getTime() / 3600000);
                System.out.println();
            }
        }

        if (true) {

        //    DateTimeFormatter FORMATTER_HEADLINE_DATE = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateFormat RPC822_TZ_LOC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
            DateFormat RPC822_TZ_GMT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
            DateFormat RPC822_TZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            TimeZone GMT = TimeZone.getTimeZone("GMT");
            RPC822_TZ_GMT_DATE_FORMAT.setTimeZone(GMT);
            RPC822_TZ_DATE_FORMAT.setTimeZone(GMT);


            String[] dateStrings = {
                "2018-01-26T0:00:00 -0400",
                "2018-01-26T0:00:00 -0500",
                "2018-01-26T0:00:00 -0600",

                "2018-01-26T4:00:00Z",
                "2018-01-26T5:00:00Z",
                "2018-01-26T6:00:00Z",
            };

            DateTimeFormatter isoTimeGMT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss Z");
            //        .withLocale(Locale.ROOT)
            //        .withChronology(ISOChronology.getInstanceUTC());

            // System.setProperty("user.timezone", "CST");
            // System.setProperty("user.timezone", "GMT-0600");
            // System.out.println("Local timezone=" + System.getProperty("user.timezone"));
            // TimeZone.setDefault(TimeZone.getTimeZone("GMT-0600"));
            System.out.println("Local timezone=" + TimeZone.getDefault().getRawOffset() / 3600000);


            for (String dateString : dateStrings) {

                System.out.println("   Input=" + dateString);

                if (false) {
                    System.out.println("Joda");
                    DateTime dt = isoTimeGMT.parseDateTime(dateString);
                    System.out.println("  Parsed=" + isoTimeGMT.print(dt));
                    System.out.println("  Parsed=" + RPC822_TZ_GMT_DATE_FORMAT.format(dt.toDate()));
                }

                System.out.println("Java");
                Date date1 = null;
                // date1 = parseDate(RPC822_TZ_LOC_DATE_FORMAT, dateString);
                if (date1 == null) {
                    date1 = parseDate(RPC822_TZ_DATE_FORMAT, dateString);
                }

                System.out.println("  ParsedGMT=" + RPC822_TZ_GMT_DATE_FORMAT.format(date1));
                System.out.println("  ParsedLOC=" + RPC822_TZ_LOC_DATE_FORMAT.format(date1));

                DateTime dt = new DateTime(date1);
                DateTime dtUtc = new DateTime(date1, DateTimeZone.UTC);
                System.out.println("         dt=" + isoTimeGMT.print(dt));
                System.out.println("      dtUtc=" + isoTimeGMT.print(dt));


            // System.out.println("\nDateTruncation");
            DateTime day1 = dt.withTimeAtStartOfDay();
            System.out.println("  withTimeAtStartOfDay=" + isoTimeGMT.print(day1));
            DateTime day2 = dt.withTime(0, 0, 0, 0);
            System.out.println("     withTime(0,0,0,0)=" + isoTimeGMT.print(day2));


                System.out.println("===================");
            }
        }

    }


    public static void TestJoda5() {
        int tzHourOffset = TimeZone.getDefault().getRawOffset() / 3600000;
        System.out.println("Local TZ hr offset=" + tzHourOffset);

        DateFormat RPC822_TZ_LOC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        TimeZone GMT = TimeZone.getTimeZone("GMT");


        String tzFmt = "2018-01-26T0:00:00 %03d00";
        String date1Str = String.format(tzFmt, tzHourOffset-1); // Before TZ
        String date2Str = String.format(tzFmt, tzHourOffset+1); // After TZ

        System.out.println("\nJoda date manipulation occurs in local TZ");
        System.out.println("Manipulation can cause day to shift");

        try {
            DateTime date1 = new DateTime(RPC822_TZ_LOC_DATE_FORMAT.parse(date1Str));
            DateTime date2 = new DateTime(RPC822_TZ_LOC_DATE_FORMAT.parse(date2Str));
            DateTime day1 = date1.withTimeAtStartOfDay();
            DateTime day2 = date2.withTimeAtStartOfDay();
            System.out.println("  Before TZ Day1=" + day1);
            System.out.println("  After  TZ Day2=" + day2);
        } catch (Exception e) {
            System.err.println("Failed to parse date " + e.getMessage() + e.getCause());
        }

        System.out.println("\nJoda date set to UTC avoids TZ shift when manipulating");
        try {
            DateTime date1 = new DateTime(RPC822_TZ_LOC_DATE_FORMAT.parse(date1Str), DateTimeZone.UTC);
            DateTime date2 = new DateTime(RPC822_TZ_LOC_DATE_FORMAT.parse(date2Str), DateTimeZone.UTC);
            DateTime day1 = date1.withTimeAtStartOfDay();
            DateTime day2 = date2.withTimeAtStartOfDay();
            System.out.println("  Before TZ Day1=" + day1);
            System.out.println("  After  TZ Day2=" + day2);
        } catch (Exception e) {
            System.err.println("Failed to parse date " + e.getMessage() + e.getCause());
        }

    }


    public static boolean inTimeRange1(DateTime time,  Date timeStart, Date timeEnd) {
        return time.getMillis() >= timeStart.getTime() && time.getMillis() <= timeEnd.getTime();
    }

    public static boolean inTimeRange2(DateTime time, Date timeStart, Date timeEnd) {
        DateTime start = new DateTime(timeStart);
        DateTime end = new DateTime(timeEnd).plusMillis(1);
        if (checkInterval(start, end)) {
            Interval interval = new Interval(start, end);
            return interval.contains(time);
        }
        return false;
    }
    public static boolean checkInterval(Date start, Date end) {
        return checkInterval(new DateTime(start), new DateTime(end));
    }

    public static boolean checkInterval(DateTime start, DateTime end) {
        return end.isAfter(start);
    }

    public static void testRange(int i1, int i2, int i3) {
        Date date = new Date();
        DateTime dt1 = new DateTime(date).plus(i1);
        Date  dt2 = new DateTime(date).plus(i2).toDate();
        Date dt3 = new DateTime(date).plus(i3).toDate();
        if (inTimeRange1(dt1, dt2, dt3) != inTimeRange2(dt1, dt2, dt3)) {
            System.out.printf("Differ %d, %d, %d\n", i1, i2, i3);
        } else {
            System.out.printf("%b %d, %d, %d\n", inTimeRange1(dt1, dt2, dt3), i1, i2, i3);
        }
    }
    public static void TestJoda6() {
        DateFormat RPC822_TZ_GMT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        TimeZone GMT = TimeZone.getTimeZone("GMT");
        RPC822_TZ_GMT_DATE_FORMAT.setTimeZone(GMT);

        testRange(0, 0, 0);
        testRange(1, 2, 3);
        testRange(1, 1, 3);
        testRange(3, 1, 3);
        testRange(2, 1, 3);
        testRange(2, 3, 1);
        testRange(2, 2, 2);
    }

    public static void testJoda7() {


        String[] dateStrs = new String[] {
                "Sat, 28 May 2016 08:46:40 Z",      // Z converted to GMT
                "Sat, 28 May 2016 08:46:40 UT",     // UT converted to GMT
                "Sat, 28 May 2016 08:46:40 GMT",
                "Sat, 28 May 2016 08:46:40 EDT",
                "Sat, 28 May 2016 08:46:40 EST",
                "Sat, 28 May 2016 08:46:40 CDT",
                "Sat, 28 May 2016 08:46:40 CST",
                "Sat, 28 May 2016 08:46:40 MDT",
                "Sat, 28 May 2016 08:46:40 MST",
                "Sat, 28 May 2016 08:46:40 PDT",
                "Sat, 28 May 2016 08:46:40 PST",
                "Sat, 28 May 2016 08:46:40 +0400",  // need to parse with Z not z
                "Sat, 28 May 2016 08:46:40 +04:00", // need to parse with Z not z
                "Sat, 28 May 2016 08:46:40 -0800",  // need to parse with Z not z
        };
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss z");

        for (String dateStr : dateStrs) {
            try {
                DateTime date = parseDateZ(dateStr);
                System.out.println(dateStr + " parsed=" + dateFmt.print(date));
            } catch (Exception ex) {
                System.out.println(dateStr + " exception=" + ex.getMessage() + " " + ex.getCause());
            }
        }

    }

    public static void testJoda8() {
        DateTime now = DateTime.now();
        DateTime before = now.minusDays(2);

        System.out.println(" diff now, before=" + Days.daysBetween(now, before).getDays()); // = -2
        System.out.println(" diff before, now=" + Days.daysBetween(before, now).getDays()); // = +2
    }

    // -----------------------------------------------------------------------------------------------------------------
    static DateTime parseDateZ(String pubDate) {
        DateTime date;

        if (pubDate.matches("[1-2][0-9]+-[0-1][0-9]-[0-3][0-9] [0-9:]+")) {
            date = parseDate(pubDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            String pubDateLocalized = pubDate.replaceAll(" Z$", " GMT").replaceAll(" UT$", " GMT");

            DateTimeFormatter dateFmt;
            if (pubDateLocalized.matches("[A-Za-z]+, [0-9]+ [A-Za-z]+ 20[0-9][0-9].*")) {
                // date = Sat, 18 Jun 2016 13:25:00 +0000
                // fmt  = EEE, dd MMM yyyy HH:mm:ss z
                dateFmt = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss Z"); // lowercase 'z' does not work for 4 digit offset
            } else {
                // date = Sat, 18 Jun 16 13:25:00 +0000
                // fmt  = EEE, dd MMM yy HH:mm:ss z
                dateFmt = DateTimeFormat.forPattern("EEE, dd MMM yy HH:mm:ss Z");
            }
            date = parseDate(pubDateLocalized, dateFmt);
        }

        return date;
    }

    static DateTime parseDate(String date, DateTimeFormatter formatter) {
        DateTime result = null;
        // try {
            result = formatter.parseDateTime(date);
        // } catch (Exception ex) {
        //     System.out.println("Cannot parse date from string:"+  date + "\n" + ex.getMessage());
        // }
        return result;
    }
}
