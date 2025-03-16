import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StreamTest1 {

    // https://betterprogramming.pub/modern-java-methods-for-processing-text-with-streams-and-lambdas-135273475057
    public static void test1() {
        var str = """
         List<String> results = stream
                .filter(s -> pattern.matcher(s).matches()).toList();
                """;

        var words = Pattern.compile("[^\\p{L}]+")  // not letters
                .splitAsStream(str).distinct().sorted().toList();

        System.out.println(words);
        // [List, String, filter, matcher, matches, pattern, results, s, stream, toList]
    }

    public static void test2() {
        // Medium article
        // https://medium.com/@tuteja_lovish/10-java-power-tricks-to-code-like-a-rockstar-6cc054e7ebe7

        List<String> items = Arrays.asList("apple", "banana", "cherry");

        Map<String, Integer> itemLengthMap1 = items.stream()
                .collect(Collectors.toMap(Function.identity(), String::length));

        System.out.println(itemLengthMap1);
        // Output: {apple=5, banana=6, cherry=6}

        // --
        Map<String, List<String>> map = new HashMap<>();

        map.computeIfAbsent("Java", k -> new ArrayList<>()).add("Streams API");
        map.computeIfAbsent("Java", k -> new ArrayList<>()).add("Lambdas");

        System.out.println(map);
        // Output: {Java=[Streams API, Lambdas]}

        // ---
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("Java", 10);
        map1.put("Spring", 8);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("Java", 5);
        map2.put("Hibernate", 7);

        map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));

        System.out.println(map1);
        // Output: {Java=15, Spring=8, Hibernate=7}

        // ---
        String input = "swiss";
        Optional<Character> firstUnique = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();

        System.out.println(firstUnique.orElse(null)); // Output: w

        Set<Character> dupChar = new HashSet<>();
        Set<Character> uniqueChar = new LinkedHashSet<>();
        for (char c : input.toCharArray()) {
            if (uniqueChar.contains(c))
                uniqueChar.remove(c);
            else if (!dupChar.contains(c)) {
                uniqueChar.add(c);
                dupChar.add(c);
            }
        }
        System.out.println( uniqueChar.isEmpty() ? "none" : uniqueChar.iterator().next().toString());


        // ---
        {
            String findFirstLeastCommonChar = "this is a test of the pass filters.";
            Map<Character, Integer> charCnt = new LinkedHashMap<>();
            TreeMap<Integer, Character> cntChar = new TreeMap<>();
            for (char c : findFirstLeastCommonChar.toCharArray()) {
                int cnt = charCnt.computeIfAbsent(c, key -> 0) + 1;
                charCnt.put(c, cnt);
            }
            for (Map.Entry<Character, Integer> item : charCnt.entrySet()) {
                if (!cntChar.containsKey(item.getValue()))
                    cntChar.put(item.getValue(), item.getKey());
            }
            System.out.println( cntChar.firstEntry());
        }

        // ---
        {
            List<String> users = Arrays.asList("john", "steve", "jack", "john", "peter", "john");
            List<String> uniqueUsers = users.stream()
                    .distinct()
                    .toList();
            System.out.println("unique users=" + uniqueUsers);
        }
    }
}
