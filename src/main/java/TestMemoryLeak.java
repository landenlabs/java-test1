import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class TestMemoryLeak {

    static class Foo {
        private String value;

        public Foo(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }


    public static void testMemoryLeak1() {
        Set<Foo> set = new HashSet<>();

        IntStream.range(0, 1000 /* Integer.MAX_VALUE */)
                .forEach(it -> {
                    Foo foo = new Foo("KEY");
                    set.add(foo);
                });

        System.out.println("Set size: " + set.size());

        Foo foo1 = new Foo("KEY");
        Foo foo2 = new Foo("KEY");
        System.out.println("Hash foo1=" + foo1.hashCode());
        System.out.println("Hash foo2=" + foo2.hashCode());
        System.out.println("foo1 equal foo2 ? " + foo1.equals(foo2));

    }
}
