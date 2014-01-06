package m3.helper;

using DateTools;


class DateHelper {
    public static function inThePast(d: Date): Bool {
        return d.getTime() < Date.now().getTime();
    }

    public static function inTheFuture(d: Date): Bool {
        return d.getTime() > Date.now().getTime();
    }

    public static function isValid(d: Date): Bool {
    	return d != null && !Math.isNaN(d.getTime());
    }

    public static function isBefore(d1: Date, d2: Date): Bool {
    	return d1.getTime() < d2.getTime();
    }

    public static function isAfter(d1: Date, d2: Date): Bool {
    	return !isBefore(d1, d2);
    }
}