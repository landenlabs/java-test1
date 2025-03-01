import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TestProxy {

    // https://medium.com/@theneckmaster/how-does-retrofit-work-6ecad1bb683b

    private static final long LOOP_CNT = Integer.MAX_VALUE/2;
    private static final String str1 = "Hello";
    private static final String str2 = "World";

    public static void testProxy() {
        // Setup... this is where the magic happens...
        ApiInterface service;  // we control what happens when the method is called (invoked), we get all the necessary information
                // this next part is just to show how you can manipulate return value

        service = (ApiInterface) Proxy.newProxyInstance(
                ApiInterface.class.getClassLoader(),
                new Class[]{ ApiInterface.class },

                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args1) throws Throwable {
                        // We control what happens when the method is called (invoked), we get all the necessary information
                        System.out.println("  Invoking method " + method.getName() + " with arguments " + Arrays.toString(args1));
                        // this next part is just to show how you can manipulate return value
                        if (args1.length > 0 && args1[0] instanceof String) {
                            return ((String) args1[0]).toUpperCase();
                        }
                        return null;
                    }
                }
        );

        System.out.println("[TestProxy]");

        // CLIENT CODE we call both methods in the ApiInterface
        System.out.println("  Returned: " + service.getSomeStringFromServer(str1));
        service.doSomeOtherThing(4.5d);

        System.out.println("[TestProxy - Done]");
    }

    interface ApiInterface {
        String getSomeStringFromServer(String parameter);
        void doSomeOtherThing(double otherParam);
    }

    // =================================================================================================================
    interface TestInterface {
        int getValue(String parameter);
    }

    public static void testProxyPerf() {
        System.out.println("\n --Full proxy allocation about 1000 times slower");
        testProxyPerf2(LOOP_CNT/1000);
        System.out.println("\n--Pre proxy allocation, almost as fast as native class");
        testProxyPerf1(LOOP_CNT);
        System.out.println("\n--Native class fastest");
        testProxyPerf3(LOOP_CNT);
    }

    public static void testProxyPerf1(long loopCnt) {
        long startMilli = System.currentTimeMillis();

        long sum = 0;

        TestInterface service;
        service = (TestInterface) Proxy.newProxyInstance(
                TestInterface.class.getClassLoader(),
                new Class[]{ TestInterface.class },

                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args1) throws Throwable {
                        int result;
                        if (args1.length > 0 && args1[0] instanceof String) {
                            result = Integer.valueOf(((String) args1[0]).length());
                        } else {
                            result = Integer.valueOf(args1.length);
                        }
                        return result;
                    }
                }
        );

        for (long idx = 0; idx < loopCnt; idx++) {
            sum += service.getValue(str1);
        }

        long endMilli = System.currentTimeMillis();
        System.out.println("  LoopCnt="+ loopCnt + " DeltaMilli=" + (endMilli - startMilli) + "  Sum=" + sum);
    }


    public static void testProxyPerf2(long loopCnt) {
        long startMilli = System.currentTimeMillis();
        long sum = 0;

        for (long idx = 0; idx < loopCnt; idx++) {
            TestInterface service;
            service = (TestInterface) Proxy.newProxyInstance(
                    TestInterface.class.getClassLoader(),
                    new Class[]{ TestInterface.class },

                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args1) throws Throwable {
                            int result;
                            if (args1.length > 0 && args1[0] instanceof String) {
                                result = Integer.valueOf(((String) args1[0]).length());
                            } else {
                                result = Integer.valueOf(args1.length);
                            }
                            return result;
                        }
                    }
            );

            sum += service.getValue(str1);
        }

        long endMilli = System.currentTimeMillis();
        System.out.println("  LoopCnt="+ loopCnt + " DeltaMilli=" + (endMilli - startMilli) + "  Sum=" + sum);
    }

    static class TestInvoke implements  TestInterface {

        @Override
        public int getValue(String args1) {
            int result;
            if (args1 instanceof String) {
                result = Integer.valueOf(((String) args1).length());
            } else {
                result = Integer.valueOf(1);
            }
            return result;
        }
    }

    public static void testProxyPerf3(long loopCnt) {
        long startMilli = System.currentTimeMillis();
        long sum = 0;

        for (long idx = 0; idx < loopCnt; idx++) {
            TestInterface service;
            service = new TestInvoke();
            sum += service.getValue(str1);
        }

        long endMilli = System.currentTimeMillis();
        System.out.println("  LoopCnt="+ loopCnt + " DeltaMilli=" + (endMilli - startMilli) + "  Sum=" + sum);

    }
}
