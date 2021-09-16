package ro.netinstructor.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utilities {
    public static long createID() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long id = zdt.toInstant().toEpochMilli();
        return id;
    }
}
