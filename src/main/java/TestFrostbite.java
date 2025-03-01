
public class TestFrostbite {

    // https://weather.stadhaugh.com/Frostbite2.html
    private static int[][] FROST_MINUTES = new int[][]{
            //        -45 -40 -35 -30 -25 -20 -15 -10 -05 +00 +05 +10 +15  <= temperature F
            new int[]{ 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99 }, // 00 wind mph
            new int[]{ 10, 10, 30, 30, 30, 30, 30, 30, 99, 99, 99, 99, 99 }, // 05
            new int[]{ 10, 10, 10, 10, 10, 30, 30, 30, 30, 99, 99, 99, 99 }, // 10
            new int[]{  5,  5, 10, 10, 10, 10, 30, 30, 30, 30, 99, 99, 99 }, // 15
            new int[]{  5,  5,  5, 10, 10, 10, 10, 30, 30, 30, 99, 99, 99 }, // 20
            new int[]{  5,  5,  5,  5, 10, 10, 10, 10, 30, 30, 99, 99, 99 }, // 25
            new int[]{  5,  5,  5,  5, 10, 10, 10, 10, 10, 30, 30, 99, 99 }, // 30
            new int[]{  5,  5,  5,  5,  5,  5, 10, 10, 10, 30, 30, 99, 99 }, // 35
            new int[]{  5,  5,  5,  5,  5,  5, 10, 10, 10, 30, 30, 99, 99 }, // 40
            new int[]{  5,  5,  5,  5,  5,  5,  5, 10, 10, 30, 30, 99, 99 }, // 45
            new int[]{  5,  5,  5,  5,  5,  5,  5, 10, 10, 30, 30, 99, 99 }, // 50
            new int[]{  5,  5,  5,  5,  5,  5,  5, 10, 10, 10, 30, 30, 99 }, // 55
            new int[]{  5,  5,  5,  5,  5,  5,  5,  5, 10, 10, 30, 30, 99 }, // 60
    };

    private static int frostbiteMinutes(float airTempF, float  windSpeedMph) {
        int tempF = Math.max(0, Math.min(12, Math.round((airTempF+45) / 5)));
        int windMph= Math.max(0, Math.min(11, Math.round((windSpeedMph / 5 )  )));
        int minutes =  FROST_MINUTES[windMph][tempF];
        return minutes;
    }

    public static void testFrostBite1() {

        int tempMin = -50;
        int tempMax = 50;
        System.out.printf("%4s  ", "");
        for (int tempF = tempMax; tempF >= tempMin; tempF -= 5)
            System.out.printf("%4d", tempF);
        System.out.println();

        for (int windMph = 0; windMph <= 70; windMph += 5) {
            System.out.printf("%4d: ", windMph);
            for (int tempF = tempMax; tempF >= tempMin; tempF -= 5) {
                int minutes = frostbiteMinutes(tempF, windMph);
                System.out.printf("%4d", minutes);
            }
            System.out.println();
        }
    }

}
