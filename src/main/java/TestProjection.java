/**
 * Created by ldennis on 8/19/16.
 */
public class TestProjection {


    public static void test1() {
        System.out.printf("Equator Primeter meters=%,.0f\n", ProjUtil.EQUATOR_PERIMETER_METERS);
        int widthPixels = ProjUtil.TILE_WIDTH_PIXELS_ZOOM0;
        for (int lat = 0; lat <= 45; lat += 45) {
            for (int zoom = 0; zoom < 20; zoom += 1) {

                double m = ProjUtil.metersAtLatZoom(lat, zoom, widthPixels);
                double z = Math.pow(2, zoom);
                double widthDeg = ProjUtil.degreesAtLatZoom(lat, zoom, widthPixels);
                System.out.printf("Zoom=%2d, Lat=%2d, WidthMeter=%,10.0f WidthDeg=%7.3f\n", zoom, lat, m, widthDeg);
            }

            System.out.println("");
        }

        System.out.printf("[done]\n");
    }


    public static class ProjUtil {
        public static final double EARTH_MEAN_RADIUS_METERS = 6378140;
        public static final double EQUATOR_PERIMETER_METERS = EARTH_MEAN_RADIUS_METERS * 2 * Math.PI;
        public static final int TILE_WIDTH_PIXELS_ZOOM0 = 256;
        public static final double METERS_PER_PIXEL = EQUATOR_PERIMETER_METERS / TILE_WIDTH_PIXELS_ZOOM0;
        public static final int EQUATOR_DEGREES = 360;

        public  static double metersAtLat(double latitudeDeg, double deg) {
            final double latScale = Math.cos(Math.toRadians(latitudeDeg));
            return EQUATOR_PERIMETER_METERS / EQUATOR_DEGREES * deg * latScale;
        }

        public  static double metersAtLatZoom(double latitudeDeg, int zoom, double widthPixels) {
            final double latScale = Math.cos(Math.toRadians(latitudeDeg));
            double metersPerPixel = METERS_PER_PIXEL * latScale / Math.pow(2, zoom);
            return widthPixels * metersPerPixel;
        }

        public  static double degreesAtLatZoom(double latitudeDeg, int zoom, double widthPixels) {
            final double latScale = Math.cos(Math.toRadians(latitudeDeg));
            return latScale * EQUATOR_DEGREES * metersAtLatZoom(0.0f, zoom, widthPixels) / EQUATOR_PERIMETER_METERS;
        }

        public  double getZoomForDegrees(double mapWidthDeg, double latitudeDeg) {
            float mapWidthDp = 640;
            return getZoomForMetersWide(metersAtLat(latitudeDeg, mapWidthDeg), mapWidthDp, latitudeDeg);
        }

        public  double getZoomForMeters(double mapWidthMeters, double latitudeDeg) {
            float mapWidthDp = 640;
            return getZoomForMetersWide(mapWidthMeters, mapWidthDp, latitudeDeg);
        }

        public  static double getZoomForMetersWide(
                final double mapWidthMeters,
                final double mapWidthPixels,
                final double latitudeDeg) {
            final double latScale = Math.cos(Math.toRadians(latitudeDeg));
            // final double zoomRate = EQUATOR_PERIMETER_METTERS * mapWidthPixels * latScale / ( mapWidthMeters * 256.0 );

            final double TILE_WIDTH_PIXELS_ZOOM0 = 256.0;
            final double TILE_WIDTH_METERS_ZOOM0 = EQUATOR_PERIMETER_METERS;
            final double metersPerPixelz0 = TILE_WIDTH_METERS_ZOOM0 / TILE_WIDTH_PIXELS_ZOOM0;
            final double zoomRate = latScale * metersPerPixelz0 * (mapWidthPixels / mapWidthMeters);

            // Convert to power of 2
            return Math.log(zoomRate) / Math.log(2.0);      // is zoom 0 based or 1 based ?
        }
    }


    public static void test2() {
        System.out.println("Test Geopoint distance");

        double km = kilometersBetweenLatLng(
                new LatLng(30.44f, -91.19f),
                new LatLng(30.44f, -91.19f));

        System.out.println("Distance km=" + km);

        System.out.println("[done]");
    }

    public static class LatLng {
        public float latitude;
        public float longitude;

        public LatLng(float lat, float lng) {
            this.latitude = lat;
            this.longitude = lng;
        }
    }

    /* *********************** Common constants ***************************** */
    public static final float EARTH_EXTENT = 40030.2294f; // kilometers
    public static final float EARTH_RADIUS = 6371.009f; // kilometers (average)
    public static final float EARTH_RADIUS_METERS = 6378137f; // meters WGS84 Major axis
    public static final int MIN_PIXEL_DISTANCE = 64;

    public static double kilometersBetweenLatLng(LatLng g1Position, LatLng g2Position) {
        double d1 = Math.sin(Math.toRadians(g1Position.latitude))
                * Math .sin(Math.toRadians(g2Position.latitude))
                + Math.cos(Math.toRadians(g1Position.latitude))
                * Math.cos(Math.toRadians(g2Position.latitude))
                * Math.cos(Math.toRadians(g2Position.longitude - g1Position.longitude));
        double d2 = Math.acos(d1);

        return EARTH_RADIUS_METERS / 1000.0
                * Math.acos(
                    Math.sin(Math.toRadians(g1Position.latitude))
                    * Math .sin(Math.toRadians(g2Position.latitude))
                    + Math.cos(Math.toRadians(g1Position.latitude))
                    * Math.cos(Math.toRadians(g2Position.latitude))
                    * Math.cos(Math.toRadians(g2Position.longitude - g1Position.longitude))
                );
    }
}
