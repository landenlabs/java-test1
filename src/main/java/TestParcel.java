

import java.util.ArrayList;
import java.util.List;

public class TestParcel {

    public static void test1(final String s0) {
        /*
        Parcel parcel = Parcel.obtain();
        writeTo1(parcel);
        readFrom1(parcel);

         */
    }

    /*
    private static void readFrom1(Parcel parcel) {
        String s1 = parcel.readString();
        int i2 = parcel.readInt();
        float f3 = parcel.readFloat();
        String s4 = parcel.readString();
        List<Data1> list5 = new ArrayList<>();
        parcel.readList(list5, Data1.class.getClassLoader());

        System.out.println("1="+s1);
        System.out.println("2="+i2);
        System.out.println("3="+f3);
        System.out.println("4="+s4);
        System.out.println("5="+list5);
    }

    private static void writeTo1(Parcel parcel) {
        parcel.writeString("1-hello");
        parcel.writeInt(2);
        parcel.writeFloat(3);
        parcel.writeString("4-world");
        List<Data1> list5 = new ArrayList<>();
        Data1 b5 = new Data1(5, "5-data");
        list5.add(b5);
        parcel.writeTypedList(list5);
    }

    // ===================================================================================================
    public static class Data1 implements Parcelable {

        private final int       mNum;
        private final String    mMsg;


        Data1(int num, String msg) {
            mNum = num;
            mMsg = msg;
        }

        Data1(Parcel src) {
            mNum = src.readInt();
            mMsg = src.readString();
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mNum);
            dest.writeString(mMsg);
        }

        public static final Parcelable.Creator<Data1> CREATOR = new Parcelable.Creator<Data1>() {

            @Override
            public Data1 createFromParcel(Parcel source) {
                return new Data1(source);
            }

            @Override
            public Data1[] newArray(int size) {
                return new Data1[size];
            }
        };
    }
     */
}
