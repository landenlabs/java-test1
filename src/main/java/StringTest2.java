import java.util.ArrayList;
import java.util.List;


public class StringTest2 {

	public static List<String> test1() {
		final int MAXSTR = 1000;
		
		List<String> arrStr = new ArrayList<String>();
		
		for (int idx = 0; idx < MAXSTR; idx++) {
			String str = "temp" + idx + ".ext";
			arrStr.add(str);
		}
		
		return arrStr;
	}
	
	
}
