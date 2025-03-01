import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dennis Lang on 10/30/16.
 */
public class TestJoin {

    public static String[] split(String string, char delimiter, int estimate) {
        List<String> words = new ArrayList<String>(estimate);
        int start = 0;
        int end;
        while ((end = string.indexOf(delimiter, start)) != -1) {
            end = string.indexOf(delimiter, start);
            words.add(string.substring(start, end));
            start = end + 1;
        }
        words.add(string.substring(start, string.length()));
        String[] resultArray = new String[words.size()];
        return words.toArray(resultArray);
    }

    /**
     * Joins the given iterable objects using the given separator into a single string.
     *
     * @return the joined string or an empty string if iterable is null
     */
    public static String join(Iterable<?> iterable, String separator) {
        if (iterable != null) {
            StringBuilder buf = new StringBuilder();
            Iterator<?> it = iterable.iterator();
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            while (it.hasNext()) {
                buf.append(it.next());
                if (it.hasNext()) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    /**
     * Joins the given ints using the given separator into a single string.
     *
     * @return the joined string or an empty string if the int array is null
     */
    public static String join(int[] array, String separator) {
        if (array != null) {
            StringBuilder buf = new StringBuilder(Math.max(16, (separator.length() + 1) * array.length));
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
                buf.append(array[i]);
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    /**
     * Joins the given Strings using the given separator into a single string.
     *
     * @return the joined string or an empty string if the String array is null
     */
    public static String join(String[] array, String separator) {
        if (array != null) {
            StringBuilder buf = new StringBuilder(Math.max(16, (separator.length() + 1) * array.length));
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
                buf.append(array[i]);
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    public static void test1() {
        System.gc();
        long start = System.currentTimeMillis();

        long duration = System.currentTimeMillis() - start;
    }
}
