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
}