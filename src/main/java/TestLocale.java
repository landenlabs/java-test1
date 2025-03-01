import java.text.NumberFormat;
import java.util.*;

/**
 * Created by ldennis on 2/28/17.
 */
public class TestLocale {

    public static class LatLng {
        public float latitude;
        public float longitude;

        public LatLng(float lat, float lng) {
            this.latitude = lat;
            this.longitude = lng;
        }
    }

    public static class Version {
        public String RELEASE = "";
        public int SDK_INT = 25;
    }
    public static class Build {
        public static String MODEL = "Computer";
        public static Version VERSION = new Version();
    }

    public static void test1() {
        final Calendar cal = Calendar.getInstance();
        // final LatLng latLng = new LatLng(-12.345f, 167.890f);
        final float fvalue = -12.345f;
        final int ivalue = -123456;

        StringBuilder resultSb = new StringBuilder(3000);
        final String  localeLatLngFmt = "%6.6s %15.15s %d/%f/%g ";
        final String  timeFmt = "Cal[%1$tT %1$tF %1$tc] ";

        resultSb.append("Model:").append(Build.MODEL).append("\n");
        resultSb.append("OS:").append(Build.VERSION.RELEASE).append("\n\n");

        Map<String, List<Locale>> languages = new HashMap<>();
        String localeFmtStr;
        String abbrLang, language, testFmt;

        Locale arabic = null;
        Locale ukrainian = null;
        Locale defLocale = Locale.getDefault();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            testFmt = String.format(locale, localeLatLngFmt, "xx", "yyyy", ivalue, fvalue, fvalue);
            // testFmt += "NUM[" +format.format(ivalue) + "/" + format.format(fvalue) + "] ";
            // testFmt += String.format(locale, timeFmt, cal);

            language = locale.getDisplayLanguage();
            abbrLang = locale.getLanguage();
            // Returns well formatted BCP-47   ll-rr   language-region, ex: en-US
            abbrLang = locale.toLanguageTag();

            if (!languages.containsKey(testFmt)) {
                List<Locale> values = new ArrayList<>();
                languages.put(testFmt, values);

                if (false) {
                    localeFmtStr = String.format(locale, localeLatLngFmt, abbrLang, language, ivalue, fvalue, fvalue);
                    localeFmtStr += "NUM[" +format.format(ivalue) + "/" + format.format(fvalue) + "] ";
                    localeFmtStr += String.format(locale, timeFmt, cal);
                    localeFmtStr += "\n";
                    resultSb.append(localeFmtStr);
                }
            }

            languages.get(testFmt).add(locale);

            if (language.equals("Arabic")) {
                arabic = locale;
            }
            if (language.equals("Ukrainian")) {
                ukrainian = locale;
            }

            if (false) {
                localeFmtStr = String.format(locale, localeLatLngFmt, abbrLang, language, ivalue, fvalue, fvalue);
                localeFmtStr += format.format(ivalue) + "/" + format.format(fvalue) + " ";
                localeFmtStr += String.format(locale, timeFmt, cal);
                localeFmtStr += "\n";
                resultSb.append(localeFmtStr);
            }
        }

        List<String> sortedKeys = new ArrayList<>(languages.keySet());
        // sortedKeys.addAll(languages.keySet());
        Collections.sort(sortedKeys);

        for (String testKey : sortedKeys) {
            String lastIso = "";
            resultSb.append(String.format(Locale.US, "%3d ", languages.get(testKey).size())).append(testKey).append("\n");
            if (false) {
                for (Locale locale : languages.get(testKey)) {
                    if (!locale.getISO3Language().equals(lastIso)) {
                        lastIso = locale.getISO3Language();
                        language = locale.getDisplayLanguage();
                        abbrLang = locale.getLanguage();
                        if (Build.VERSION.SDK_INT >= 21) {
                            // Returns well formatted BCP-47   ll-rr   language-region, ex: en-US
                            abbrLang = locale.toLanguageTag();
                        } else {
                            // Returns ll_rr   language_region, ex: en_US
                            abbrLang = locale.toString();
                        }
                        resultSb.append("   ").append(abbrLang).append(" ").append(language).append("\n");
                    }
                }
            }
        }


        resultSb.append("\n ---[Default]---- \n");
        resultSb.append(getLocalizedString(defLocale, ivalue, fvalue, cal));

        if (ukrainian != null) {
            Locale.setDefault(ukrainian);
            resultSb.append("\n ---[" + Locale.getDefault().toLanguageTag() + "]---- \n");
            resultSb.append(getLocalizedString(arabic, ivalue, fvalue, cal));
            Locale.setDefault(defLocale);
        }

        if (arabic != null) {
            Locale.setDefault(arabic);
            resultSb.append("\n ---[" + Locale.getDefault().toLanguageTag() + "]---- \n");
            resultSb.append(getLocalizedString(arabic, ivalue, fvalue, cal));
            Locale.setDefault(defLocale);
        }

        resultSb.append("Model:").append(Build.MODEL).append("\n");
        resultSb.append("OS:").append(Build.VERSION.RELEASE).append("\n\n");

        System.out.println(resultSb.toString());
    }

    static String  getLocalizedString(Locale locale, int ivalue, float fvalue, Calendar cal) {
        final String  timeFmt = "cal= %1$tT %1$tF [%1$tc]\n";
        final String  localeLatLngFmt = "%s %d/%f/%g\n";
        String strLang = locale.getLanguage();

        String strFmt = String.format(localeLatLngFmt, "fmt=", ivalue, fvalue, fvalue);
        strFmt += String.format(timeFmt, cal);
        String strCat = "cat=" + " " + ivalue + "/" + fvalue  + "\n";
        String strVal = "val=" + " " + String.valueOf(ivalue) + "/" + String.valueOf(fvalue) + "\n";

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        // format.setCurrency(Currency.getInstance("CZK"));
        String strNum = "num= " + format.format(ivalue) + "/" + format.format(fvalue) + "\n";

        StringBuilder resultSb = new StringBuilder();
        resultSb
                .append(strFmt)
                .append(strCat)
                .append(strVal)
                .append(strNum);
        return resultSb.toString();
    }

/*
    public static DateTimeFormatter getLocalizedDateFormatter(Locale requestedLocale) {
        String formatPatternString = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                FormatStyle.SHORT, null,
                Chronology.ofLocale(requestedLocale), requestedLocale);
        // if not already showing month name, modify so it shows abbreviated month name
        if (! formatPatternString.contains("MMM")) {
            formatPatternString = formatPatternString.replaceAll("M+", "MMM");
        }
        return DateTimeFormatter.ofPattern(formatPatternString, requestedLocale);
    }

    public static void test2() {
        Locale[] locales = { Locale.US, Locale.FRANCE, Locale.GERMANY,
                Locale.forLanguageTag("da-DK"), Locale.forLanguageTag("sr-BA") };
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Sarajevo"));
        for (Locale currentLocale : locales) {
            DateTimeFormatter ldf = getLocalizedDateFormatter(currentLocale);
            System.out.format(currentLocale, "%-33S%s%n",
                    currentLocale.getDisplayName(), today.format(ldf));
        }
    }
    */
}
