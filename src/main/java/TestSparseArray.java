/**
 * Created by ldennis on 3/23/17.
 */
public class TestSparseArray {
    public static void TestSparseArray1() {

        final int DATASOURCE_COUNT = 10;
        final int GROW_BY = 4;

        SparseArray<String> array = new SparseArray<>(DATASOURCE_COUNT);

        array.put(0, "zero");
        array.put(1, "one");

        int startIdx = array.size();
        int endIdx = startIdx + 20;

        for (int idx = startIdx; idx != endIdx; idx++) {
            int size = array.size();
            if (size >= DATASOURCE_COUNT &&
                    (size == DATASOURCE_COUNT || ((size - DATASOURCE_COUNT) % GROW_BY) == 0)) {
                SparseArray<String> tmpArray = new SparseArray<>(array.size() + GROW_BY);
                for (int oldIdx = 0; oldIdx < array.size(); oldIdx++) {
                    int oldKey = array.keyAt(oldIdx);
                    tmpArray.put(oldKey, array.get(oldKey));
                }
                System.out.println("Grow array from " + array.size() + " to " + (array.size() + GROW_BY));
                array = tmpArray;
            }
            array.put(idx, String.valueOf(idx));
            System.out.println(idx + " value=" + array.get(idx) + " size=" + array.size());
        }

        System.out.println("\n [SparseArray TEST done]\n");
    }
}
