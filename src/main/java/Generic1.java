/**
 * Created by ldennis on 5/2/17.
 */
public class Generic1<T> {
    T mData;
    public  Generic1<T> data(T data) {
        // implementation
        return this;
    }

    public   Generic1<T> error(Throwable error) {
        // implementation
        return this;
    }

    public  Generic1<T> loading() {
        // implementation
        return this;
    }
    
    T getData() {
        return mData;
    }
}

