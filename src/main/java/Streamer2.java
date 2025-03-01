import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Streamer2<TT> {

    public interface ActOn <TT>{
        boolean act(TT item);
    }
    public ArrayList<ActOn<TT>> actions = new ArrayList<>();

    public TT item;
    public List<TT> inList;
    public List<TT> outList;
    public boolean cancel = false;

    // ---------------

    public interface  Iboolean <TT>  {
        boolean isOkay(TT item );
    }
    public Streamer2<TT> filter(Iboolean<TT> filter) {
        actions.add(filter::isOkay);
        return this;
    }
    // ---------------

    public interface  Ivalue <TT>  {
        TT mapIt(TT item );
    };
    public Streamer2<TT> map(Ivalue<TT> mapper) {
        actions.add( n -> { item = mapper.mapIt(n); return true; });
        return this;
    }

    // ---------------


    public Streamer2(List<TT> list) {
        this.inList = list;
    }


    public List<TT> toList() {
        outList = new ArrayList<>();

        for (TT inItem : inList) {
            if (cancel)
                break;
            item = inItem;
            boolean good = false;
            for (ActOn<TT> actor1 : actions) {
                if (!actor1.act(item))
                    break;
                good = true;
            }
            if (good) {
                outList.add(item);
            }
        }
        return outList;
    }

    public int count() {
        int cnt = 0;
        for (TT inItem : inList) {
            if (cancel)
                break;
            item = inItem;
            for (ActOn<TT> actor1 : actions) {
                if (actor1.act(item)) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public  static  <TT> Streamer2<TT> stream(List<TT> list) {
        return new Streamer2<TT>(list);
    }




    public static void test1() {

        System.out.println("[Start] streamer");
        List<String> list1 = Arrays.asList( "hello", "world", "this", "is", "a", "test");

        List<String> newList =
            stream(list1)
                    .filter(n -> n.startsWith("t"))
                    .map( n -> n.toUpperCase())
                    .toList();

        for (String item : newList) {
            System.out.println(item);
        }

        System.out.println("[End] streamer");
    }
}
