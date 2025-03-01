import java.util.*;

/**
 * Created by Dennis Lang on 11/3/16.
 *
 * PlantUML diagram
 *
 * @startuml
 * car --|> wheel
 * @enduml
 */
public class TestMap {

    public static void test1() {
        Map<String, Integer> map1 = new HashMap<>();
        int idx = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            String str = "" + c;
            map1.put(str, idx++);
        }


        remove1(map1, "k");
        remove1(map1, "g");
        remove1(map1, "a");

        System.out.println("Map size=" + map1.size());
        System.out.println(map1);
        System.out.println("TestMap.test1 [Done]");
    }

    /**
     * This approach throws a concurrent access exception.
     * @param map1
     * @param removeKey
     */
    public static void remove1(Map<String, Integer> map1, String removeKey) {
        Set<String> keys = map1.keySet();

        for (String key : keys) {
            if (key.equals(removeKey)) {
                map1.remove(removeKey);
            }
        }
    }

    public static void test2() {
        Map<String, Integer> map1 = new HashMap<>();
        int idx = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            String str = "" + c;
            map1.put(str, idx++);
        }


        remove2(map1, "k");
        remove2(map1, "g");
        remove2(map1, "a");

        System.out.println("Map size=" + map1.size());
        System.out.println(map1);
        System.out.println("TestMap.test2 [Done]");
    }

    /**
     * This method is concurrent safe.
     */
    public static void remove2(Map<String, Integer> map1, String removeKey) {
        Set<String> keys = new HashSet<String>(map1.keySet());

        for (String key : keys) {
            if (key.equals(removeKey)) {
                map1.remove(removeKey);
            }
        }
    }

    /**
     * Java multimap (duplicate keys), minimize storage of value until needed then store data as a set.
     *
     * Get returns single value or Set.
     *
     * By Dennis Lang (www.landenlabs.com)
     */
    @SuppressWarnings("unchecked")
    static class MultiMap<KEY, DATA> extends HashMap<KEY, Object> {

        @Override
        public DATA put(KEY key, Object data) {
            Object currentData = get(key);
            if (currentData == null) {
                super.put(key, data);
            } else if (currentData instanceof Set) {
                ((Set)currentData).add(data);
            } else {
                Set<DATA> dataSet = new HashSet<>();
                dataSet.add((DATA)currentData);
                dataSet.add((DATA)data);
                super.put(key, dataSet);
            }
            return null;
        }

        /**
         * Helper to force return to always be a set.
         */
        Set<DATA> getSet(KEY key) {
            Object currentData = get(key);
            if (currentData == null) {
                return new HashSet<DATA>();
            } else if (currentData instanceof Set) {
                return (Set<DATA>)currentData;
            } else {
                HashSet<DATA> dataSet = new HashSet<>();
                dataSet.add((DATA)currentData);
                return dataSet;
            }
        }
    }

    public static void test3() {
        MultiMap<String, String> mmap = new MultiMap<>();

        mmap.put("to_a", "tornado1 a");
        mmap.put("to_b", "tornado2 b");
        mmap.put("to_c", "tornado3 c");
        mmap.put("to_ca", "tornado4 ca1");
        mmap.put("to_ca", "tornado5 ca2");
        mmap.put("to_d", "tornado6 d");
        mmap.put("to_da", "tornado7 da1");
        mmap.put("to_da", "tornado8 da2");

        System.out.println("[TestMap Test3 - Start]\n");
        System.out.println("  KeyCount=" + mmap.size() + "\n");
        Iterator<Map.Entry<String, Object>> iter = mmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            System.out.println("  Key=" + entry.getKey() + " value=" + entry.getValue());
        }

        System.out.println("\nAfter remove to_c key:");
        // mmap.entrySet().removeIf( entry -> entry.getKey().equals("to_c"));
        // mmap.forEach( (k,v) -> System.out.println("  " + k + " " + v));

        System.out.println("\n[TestMap Test3 - Done]");
    }
}
