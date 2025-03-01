import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LargestSum {

    public static int  sum(int maxLen, int[] data) {
        int maxSum = 0, i = 0;
        while (i < maxLen) maxSum += data[i++];
        for(int sum = maxSum; i < data.length; i++) {
            sum += data[i] - data[i-maxLen];
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }

    public static void testIt(int maxLen, int[] data) {
        System.out.printf("Len=%3d Array=%s maxSum=%d\n"
                , maxLen
                ,  Arrays.stream(data)
                        .mapToObj(Integer::toString)
                        .collect(Collectors
                                .joining(",   "))
                , sum(maxLen, data));

    }
    public static void test1() {
        testIt(3, new int[] { 2,1,5,1,3,2 });
        testIt(3, new int[] { 1,2,3,4,5,6 });
        testIt(3, new int[] { 6,5,4,3,2,1 });
    }
}
