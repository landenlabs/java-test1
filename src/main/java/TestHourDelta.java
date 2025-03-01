import java.util.Locale;

public class TestHourDelta {

    public static void test1() {

        System.out.printf(Locale.US, "%5s %5s %5s %s\n",  "Hr1", "Hr2", "Delta", "Expected");
        System.out.printf(Locale.US, "%5s %5s %5s %s\n",  "---", "---", "----", "--------");

        showDeltaHours(1, 2, 1);
        showDeltaHours(1, 3, 2);
        showDeltaHours(1, 4, 3);
        showDeltaHours(1, 5, 4);
        showDeltaHours(1, 6, 5);
        showDeltaHours(1, 7, 6);
        showDeltaHours(1, 8, 7);
        showDeltaHours(1, 9, 8);
        showDeltaHours(1, 10, 9);
        showDeltaHours(1, 11, 10);
        showDeltaHours(1, 12, 11);
        showDeltaHours(1, 13, 12);
        showDeltaHours(1, 14, 11);
        showDeltaHours(1, 15, 10);
        showDeltaHours(1, 16, 9);
        showDeltaHours(1, 17, 8);
        showDeltaHours(1, 18, 7);
        showDeltaHours(1, 19, 6);
        showDeltaHours(1, 20, 5);
        showDeltaHours(1, 21, 4);
        showDeltaHours(1, 22, 3);
        showDeltaHours(1, 23, 2);

        System.out.println("TestHourDelta.test1 [Done]");
    }

    public static void test2() {

        System.out.printf(Locale.US, "%5s %5s %5s %s\n", "Hr1", "Hr2", "Delta", "Expected");
        System.out.printf(Locale.US, "%5s %5s %5s %s\n", "---", "---", "----", "--------");

        for (int hr1 = 1; hr1 <= 24; hr1++) {
            showDeltaHours(hr1, 1);
        }
        for (int hr1 = 1; hr1 <= 24; hr1++) {
            showDeltaHours(hr1, 12);
        }
        for (int hr1 = 1; hr1 <= 24; hr1++) {
            showDeltaHours(hr1, 23);
        }
        for (int hr1 = 1; hr1 <= 24; hr1++) {
            showDeltaHours(hr1, 24);
        }
        System.out.println("TestHourDelta.test2 [Done]");
    }

    public static void showDeltaHours(int hour1, int hour2) {
        System.out.printf(Locale.US, "%5d %5d %5d \n", hour1, hour2, hourChange(hour1, hour2));
    }

    public static void showDeltaHours(int hour1, int hour2, int expected) {
        System.out.printf(Locale.US, "%5d %5d %5d %5d \n", hour1, hour2, hourChange(hour1, hour2), expected);
    }

    private static int hourChange(int hour1, int hour2) {
        return 12 - Math.abs((Math.abs(hour1 - hour2) % 24) - 12);
    }
}
