import java.util.ArrayList;
import java.util.List;

public class Generic2 {

    public void test() {

        List<Object> listObj = new ArrayList<>();
        List<String> listStr = new ArrayList<>();

        listObj.add(listStr);

        /*
        // Does not compile, in correct type, listObj returns an object
        List<String> prefListStr = listObj.get(0);

        // Lint fails - unsafe cast
        List<String> prefListStr = (List<String>)listObj.get(0);

        // instanceof does not support generics
        if (listObj.get(0) instanceof List<String>) {
            List<String> prefListStr = (List<String>)listObj.get(0);
        }

        // Does not help with cast safety
        if (listObj.get(0) instanceof List<?>) {
            List<String> prefListStr = (List<String>)listObj.get(0);
        }
        */
    }
}
