package monster.helloworld.gdflbd.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    /**
     * timeStamp to yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String timeStampToStr_1(Long timeStamp){

        Instant instant = Instant.ofEpochMilli(timeStamp);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

        return dateTimeFormatter.format(localDateTime);
    }

    // timeStamp to yyyy-MM-dd


}
