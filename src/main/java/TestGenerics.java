import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by ldennis on 4/19/17.
 */
public class TestGenerics {

    static class Base {
        public String name;

        public Base(String name) {
            this.name = name;
        }
    }
    static class Derived extends Base {

        public Derived(String name) {
            super(name);
        }
    }

    static void ProcessGenerics1(Collection<Base> list) {
        System.out.println("ProcessGenerics1 - list of type " + list.getClass().getSimpleName());
    }

    static void ProcessGenerics2(Collection<? extends Base> list) {
        System.out.println("ProcessGenerics2 - list of type " + list.getClass().getSimpleName());
    }

    static void ProcessGenerics3(Base list) {
        System.out.println("ProcessGenerics3 - list of type " + list.getClass().getSimpleName());
    }

    static class List1<E> extends ArrayList<E> {
        public List1(int initialCapacity) {
            super(initialCapacity);
        }

        public static <F extends ArrayList<E>,E> ArrayList<E> merge( F list1, F list2) {
            if (list1 == null) {
                return list2;
            } else if (list2 == null) {
                return list1;
            }

            List1<E> outList= new List1<>( list1.size() + list2.size());
            outList.addAll(list1);
            for (E item : list2) {
                if (!outList.contains(item)) {
                    outList.add(item);
                }
            }

            return outList;
        }
    }

    static class List2<E> extends List1<E> {
        public List2(int initialCapacity) {
            super(initialCapacity);
        }
    }

    static public void TestGeneric1() {
        List<Base> mListBase = new ArrayList<Base>();
        List<Derived> mListDerivedArray = new ArrayList<>();
        List<Derived> mListDerivedLinked = new LinkedList<>();
        List1<Derived> mListDerivedList1 = new List1<>(5);
        List2<Derived> mListDerivedList2 = new List2<>(6);

        ProcessGenerics1(mListBase);
        // ProcessGenerics1(mListDerived);     // <-- Does not compile

        ProcessGenerics2(mListBase);
        ProcessGenerics2(mListDerivedArray);
        ProcessGenerics2(mListDerivedLinked);
        ProcessGenerics2(mListDerivedList1);
        ProcessGenerics2(mListDerivedList2);
        ProcessGenerics2(List1.merge(mListDerivedList1, mListDerivedList2));
        ProcessGenerics2(List1.merge(null, mListDerivedList2));

        Base base = new Base("base");
        Derived derived = new Derived("derived");
        ProcessGenerics3(base);
        ProcessGenerics3(derived);      // <-- Works as exepcted

        /* Does not compile
        List<? extends Base> mListsBasePlus = new ArrayList<>();
        mListsBasePlus.add(new Base("base1"));
        mListsBasePlus.add(new Derived("derived1"));

        List<? extends Object> mListsObjPlus = new ArrayList<>();
        mListsObjPlus.add(new Base("base1"));
        mListsObjPlus.add(new Derived("derived1"));
        */
        
        // List<Base> mListsBase = new ArrayList<>();
        mListBase.add(new Base("base1"));
        mListBase.add(new Derived("derived1"));

        List<Object> mListsObj = new ArrayList<>();
        mListsObj.add(new Base("base1"));
        mListsObj.add(new Derived("derived1"));
    }



    // =================================================================================================================

    public  interface FeatureStyler {
        String getName();
    }

    public static  class OverlaySpecItem  <FeatureStylerT> {
        public final String featureName;
        public  Class<FeatureStylerT> clazz;
        public  FeatureStylerT styler;


        public OverlaySpecItem(String featureName, Class<FeatureStylerT> clazz) {
            this.featureName = featureName;
            this.clazz = clazz;
            this.styler = null;

        }

        public FeatureStylerT getFeatureStyle(Object context) {
            if (styler == null) {
                try {
                    Constructor<?>[] ctor = clazz.getConstructors();

                    System.out.println("ctors=" + ctor);
                    styler =  (FeatureStylerT)ctor[0].newInstance(context);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
            return styler;
        }
    }

    static  <FeatureStylerT> OverlaySpecItem<FeatureStylerT> createSpec(String name, Class<FeatureStylerT> clazz) {
        return new OverlaySpecItem<FeatureStylerT>(name, clazz);
    }

    static class EarthQuakesFeatureStyler implements  FeatureStyler {
        public EarthQuakesFeatureStyler(Object obj1) {
        }
        public String getName() {
            return this.getClass().getSimpleName();
        }
    }

    public static final OverlaySpecItem  earthquakes = createSpec("Earthquakes", EarthQuakesFeatureStyler.class);

    static class LightningFeatureStyler implements  FeatureStyler {
        public LightningFeatureStyler(Objects obj1) {
        }
        public String getName() {
            return this.getClass().getSimpleName();
        }
    }

    public static final OverlaySpecItem<LightningFeatureStyler> lightning =  createSpec("Lightning", LightningFeatureStyler.class);

    static public void TestGeneric2() {

        FeatureStyler foo = (FeatureStyler) lightning.getFeatureStyle(null);
        System.out.println("obj=" + foo.getName());
    }

    // =================================================================================================================

    public  static class IsInstanceTest1<T> {
        T objT;


        public  void something(Object arg) {
            System.out.println("\nclassName1=" + arg.getClass().getSimpleName());

            Type t = objT.getClass().getGenericSuperclass();

            if (arg.getClass().getGenericSuperclass().equals(t)) {
                System.out.println("  Generic1 is a " + this.getClass().getName());
            }

        }
    }

    public static class IsInstanceTest2 {

         public static <T> void something(T arg) {

             System.out.println("\nclassName2=" + arg.getClass().getSimpleName());
             if (arg instanceof Base) {
                 System.out.println("  Generic2 is a Base");
             }
             if (arg instanceof Derived) {
                 System.out.println("  Generic2 is a Derived");
             }
        }
    }

    public static void TestGeneric4() {
        Base base = new Base("base");
        Derived derived = new Derived("derived");

        IsInstanceTest1<Base> isInstanceBase = new IsInstanceTest1<>();
        isInstanceBase.something(base);
        isInstanceBase.something(derived);

        IsInstanceTest1<Derived> isInstanceDerived = new IsInstanceTest1<>();
        isInstanceDerived.something(base);
        isInstanceDerived.something(derived);

        IsInstanceTest2.something(base);
        IsInstanceTest2.something(derived);

        System.out.println("\n[TestGeneric4 - Done]\n");
    }

    // =================================================================================================================

/*
    public static <T extends Base> T castClass(Class<? extends Base> aClass) {
        return (T)aClass;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> cast(Class<?> aClass) {
        return (Class<T>)aClass;
    }

    public static void TestGeneric5() {
        Derived derived1 = new Derived("derived");
        Base base1 = derived1;
    //    Derived derived2 = cast(base1);

    //    Class<List<Object>> clazz = castClass(List.class);
    }

  */

    public static void TestGeneric5() {

        Map<String, String> mapStrStr = new HashMap<>();
        mapStrStr.put("100", "test 100");

        System.out.println("get(100)=" + mapStrStr.get(100));
        System.out.println("get(\"100\")=" + mapStrStr.get("100"));

        System.out.println("\n[TestGeneric5 - Done]\n");
    }
}
