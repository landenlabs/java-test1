public class TestExperiment {

    public static void Test1() {

        // Java puzzels
        // https://doc.lagout.org/programmation/Java/Java%20Puzzlers_%20Traps%2C%20Pitfalls%2C%20and%20Corner%20Cases%20%5BBloch%20%26%20Gafter%202005-07-04%5D.pdf
        {
            // 0.8999999999999999
            System.out.println(2.00 - 1.10);
        }
        {
            // 0.90
            System.out.format("%.2f%n", 2.00 - 1.10);
        }


        {
            // 5 due to overflow of integer math
            final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
            final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
        }

        {
            // 1000, by forcing Long math in contant
            final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000L;
            final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
        }

        {
            // 17777
            System.out.println(12345 + 5432l);
        }
        {
            // 66666
            System.out.println(12345 + 54321);
            // System.out.println(Integer.MAX_VALUE);  // 2147483647 0x7fffffff
        }
        {
            // 66666
            System.out.println(12345 + 54321L);
        }

        {
            // cafebabe
            System.out.println(   //      987654321    (negative integer)
                    Long.toHexString(0x100000000L + 0xcafebabe));
        }

        {
            // 0xffff = 65535
            System.out.println((int) (char) (byte) -1);
        }

        {
            int x = 1984; // (0x7c0)
            int y = 2001; // (0x7d1)

            y ^= x ^= y;
            System.out.println("x=" + Long.toHexString(x) + " y=" +  Long.toHexString(y));

            x=1984;
            y=2001;
            x ^= y ^= x ^= y;
            // x=0x7c0 ^ 0x7d1 = 0x011
            // y=0x7d1 ^ 0x011 = 0x7c0
            // x=0x011 ^ 0x7c0 = 0x7d1

            System.out.println("x = " + x + "; y = " + y);
        }

        {
            char x = 'X';
            int i = 0;
            System.out.print(true ? x : 0);     // X
            System.out.print(false ? i : x);    // 88 dec for ascii X
        }
    }
}
