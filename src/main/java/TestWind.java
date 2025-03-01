/**
 * Created by ldennis on 5/15/17.
 */
public class TestWind {

    public static void WindTest() {

        for (float deg = -380; deg <= 380; deg += 10) {
            int idx1 = (int) ((deg / 22.5 + 0.5) % 16);
            int idx2 = (int) Math.floor(((deg + 11.25) % 360) / 22.5);

            if (idx1 != idx2)
            System.out.printf(" Deg=%.2f,  Idx=%d, Idx=%d\n", deg, idx1, idx2);
        }

        System.out.println("\n[TestWind Done]\n");
    }
}
