import java.util.ArrayList;
import java.util.List;

public class TestString3 {

    static final int MAXSTR = 1000;
    static final String TEMP = "temp";
    static final String EXT = ".ext";

    public static List<String> test1() {

        List<String> arrStr = new ArrayList<String>(MAXSTR);

        for (int idx = 0; idx < MAXSTR; idx++) {
            StringBuilder sb = new StringBuilder(TEMP);
            String str = sb.append(String.valueOf(idx)).append(EXT).toString();
            arrStr.add(str);
        }

        return arrStr;
    }

    public static String test2(String str1) {

        // String str1 = null;
        String str2 = "|";
        String str3 = "HelloWorld";

        String result =  new String(str1 + str2 + str3);
        System.out.println(result);
        return result;
    }
}
