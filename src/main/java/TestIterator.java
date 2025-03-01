
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ldennis on 3/22/17.
 */
public class TestIterator {

    public static void TestSet1() {
        System.out.println("\nTest Set Interator and remove");
        Set<String> mSet = new LinkedHashSet<>();

        mSet.add("  Hello");
        mSet.add("world");
        mSet.add("this");
        mSet.add("is");
        mSet.add("a");
        mSet.add("test");

        Iterator<String> iterator = mSet.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        iterator = mSet.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            System.out.print(str + " ");
            if (str.equals("world")) {
                iterator.remove();
            }
        }
        System.out.println();

        iterator = mSet.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }


    public void iterable1() {
        // https://medium.com/javarevisited/iterate-over-any-iterable-in-java-bec78eeeb452
        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
        iter.forEach(System.out::println);

        List<Integer> list1 = List.of(1, 2, 3);
        System.out.println(list1.stream().map(Objects::toString).collect(Collectors.joining(", ")));
    }

    public static void TestList1() {
        System.out.println("\nTest asList Interator and remove");
        // Next two don't allow remove because asList
        //   List<String> words = Arrays.asList(new String[] {"  Hello", "world", "this", "is", "a", "test"} );
        //   List<String> words = Arrays.asList("  Hello", "world", "this", "is", "a", "test");

        String[] wordArray = new String[] {"  Hello", "world", "this", "is", "a", "test"};
        List<String> words = new ArrayList<>();
        Collections.addAll(words, wordArray);

        /*
        List<String> words = new ArrayList<>();
        words.add("  Hello");
        words.add("world");
        words.add("this");
        words.add("is");
        words.add("a");
        words.add("test");
        */

        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        iterator = words.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            System.out.print(str + " ");
            if (str.equals("world")) {
                iterator.remove();
            }
        }
        System.out.println();

        iterator = words.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}
