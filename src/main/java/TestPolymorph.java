public class TestPolymorph {

    public static class Base {
        protected int base = 1;

        public Base(int val) {
            this.base = val;
        }

        @Override
        public String toString() {
            return getClass().getName() + " " + base;
        }
    }

    public static class FooBase extends  Base {

        public FooBase(int val) {
            super(val);
        }

        public FooBase(Base base) {
            super(base.base);
        }
    }


    public static void test() {

        Base base = new Base(10);
        FooBase fooBase =new FooBase(20);

        System.out.println(base);
        System.out.println(fooBase);

        base = fooBase;
        fooBase = (FooBase)base;

        System.out.println(base);
        System.out.println(fooBase);
    }
}
