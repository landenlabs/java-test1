import org.joda.time.DateTimeZone;
import utils.HashCode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestHashCode {
    
    private static final String WORDS[] = {
            "Menu",
            //   "Bounded views",
            //   "DrawerLayout",
            "Scroll Resize",
            "Expand List",

            "Assorted",
            "Text Alignment",
            "TextSize",

            "GridView Images",
            "GridLayout",
            "Image Scales",
            "Image Overlap",
            "Image Blend",
            "Drawables",

            "RadioBtn Tabs",
            "RadioBtn List",
            "TextView+Image",
            "CkBox List",
            "Custom List",
            "RecyclerView",

            "Toggle/Switch",
            "Checkbox Right",
            "Checkbox Left",

            "Animated Vector",
            "Animation",
            "Anim Bg",

            "SeekBar",
            "Screen Draw",
            "DragView",

            "Relative Layout",
            "Constraint Layout ",
            "Other Layout",
            "Bounded views",
            "Scaled Btn",
            "Layout Anim",
            "Full Screen",
            "Rotate Screen",

            "ElevShadow (API21)",
            "Coordinated (API21)",
            "TabLayout (API21)",
            "Ripple",

            "GL Cube",
            "Graph Line",

            "View Shadows",
            "Render Blur"
    };

    public static void test1() {
        Map<String, String> testHash = new HashMap<>();
        for (String word : WORDS) {
            testHash.put(word, word);
        }

        System.out.println("String len=" + WORDS.length + " Hash Map size=" + WORDS.length);
        for (Map.Entry<String, String> entry : testHash.entrySet()) {
            if (!entry.getKey().equals(entry.getValue())) {
                System.out.println("Hash value wrong key=" + entry.getKey() + " value=" + entry.getValue());
            }
        }
    }

    public static void test2() {
        System.out.println("Hash empty string=" + "".hashCode());
    }


    // -----------------------------------------------------------------------------------------------------------------

    private static void append(StringBuilder sb, int hex) {
        sb.append( (hex < 10) ? (char)('0' + hex) : (char)('a' + hex - 10));
    }
    private static String convertToHex( byte[] data ) {
        StringBuilder buf =  new StringBuilder(41); // a normal sha1 hash is 40 bytes long
        for ( Byte aData : data) {
            append(buf,  (aData >> 4) & 0x0F);
            append(buf, aData & 0x0F);
        }
        return buf.toString();
    }
    private static String getSHA1(String text)  {
        if (text == null)
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
            byte[] hash = md.digest();
            return convertToHex(hash);
        } catch (Exception ex) {
            System.err.println("Error generating generating SHA-1: $ex");
        }
        return "";
    }

    public static void test3() {
        String adID = "A566153D-CBE9-41F6-9125-10E8B5E1DF46";

        System.out.println("Ad ID=" + adID);
        System.out.println(" sha1 hash=" + getSHA1(adID));
        System.out.println(" sha1 hash=" + HashCode.fromString(adID));
    }

    public static void test4() {
        Set<Long> dupHash = new HashSet<>(90*360*100);

        for (int latDeg = -9000 ; latDeg < 9000; latDeg +=1) {
            for (int lngDeg = -18000; lngDeg < 18000; lngDeg +=1) {
                long key = lngDeg * 10000L + latDeg;
                // int hash = Long.hashCode(key);
                if (dupHash.contains(key))
                    System.out.printf("Duplicate hash %d, %d\n", latDeg, lngDeg );
                dupHash.add(key);
            }
        }
        System.out.println("Test3 [Done]" );

    }
}
