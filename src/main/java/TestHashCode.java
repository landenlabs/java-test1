import java.util.HashMap;
import java.util.Map;

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
}
