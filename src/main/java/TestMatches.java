/**
 * Created by Dennis Lang 7-Jul-2017
 */
public class TestMatches {

    public static void MatchTest() {

        String dateStr1 = "2017-07-06 16:39:12";
        String dateStr2 = "2017-07-06 T16:39:12";           // ISO8601
        String dateStr3 = "Thu, 06 Jul 2017 16:39:12";      // RFC822

        String patternDate = "[1-2][0-9]+-[0-1][0-9]-[0-3][0-9] [0-9:]+";

        String[] dates = { dateStr1, dateStr2, dateStr3 };

        for (String dateStr : dates) {
            if (dateStr.matches(patternDate)) {
                System.out.println("  " + patternDate + " Matches:" + dateStr);
            } else {
                System.out.println("  " + patternDate + " NOmatch:" + dateStr);
            }
        }

        System.out.println("\n[MatchTest Done]\n");
    }

    public static void RegexTest() {

        // Warning - Lint tries to convert regex from  "\\[|,|\\]" to this "[\\[,]]" which does not work.
        String regCoords =  "[123.45, 56.789]";

        String reg1 = "\\[|,|]";
        String[] parts1 = regCoords.split(reg1);
        System.out.println("Split " + regCoords + " with " + reg1 + " parts=" + parts1.length);

        String reg2 = "[\\[,\\]]";
        String[] parts2 = regCoords.split(reg2);
        System.out.println("Split " + regCoords + " with " + reg2 + " parts=" + parts2.length);

        System.out.println("[Done] ");
    }
}
