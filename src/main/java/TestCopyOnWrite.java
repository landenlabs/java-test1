import java.util.ArrayList;

/**
 * Created by ldennis on 3/16/17.
 */
public class TestCopyOnWrite {

    public interface OnGlobalLayoutListener {
        /**
         * Callback method to be invoked when the global layout state or the visibility of views
         * within the view tree changes
         */
        public void onGlobalLayout();
    }

    /**
     * Copy on write array. This array is not thread safe, and only one loop can
     * iterate over this array at any given time. This class avoids allocations
     * until a concurrent modification happens.
     *
     * Usage:
     *
     * CopyOnWriteArray.Access<MyData> access = array.start();
     * try {
     *     for (int i = 0; i < access.size(); i++) {
     *         MyData d = access.get(i);
     *     }
     * } finally {
     *     access.end();
     * }
     */
    static class CopyOnWriteArray<T> {
        private ArrayList<T> mData = new ArrayList<T>();
        private ArrayList<T> mDataCopy;

        private final Access<T> mAccess = new Access<T>();

        private boolean mStart;

        static class Access<T> {
            private ArrayList<T> mData;
            private int mSize;

            T get(int index) {
                return mData.get(index);
            }

            int size() {
                return mSize;
            }
        }

        CopyOnWriteArray() {
        }

        private ArrayList<T> getArray() {
            if (mStart) {
                if (mDataCopy == null) mDataCopy = new ArrayList<T>(mData);
                return mDataCopy;
            }
            return mData;
        }

        Access<T> start() {
            if (mStart) throw new IllegalStateException("Iteration already started");
            mStart = true;
            mDataCopy = null;
            mAccess.mData = mData;
            mAccess.mSize = mData.size();
            return mAccess;
        }

        void end() {
            if (!mStart) throw new IllegalStateException("Iteration not started");
            mStart = false;
            if (mDataCopy != null) {
                mData = mDataCopy;
                mAccess.mData.clear();
                mAccess.mSize = 0;
            }
            mDataCopy = null;
        }

        int size() {
            return getArray().size();
        }

        void add(T item) {
            getArray().add(item);
        }

        void addAll(CopyOnWriteArray<T> array) {
            getArray().addAll(array.mData);
        }

        void remove(T item) {
            getArray().remove(item);
        }

        void clear() {
            getArray().clear();
        }
    }

    static class  ViewTreeObserver {

        // Non-recursive listeners use CopyOnWriteArray
        // Any listener invoked from ViewRootImpl.performTraversals() should not be recursive
        private CopyOnWriteArray<OnGlobalLayoutListener> mOnGlobalLayoutListeners;

        private boolean mAlive = true;

        /**
         * Register a callback to be invoked when the global layout state or the visibility of views
         * within the view tree changes
         *
         * @param listener The callback to add
         *
         * @throws IllegalStateException If {@link #isAlive()} returns false
         */
        public void addOnGlobalLayoutListener(OnGlobalLayoutListener listener) {
            // checkIsAlive();

            if (mOnGlobalLayoutListeners == null) {
                mOnGlobalLayoutListeners = new CopyOnWriteArray<OnGlobalLayoutListener>();
            }

            mOnGlobalLayoutListeners.add(listener);
        }

        /**
         * Remove a previously installed global layout callback
         *
         * @param victim The callback to remove
         *
         * @throws IllegalStateException If {@link #isAlive()} returns false
         *
         * @see #addOnGlobalLayoutListener(OnGlobalLayoutListener)
         */
        public void removeOnGlobalLayoutListener(OnGlobalLayoutListener victim) {
            // checkIsAlive();
            if (mOnGlobalLayoutListeners == null) {
                return;
            }
            mOnGlobalLayoutListeners.remove(victim);
        }

        /**
         * Notifies registered listeners that a global layout happened. This can be called
         * manually if you are forcing a layout on a View or a hierarchy of Views that are
         * not attached to a Window or in the GONE state.
         */
        public final void dispatchOnGlobalLayout() {
            // NOTE: because of the use of CopyOnWriteArrayList, we *must* use an iterator to
            // perform the dispatching. The iterator is a safe guard against listeners that
            // could mutate the list by calling the various add/remove methods. This prevents
            // the array from being modified while we iterate it.
            final CopyOnWriteArray<OnGlobalLayoutListener> listeners = mOnGlobalLayoutListeners;
            if (listeners != null && listeners.size() > 0) {
                CopyOnWriteArray.Access<OnGlobalLayoutListener> access = listeners.start();
                try {
                    int count = access.size();
                    for (int i = 0; i < count; i++) {
                        access.get(i).onGlobalLayout();
                    }
                } finally {
                    listeners.end();
                }
            }
        }

    }

    static class GlobalLayoutListener implements OnGlobalLayoutListener {
        static int counter = 0;
        @Override
        public void onGlobalLayout() {
            counter++;
            System.out.println("Global ctor " + counter);
        }

        @Override
        protected void finalize() throws Throwable {
            counter--;
            System.out.println("Global dtor " + counter);
            super.finalize();
        }
    }

    public static void TestCOW1() {

        ViewTreeObserver viewTreeObserver = new ViewTreeObserver();

        GlobalLayoutListener listener1 = new GlobalLayoutListener();

        viewTreeObserver.addOnGlobalLayoutListener(listener1);
        viewTreeObserver.dispatchOnGlobalLayout();
        viewTreeObserver.removeOnGlobalLayoutListener(listener1);

        System.gc();
        System.out.println("--Remove called--");
        viewTreeObserver.dispatchOnGlobalLayout();

        System.gc();
        System.out.println("--dispatchOnGlobalLayout called--");

        System.out.println("[ TestCOW1] done");
    }
}
