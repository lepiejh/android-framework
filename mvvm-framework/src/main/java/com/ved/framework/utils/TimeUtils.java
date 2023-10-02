package com.ved.framework.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public class TimeUtils {

    // ///s 代表Simple日期格式：yyyy-MM-dd
    // ///f 代表Full日期格式：yyyy-MM-dd hh:mm:ss

    public static final SimpleDateFormat ss_format = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat f_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 时分秒
     */
    public static final String HHmmss = "HH:mm:ss";

    /**
     * 时分
     */
    public static final String HHmm = "HH:mm";
    public static final String HHmm1 = "HH-mm-ss";


    /**
     *
     */
    public static final String YEAR = "yyyy";

    public static final String MONTH = "MM";

    public static final String DAY = "dd";

    /**
     * 年月日
     */
    public static final String YMD_PATTERN = "yyyy年MM月dd日";
    public static final String YMD_PATTERN_HHmmss = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final SimpleDateFormat f_12_format = new SimpleDateFormat("MM月dd日 a HH:mm");

    public static final String YMD_PATTERN2 = "yyyy.MM.dd";
    public static final String YMD_PATTERN3 = "yyyy-MM-dd";
    public static final String YMD_PATTERN4 = "yyyy/MM/dd";

    public static final String YM_PATTERN = "yyyy-MM";

    public static final String MD_PATTERN = "MM月dd日";
    public static final String MD_PATTERN1 = "MM-dd";

    public static final String YMD_HMS_PATTERN = YMD_PATTERN3 + " " + HHmmss;
    public static final String YMD_HMS_PATTERN4 = YMD_PATTERN4 + " " + HHmmss;
    public static final String YMD_HMS_PATTERN5 = YMD_PATTERN4 + "-" + HHmm1;
    public static final String YMD_HMS_PATTERN6 = MD_PATTERN1 + " " + HHmm;
    public static String yyyy = new SimpleDateFormat("yyyy.").format(new Date());

    public static long s_str_2_long(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static Date longToDate(long currentTime) throws ParseException {
        if (String.valueOf(currentTime).length() == 13){
            return longToDate(currentTime,"yyyy-MM-dd HH:mm:ss");
        }else {
            return longToDate(currentTime*1000L,"yyyy-MM-dd HH:mm:ss");
        }
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld; // 根据long类型的毫秒数生命一个date类型的时间
        if (String.valueOf(currentTime).length() == 13) {
            dateOld = new Date(currentTime);
        } else {
            dateOld = new Date(currentTime*1000L);
        }
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date; // long类型转成Date类型
        if (String.valueOf(currentTime).length() == 13) {
            date = longToDate(currentTime, formatType);
        } else {
            date = longToDate(currentTime*1000L, formatType);
        }
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String dateToStr(Date date, SimpleDateFormat format) {//可根据需要自行截取数据显示
        return format.format(date);
    }

    public static long f_str_2_long(String dateString) {
        try {
            Date d = f_format.parse(dateString);
            return d.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long f_str_2_long(String dateString, SimpleDateFormat f_format) {
        try {
            Date d = f_format.parse(dateString);
            return d.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String ss_long_2_str(long timestamp) {
        if (String.valueOf(timestamp).length() == 13) {
            return ss_format.format(new Date(timestamp));
        } else {
            return ss_format.format(new Date(timestamp*1000L));
        }
    }

    public static String f_str_2_12str(long timestamp, SimpleDateFormat f_format) {
        if (String.valueOf(timestamp).length() == 13){
            return f_format.format(new Date(timestamp));
        }else {
            return f_format.format(new Date(timestamp*1000L));
        }
    }

    public static String ss_long_2_12str(long timestamp) {
        if (String.valueOf(timestamp).length() == 13){
            return f_12_format.format(new Date(timestamp));
        }else {
            return f_12_format.format(new Date(timestamp*1000L));
        }
    }

    public static String f_long_2_str(String timestamp) {
        if (String.valueOf(timestamp).length() == 13) {
            return f_format.format(new Date(Long.parseLong(timestamp)));
        } else {
            return f_format.format(new Date(Long.parseLong(timestamp)*1000L));
        }
    }

    public static String s_long_2_str(long timestamp) {
        if (String.valueOf(timestamp).length() == 13) {
            return s_format.format(new Date(timestamp));
        } else {
            return s_format.format(new Date(timestamp*1000L));
        }
    }

    public static String f_long_2_str(long timestamp) {
        if (String.valueOf(timestamp).length() == 13) {
            return f_format.format(new Date(timestamp));
        } else {
            return f_format.format(new Date(timestamp*1000L));
        }
    }

    public static String f_long_2_str(long timestamp, SimpleDateFormat f_format) {
        if (String.valueOf(timestamp).length() == 13) {
            return f_format.format(new Date(timestamp));
        } else {
            return f_format.format(new Date(timestamp*1000L));
        }
    }

    /**
     * 获取字符串时间的年份
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getYear(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getYear() + 1900;// 年份是基于格林威治时间，所以加上1900
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取字符串时间的月份
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int getMonth(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getMonth();// 月份从0-11
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取字符串时间的天
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int getDayOfMonth(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getDate();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getHours(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getHours();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMinutes(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getMinutes();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getSeconds(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getSeconds();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentTime() {
        return f_format.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 在当前时间上加上多少毫秒，返回这个时间
     *
     * @param mask
     * @return
     */
    public static String getCurrentTimeMask(long mask) {
        return f_format.format(new Date(System.currentTimeMillis() + mask));
    }

    // /////////////////////以上是通用的，下面为特殊需求的////////////////////////
    // /**
    // * 时间戳转换日期格式
    // *
    // * @param timestamp
    // * 单位秒
    // * @return
    // */
    // public static String getCurrentTime(long timestamp) {
    // SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // return f.format(new Date(timestamp * 1000));
    // }

    /**
     * 获取精简的日期
     *
     * @param time
     * @return
     */
    public static String getSimpleDate(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param time
     * @return
     */
    public static String getSimpleDateTime(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSimpleTime(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getChatSimpleDate(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeHM(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeMMdd(long time) {
        SimpleDateFormat formater = new SimpleDateFormat("MM-dd");
        if (String.valueOf(time).length() == 13) {
            return formater.format(time);
        } else {
            return formater.format(time * 1000L);
        }

    }

    public static SimpleDateFormat friendly_format1 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat friendly_format2 = new SimpleDateFormat("MM-dd HH:mm");

    public static String sk_time_friendly_format2(long time) {
        if (String.valueOf(time).length() == 13) {
            return friendly_format2.format(new Date(time));
        } else {
            return friendly_format2.format(new Date(time * 1000L));
        }
    }

    public static String sk_time_s_long_2_str(long time) {
        if (String.valueOf(time).length() == 13) {
            return s_long_2_str(time);
        } else {
            return s_long_2_str(time * 1000L);
        }
    }


    public static String sk_time_ss_long_2_str(long time) {
        if (String.valueOf(time).length() == 13) {
            return ss_long_2_str(time);
        } else {
            return ss_long_2_str(time * 1000L);
        }
    }

    public static long sk_time_s_str_2_long(String dateString) {
        if (String.valueOf(dateString).length() == 13) {
            return s_str_2_long(dateString);
        } else {
            return s_str_2_long(String.valueOf(StringUtils.parseLong(dateString)*1000L));
        }
    }

    public static long sk_time_current_time() {
        return System.currentTimeMillis();
    }

    private static SimpleDateFormat hm_formater = new SimpleDateFormat("HH:mm");


    private static SimpleDateFormat hm_formater12 = new SimpleDateFormat("aa hh:mm ");

    public static String sk_time_long_to_hm_str(long time) {
        try {
            if (String.valueOf(time).length() == 13) {
                return hm_formater.format(new Date(time));
            } else {
                return hm_formater.format(new Date(time * 1000L));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sk_time_long_to_hm_str12(long time) {
        try {
            if (String.valueOf(time).length() == 13) {
                return hm_formater12.format(new Date(time));
            } else {
                return hm_formater12.format(new Date(time * 1000L));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sk_time_long_to_chat_time_str(Context mContext,long time) {
        String date1 = sk_time_s_long_2_str(time);
        String date2 = sk_time_s_long_2_str(System.currentTimeMillis() / 1000);
        if (date1.compareToIgnoreCase(date2) == 0) {// 是同一天
            if(is24Hh(mContext)){
                return sk_time_long_to_hm_str(time);
            }else{
                return sk_time_long_to_hm_str12(time);
            }

        } else {
            if(is24Hh(mContext)){
                return long_to_yMdHm_str(time * 1000);
            }else{
                return long_to_yMdHm_st12r(time * 1000);
            }

        }
    }


    /**
     * @param mContext
     * @return
     * @since 是否24小时制 true  是
     */
    public static Boolean is24Hh(Context mContext){
        ContentResolver cv = mContext.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);
        if(strTimeFormat !=null && strTimeFormat.equals("24")){
            return true;
        }
        return false;
    }

    public static final SimpleDateFormat sk_format_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static final SimpleDateFormat sk_format_12 = new SimpleDateFormat("yyyy/MM/dd aa hh:mm");

    // 日期加小时的字符串
    public static String long_to_yMdHm_str(long time) {
        if (String.valueOf(time).length() == 13) {
            return sk_format_1.format(new Date(time));
        } else {
            return sk_format_1.format(new Date(time*1000L));
        }
    }



    // 日期加小时的字符串
    public static String long_to_yMdHm_st12r(long time) {
        if (String.valueOf(time).length() == 13) {
            return sk_format_12.format(new Date(time));
        } else {
            return sk_format_12.format(new Date(time*1000L));
        }
    }


    public static long sk_time_yMdHm_str_to_long(String time) {
        try {
            return sk_format_1.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getYear(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getYear() + 1900;// 年份是基于格林威治时间，所以加上1900
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMonth(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getMonth();// 月份从0-11
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getDayOfMonth(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getHours(String timeString) {
        try {
            Date date = sk_format_1.parse(timeString);
            return date.getHours();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMinutes(String timeString) {
        try {
            Date date = sk_format_1.parse(timeString);
            return date.getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param textView
     * @param time     时间戳/1000
     * @return
     */
    public static long getSpecialBeginTime(TextView textView, long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        if (time > currentTime) {
            time = currentTime;
        }
        textView.setText(sk_time_s_long_2_str(time));
        return time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFormatDateMsg(long time){
        String callDate = TransitionTime.getDate(time);
        String todayData = TransitionTime.getTodayData();
        SimpleDateFormat f = null;
        if (TextUtils.equals(todayData, callDate)) {//今天
            f = new SimpleDateFormat("HH:mm");
        }else {
            f = new SimpleDateFormat("yyyy/MM/dd");
        }
        return f.format(time);
    }

    /**
     * 时间格式化为今天，昨天，前天   年-月-日
     *
     * @param time
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatDate(long time) {
        time *= 1000;
        //获取拨打电话的日期
        String callDate = TransitionTime.getDate(time);
        //获取今天的日期
        String todayData = TransitionTime.getTodayData();
        //获取昨天的日期
        String yesData = TransitionTime.getYesData();
        //获取前天的日期
        String beforeYesterday = TransitionTime.getBeforeYesterdayData();

        SimpleDateFormat f = null;

        if (TextUtils.equals(todayData, callDate)) {//今天
            return "今天";
        } else if (TextUtils.equals(yesData, callDate)) {//昨天
            return "昨天";
        } else if (TextUtils.equals(beforeYesterday, callDate)) {//前天
            return "前天";
        } else {
            // 月日 : 时分
            f = new SimpleDateFormat("yyyy-MM-dd");
            return f.format(time);
        }
    }

    /**
     * 字符串转为时间格式
     *
     * @return
     */
    public static String strToTime(double str) {
        long time = Long.parseLong(StringUtils.removeDecimal(String.valueOf(str)));
        StringBuilder stringBuilder = new StringBuilder();
        if (time < 60) {
            stringBuilder.append("00:");
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            return stringBuilder.toString();
        } else if (time < 3600) {
            return getBranch(time, stringBuilder);
        } else {
            if (time / 3600 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time / 3600);
            stringBuilder.append(":");
            return getBranch(time % 3600, stringBuilder);
        }
    }

    //获取分秒的时间格式
    private static String getBranch(long time, StringBuilder stringBuilder) {
        if (time / 60 < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(time / 60);
        stringBuilder.append(":");
        if (time % 60 < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(time % 60);
        return stringBuilder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String timeToStr(Context context,long time){
        String callDate = TransitionTime.getDate(time);
        String todayData = TransitionTime.getTodayData();
        SimpleDateFormat f = null;
        long currentTimeMillis = System.currentTimeMillis();
        if (TextUtils.equals(todayData, callDate)) {//今天
            if (android.text.format.DateFormat.is24HourFormat(context)){
                f = new SimpleDateFormat("HH:mm");
            }else {
                f = new SimpleDateFormat("a hh:mm");
            }
            return f.format(time);
        }else {
            if (android.text.format.DateFormat.is24HourFormat(context)){
                f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            }else {
                f = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
            }
            String dateStr = f.format(time);
            String currentStr = f.format(currentTimeMillis);
            if (TextUtils.equals(currentStr.substring(0, 5), dateStr.substring(0, 5))) {
                dateStr = dateStr.substring(5);
            }
            return dateStr;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean compare(String time,String time2){
        if (time.contains(":")) {
            int t1;
            int t2;
            String s1 = time.split(":")[0];
            String s2 = time.split(":")[1];
            if (s1.startsWith("0")){
                t1 = StringUtils.parseInt(s1.substring(1));
            }else {
                t1 = StringUtils.parseInt(s1);
            }
            if (s2.startsWith("0")){
                t2 = StringUtils.parseInt(s2.substring(1));
            }else {
                t2 = StringUtils.parseInt(s2);
            }

            int t3;
            int t4;
            String s3 = time2.split(":")[0];
            String s4 = time2.split(":")[1];
            if (s3.startsWith("0")){
                t3 = StringUtils.parseInt(s3.substring(1));
            }else {
                t3 = StringUtils.parseInt(s3);
            }
            if (s4.startsWith("0")){
                t4 = StringUtils.parseInt(s4.substring(1));
            }else {
                t4 = StringUtils.parseInt(s4);
            }
            if (t3 == t1){
                return t4 > t2;
            }else {
                return t3 > t1;
            }
        } else {
            return false;
        }
    }

    /**
     * 比较时分在当前时间前后
     * @param time      MM:dd
     * @return     true  当前时间大于设定时间
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare(String time){
        return compare(time,TimeUtils.f_long_2_str(System.currentTimeMillis(),new SimpleDateFormat("HH:mm")));
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 取得上月天数
     * */
    @SuppressLint("SimpleDateFormat")
    public static int getUpMonthLastDay(){
        String currentTime = TimeUtils.f_long_2_str(System.currentTimeMillis(),
                new SimpleDateFormat("yyyy-MM"));
        int year = StringUtils.parseInt(currentTime.split("-")[0]);
        String month = currentTime.split("-")[1];
        int m;
        if (month.startsWith("0")){
            m = StringUtils.parseInt(month.substring(1));
        }else {
            m = StringUtils.parseInt(month);
        }
        if (m == 1){
            year--;
            m = 12;
        }else {
            m--;
        }
        return getMonthLastDay(year,m);
    }

    /**
     * 获得指定月的天数
     * */
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    //是否为本月
    public static boolean equalsMonth(String m1,String m2){
        int cMonth;
        if (m1.startsWith("0")){
            cMonth = StringUtils.parseInt(m1.substring(1));
        }else {
            cMonth = StringUtils.parseInt(m1);
        }
        int month;
        if (m2.startsWith("0")){
            month = StringUtils.parseInt(m2.substring(1));
        }else {
            month = StringUtils.parseInt(m2);
        }
        return cMonth == month;
    }

    //秒转为小时，并保留一位小数
    public static double getHour(int mis){
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(mis/3600.0d));
    }

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getNowDate(String pattern) {
        return getDate(new Date(System.currentTimeMillis()), pattern);
    }

    /**
     * @param pattern
     * @return
     */
    public static String getYesterday(String pattern) {
        return getDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000), pattern);
    }

    /**
     * @param pattern
     * @return
     */
    public static String getTomorrowDay(String pattern) {
        return getDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000), pattern);
    }


    /**
     * 获取时间
     *
     * @param pattern
     * @return
     */
    public static String getDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取时间
     *
     * @param pattern
     * @return
     */
    public static Date getDate(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }

    public static String formateChange(String date,String patternFrom,String pattrtnTo){
        Date d1 = null;
        String dateTo="";
        try {
            d1 = new SimpleDateFormat(patternFrom).parse(date);
            dateTo = new SimpleDateFormat(pattrtnTo).format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTo;
    }


    /**
     * 取得当月天数
     */
    public static int getNowMonthDayNum() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 得到指定月的天数
     */
    public static int getMonthDayNum(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static String getWeek() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }


    public static String formatDate(long millis) {
        StringBuilder sb = new StringBuilder();
        long mss = millis / 1000;
        long hours = mss / (60 * 60);
        long day = mss / (60 * 60 * 24);
        DecimalFormat decimalFormat = new DecimalFormat("00");
        sb.append(decimalFormat.format(day) + "天").append(decimalFormat.format(hours) + "时");
        return sb.toString();
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     */
    public static String getTimeInterval(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        String imptimeBegin = simpleDateFormat.format(cal.getTime());
        //System.out.println("所在周星期一的日期："+imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = simpleDateFormat.format(cal.getTime());
        //System.out.println("所在周星期日的日期："+imptimeEnd);
        return imptimeBegin + "-" + imptimeEnd;
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     * t 下一周
     * f 上一周
     */
    public static String getTimeInterval(String time, boolean next) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        Date date = null;
        if (!next) {
            time = time.substring(0, 5);
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            date = c.getTime();
        } else {
            time = time.substring(6);
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);
            date = c.getTime();

        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        String imptimeBegin = simpleDateFormat.format(cal.getTime());
        //System.out.println("所在周星期一的日期："+imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = simpleDateFormat.format(cal.getTime());
        //System.out.println("所在周星期日的日期："+imptimeEnd);
        return imptimeBegin + "-" + imptimeEnd;
    }

    public static String getYearAndMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        return simpleDateFormat.format(new Date());
    }

    public static String getYear() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.");
        return simpleDateFormat.format(new Date());
    }

    public static String year() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date());
    }

    public static String getMonthAndDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        return simpleDateFormat.format(new Date());
    }

    public static String selectDay(String time, boolean next) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        Date date = null;
        if (next) {
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);
            date = c.getTime();
            return simpleDateFormat.format(date);
        } else {
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            date = c.getTime();
            return simpleDateFormat.format(date);
        }
    }

    public static String selectMonth(String time, boolean next) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        Date date = null;
        if (next) {
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, 1);
            date = c.getTime();
            return simpleDateFormat.format(date);
        } else {
            try {
                date = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, -1);
            date = c.getTime();
            return simpleDateFormat.format(date);
        }
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static String getTimeOut(String startTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(df.format(new Date()));
            Date d2 = df.parse(startTime);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
            if (diff > 432000000) {
                long days = (diff - 432000000) / (1000 * 60 * 60 * 24);
                long hours = ((diff - 432000000) - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = ((diff - 432000000) - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                return "已超时" + days + "天" + hours + "小时" + minutes + "分";
            } else {
                long days = (432000000 - diff) / (1000 * 60 * 60 * 24);
                long hours = ((432000000 - diff) - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = ((432000000 - diff) - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                return "距到期" + days + "天" + hours + "小时" + minutes + "分";
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String timeComparison(String time, String type) {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if ("day".equals(type)) {
            int res = time.compareTo(nowTime);
            if (res > 0) {
                return nowTime;
            } else {
                return time;
            }

        } else if ("week".equals(type)) {
            if (((yyyy + (time.substring(0, 5))).replace(".", "-").compareTo(nowTime)) > 0) {
                String week = getTimeInterval(new Date());
                if ((yyyy + (week.substring(6))).replace(".", "-").compareTo(nowTime) > 0) {
                    return week.substring(0, 5) +"-"+ nowTime.substring(5);
                } else {
                    return week;
                }
            } else {
                if ((yyyy + (time.substring(6))).replace(".", "-").compareTo(nowTime) > 0) {
                    return time.substring(0, 5) +"-"+ nowTime.substring(5);
                } else {
                    return time;
                }
            }
        } else if ("month".equals(type)) {
            String start = ((time.substring(0, 7)) + ".01").replace(".", "-");
            if ((start.compareTo(nowTime)) > 0) {
                return nowTime.substring(0,7)+".01"+nowTime;
            }else {
                if ((time.substring(0, 7)+"."+getDaysByYearMonth(Integer.valueOf(time.substring(0, 4)),Integer.valueOf(time.substring(5, 7)))).replace(".","-").compareTo(nowTime)>0){
                    return (time.substring(0, 7)+".01").replace(".","-")+nowTime;
                }else {
                    return (time.substring(0, 7)+".01").replace(".","-")+(time.substring(0, 7)+"."+getDaysByYearMonth(Integer.valueOf(time.substring(0, 4)),Integer.valueOf(time.substring(5, 7)))).replace(".","-");
                }
            }
        }
        return "";
    }
}
