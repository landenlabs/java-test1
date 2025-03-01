package utils;

public class TestPolygon {

    static class Point {
        public double x;
        public double y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Polygon {
        ArrayListEx<Point> points = new ArrayListEx<>();
        RectD bounds = new RectD();
        void addPoint(double x, double y) {
            bounds.union(x, y);
            points.add(new Point(x, y));
        }

        RectD getBounds() {
            return bounds;
        }

        boolean contains(double x, double y) {
            for (Point point : points) {
                if (point.x == x && point.y == y) {
                    return true;
                }
            }

            return false;
        }
    }


    static boolean isInside(Point[] points, Point point)
    {
        int nvert = points.length;
        int i, j;
        boolean c = false;

        for (i = 0, j = nvert-1; i < nvert; j = i++) {
            Point pi = points[i];
            Point pj = points[j];

            if ( ((pi.y > point.y) != (pj.y > point.y)) &&
                    (point.x < (pj.x - pi.x) * (point.y - pi.y) / (pj.y - pi.y) + pi.x) )
                c = !c;
        }
        return c;
    }

    private static boolean contains(Point[] points, double x, double y) {
        for (Point point : points) {
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    private static boolean similar(Point p1, Point p2, double tolerance) {
        return (Math.abs(p1.x - p2.x) <= tolerance
            && Math.abs(p1.y - p2.y) <= tolerance);
    }

    private static void report(Point[] tests, Point[] polyPoints, String desc ) {
        double scale = 100;
        Polygon polygon = new Polygon();
        for (Point p : polyPoints) {
            polygon.addPoint(p.x*scale, p.y*scale);
        }
        RectD rect =  polygon.getBounds();
        polyPoints = polygon.points.toArray(polyPoints);

        System.out.println(desc);
        for (Point test : tests) {
            System.out.printf(" Test X=%5.3f, Y=%5.3f = ", test.x, test.y);
            System.out.print(" Polygon " +  (polygon.contains(test.x, test.y) ? "inside" : "      "));
            System.out.print("  Code " +  (isInside(polyPoints, test) ? "inside" : "      "));
            System.out.println();

            // rect.union(test.x, test.y);
        }
        System.out.println();


        // Only works for integer points.
        double step = (rect.height() > 2) ? 1 : 0.01;

        System.out.printf("X %.2f ... %.2f\n", rect.getMinX(), rect.getMaxX());


        for (double row = 0; row < rect.height()+step; row += step) {
            double y = ( rect.getMaxY() - row);
            System.out.printf("Y %.2f: ", y);
            for (double col = 0; col < rect.width()+step; col += step) {
                double x = (col + rect.getMinX());

                Point gridPoint = new Point(x, y);

                boolean isTestPt = false;
                for (Point test : tests) {
                    Point testPt = new Point(test.x * scale, test.y*scale);
                    if (similar(testPt, gridPoint, step/2)) {
                        isTestPt = true;
                        if (isInside(polyPoints, gridPoint)) {
                            System.out.print("#");
                        } else {
                            System.out.print("?");
                        }
                        break;
                    }
                }

                if (!isTestPt) {
                    if (isInside(polyPoints, gridPoint)) {
                        System.out.print("+");
                    } else {
                        System.out.print(" ");
                    }
                }
                /*
                if (contains(test, x, y)) {
                    if (polygon.contains(x, y)) {
                        System.out.print("*");
                    } else {
                        System.out.print("?");
                    }
                } else if (contains(polyPoints, x, y)) {
                    System.out.print("+");
                } else {
                    if (polygon.contains(x, y)) {
                        System.out.print("_");
                    } else {
                        System.out.print(".");
                    }
                }
                 */
            }
            System.out.println();
        }
    }

    public static void testPolygon1() {
        Point[] triangle = new Point[]{
                new Point(10, 10),
                new Point(20, 20),
                new Point(30, 10),
                new Point(10, 10),
        };

        Point[] box = new Point[] {
                new Point(10, 10),
                new Point(20, 10),
                new Point(20, 20),
                new Point(10, 20),
                new Point(10, 10),
        };

        Point[] v1 = new Point[] {
                new Point(10, 20),
                new Point(20, 10),
                new Point(30, 20),
                new Point(20, -5),
                new Point(10, 20),
        };

        Point[] test = new Point[] {
                new Point(5, 5),
                new Point(10, 10),
                new Point(15, 15),
                new Point(20, 20),
                new Point(25, 25),
                new Point(15, 10),
                new Point(10, 15),
                new Point(20, 11),
                new Point(20, -5),
                new Point(20, 0),
                new Point(20, -7),
        };

        report(test, triangle, "Triangle");
        report(test, box, "Box");
        report(test, v1, "v1");

    }


    public static void testPolygon2()  {
        Point[] shape1 = new Point[] {
                new Point(-9170, 3101),
                new Point(-9167, 3130),
                new Point(-9129, 3193),
                new Point(-9114, 3187),
                new Point(-9147, 3130),
                new Point(-9154, 3100),
                new Point(-9170, 3101),
        };

        Point[] test = new Point[]{
                new Point(-9140, 3140),
                new Point(-9170, 3160),
        };

        report(test, shape1, "Shape1");
    }

    // Salem NH Flood Warning
    // https://b2b-test-data.media.weather.com/sun/v3/alerts/detail?alertId=42.7_-71.1_flood_w&format=json&language=en-US&apiKey=0eeb3bdc3af8617ffb2f27380ad920a8
    public static void testPolygon3()  {
        Point[] salemNH = new Point[] {
                new Point( -71.38,42.73),
                new Point(-71.03,42.73),
                new Point(-71.03,42.84),
                new Point(-71.15,42.84),
                new Point(-71.15,42.79),
                new Point(-71.38,42.79),
                new Point(-71.38,42.73)
        };

        Point[] test = new Point[]{
                new Point( -71.21, 42.799),      // North of 42.73 is outside polygon
                new Point(-71.21, 42.76),        // Between 42.73 and 42.79 is inside
        };

        report(test, salemNH, "Salem");
    }

    public static void testLightningOpacity() {

        for (int min = 0; min <= 15; min++) {
            float opacity = 1.0f;
            if (min > 15) {
                opacity = 0;
            } else if (min > 5) {
                int minX = min - 5;
                opacity =
                        (float) (0.75 - (minX / 10) * .50); // Compute opacity 0.75 to 0.50 for 5 to 15 minutes
            }
            System.out.printf("%d min, opacity= %d\n",  min, (int)(opacity*100));
        }
    }
}
