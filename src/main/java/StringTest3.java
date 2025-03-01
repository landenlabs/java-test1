import java.util.ArrayList;
import java.util.List;


public class StringTest3 {
	static final int MAXSTR = 1000;
	static final String TEMP = "temp";
	static final String EXT = "ext";
	
	public static List<String> test1() {
		
		
		List<String> arrStr = new ArrayList<String>(MAXSTR);
		
		for (int idx = 0; idx < MAXSTR; idx++) {
			StringBuilder sb = new StringBuilder(TEMP);
			String str = sb.append(String.valueOf(idx)).append(EXT).toString();
			arrStr.add(str);
		}
		
		return arrStr;
	}
}
