import java.lang.reflect.Array;

/**
 * Created by ldennis on 3/23/17.
 */
public class ArrayUtils {

    public static <T> T[] newUnpaddedArray(Class<T> clazz, int minLen) {
        System.out.println("Allocation T Array size=" + minLen);
        return (T[]) Array.newInstance(clazz, minLen);
    }
    public static int[] newUnpaddedIntArray(int minLen) {
        System.out.println("Allocation Int Array size=" + minLen);
        return new int[minLen];
    }

    public static long[] newUnpaddedLongArray(int minLen) {
        return new long[minLen];
    }

    public static boolean[] newUnpaddedBooleanArray(int minLen) {
        return new boolean[minLen];
    }

    public static Object[] newUnpaddedObjectArray(int minLen) {
        System.out.println("Allocation Object Array size=" + minLen);
        return new Object[minLen];
    }
}