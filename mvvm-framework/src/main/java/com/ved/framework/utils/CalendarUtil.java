package com.ved.framework.utils;

import static android.text.TextUtils.isEmpty;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanfeng on 2017/5/15.
 */
@SuppressLint("SimpleDateFormat")
public class CalendarUtil {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat format4 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    static SimpleDateFormat format5 = new SimpleDateFormat("yyyy年MM月dd日");
    static SimpleDateFormat format6 = new SimpleDateFormat("MM月dd日");
    static SimpleDateFormat format7 = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat format8 = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat format9 = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat format10 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat format11 = new SimpleDateFormat("MM月dd日 a HH:mm");     //a 代表  上午/下午
    static SimpleDateFormat format12 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    static SimpleDateFormat format13 = new SimpleDateFormat("yyyy.MM.dd");
    static SimpleDateFormat format14 = new SimpleDateFormat("HH-mm-ss");
    static SimpleDateFormat format15 = new SimpleDateFormat("MM月dd日  aHH时mm分");


    public static SimpleDateFormat getFormat(int type) {
        SimpleDateFormat sf = null;
        switch (type) {
            case 1:
                sf = format;
                break;
            case 2:
                sf = format2;
                break;
            case 4:
                sf = format4;
                break;
            case 5:
                sf = format5;
                break;
            case 6:
                sf = format6;
                break;
            case 7:
                sf = format7;
                break;
            case 8:
                sf = format8;
                break;
            case 9:
                sf = format9;
                break;
            case 10:
                sf = format10;
                break;
            case 11:
                sf = format11;
                break;
            case 12:
                sf = format12;
                break;
            case 13:
                sf = format13;
                break;
            case 14:
                sf = format14;
                break;
            case 15:
                sf = format15;
                break;
            default:
                sf = format3;
                break;
        }
        return sf;
    }

    public static String getTime(Calendar calendar) {
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getTime(Date calendar) {
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getTime(String mills, int type) {
        if (TextUtils.isEmpty(mills)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(mills));
        String time = getFormat(type).format(calendar.getTime());
        return time;
    }

    //java--》php
    public static String getTime(String datee) {
        String time = "";
        if (!isEmpty(datee)) {
            try {
                Date date = getFormat(1).parse(datee);
                time = date.getTime() / 1000 + "";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public static String getTime2(Calendar calendar) {
        String time = format2.format(calendar.getTime());
        return time;
    }

    public static String getTime2(Date calendar) {
        String time = format2.format(calendar.getTime());
        return time;
    }

    public static String getTime3(Calendar calendar) {
        String time = format3.format(calendar.getTime());
        return time;
    }

    public static String getTime4(Calendar calendar) {
        String time = format4.format(calendar.getTime());
        return time;
    }

    //包括今天
    public static boolean isYouXiaoDate(Calendar calendar) {
        Calendar calendarnow = Calendar.getInstance();
        //给出的时间在今天之前
        if (!calendar.before(calendarnow)) {
            return true;
        } else {
            return false;
        }
    }

    //今天大于传入的日期为过期日期
    public static boolean isYouXiaoDate2(Calendar calendar) {
        if (calendar.compareTo(Calendar.getInstance()) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean sameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static boolean isToday(Calendar selectedDate) {
        Calendar cal = Calendar.getInstance();
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static boolean betweenDates(Calendar cal, Calendar minCal, Calendar maxCal) {
        final Date date = cal.getTime();
        return betweenDates(date, minCal, maxCal);
    }

    public static boolean betweenDates(Date date, Calendar minCal, Calendar maxCal) {
        final Date min = minCal.getTime();
        return (date.equals(min) || date.after(min)) // >= minCal
                && date.before(maxCal.getTime()); // && < maxCal
    }

    //php---》java
    public static String toDate(String date, int type) {
        if (!isEmpty(date)) {
            if ("0".equals(date)) {
                return "";
            } else {
                return getTime(Long.parseLong(date) * 1000 + "", type);
            }
        } else {
            return "";
        }
    }

    //获取当天距月底还有多少天
    public static int getDaysToMouth() {
        Calendar c = Calendar.getInstance();
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);
        return d - now;
    }

    //获取当天距指定天还有多少天
    public static int getDaysToCurrentDyas(int day) {
        Calendar c = Calendar.getInstance();
        int now = c.get(Calendar.DAY_OF_MONTH);
        return now - day;
    }


    /**
     * 获取指定日期之前，之后的时间戳
     *
     * @param date
     * @param amount
     * @return
     */
    public static int addDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return (int) (calendar.getTimeInMillis() / 1000);
    }


    /**
     * 返回明天
     *
     * @param today
     * @return
     */
    public static Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    /**
     * 获取今天是几号
     *
     * @return
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 获取今天是几月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取今天是哪年
     *
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_SECOND_AGO = "刚刚";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
//            long seconds = toSeconds(delta);
//            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            return ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天 " + format7.format(date.getTime());
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    /**
     * 根据毫秒获取时分秒
     *
     * @param milliseconds
     * @return
     */
    public static String formatDateTime(long milliseconds) {
        StringBuilder sb = new StringBuilder();
        long mss = milliseconds / 1000;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        DecimalFormat format = new DecimalFormat("00");
        if(days>0){
            sb.append(format.format(days)).append("天").append(format.format(hours)).append("时").append(format.format(minutes)).append("分").append(format.format(seconds)).append("秒");
        }else
        if (hours > 0) {
            sb.append(format.format(hours)).append("时").append(format.format(minutes)).append("分").append(format.format(seconds)).append("秒");
        } else if (hours <= 0 && minutes > 0) {
            sb.append(format.format(minutes)).append("分").append(format.format(seconds)).append("秒");
        } else if (hours <= 0 && minutes <= 0) {
            sb.append(format.format(seconds)).append("秒");
        }
        return sb.toString();
    }

    /**
     * 根据毫秒获取时分秒
     *
     * @param milliseconds
     * @return
     */
    public static String formatDateTime1(long milliseconds) {
        StringBuilder sb = new StringBuilder();
        long mss = milliseconds / 1000;
        //long days = mss / (60 * 60 * 24);
        //long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long hours = mss / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        DecimalFormat format = new DecimalFormat("00");
        sb.append(format.format(hours)).append(":").append(format.format(minutes)).append(":").append(format.format(seconds));
        return sb.toString();
    }
}
