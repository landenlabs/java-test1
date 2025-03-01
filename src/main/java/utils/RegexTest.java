package utils;

public class RegexTest {

    public static void testRegReplace(String inStr, String regPat, String replaceWith) {
        System.out.println();
        System.out.println(" RegPat:" + regPat);
        System.out.println("Replace:" + replaceWith);
        System.out.println("  Input:" + inStr);
        System.out.println(" Result:" + inStr.replaceAll(regPat, replaceWith));
        System.out.println();
    }

    public static void test1() {

        String test1 = "hello [/RESP] world";

        testRegReplace(test1, "\\[/[A-Za-z]+\\]", "");

        // Does not work
        testRegReplace(test1, "[[][A-Za-z]+]", "");
        // Does not work
        testRegReplace(test1, "\133/[A-Za-z]+\\]", "");

        System.out.println("[Done]");
    }
}
