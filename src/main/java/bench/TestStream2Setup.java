package bench;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*
  Options
  https://github.com/melix/jmh-gradle-plugin#configuration-options
 */
public class TestStream2Setup {
    public static final List<String> persons = new ArrayList<>();

    public static List<String> get(int N) {
        setup(N);
        return persons;
    }

    public static void setup(int N) {
        final int MIN_LEN = 3;
        final int MAX_LEN = 10;
        final int FLEX_LEN = MAX_LEN - MIN_LEN;
        final byte MIN_CHAR = 'A';
        final byte MAX_CHAR = '~';
        final byte FLEX_CHAR = MAX_CHAR - MIN_CHAR;
        final Charset CHARSET = Charset.forName("UTF-8");
        if (persons.size() != N) {
            persons.clear();
            int cnt = Math.max(10, N);
            byte[] chars = new byte[MAX_LEN];

            for (int idx = 0; idx < cnt; idx++) {
                int chrLen = MIN_LEN + (int) Math.round(Math.random() * FLEX_LEN);
                for (int chrIdx = 0; chrIdx < chrLen; chrIdx++) {
                    chars[chrIdx] = (byte) (MIN_CHAR + (byte) Math.round(Math.random() * FLEX_CHAR));
                }
                persons.add(new String(chars, 0, chrLen, CHARSET));
            }

            System.out.println("---------- Setup[" + N + "]:");
            for (String str : persons) System.out.print(str);
            System.out.println();
        }
    }
}
