public class TestLatLng {

    public static void test1() {

        System.out.println("Center Longitude\n");

        getCenterLng(20, 10);
        getCenterLng(170, 10);
        getCenterLng(180, 10);
        getCenterLng(180, 140);

        getCenterLng(-10, -20);
        getCenterLng(-10, -170);
        getCenterLng(-10, -180);
        getCenterLng(-140, -180);

        getCenterLng(10, 0);
        getCenterLng(180, 0);
        getCenterLng(0, -10);
        getCenterLng(0, -180);

        getCenterLng(-170, 170);
        getCenterLng(-170, 100);
        getCenterLng(-170, 11);
        getCenterLng(-170, 10);
        getCenterLng(-170, 9);

        getCenterLng(170, -170);
        getCenterLng(170, -100);
        getCenterLng(170, -11);
        getCenterLng(170, -10);
        getCenterLng(170, -9);

        normalizeLng(0);
        normalizeLng(10);
        normalizeLng(179);
        normalizeLng(180);
        normalizeLng(181);
        normalizeLng(359);
        normalizeLng(360);

        normalizeLng(-10);
        normalizeLng(-179);
        normalizeLng(-180);
        normalizeLng(-181);
        normalizeLng(-359);
        normalizeLng(-360);
    }

    private static void getCenterLng(double lng1, double lng2) {
        double lngSum = lng1 + lng2;
        if (lng1 * lng2 < 0 && Math.abs(lng1 - lng2) > 180) {
            lngSum += (lngSum > 0) ? -360 : 360;
        }
        double centerLng = lngSum / 2.0D;

        System.out.printf("From %4.0f to %4.0f Center= %4.0f\n", lng1, lng2, centerLng);
    }


    private static void normalizeLng(double lng) {
        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        double nLng = lng - ((int)lng / 180 * 360);
        System.out.printf("Raw lng=%4.0f  normalized to %4.0f\n", lng, nLng);
        // return nLng;
    }
}
