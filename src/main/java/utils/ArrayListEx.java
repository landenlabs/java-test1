package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ArrayListEx<E>  extends ArrayList<E> /* implements Parcelable */ {

    @SuppressWarnings("unused")
    public ArrayListEx(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListEx(int initialCapacity, E defObj) {
        super(initialCapacity);
        for (int idx = 0; idx < initialCapacity; idx++) {
            add(defObj);
        }
    }

    public ArrayListEx(Collection<? extends E> collection) {
        super(collection);
    }

    @SafeVarargs
    public ArrayListEx(E... values) {
        addAll(values);
    }

    @SafeVarargs
    public final void addAll(E... values) {
        ensureCapacity(size() + values.length);
        for (E value : values) {
            add(value);
        }
    }

    @SuppressWarnings({"unchecked"})
    public <E> E get(int idx, E defValue) {
        return (idx >= 0 && idx < size()) ? (E)get(idx) : defValue;
    }

    @SuppressWarnings({"unchecked"})
    public <E> E first(E def) {
        return isEmpty() ? def : (E)get(0);
    }


    @SuppressWarnings({"unchecked"})
    public <E> E last(E def) {
        return isEmpty() ? def : (E)get(size()-1);
    }

    public void set(int startIdx, int len, E defObj) {
        for (int idx = startIdx; idx < startIdx+len; idx++) {
            set(idx, defObj);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Static

    @SuppressWarnings("unchecked")
    public static <E,D> D first(ArrayList<E> list, D def) {
        return (list==null || list.isEmpty()) ? def : (D)list.get(0);
    }

    public static  <E> E get(ArrayList<E> list, int idx, E defValue) {
        return (list != null && idx >= 0 && idx < list.size() && list.get(idx) != null)
                ? list.get(idx) : defValue;
    }

    public static  <E extends Number> float getFloat (ArrayList<E> list, int idx, float defValue) {
        return (list != null && idx >= 0 && idx < list.size() &&  list.get(idx) != null)
                ? list.get(idx).floatValue() : defValue;
    }

    @SuppressWarnings("unchecked")
    public static <E,D> D last(ArrayList<E> list, D def) {
        return (list==null || list.isEmpty()) ? def : (D)list.get(list.size()-1);
    }

    public static <E> int size(ArrayList<E> list, int def) {
        return (list==null) ? def : list.size();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public static <E> boolean equals(List<E> list1, List<E> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        return list1.equals(list2);
    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
}
