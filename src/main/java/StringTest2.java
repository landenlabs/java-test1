import android.os.SystemPropertiesProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Test xor character set to encrypt filenames.
 */
public class StringTest2 {

    // Normal sequence
    // static const char shiftSet[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    // Drop a few character
    //                              1234567890123456789012345678901234567890123456789012345678901234
    private static final String shiftSet = "013456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefhijklmnopqrstuvwxyz+-[]_"; // 64 = power of 2
    private static final int shiftLen = shiftSet.length();

    static String shiftAlphaNumeric(String inPath, int shiftBy) {
        assert (shiftBy < shiftLen);
        assert (shiftBy != 0);
        assert (shiftLen == 64);  // xor requires power of 2.
        int len = inPath.length();
        char[] outPath = new char[len];

        for (int idx = 0; idx < len; idx++) {
            int pos = shiftSet.indexOf(inPath.charAt(idx));
            if (pos != -1) {
                outPath[idx] = shiftSet.charAt((pos ^ shiftBy) % shiftLen);
            } else {
                outPath[idx] = inPath.charAt(idx);
            }
        }
        return new String(outPath);
    }

    public static void tester(String inStr, int maxShifter) {
        int errCnt = 0;
        System.out.println(inStr);
        for (int shifter = 1; shifter < maxShifter; shifter++) {
            String outStr = shiftAlphaNumeric(inStr, shifter);
            String orgStr = shiftAlphaNumeric(outStr, shifter);
            if (inStr.equals(orgStr))
                System.out.printf("  %3d: '%s' \n", shifter, outStr);
            else {
                System.err.printf("  %3d: '%s' '%s'  FAILED\n\007 ", shifter, outStr, orgStr);
                errCnt++;
            }
        }

        if (errCnt != 0) {
            System.err.printf("\007 Round trip conversion failed %d times \n\007 ", errCnt);
        }
    }
	public static void test1() {

        tester("Hello World 1234", 20);
        tester("Test File1.txt", 20);
        tester("0123456789 !@#$%^&*()_+-=", 20);
        tester("abcdefghijklmnopqrstuvwxyz", 20);
        tester("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 20);
	}
}
