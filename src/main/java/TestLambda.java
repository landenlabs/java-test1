import java.util.function.Consumer;
import java.util.function.Function;

public class TestLambda {

    public static void Test1() {

        testEntry(new Entry(null));
        testEntry(new Entry(new Person(new Name("Bob"))));
    }

    static void testEntry(Entry entry) {
        System.out.print("Test ");
        doIt(doIt(doIt(entry,  "getPerson"), "getName"), "getName");
        doFunc(doFunc(doFunc(entry, Entry::getPerson), Person::getName), Name::getName);
        // doFuncs(entry, Entry::getPerson, Person::getName, Name::getName);

        /*
        Optional.ofNullable(entry).map(Entry::getPerson).map(Person::getName).map(Name::getName).orElse(null);

        // Get object in several steps
        String givenName = getNullsafe(entry, e -> e.getPerson(), p -> p.getName(), n -> n.getName());
        // Call void methods
        doNullsafe(entry, e -> e.getPerson(), p -> p.getName(), n -> n.getName());
        */

        System.out.println(" done");
    }

    /** Return result of call to f1 with o1 if it is non-null, otherwise return null. */
    public static <R, T1> R getNullsafe(T1 o1, Function<T1, R> f1) {
        if (o1 != null) return f1.apply(o1);
        return null;
    }

    public static <R, T0, T1> R getNullsafe(T0 o0, Function<T0, T1> f1, Function<T1, R> f2) {
        return getNullsafe(getNullsafe(o0, f1), f2);
    }

    public static <R, T0, T1, T2> R getNullsafe(T0 o0, Function<T0, T1> f1, Function<T1, T2> f2, Function<T2, R> f3) {
        return getNullsafe(getNullsafe(o0, f1, f2), f3);
    }


    /** Call consumer f1 with o1 if it is non-null, otherwise do nothing. */
    public static <T1> void doNullsafe(T1 o1, Consumer<T1> f1) {
        if (o1 != null) f1.accept(o1);
    }

    public static <T0, T1> void doNullsafe(T0 o0, Function<T0, T1> f1, Consumer<T1> f2) {
        doNullsafe(getNullsafe(o0, f1), f2);
    }

    public static <T0, T1, T2> void doNullsafe(T0 o0, Function<T0, T1> f1, Function<T1, T2> f2, Consumer<T2> f3) {
        doNullsafe(getNullsafe(o0, f1, f2), f3);
    }

    public static <T,R> R doFunc(T obj, Function<T,R> func) {
        if (obj != null) {
            return func.apply(obj);
        }
        return null;
    }
    public static void doFuncs(Object obj, Function<Object, Object> ... funcs) {
        for  (Function<Object, Object> f : funcs) {
            if (obj != null) {
                 obj = f.apply(obj);
            }
        }
        return;
    }

    public static <T,R> R doIt(T obj, String methodName) {
        try {
            if (obj != null) return (R)obj.getClass().getDeclaredMethod(methodName).invoke(obj);
        } catch (Exception ignore) {
        }
        return null;
    }

    static class Entry {
        Person person;
        Entry(Person person) { this.person = person; }
        Person getPerson() { return person; }
    }

    static class Person {
        Name name;
        Person(Name name) { this.name = name; }
        Name getName() { return name; }
    }

    static class Name {
        String name;
        Name(String name) { this.name = name; }
        String getName() {
            System.out.print(" Name:" + name + " ");
            return name;
        }
    }
}
