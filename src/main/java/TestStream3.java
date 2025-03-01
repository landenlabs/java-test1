import org.openjdk.jmh.infra.Blackhole;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestStream3 {

    // https://medium.com/@Athula.B/java-heap-memory-candidates-6785c84ae09d
    private static MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    public static boolean isPerfectSquare(int value) {
        int root = (int)Math.sqrt(value);
        return (root*root == value);
    }

    public static void test1loop(List<Integer> list) {
        System.out.println("Test1loop [Start]");
        MemoryUsage heapMemoryUsage1 = memoryMXBean.getHeapMemoryUsage();

        List<Integer> ans1 = new ArrayList<>();
        for (int value : list)
            if ((value % 2) == 0 || isPerfectSquare(value))
                ans1.add(value);

        System.out.println( ans1.size() + " ForLoop results"); // String.join(",", ans.toString()));
        MemoryUsage heapMemoryUsage2 = memoryMXBean.getHeapMemoryUsage();
        System.out.println("  Heap Used (before,after): " + String.join(","
                , formatBytes(heapMemoryUsage1.getUsed())
                , formatBytes(heapMemoryUsage2.getUsed())
                ));

        System.out.println("Test1loop [Done] ");
    }
    public static void test1stream(List<Integer> list) {
        System.out.println("Test1stream [Start]");
        MemoryUsage heapMemoryUsage1 = memoryMXBean.getHeapMemoryUsage();


        List<Integer> ans2 = list.stream()
                .filter(value -> value % 2 == 0 || isPerfectSquare(value))
                .collect(Collectors.toList());

        System.out.println(ans2.size() + " StreamFilter results"); // String.join(",", ans.toString()));
        MemoryUsage heapMemoryUsage2 = memoryMXBean.getHeapMemoryUsage();

        System.out.println("  Heap Used (before,after): " + String.join(","
                , formatBytes(heapMemoryUsage1.getUsed())
                , formatBytes(heapMemoryUsage2.getUsed())
        ));

        System.out.println("Test1 [Done] ");
    }
    public static void test2() {
        System.out.println("Test2 [Start]");
        List<String> cities = List.of("delhi", "udaipur", "jaipur", "dehradun");
        MemoryUsage heapMemoryUsage1 = memoryMXBean.getHeapMemoryUsage();

        List<String> filteredCities1 = new ArrayList<>();
        for (String city : cities) {
            if (city.startsWith("d"))
                filteredCities1.add(city.toUpperCase());
        }

        System.out.println(" Stream filteredCities=" + String.join(",", filteredCities1.toString()));
        MemoryUsage heapMemoryUsage2 = memoryMXBean.getHeapMemoryUsage();

        List<String> filteredCities2 = cities.stream()
                .filter(city -> city.startsWith("d"))
                .map(city -> city.toUpperCase())
                .toList();
        System.out.println("   Loop filteredCities=" + String.join(",", filteredCities2.toString()));
        MemoryUsage heapMemoryUsage3 = memoryMXBean.getHeapMemoryUsage();

        System.out.println("  Heap Used (setup,loop,stream): " + String.join(","
                , formatBytes(heapMemoryUsage1.getUsed())
                , formatBytes(heapMemoryUsage2.getUsed())
                , formatBytes(heapMemoryUsage3.getUsed())
        ));
        System.out.println("Test2 [Done]");
    }

    public static void test3() {
        System.out.println("Test3 [Start]");
        List<String> cities = List.of("delhi", "udaipur", "jaipur", "dehradun");

        List<String> filteredCities1 = cities.stream()
                .filter(city -> city.startsWith("d"))
                .map(city -> city.toUpperCase())
                .toList();
        System.out.println(" Stream filteredCities=" + String.join(",", filteredCities1.toString()));

        List<String> filteredCities2d = new ArrayList<>();
        List<String> filteredCities2j = new ArrayList<>();
        List<String> filteredCities2u = new ArrayList<>();
        for (String city : cities) {
            switch (city.charAt(0)) {
                case 'd' -> filteredCities2d.add(city.toUpperCase());
                case 'j' -> filteredCities2j.add(city.toUpperCase());
                case 'u' -> filteredCities2u.add(city.toUpperCase());
            }
        }
        System.out.println("   Loop filteredCities d=" + String.join(",", filteredCities2d.toString()));
        System.out.println("   Loop filteredCities j=" + String.join(",", filteredCities2j.toString()));
        System.out.println("   Loop filteredCities u=" + String.join(",", filteredCities2u.toString()));

        System.out.println("Test3 [Done]");
    }

    private static String formatBytes(long bytes)  {
        long megabytes = bytes / 1024 / 1024;
        return megabytes + " MB";
        // return String.valueOf(bytes);
    }

    public static void junk() {

        Set<Integer> iset = new HashSet<>();
    }

    public static void test() {
        memoryMXBean = ManagementFactory.getMemoryMXBean();

        final int LIST_SIZE = 1000;
        List<Integer> list = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++)
            list.add(i);

        test1loop(list);
        test1stream(list);

        test2();
        test3();

        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("  Heap Used: " + formatBytes(heapMemoryUsage.getUsed()));
    }
}
