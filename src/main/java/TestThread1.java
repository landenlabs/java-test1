import java.util.ArrayList;
import java.util.function.Consumer;

public class TestThread1 {

    static class Buffer {
        private final ArrayList<Integer> list = new ArrayList<>(); // common buffer
        private final int capacity;
        volatile public boolean isActive = true;

        public Buffer(int capacity) {
            this.capacity = capacity;
        }

        synchronized
        public void remove(int consumerId)  throws InterruptedException {
            if (!list.isEmpty()) {
                Integer value = list.get(0);
                list.remove(0);
                System.out.println("Removed=" + value + " size=" + list.size());
                notifyAll();
            } else {
                System.out.println("Buffer empty");
                wait();
            }
        }

        synchronized
        public void add(int item, int producerId) throws InterruptedException  {
            if (list.size() < capacity) {
                list.add(item);
                System.out.println("Added=" + item + " size=" + list.size());
                notifyAll();
            } else {
                System.out.println("Buffer full");
                wait();
            }
        }
    }

    static class Producer extends Thread {
        private final Buffer buffer;
        private final int id;

        public Producer(Buffer buffer, int id) {
            this.buffer = buffer;
            this.id = id;
        }

        @Override
        public void run() {
            buffer.isActive = true;
            for (int i = 0; i < 10; i++) {
                try {
                    this.buffer.add(i, this.id);
                    sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            buffer.isActive = false;
        }
    }

    static class  Consumer extends Thread {
        private final Buffer buffer;
        private final int id;

        public Consumer(Buffer buffer, int id) {
            this.buffer = buffer;
            this.id = id;
        }

        @Override
        public void run() {
            while (buffer.isActive)
                try {
                    this.buffer.remove(this.id);
                    sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
        }
    }

    public static void test1() {
        Buffer b = new Buffer(5);
        Producer p1 = new Producer(b, 1);
        Consumer c1 = new Consumer(b, 1);
        Consumer c2 = new Consumer(b, 2);
        p1.start();
        c1.start();
        c2.start();
    }
}
