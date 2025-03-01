public class TestWordWrap {

    public static String wrapString1(String s, String deliminator, int length) {
        String result = "";
        int lastdelimPos = 0;
        for (String token : s.split("_", -1)) {
            if (result.length() - lastdelimPos + token.length() > length) {
                result = result + deliminator + token;
                lastdelimPos = result.length() + 1;
            }
            else {
                result += (result.isEmpty() ? "" : "_") + token;
            }
        }
        return result;
    }

    public static String wrapString2(String s, String deliminator, int length) {
        StringBuilder result = new StringBuilder(s.length()+s.length()/length+1);
        int rowPos = 0;
        int spcIdx = -1;
        while ((spcIdx = s.indexOf("_", spcIdx+1)) != -1) {
            if (spcIdx - rowPos > length) {
                result.append(s.substring(rowPos, spcIdx)).append(deliminator);
                rowPos = spcIdx+1;
            }
        }
        result.append(s.substring(rowPos));
        return result.toString();
    }

    public static String wrapString3(String s, String deliminator, int length) {
        StringBuilder result = new StringBuilder(s.length()+s.length()/length+1);
        int rowPos = 0;
        int begIdx = 0;
        int spcIdx = -1;
        do {
            spcIdx = s.indexOf("_", begIdx);
            spcIdx = (spcIdx == -1) ? s.length() : spcIdx;
            if (spcIdx - rowPos > length) {
                int wordLen = spcIdx - begIdx;
                int rowLen = spcIdx - rowPos;
                if (rowLen - length >  length - rowLen - wordLen) {
                    spcIdx = spcIdx - wordLen - 1;
                }
                if (rowPos < spcIdx) {
                    result.append(s.substring(rowPos, spcIdx)).append(deliminator);
                    rowPos = spcIdx + 1;
                }
            }
            begIdx = spcIdx+1;
        } while (begIdx < s.length());
        result.append(s.substring(rowPos));
        return result.toString();
    }

    public static String wrapToRows(String s, int rows) {
        int width = s.length() / rows;
        StringBuilder result = new StringBuilder(s.length()+rows);
        String eol = "";
        int rowStart = 0;
        while (rowStart < s.length()) {
            rows--;
            int rowBreak = rowStart + width;
            int maxIdx = s.indexOf("_", rowBreak);
            maxIdx = (maxIdx == -1) ? s.length() : maxIdx;
            int minIdx = s.lastIndexOf("_", rowBreak);
            int splitIdx = (maxIdx - rowBreak <= rowBreak - minIdx || rows == 0) ? maxIdx : minIdx;
            result.append(eol).append(s.substring(rowStart, splitIdx));
            eol = "\n";
            rowStart = splitIdx+1;
        }

        return result.toString();
    }

    static String[] STRS = {
        "*This_is_a_test_this_is_a_long_test",
        "*We\\'ll_bring_you_weather_wherever_you_are",
        "*AA_BBB_CCCC_DDDDD_EEEEEE_FFFFFFF_GGGGGGGG_HHHHHHHHH",
        "*AAAAAAAAA_BBBBBBBB_CCCCCCC_DDDDDD_EEEEE_FFFF_GGG_HH"
    };

    static int[] LENS = { 10, 15, 20, 25, 30, 35 };

    public static void test1() {

        for (int len : LENS) {
            System.out.println("\n==== Line length=" + len);
            for (int idx = 0; idx < len-1; idx++) System.out.print("-");
            System.out.println("|");
            for (String str : STRS) {
                // System.out.println(str);
                // System.out.println("#1\n" + wrapString1(str, "\n", len));
                // System.out.println("#2\n" + wrapString2(str, "\n", len));
                System.out.println("#3\n" + wrapString3(str, "\n", len));

            }
        }

        System.out.println("\n----\n");

        for (int rows = 1; rows < 4; rows++) {
            System.out.println("\n==== #Rows=" + rows);
            for (String str : STRS) {
                System.out.println(wrapToRows(str, rows));
            }
        }
    }
}
