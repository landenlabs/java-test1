import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class StringTest1 {

	public boolean isSpecialWord(String find) {
		String[] arrStr = new String[20];
		arrStr[0] = "World";
		arrStr[1] = "Hello";
		arrStr[3] = "Bye";
		
		for (int idx = 0; idx != arrStr.length; idx++) {
			if (arrStr[idx] == find)
				return true;
		}

		int number = 10;
		Integer inum = number;
		System.out.println(inum);

		String joined = String.join(",", arrStr);

		return false;
	}
	
	public  void test1()
	{
		if (isSpecialWord("hello")) {
			System.out.println("hello is special");
		} else {
			System.out.println("hello is NOT special");
		}
		
		if (isSpecialWord("Hello")) {
			System.out.println("Hello is special");
		} else {
			System.out.println("Hello is NOT special");
		}
	}
	
	public void test2()
	{
		List<String> strList = new ArrayList<String>();
		for (int idx = 0; idx != 100; idx++) {
			String str = "tmp" + idx + ".ext";
			strList.add(str);
		}
	}

	public void test3() {
		showParts("Fist part of address, Second part, of, address");
		showParts("Fist part of address");
		showParts(", Second part, of, address");
		showParts(",");
		showParts("");
	}
	public static void showParts(String address) {
		int pos = address.indexOf(",");
		String part1 = ((pos == -1) ? address : address.substring(0,pos));
		String part2 = (pos == -1) ? "" : address.substring(address.indexOf(",")+1);
		System.out.println("\nInput address=[" + address + "]");
		System.out.println("       1st part=[" + part1.trim() + "]");
		System.out.println("       2nd part=[" + part2.trim() + "]");
	}

	/**
	 * Return front or back of address, where first comma splits the two areas.
	 */
	/*
	public static String getAddressPart(String address, boolean firstPart, String defaultResult) {
		if (TextUtils.isEmpty(address)) {
			return defaultResult;
		}
		int pos = address.indexOf(",");
		return firstPart
		 ? ((pos == -1) ? address : address.substring(0,pos)).trim()
		 : (pos == -1) ? "" : address.substring(address.indexOf(",")+1).trim();
	}
	 */
}
