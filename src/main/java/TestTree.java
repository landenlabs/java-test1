import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ldennis on 4/18/16.
 */
public class TestTree {

    static class Item {
        public int mBitRate;
        public int mIdx;

        public Item(int rate, int idx) {
            mBitRate = rate;
            mIdx = idx;
        }
    }

    static class CompareItem implements Comparator<Item> {
        @Override
        public int compare(Item lhs, Item rhs) {
            int lhsBitrate = lhs.mBitRate;
            int rhsBitrate = rhs.mBitRate;
            if ((lhsBitrate > 0) && (rhsBitrate > 0)) {
                return lhsBitrate - rhsBitrate;
            }
            return rhsBitrate - lhsBitrate;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
    }

    public static void Test() {
        SortedSet<Item> mVideoContentSet = new TreeSet<Item>(new CompareItem());

        int idx = 0;
        mVideoContentSet.add(new Item(1000, idx++));
        mVideoContentSet.add(new Item(0, idx++));
        mVideoContentSet.add(new Item(1000, idx++));
        mVideoContentSet.add(new Item(0, idx++));
        mVideoContentSet.add(new Item(100, idx++));
        mVideoContentSet.add(new Item(0, idx++));
        mVideoContentSet.add(new Item(100, idx++));
        mVideoContentSet.add(new Item(0, idx++));
        mVideoContentSet.add(new Item(10, idx++));
        mVideoContentSet.add(new Item(0, idx++));
        mVideoContentSet.add(new Item(10, idx++));
        mVideoContentSet.add(new Item(0, idx++));


        System.out.println("Sorted List - all items");
        for (Item item : mVideoContentSet) {
            System.out.printf("  Idx:%d   Rate:%d\n", item.mIdx, item.mBitRate);
        }

        int headLimit = 200;
        System.out.printf("\nSorted List - less than %d\n", headLimit);
        for (Item item : mVideoContentSet.headSet(new Item(headLimit,0))) {
            System.out.printf("  Idx:%d   Rate:%d\n", item.mIdx, item.mBitRate);
        }

        headLimit = 2000;
        System.out.printf("\nSorted List - less than %d\n", headLimit);
        for (Item item : mVideoContentSet.headSet(new Item(headLimit,0))) {
            System.out.printf("  Idx:%d   Rate:%d\n", item.mIdx, item.mBitRate);
        }

        System.out.println("[Done]");
    }

}
