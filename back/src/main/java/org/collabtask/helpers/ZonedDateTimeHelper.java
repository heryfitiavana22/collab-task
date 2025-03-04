package org.collabtask.helpers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeHelper {
    static public ZoneId zoneId = ZoneId.of("GMT+03");

    public static ZonedDateTime now() {
        return ZonedDateTime.now(zoneId);
    }

    public static ZonedDateTime parse(String text) {
        return ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME.withZone(zoneId));
    }

    public static ZonedDateTime parse(ZonedDateTime date) {
        if (date == null)
            return null;
        return ZonedDateTime.parse(date.toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME.withZone(zoneId));
    }
}
