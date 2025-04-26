import java.util.HashMap;
import java.util.Map;

/**
 * Medium article
 * https://medium.com/@singourhimanshu3/how-i-made-my-java-code-77x-faster-7000ms-to-90ms-without-changing-much-aad8739e763b
 *
 */
public class TestOptimize1 {

    static int arr[] = new int[] { 5, 5, 5, 5, 5, 4, 3, 2, 1 };

    public static void test1a() {

        int target = 10;
        int count = 0;

        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : arr) {
            int complement = target - num;

            // If complement is already in the map, we found some pairs
            if (freqMap.containsKey(complement)) {
                count += freqMap.get(complement);
            }

            // Update frequency of current number
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        System.out.printf("A Total=%d\n", count );
    }

    public static void test1b() {

        int target = 10;
        int count = 0;

        int[] freqMap = new int[target];

        for (int num : arr) {
            int complement = target - num;

            // If complement is already in the map, we found some pairs
            if (freqMap[complement] != 0) {
                count += freqMap[complement];
            }

            // Update frequency of current number
            freqMap[num]++;
        }

        System.out.printf("B Total=%d\n", count );

    }

    public static void test1() {
        test1a();
        test1b();
    }
}
