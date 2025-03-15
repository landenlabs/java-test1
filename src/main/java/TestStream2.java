import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestStream2 {


    public static void streamDual1() {
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "mango");

        // First operation with its own stream
        long count = fruits.stream().count();
        System.out.println("Number of fruits: " + count);

        // Second operation with a new stream
        List<String> upperCaseFruits = fruits.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Uppercase fruits: " + upperCaseFruits);
    }

    public static void loopDual1() {
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "mango");

        // Perform both operations in one pass.
        long count = 0;
        List<String> upperCaseFruits = new ArrayList<>(fruits.size());
        for (String fruit : fruits) {
            count++;
            upperCaseFruits.add(fruit.toUpperCase());
        }
        System.out.println("Number of fruits: " + count);
        System.out.println("Uppercase fruits: " + upperCaseFruits);
    }

    public static void test1() {
        streamDual1();
        loopDual1();
    }

}
