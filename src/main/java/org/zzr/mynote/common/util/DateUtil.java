package org.zzr.mynote.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据timestamp拿到日期
     * @param time
     * @return String（yyyy-MM-dd）
     */
    public static String getDateByTime(long time){
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String formatDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formatDate;
    }

    /**
     * 拿到月份第一天
     * @param dateStr
     * @return String（yyyy-MM-dd）
     */
    public static String getFirstDateInMonthByTime(String dateStr) {
        try{
            Date date = dateFormat.parse(dateStr);
            Instant instant = Instant.ofEpochMilli(date.getTime());
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDateTime firsetDate = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
            String formatDate = firsetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return formatDate;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 拿到月份最后一天
     * @param dateStr
     * @return String（yyyy-MM-dd）
     */
    public static String getLastDateInMonthByTime(String dateStr) {
        try{
            Date date = dateFormat.parse(dateStr);
            Instant instant = Instant.ofEpochMilli(date.getTime());
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDateTime lastDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
            String formatDate = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return formatDate;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
