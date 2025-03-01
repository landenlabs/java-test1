/**
 * Created by ldennis on 1/8/17.
 */
public class TestSyntax {

    public  static <T>  int ArrayFind(T[] array, T find) {
        for (int idx = 0; idx < array.length; idx++){
            if (array[idx] == find)
                return idx;
        }
        return -1;
    }

    public static <T extends Number > int asInt(T idx) {
        return  idx.intValue();
    }

    public void Problem() {

        int[] colors = {1, 2, 3, 4, 5} ;
        int color = 3;
        // int idx1 = ArrayFind(colors, color); // colors not valid type.
        int idx2 = asInt(123.456);
    }

    public void Okay() {

        Integer[] colors = {1, 2, 3, 4, 5} ;
        Integer color = 3;
        int idx = ArrayFind(colors, color);
    }


}
