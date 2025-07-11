import utils.RegexTest;
import utils.TestPolygon;

/**
 * Created by ldennis on 4/18/16.
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });

        System.out.println("Java JRE=" + System.getProperty("java.version"));

        // TestString.test1(null);
        // TestTree.Test();

        // TestDate.TestDate7();
        // TestProjection.test1();
        // TestJson.test1("");
        // TestJoin.test1();
        // TestMap.test1();
        // TestMap.test2();
        // TestXml.tests();
        // AppendStringTest.tests();
        // TestDate.TestDate9();
        // AppendStringTest.test2();
        // TestJoda.TestJoda1();
        // TestLocale.test();
        // TestCopyOnWrite.TestCOW1();
        // TestIterator.TestSet1();
        // TestIterator.TestList1();
        // TestSparseArray.TestSparseArray1();
        //  TestDate.TestDate10();
        // TestString.testSplit();
        // TestGenerics.TestGeneric4();
        // TestWind.WindTest();
        //  TestMatches.MatchTest();
        // TestJoda.TestJoda6();
        // TestProjection.test2();
        // TestNumber.test2("a");
        // TestEnum.test1();
        // TestPrecedence.test();
        // TestTime.test1();
        //  TestPolymorph.test();
        // TestMatches.RegexTest();
        // TestProxy.testProxy();
        // TestProxy.testProxyPerf();
        // TestConstructorOrder.test2();
        // TestJoda.testJoda7();
        // TestGenerics.TestGeneric5();
        //    TestUrlUri.Test();
        // TestDate.DateTest1();
        // TestWordWrap.test1();
        //  TestUrlUri.Test();
        // TestExperiment.Test1();
        // TestMap.test3();
        // TestStatic.test1();
        // AppendStringTest.test2();
        // TestHashCode.test1();
        // TestLatLng.test1();
        // TestLong.test1();
        // TestLambda.Test1();
        // TestHashCode.test2();
        // TestDate.DateTest3();
        // TestString3.test2(null);
        // TestGenerics.TestGeneric1();
        // TestDate.TestDate10();
        // TestFrostbite.testFrostBite1();
        // TestSequence.testSequence1();
        // TestMath.testMath1();
        // TestPolygon.testPolygon3();
        // TestPolygon.testLightningOpacity();
        // TestJoda.testJoda8();
        // TestCompletable.test3();
        // TestParcel.test1("test1");
        // TestJoda.testJoda7();
        //    TestHourDelta.test1();
        // TestHourDelta.test2();
        // StringTest1 test = new StringTest1();
        // TestThread1 test = new TestThread1();
        //  StreamTest1 test = new StreamTest1();
        // LargestSum.test1();
        //  LongestString.test1();
        // TestStream3.test();
        // StringTest1.testEqual();
        // TestCompletable.test5();
        //  TestContainers.test1();
        // TestVirtualJavaThreads.test1();
        // TestMemoryLeak.testMemoryLeak1();
        // TestHashCode.test4();
        //
        try {
            // TestOptimize1.test1();
            TestWordWrap.test2();
        } catch (Exception ex) {
            // ex.fillInStackTrace();
            String stackTrace = stackTraceToString(ex);
            System.out.println("\n[Exception]  " + ex.getMessage() + "\n" + stackTrace);
            // ex.printStackTrace();
        }

        System.out.println("[Done]");
    }

    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
