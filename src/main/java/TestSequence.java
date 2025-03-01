import utils.ArrayListEx;

public class TestSequence {

    public static void testSequence1() {

        ArrayListEx<Number> list = new ArrayListEx(48, null);

        System.out.println("null=" + WxSamplePeriod.findPeriod(list, 5));

        list.set( 0, 4, 4);
        System.out.println(" 1 " + WxSamplePeriod.findPeriod(list, 5));

        list.set( 5, 3, 3);
        System.out.println(" 2 " + WxSamplePeriod.findPeriod(list, 5));
        list.set( 9, 2, 2);
        System.out.println(" 3 " + WxSamplePeriod.findPeriod(list, 5));
        list.set(15, 5, 5);
        System.out.println(" 4 " + WxSamplePeriod.findPeriod(list, 5));
        list.set(22, 4, 4);
        System.out.println(" 5 " + WxSamplePeriod.findPeriod(list, 5));
        list.set(30, 10, 10);
        System.out.println(" 6 " + WxSamplePeriod.findPeriod(list, 5));
    }

    private static class WxSamplePeriod<T> {
        T first;
        T last;
        int sequenceLength = 0;

        static <T> WxSamplePeriod findPeriod( ArrayListEx<T> samples, int sequenceMinLength) {
            WxSamplePeriod period = new WxSamplePeriod();

            int firstIdx = 0;
            do {
                while (firstIdx < samples.size() && samples.get(firstIdx) == null) {
                    firstIdx++;
                }
                int lastIdx = firstIdx + 1;
                while (lastIdx < samples.size() && samples.get(lastIdx) != null) {
                    lastIdx++;
                }
                if (lastIdx < samples.size() && lastIdx - firstIdx > period.sequenceLength) {
                    period.first = samples.get(firstIdx);
                    period.last =  samples.get(lastIdx);
                    period.sequenceLength = lastIdx - firstIdx;
                }
                firstIdx = lastIdx+1;
            } while (firstIdx < samples.size());

            return (period.sequenceLength >= sequenceMinLength) ? period : null;
        }

        @Override
        public String toString() {
            return String.valueOf(sequenceLength);
        }
    }

}
