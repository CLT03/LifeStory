package com.vivwe.base.util;

/**
 * ahtor: super_link
 * date: 2019/6/5 14:56
 * remark:
 */
public class TimeUtils {

    /**
     * 转换时间为音乐格式时间
     * @param time
     * @return xx:xx
     */
    public static String toMusicFormatTime(long time){
        long hour = time / 1000 / 60;
        long minute = time / 1000 % 60;
        String minute1;
        String hour1;
        if (hour >= 10) {
            hour1 = String.valueOf(hour);
        } else hour1 = "0" + hour;
        if (minute >= 10) {
            minute1 = String.valueOf(minute);
        } else minute1 = "0" + minute;
        return hour1 + ":" + minute1;
    }
}
