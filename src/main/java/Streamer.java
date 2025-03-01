import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class  Streamer <TT> {
    public Streamer<TT> root;
    public Streamer<TT> chain;
    public TT item;
    public List<TT> list;
    public List<TT> newList;
    public boolean cancel = false;

    // ---------------

    public interface  Ifilter <TT>  {
        boolean isOkay(TT item );
    }
    public static  class Filterer<TT> extends Streamer<TT> {
        Ifilter<TT> filterIt;

        public Filterer(Streamer<TT> s, Ifilter<TT> filterIt) {
            super(s);
            this.filterIt = filterIt;
        }

        @Override
        public  boolean actOn() {
            return chain.actOn() && filterIt.isOkay(root.item);
        }
    }
    public Streamer<TT> filter(Ifilter<TT> filter) {
        return new Filterer<TT>(this, filter);
    }
    // ---------------

    public interface  Imap <TT>  {
        TT mapIt(TT item );
    };
    public static class Mapper<TT> extends Streamer<TT> {
        Imap<TT> mapIt;
        public Mapper(Streamer<TT> s, Imap<TT> mapIt) {
            super(s);
            this.mapIt = mapIt;
        }

        @Override
        public  boolean actOn() {
            if (! chain.actOn())
                return false;
            root.item = mapIt.mapIt(root.item);
            return true;
        }
    }
    public Streamer<TT> map(Imap<TT> mapper) {
        return new Mapper<TT>(this, mapper);
    }

    // ---------------


    public Streamer(List<TT> list) {
        this.list = list;
        chain = this;
        root = this;
    }

    public Streamer(Streamer<TT> chain) {
        this.chain = chain;
        this.root = chain.root;
    }

    public boolean actOn() {
        return true;
    }

    public Streamer<TT> collect() {
        newList = new ArrayList<>();

        for (TT inItem : root.list) {
            if (root.cancel)
                break;
            root.item = inItem;
            if (actOn()) {
                newList.add(root.item);
            }
        }
        list = newList;
        return this;
    }

    public List<TT> toList() {
        return collect().list;
    }
    public int count() {
        return collect().list.size();
    }

    public  static  <TT> Streamer<TT>  stream(List<TT> list) {
        return new Streamer<TT>(list);
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
