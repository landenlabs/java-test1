

/**
 *  
 */
public class WTimeUnit {
    // WTimeUnit.toSeconds.fromHours(10.5);
    enum ToUnits {
        toMilli {
            public double fromMillis(double millis) {
                return millis;
            }
            public double fromSeconds(double seconds) { return seconds*1000; }
            public double fromMinutes(double minutes) { return minutes*60*1000; }
            public double fromHours(double hours) { return hours*3600*1000; }
        }, toSeconds {
            public double fromMillis(double millis) { return millis/1000; }
            public double fromSeconds(double seconds) { return seconds; }
            public double fromMinutes(double minutes) { return minutes*60; }
            public double fromHours(double hours) { return hours*3600; }
        }, toMinutes {
            public double fromMillis(double millis) { return millis/1000/60; }
            public double fromSeconds(double seconds) { return seconds/60; }
            public double fromMinutes(double minutes) { return minutes; }
            public double fromHours(double hours) { return hours*60; }
        }, toHours {
            public double fromMillis(double millis) { return millis/1000/3600; }
            public double fromSeconds(double seconds) { return seconds/3600; }
            public double fromMinutes(double minutes) { return minutes/60; }
            public double fromHours(double hours) { return hours; }
        };

        public double fromMillis(double millis) {
            throw new AbstractMethodError();
        }
        public double fromSeconds(double seconds) {
            throw new AbstractMethodError();
        }
        public double fromMinutes(double minutes) {
            throw new AbstractMethodError();
        }
        public double fromHours(double hours) {
            throw new AbstractMethodError();
        }
    }

    // WTimeUnit.fromHours.toSeconds(10.5)
    enum FromUnits {
        fromMilli {
            public long toMillisLong(double millis) {
                return (long)millis;
            }
            public double toMillis(double millis) {
                return millis;
            }
            public double toSeconds(double millis) { return millis/1000; }
            public double toMinutes(double millis) { return millis/(60*1000); }
            public double toHours(double millis) { return millis/(3600*1000); }
        }, fromSeconds {
            public long toMillisLong(double seconds) { return (long)(seconds*1000); }
            public double toMillis(double seconds) { return seconds*1000; }
            public double toSeconds(double seconds) { return seconds; }
            public double toMinutes(double seconds) { return seconds/60; }
            public double toHours(double seconds) { return seconds/3600; }
        }, fromMinutes {
            public long toMillisLong(double minutes) { return (long)(minutes*60*1000); }
            public double toMillis(double minutes) { return minutes*60*1000; }
            public double toSeconds(double minutes) { return minutes*60; }
            public double toMinutes(double minutes) { return minutes; }
            public double toHours(double minutes) { return minutes/60; }
        }, fromHours {
            public long toMillisLong(double hours) { return (long)(hours*3600*1000); }
            public double toMillis(double hours) { return hours*3600*1000; }
            public double toSeconds(double hours) { return hours*3600; }
            public double toMinutes(double hours) { return hours*60; }
            public double toHours(double hours) { return hours; }
        };

        public long toMillisLong(double val) {
            throw new AbstractMethodError();
        }
        public double toMillis(double val) {
            throw new AbstractMethodError();
        }
        public double toSeconds(double val) {
            throw new AbstractMethodError();
        }
        public double toMinutes(double val) {
            throw new AbstractMethodError();
        }
        public double toHours(double val) {
            throw new AbstractMethodError();
        }
    }
    
    //  WTimeUnit.dbl(fromHours, 10.5 toMinutes);
    public static double dbl(FromUnits fromUnits, double val, ToUnits toUnits) {
        return toUnits.fromSeconds(fromUnits.toSeconds(val));
    }
    //  WTimeUnit.round(fromHours, 10.5 toMinutes);
    public static long round(FromUnits fromUnits, double val, ToUnits toUnits) {
        return Math.round(toUnits.fromSeconds(fromUnits.toSeconds(val)));
    }
    public static long trunc(FromUnits fromUnits, double val, ToUnits toUnits) {
        return (long)(toUnits.fromSeconds(fromUnits.toSeconds(val)));
    }

    // WTimeUnit.fromSeconds(10.5, toSeconds);
    public static double fromSeconds(double sec, ToUnits toUnits) {
        return toUnits.fromSeconds(sec);
    }

    //  WTimeUnit.toSeconds(fromHours, 10.5)
    public static double toSeconds(FromUnits fromUnits, double value) {
        return fromUnits.toSeconds(value);
    }

    // WTimeUnit.fromMinutes(10.5, toSeconds);
    public static double fromMinutes(double minutes, ToUnits toUnits) {
        return toUnits.fromMinutes(minutes);
    }

    //  WTimeUnit.toMinutes(fromHours, 10.5)
    public static double toMinutes(FromUnits fromUnits, double value) {
        return fromUnits.toMinutes(value);
    }

    // WTimeUnit.fromHours(10.5, toSeconds);
    public static double fromHours(double hours, ToUnits toUnits) {
        return toUnits.fromHours(hours);
    }

    //  WTimeUnit.toSeconds(fromHours, 10.5)
    public static double toHours(FromUnits fromUnits, double value) {
        return fromUnits.toHours(value);
    }
}
