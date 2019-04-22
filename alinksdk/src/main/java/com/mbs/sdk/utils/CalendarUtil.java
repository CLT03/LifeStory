package com.mbs.sdk.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:zlcai
 * @createrDate:2018/7/19
 * @lastTime:2018/7/19
 * @detail:
 */

public class CalendarUtil {



    public static int getHouse(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);//获取小时
    }

    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);//获取年份
    }

    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);//获取年份
    }

    public static int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);//获取年份
    }

    public static Date getNow(){
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 获取昨天
     * @return
     */
    public static Date getLastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /***
     * 获取本月第一天
     * @return
     */
    public static Date getMonthDayFist(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /**
     * 获取上周周一
     * @return
     */
    public static Date getMondayOfLastWeek(){
        // 取上周一 00:00:00
        Calendar calendar = Calendar.getInstance();
        int n1 = -1;// n为推迟的周数，-1上周，0本周，1下周，2下下周，依次类推
        calendar.add(Calendar.DATE, n1 * 7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取上周周日
     * @return
     */
    public static Date getSundayOfLastWeek(){
        // 取上周一 00:00:00
        Calendar calendar = Calendar.getInstance();
        int n1 = -1;// n为推迟的周数，-1上周，0本周，1下周，2下下周，依次类推
        calendar.add(Calendar.DATE, n1 * 7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 获取本周周一
     * @return
     */
    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 获取本周周日
     * @return
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     * 获取上个月第一天
     * @return
     */
    public static Date getLastMonthDayFrist(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getLastMonthDayLast(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }


    public static String dateToStr(Date dateTime){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dateTime);
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    public static Date toDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String toStr(Date strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(strDate);
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    // 计算当月最后一天,返回字符串
    public String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int month, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }

}
