import java.util.Collections;
import java.util.List;

public class TestContainers {


    public static void test1() {

        List<Integer> integers = List.of(1, 2, 3, 4);
        List<Integer> integers1 = List.of(5, 6);
        List<Integer> integers2 = List.of(1, 2, 3, 4);
        List<Integer> integers3 = List.of(1, 4);
        List<Integer> integers4 = List.of(0, 1, 4, 5);

        System.out.println(Collections.disjoint(integers1, integers));
        System.out.println(Collections.disjoint(integers2, integers));
        System.out.println(Collections.disjoint(integers3, integers));
        System.out.println(Collections.disjoint(integers4, integers));
    }
}
