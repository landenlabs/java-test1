import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class TestDate {

	static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static void TestDate10() {
		DateTime dayOfWeek;
		boolean shortForm = true;
		TimeZone tz = GMT;

		Locale xx = Locale.ENGLISH;

		Locale[]  locales = new Locale[] {
			Locale.US,
			Locale.forLanguageTag("es"),
			Locale.FRENCH,
			Locale.ITALIAN,
			Locale.GERMAN,
			Locale.CHINESE,
		};

		for (Locale locale : locales) {
			System.out.printf("%5s ", locale.toLanguageTag());
			String language = locale.toLanguageTag();

			for (int dayOfWk = 1; dayOfWk <= 7; dayOfWk++) {
				dayOfWeek = new DateTime().withDayOfWeek(dayOfWk);
				Date date = dayOfWeek.toDate();
				String dowStr2 = DayOfWeekFormat.formatDay(date, tz, shortForm, language);

				String formatPattern = shortForm ? "EE" : "EEEE";
				SimpleDateFormat format = new SimpleDateFormat(formatPattern, new Locale(language));
				format.setTimeZone(tz);
				String dowStr3 = format.format(date);
				System.out.printf("  %4.4s ", dowStr2);
			}
			System.out.printf(" %s\n", locale.getDisplayLanguage());
		}

		System.out.println();

		for (Locale locale : locales) {
			System.out.printf("%5s ", locale.toLanguageTag());
			String language = locale.toLanguageTag();

			for (int monOfYr = 1; monOfYr <= 12; monOfYr++) {
				Date date = new DateTime().withMonthOfYear(monOfYr).toDate();
				String formatPattern = shortForm ? "MMM" : "MMMM";
				SimpleDateFormat format = new SimpleDateFormat(formatPattern, new Locale(language));
				format.setTimeZone(tz);
				String dateStr = format.format(date).replace(".", "");
				System.out.printf("%4.4s ", dateStr);
			}
			System.out.printf(" %s\n", locale.getDisplayLanguage());
		}

		System.out.println();
		shortForm = false;
		for (Locale locale : locales) {
			System.out.printf("%5s ", locale.toLanguageTag());
			String language = locale.toLanguageTag();

			for (int monOfYr = 1; monOfYr <= 12; monOfYr++) {
				Date date = new DateTime().withMonthOfYear(monOfYr).toDate();
				String formatPattern = shortForm ? "MMM" : "MMMM";
				SimpleDateFormat format = new SimpleDateFormat(formatPattern, new Locale(language));
				format.setTimeZone(tz);
				String dateStr = format.format(date).replace(".", "");
				System.out.printf("%9.9s ", dateStr);
			}
			System.out.printf(" %s\n", locale.getDisplayLanguage());
		}
	}

	public static void TestDate9() {
	    // Parse iso 8601

		TryAndParseDate("2017-01-13 19:37:00");
		TryAndParseDate("2017-01-13 19:37:00+01");      // +hh hour timezone offset
        TryAndParseDate("2017-01-13 19:37:00+02:03");   // +hh:mm  hour and minute timezone offset
		TryAndParseDate("2017-01-13 19:37:00+0000");
		TryAndParseDate("2017-01-13 19:37:00 GMT");
	}

	public static void TryAndParseDate(String date1Str) {
		ParseDateCell("yyyy-MM-dd HH:mm:ssX", date1Str);
		ParseDateCell("yyyy-MM-dd HH:mm:ssZ", date1Str);
	}

	public static void ParseDateCell(String dateFmtStr, String dateStr) {

		DateFormat df1 = new SimpleDateFormat(dateFmtStr, Locale.US);
		try {
			Date result1 = df1.parse(dateStr);
			System.out.printf("RawStr %s\t  Date=%s\t  Fmt=%s\n",
					dateStr, result1.toString(), dateFmtStr);
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + " with " + dateFmtStr);
		}
	}

	public static void TestDate8() {
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 +0400");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 +0430");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 +0000");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 -0000");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 -0400");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 -0430");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 CST");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 EDT");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 EST");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 Z");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 UT");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 AM");
		ParseDateRFC882("Tue, 03 Jan 2017 06:01:01 PM");

		System.out.println("[ParseDateRFC882 Done]");
	}


	public static void ParseDateRFC882(String isoDateStr) {
		try {
			DateFormat df1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US);
			String modIsoDateStr = isoDateStr.replaceAll("Z$", "+0000").replaceAll("UT$", "+0000");
			Date result1 = df1.parse(modIsoDateStr);

			DateFormat outDf1 = new SimpleDateFormat("h:mma");

			System.out.printf("RawStr %s\t  isoDate=%s\t  h:mma=%s\n",
					isoDateStr, result1.toString(), outDf1.format(result1));

			// DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			// String string2 = "2001-07-04T12:08:56.235-07:00";
			// Date result2 = df2.parse(isoDateStr);
			// System.out.println("isoDate1 = " + result1.toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}


	public static void TestDate7() {
		DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Calendar calNow =  Calendar.getInstance();
		Date dateNow = new Date();
		Calendar calUtc = Calendar.getInstance(GMT);
		Date dateUtc = new Date(System.currentTimeMillis());

		System.out.printf(" Cal Lcl Year=%d, Month=%d, Day=%d, Hour=%d\t",
				calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH),
				calNow.get(Calendar.DAY_OF_MONTH), calNow.get(Calendar.HOUR_OF_DAY));
		System.out.println(" Cal Now=" + ISO8601_DATE_FORMAT.format(calNow.getTime()) + " " + calNow.getTimeInMillis());
		System.out.printf("Date Lcl Year=%d, Month=%d, Day=%d, Hour=%d\t",
				dateNow.getYear()+1900, dateNow.getMonth(), dateNow.getDate(), dateNow.getHours());
		System.out.println("Date Now=" + ISO8601_DATE_FORMAT.format(dateNow.getTime()) + " " + dateNow.getTime());

		System.out.println();


		ISO8601_DATE_FORMAT.setTimeZone(GMT);

		System.out.printf(" Cal UTC Year=%d, Month=%d, Day=%d, Hour=%d\t",
				calUtc.get(Calendar.YEAR), calUtc.get(Calendar.MONTH),
				calUtc.get(Calendar.DAY_OF_MONTH), calUtc.get(Calendar.HOUR_OF_DAY));
		System.out.println(" Cal UTC=" + ISO8601_DATE_FORMAT.format(calUtc.getTime()) + " " + calUtc.getTimeInMillis());

		System.out.printf("Date Lcl Year=%d, Month=%d, Day=%d, Hour=%d\t",
				dateUtc.getYear()+1900, dateUtc.getMonth(), dateUtc.getDate(), dateUtc.getHours());
		System.out.println("Date UTC=" + ISO8601_DATE_FORMAT.format(dateUtc.getTime()) + " " + dateUtc.getTime());
		System.out.println(" ** Date always returns parts in local time");

		System.out.println("[Done - TestDate7]\n");
	}

	public static void TestDate6() {
		DateTest6("2016-03-12T06:01:01-0500");
		DateTest6("2016-03-13T07:01:01-0400");

		DateTest6("2016-03-11T06:01:01-0300");  // Brasilia Brasil, Friday sunrise
		DateTest6("2016-03-12T06:01:01-0300");  // Brasilia Brasil, Friday sunrise
		DateTest6("2016-03-13T06:01:01-0300");  // Brasilia Brasil, Friday sunrise

		DateTest6("2016-03-13T06:01:01-0100"); 
		DateTest6("2016-03-13T06:01:01-0300"); 
		DateTest6("2016-03-13T06:01:01-0500");


		DateTest6("2016-03-13T06:01:01+0000");
		DateTest6("2016-03-13T06:01:01-0000");
		DateTest6("2016-03-13T06:01:01 CST");
		DateTest6("2016-03-13T06:01:01 EDT");
		DateTest6("2016-03-13T06:01:01 EST");
		DateTest6("2016-03-13T06:01:01 Z");
		DateTest6("2016-03-13T06:01:01 UT");
		DateTest6("2016-03-13T06:01:01 AM");
		DateTest6("2016-03-13T06:01:01 PM");

		System.out.println("[Done]");
	}

	public static void DateTest6(String isoDateStr) {
		try {
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			DateFormat df2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
			// String string1 = "2001-07-04T12:08:56.235-0700";
			Date result1 = df1.parse(isoDateStr);
			DateFormat outDf1 = new SimpleDateFormat("h:mma");
			float  tzHroff = result1.getTimezoneOffset() /60.0f;

			System.out.printf("RawStr %s\t  isoDate=%s\t  h:mma=%s\t  hzHrOff=%.1f\n",
					isoDateStr, result1.toString(), outDf1.format(result1), tzHroff);

			// DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			// String string2 = "2001-07-04T12:08:56.235-07:00";
			// Date result2 = df2.parse(isoDateStr);
			// System.out.println("isoDate1 = " + result1.toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private long getTimeZoneOffset(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return date.getTimezoneOffset() * (int) Math.signum(cal.get(Calendar.ZONE_OFFSET));
	}
	public static void DateTest5() {

		Calendar caltest = Calendar.getInstance(GMT);
		// Calendar cal = Calendar.getInstance();
		caltest.set(Calendar.DAY_OF_MONTH, 1);
		Date testDate = caltest.getTime();



		long datMilli= testDate.getTime();
		long sysMilli = System.currentTimeMillis();
		TimeZone GMT = TimeZone.getTimeZone("GMT");
		// Returns date with cleared HOUR/MINUTE/SECOND/MILLISECOND fields.

		Calendar cal = Calendar.getInstance(GMT);
		// Calendar cal = Calendar.getInstance();
		cal.setTime(testDate);

		System.out.println("Cal GMT=" + cal.getTime().toGMTString());
		System.out.println("Dat GMT=" + testDate.toGMTString());
		System.out.printf("\n");

		long calMilli = cal.getTimeInMillis();
		System.out.printf("all cal milli= %12d\n", cal.getTimeInMillis());
		cal.set(Calendar.MILLISECOND, 0);
		// System.out.printf("ms0 cal milli= %12d %12d\n", cal.getTimeInMillis(), cal.getTimeInMillis()/1000*1000);
		cal.set(Calendar.SECOND, 0);
		// System.out.printf("sc0 cal milli= %12d %12d\n", cal.getTimeInMillis(), cal.getTimeInMillis()/60000*60000);
		cal.set(Calendar.MINUTE, 0);
		// System.out.printf("nn0 cal milli= %12d %12d\n", cal.getTimeInMillis(), cal.getTimeInMillis()/3600000*3600000);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		// System.out.printf("hr0 cal milli= %12d %12d\n", cal.getTimeInMillis(), cal.getTimeInMillis()/(24*360000)*(24*360000));



		long calDayMilli = cal.getTimeInMillis();
		cal.setTimeZone(GMT);
		calDayMilli = cal.getTimeInMillis();

		final long MIIL_PER_Day = 24* 60 * 60 * 1000;
		long datDayMilli = (datMilli / MIIL_PER_Day) * MIIL_PER_Day;
		long deltaMilli = datDayMilli - calDayMilli;

		System.out.printf("   Dat Day sec=%12d\n", datDayMilli / 1000);
		System.out.printf("-  Cal Day sec=%12d\n", calDayMilli / 1000);
		System.out.println("====================================");
		System.out.printf("              %12d\n", deltaMilli / 1000);

		System.out.printf("\n");
		System.out.println("Cal Day GMT=" + cal.getTime().toGMTString());
		// System.out.println("Cal Day Loc=" + cal.getTime().toLocaleString());

		Date dateDay = new Date(datMilli);
		System.out.println("Dat Day GMT=" + dateDay.toGMTString());

		dateDay = new Date(getDateWithoutHour(testDate));
		System.out.println("Now Day GMT=" + dateDay.toGMTString());
	}

	public static long getDateWithoutHour(Date date) {
		final long MILLISEC_PER_DAY = 24* 60 * 60 * 1000;
		return date.getTime() / MILLISEC_PER_DAY * MILLISEC_PER_DAY;
	}

	public  static void DateTest4() {
		TimeZone localTz = TimeZone.getTimeZone("GMT+7");
		// TimeZone localTz = TimeZone.getDefault();
		
		// Setting timezone has not effect if also setting millis.
		Calendar calendar1 = Calendar.getInstance(localTz);
		System.out.println("Hour1Tz=" + calendar1.get(Calendar.HOUR_OF_DAY));
        calendar1.setTimeInMillis(System.currentTimeMillis());
        System.out.println("Hour1Tz=" + calendar1.get(Calendar.HOUR_OF_DAY));
        Date date1 = calendar1.getTime();
        
        Calendar calendar2 = Calendar.getInstance();
        System.out.println("Hour2Tz=" + calendar2.get(Calendar.HOUR_OF_DAY));
        calendar2.setTimeInMillis(System.currentTimeMillis());
        System.out.println("Hour2Tz=" + calendar2.get(Calendar.HOUR_OF_DAY));
        Date date2 = calendar2.getTime();
        
        System.out.println("Date1 = " + date1.toLocaleString());
        System.out.println("Date2 = " + date2.toLocaleString());
        
        System.out.println("[DONE]");
	}

	public static class WSITimeZone {
		public float timeZoneOffsetHours;
		public boolean isDaylightSavings;
		public boolean observesDaylightSavings;

		public WSITimeZone(float offsetHr, boolean isDaylightSavings, boolean observesDaylightSavings) {
			this.timeZoneOffsetHours = offsetHr;
			this.isDaylightSavings = isDaylightSavings;
			this.observesDaylightSavings = observesDaylightSavings;
		}

		/**
		 * @return approximate timezone object given TZ offset and observation of daylight saving.
		 *         Uses New York city rules for daylight savings transition dates.
		 */
		public TimeZone getTimeZone() {
			// return ServiceUtils.getTimeZone(timeZoneOffsetHours);
			TimeZone tz = TimeZone.getTimeZone(observesDaylightSavings ? "America/New_York": "GMT");
			tz.setRawOffset((int)(timeZoneOffsetHours * 3600000));
			return tz;
		}
	}
	
    public static Date replaceHHMMSS1(Date fullDate, Date hhmmssDate) {
       
        Calendar calResult = Calendar.getInstance();
        calResult.setTime(fullDate);
        Calendar calTime = Calendar.getInstance();
        calTime.setTime(hhmmssDate);

        // calResult.setTimeZone(TimeZone.getTimeZone("GMT"));
        calTime.setTimeZone(calResult.getTimeZone());

        calResult.set(Calendar.HOUR, calTime.get(Calendar.HOUR));
        calResult.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
        calResult.set(Calendar.SECOND, calTime.get(Calendar.SECOND));

        return calResult.getTime();

    }
    public static Date replaceHHMMSS2(Date fullDate, Date hhmmssDate) {
        Calendar calyymmdd = Calendar.getInstance();
        calyymmdd.setTime(fullDate);
        Calendar calResult = Calendar.getInstance();
        calResult.setTime(hhmmssDate);

        // calResult.setTimeZone(TimeZone.getTimeZone("GMT-10:00"));
        calyymmdd.setTimeZone(calResult.getTimeZone());
        
        calResult.set(Calendar.DAY_OF_MONTH, calyymmdd.get(Calendar.DAY_OF_MONTH));
        calResult.set(Calendar.MONTH, calyymmdd.get(Calendar.MONTH));;
        calResult.set(Calendar.YEAR, calyymmdd.get(Calendar.YEAR));
        
        calResult.setTimeZone(TimeZone.getTimeZone("GMT"));
        return calResult.getTime();
    }
    
    public static Date replaceHHMMSS3(Date fullDate, Date hhmmssDate) {
		Date result = new Date(fullDate.getTime());
		result.setHours(hhmmssDate.getHours());
		result.setMinutes(hhmmssDate.getMinutes());
		result.setSeconds(hhmmssDate.getSeconds());
		return result;
    }
    
    public static Date replaceHHMMSS4(Date fullDate, Date hhmmssDate) {
		Date result = new Date(hhmmssDate.getTime());
		result.setDate(fullDate.getDate());
		result.setMonth(fullDate.getMonth());
		result.setYear(fullDate.getYear());
		return result;
    }
    
    public static Date replaceHHMMSS5(Date fullDate, float hourTzOffset, Date hhmmssDate) {
    	final long MILL_PER_DAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
    	long resultDays = TimeUnit.DAYS.convert(fullDate.getTime(), TimeUnit.MILLISECONDS);

  		Date result = new Date(resultDays * MILL_PER_DAY + hhmmssDate.getTime()) ;

  		return result;
  	}

	/**
	 * Replace time (hh:mm:ss) part of fullDate and return result.
	 *
	 * @param fullDate   - Full reference date which supplies year, month and day.
	 * @param hhmmssDate - Assumes only time part is populated.
	 * @return fullDate with its time replaced with timeDate (hh:mm:ss)
	 */
	public static Date replaceHHMMSS6(Date fullDate, Date hhmmssDate) {
		Calendar result = Calendar.getInstance();
		Calendar hoursMinutesSecondsCalendar = Calendar.getInstance();
		result.setTime(fullDate);
		hoursMinutesSecondsCalendar.setTime(hhmmssDate);
		result.set(Calendar.HOUR_OF_DAY, hoursMinutesSecondsCalendar.get(Calendar.HOUR_OF_DAY));
		result.set(Calendar.MINUTE, hoursMinutesSecondsCalendar.get(Calendar.MINUTE));
		result.set(Calendar.SECOND, hoursMinutesSecondsCalendar.get(Calendar.SECOND));
		return result.getTime();
	}

	public static void DateTest3() {
		DateFormat VALID_GMT_DATE_FORMAT = new SimpleDateFormat("M/d/yyyy h:m:ss a");
		VALID_GMT_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		DateFormat RFC822_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		DateFormat ISO8601_NOTZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat RISE_SET_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a",  Locale.US);
		
		DateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("M/d/yyyy h:m:ss a Z");
		DateFormat DISPLAY_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a");


		String[]  refDateStrs  = {
			"6/9/2019 1:00:00 AM",
			"6/9/2019 4:00:00 AM",
			"6/9/2019 7:00:00 AM",
			"6/9/2019 10:00:00 AM",
			"6/9/2019 1:00:00 PM",
			"6/9/2019 4:00:00 PM",
			"6/9/2019 7:00:00 PM",
			"6/9/2019 10:00:00 PM",
			"6/9/2019 11:59:00 PM",
			// "9/29/2015 12:00:00 AM"
		};

		System.out.printf(" %-16s %-30s %-24s  %20s  %20s  %20s  %20s\n",
				"TimeZone",  "Ref-Time(lcl)", "Ref-Time(GMT)", "5SunRise", "5SunSet", "6SunRise", "6SunSet");

		for (String refDateStr : refDateStrs)
		for (int hourOffset = -10; hourOffset < 12; hourOffset += 1) {
			String gmtTzStr = String.format("GMT%+d:00",  hourOffset);
			TimeZone timeZone = TimeZone.getTimeZone(gmtTzStr);
			RISE_SET_TIME_FORMAT.setTimeZone(timeZone);
			DISPLAY_DATE_FORMAT.setTimeZone(timeZone);
			
			Calendar cal = Calendar.getInstance();
			Date refDate = cal.getTime();
			
			// final String refDateStr = "9/29/2015 12:00:00 AM";
			try {
				refDate = VALID_GMT_DATE_FORMAT.parse(refDateStr);
			} catch (ParseException e) {
			}
	
	 
			int milliTzOffset = timeZone.getOffset(refDate.getTime());
			float hourTzOffset = milliTzOffset/1000/3600.0f;
		    System.out.printf(" %10.10s %5.1f", timeZone.getDisplayName(), hourTzOffset);
		    
		    // Sunrise="06:22:09 am" Sunset="06:21:08 pm" 
		    		
		    final String sunrise  = "06:22:00 am";
			final String sunset   = "08:21:00 pm";

			final Date sunRiseDt = parseAndDisplay(sunrise, RISE_SET_TIME_FORMAT, DISPLAY_DATE_FORMAT);
			final Date sunSetDt = parseAndDisplay(sunset, RISE_SET_TIME_FORMAT, DISPLAY_DATE_FORMAT);

			System.out.printf(" %-30s",  DISPLAY_DATE_FORMAT.format(refDate));
			System.out.printf(" %-24s",  VALID_GMT_DATE_FORMAT.format(refDate));
		/*
			Date sunRiseFullDt1 = replaceHHMMSS1(refDate, sunRiseDt);
			System.out.println("  SunRise:" + DISPLAY_DATE_FORMAT.format(sunRiseFullDt1));
			Date sunRiseFullDt2 = replaceHHMMSS2(refDate, sunRiseDt);
			System.out.println("  SunRise:" + DISPLAY_DATE_FORMAT.format(sunRiseFullDt2));
			
			Date sunRiseFullDt3 = replaceHHMMSS3(refDate), sunRiseDt);
			System.out.println("  SunRise:" + DISPLAY_DATE_FORMAT.format(sunRiseFullDt3));
			Date sunRiseFullDt4 = replaceHHMMSS4(refDate, sunRiseDt);
			System.out.println("  SunRise:" + DISPLAY_DATE_FORMAT.format(sunRiseFullDt4));
		*/


			Date sunRiseFullDt5 = replaceHHMMSS5(refDate, hourTzOffset, sunRiseDt);
			System.out.printf("  %20s",  DISPLAY_DATE_FORMAT.format(sunRiseFullDt5));
			Date sunSetFullDt5 = replaceHHMMSS5(refDate, hourTzOffset, sunSetDt);
			System.out.printf("  %20s",  DISPLAY_DATE_FORMAT.format(sunSetFullDt5));

			Date sunRiseFullDt6 = replaceHHMMSS6(refDate, sunRiseDt);
			System.out.printf("  %20s",  DISPLAY_DATE_FORMAT.format(sunRiseFullDt5));
			Date sunSetFullDt6 = replaceHHMMSS6(refDate, sunSetDt);
			System.out.printf("  %20s",  DISPLAY_DATE_FORMAT.format(sunSetFullDt5));

			if (sunRiseFullDt5 != sunRiseFullDt6) {
				if (DISPLAY_DATE_FORMAT.format(sunRiseFullDt5).equals(DISPLAY_DATE_FORMAT.format(sunRiseFullDt6))) {
					System.out.print("  *** ERROR - 5SunRise != 6SunRise");
				}
			}
			if (sunSetFullDt5 != sunSetFullDt6) {
				if (DISPLAY_DATE_FORMAT.format(sunSetFullDt5).equals(DISPLAY_DATE_FORMAT.format(sunSetFullDt6))) {
					System.out.print("  *** ERROR - 5SunSet != 6SunSet");
				}
			}
			System.out.println();
			// break;
		}
		
		System.out.println("[Done]");
	}
	
	
	public static void DateTest2() {
		DateFormat ISO8601_NOTZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal =  Calendar.getInstance();
		
		Date date = cal.getTime();
		
		System.out.println("Now=" + ISO8601_NOTZ_DATE_FORMAT.format(date));
		// System.out.println("Now(Local)=" + date.toLocaleString());
		// System.out.println("Now(GMT)=" + date.toGMTString());
		 
		final long MILLIS_PER_DAY = (24  * 60 * 60 * 1000);
		Date dayDate1 = new Date(date.getTime() / MILLIS_PER_DAY * MILLIS_PER_DAY);
		System.out.println("dayDate1=" + ISO8601_NOTZ_DATE_FORMAT.format(dayDate1));
		
		long days = TimeUnit.DAYS.convert(date.getTime(), TimeUnit.MILLISECONDS);
		Date dayDate2 = new Date(days * MILLIS_PER_DAY);
		System.out.println("dayDate2=" + ISO8601_NOTZ_DATE_FORMAT.format(dayDate2));
		
		
		System.out.println("[DONE]");
	}
	
	public static void DateTest1() {

		DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		DateFormat ISO8601_WITZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		DateFormat ISO8601_NOTZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat RISE_SET_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a",  Locale.US);
		
		DateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy h:mma");
		DateFormat DISPLAY_TIME_FORMAT = new SimpleDateFormat("h:mma");
		
		String fcastZ   = "2015-05-11T07:00:00Z";
		String fcastUTC = "2015-05-11T07:00:00UTC";
		String fcast    = "2015-05-11T07:00:00-0400";
		String sunrise  = "2015-05-11T06:38:23-0400"; 
		String sunset   = "2015-05-11T20:28:00-0400";
		String moonrise = "2015-05-11T02:10:15-0400";
		String moonset  = "2015-05-11T13:34:36-0400";


		parseAndDisplay(fcastUTC, ISO8601_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcastZ, ISO8601_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcast, ISO8601_DATE_FORMAT, DISPLAY_DATE_FORMAT);

		parseAndDisplay(fcastUTC, ISO8601_WITZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcastZ, ISO8601_WITZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcast, ISO8601_WITZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);

		parseAndDisplay(fcastUTC, ISO8601_NOTZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcastZ, ISO8601_NOTZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);
		parseAndDisplay(fcast, ISO8601_NOTZ_DATE_FORMAT, DISPLAY_DATE_FORMAT);

		String rise  = "07:00:00 am";
		parseAndDisplay(rise, RISE_SET_TIME_FORMAT, DISPLAY_TIME_FORMAT);
		
	}
	
	private static Date  parseAndDisplay(String dateStr, DateFormat dateParse, DateFormat dateDisplay) {
		try {
			
			// dateParse.setTimeZone(GMT);
			Date date = dateParse.parse(dateStr);
			// System.out.print("Input: " + dateStr);
			
			// dateDisplay.setTimeZone(GMT);
			// System.out.println(" ==> " + dateDisplay.format(date));
			
			return date;
			
		} catch (Exception ex) {
			System.out.println(" Date parse of '" + dateStr + "' failed " + ex.getLocalizedMessage());
		}
		
		return null;
	}

}
