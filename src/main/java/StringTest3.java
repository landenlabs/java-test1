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

	public static void test2() {
		//
 		// Medium article
		//  https://medium.com/@rabinarayandev/how-to-create-a-diamond-star-pattern-in-java-complete-guide-for-beginners-a8a8dddf4521
		//
		int rows = 5;  // Total number of rows for the diamond

		// Top Pyramid
		for (int i = 1; i <= rows; i++) {
			// Print leading spaces
			for (int j = rows; j > i; j--) {
				System.out.print(" ");
			}

			// Print stars
			for (int k = 1; k <= i; k++) {
				System.out.print("* ");
			}

			// Move to the next line after each row
			System.out.println();
		}

		// Bottom Inverted Pyramid
		for (int i = rows - 1; i >= 1; i--) {
			// Print leading spaces
			for (int j = rows; j > i; j--) {
				System.out.print(" ");
			}

			// Print stars
			for (int k = 1; k <= i; k++) {
				System.out.print("* ");
			}

			// Move to the next line after each row
			System.out.println();
		}

		System.out.println("-".repeat(10));
		for (int i = 1; i <= rows; i++) {
			System.out.println(" ".repeat(rows - i) + "* ".repeat(i));
		}
		for (int i = rows-1; i >=0; i--) {
			System.out.println(" ".repeat(rows - i) + "* ".repeat(i));
		}

		System.out.println("-".repeat(10));
		for (int i = 1; i <= rows*2-1; i++) {
			int j = Math.min(rows, i) + Math.min(0, rows - i);
			System.out.println(" ".repeat(rows - j) + "* ".repeat(j));
		}
	}
}
