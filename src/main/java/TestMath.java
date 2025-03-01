public class TestMath {

    public static void testMath1() {

        float min10 = Math.min(10f,  Float.NaN);
        float max10 = Math.max(10f,  Float.NaN);

        System.out.println("Min 10 Nan = " + min10);
        System.out.println("Max 10 Nan = " + max10);
        System.out.println("[Test Math done]");
    }
}
