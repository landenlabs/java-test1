import java.util.*;

public class LongestString {

    // https://medium.com/@vikas.taank_40391/interview-questions-longest-substring-in-a-string-b9aaa5d03d51
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> currentCharMap = new HashMap<>();
        int maxSubstringLength = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);
            if (currentCharMap.containsKey(currentChar)) {
                start = Math.max(currentCharMap.get(currentChar) + 1, start);
            }
            currentCharMap.put(currentChar, end);
            maxSubstringLength = Math.max(maxSubstringLength, end - start + 1);
        }
        return maxSubstringLength;
    }

    public static int find(char[] inArray, int idx, int end, char findChar) {
        while (idx != end) {
            if (inArray[idx] == findChar) return idx;
            idx = (idx+1)%inArray.length;
        }
        return -1;
    }

    public static int lengthUniqueSubstring(String str, int maxLength) {
        char[] uniqueRingBuffer = new char[maxLength]; // Could use ArrayList for dynamic sizing.
        int bIdx = 0;   // Begin Ring buffer
        int eIdx = 0;   // End Ring buffer

        int foundMaxLen = 0;
        int idx = 0;
        while (idx < str.length() - foundMaxLen) {
            char currentChar = str.charAt(idx++);
            int dupAt = find(uniqueRingBuffer, bIdx, eIdx, currentChar);
            if (dupAt != -1) {
                foundMaxLen = Math.max(foundMaxLen, (eIdx - bIdx + maxLength) % maxLength);
                bIdx = (dupAt+1) % maxLength;
            }
            uniqueRingBuffer[eIdx++] = currentChar;
            eIdx %= maxLength;
            assert(bIdx != eIdx);   // unique array not long enough to hold match
        }

        return foundMaxLen;
    }

    public static void testLongest(String exampleString) {
        System.out.println("Length of the longest substring of " + exampleString);
        System.out.println(lengthOfLongestSubstring(exampleString) + "\tlengthOfLongestSubstring");
        System.out.println(lengthUniqueSubstring(exampleString, exampleString.length()) + "\tlengthUniqueSubstring");
    }

    public static void test1() {
        testLongest("abcabcbba");
        testLongest("abcbcdecabcdefcabcfedcba");
        testLongest("24643212345432123456543234");
    }
}
