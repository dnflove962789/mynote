package org.zzr.mynote.userinfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) {
        long time = new Date().getTime();
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        //return LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of("+8"));
        LocalDateTime localDateTime1 = localDateTime.ofInstant(instant, zone);

        String format = localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format);
    }
}
