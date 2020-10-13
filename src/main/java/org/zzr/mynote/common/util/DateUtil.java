package org.zzr.mynote.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getDateByTime(long time){
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String formatDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formatDate;
    }
}
