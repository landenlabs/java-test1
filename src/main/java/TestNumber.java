public class TestNumber {

    public static void test1(final String s0) {

        Integer a1 = 1;
        Integer a2 = 2;

        // Does not work, no autoboxing on mutable types
        // No automatic conversion or construction like C++
        //
        // MutableInteger m3 = 3;

        int sum = a1 + a2;

        /*
         * No automatic unboxing.
          
        MutableInteger m3 = new MutableInteger(3);
        int sum2 = a1 + a2 + m3;
        */
    }

    public static void test2(final String s0) {

        Integer i1 = 1;
        Integer i2 = 2;

        Number sumI = i1 + 2;
        System.out.println("SumI Integer(1) + Integer(2) = " + sumI);


        Integer i8 = 8;
        Long l8 = 8L;
        Float f8 = 8.0f;
        Double d8 = 8.0;

        diff(i8, l8);
        diff(l8, f8);
        diff(f8, d8);
        diff(d8, i8);

        System.out.println("TestNumber.test2 [Done]");
    }

    private static void  diff(Number n1, Number n2) {
        System.out.print(n1.getClass().getSimpleName() + " " + n1);
        System.out.print(" - ");
        System.out.print(n2.getClass().getSimpleName() + " " + n1);
        Number diffVal = n1.doubleValue() - n2.doubleValue();
        System.out.println(" = " + diffVal);
    }

    public static int compare(Number num1, Number num2) {
        int result;
        if (num1.equals(num2)) {
            result = 0;
        } else if (num1 == null || num2 == null) {
            result = (num1 == null) ? -1 : 1;
        } else if (num1 instanceof Integer) {
            result = (int)( ((Integer)num1).intValue() - ((Integer)num2).intValue() );
        } else if (num1 instanceof Long) {
            result = (int)( ((Long)num1).longValue() - ((Long)num2).longValue() );
        } else if (num1 instanceof Float) {
            result = (int)( ((Long)num1).floatValue() - ((Long)num2).floatValue() );
        } else {
            result = (int)( ((Long)num1).doubleValue() - ((Long)num2).doubleValue() );
        }
        return result;
    }
}
