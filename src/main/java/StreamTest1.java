import java.util.regex.Pattern;

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
}
