public class TestLong {

    public static void test1() {

        long lvalue1 = 0;

        System.out.println("TestLong1");

        lvalue1 = Integer.MAX_VALUE;
        System.out.printf("long set to Integer MAX_VALUE value=%x\n", lvalue1);
        lvalue1 = Integer.MIN_VALUE;
        System.out.printf("long set to Integer MIN_VALUE value==%x\n", lvalue1);

        lvalue1 = Long.MAX_VALUE;
        System.out.printf("long set to Long MAX_VALUE value==%x\n", lvalue1);
        lvalue1 = Long.MIN_VALUE;
        System.out.printf("long set to Long MIN_VALUE value==%x\n", lvalue1);

        System.out.printf("long set to -1L value==%x\n", -1L);
    }
}
