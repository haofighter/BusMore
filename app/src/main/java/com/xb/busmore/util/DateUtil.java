package com.xb.busmore.util;

import android.util.Log;

import com.szxb.jni.libszxb;
import com.xb.busmore.base.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.util
 * 邮箱：996489865@qq.com
 * TODO:时间工具类
 */

public class DateUtil {
    private static SimpleDateFormat format_2 = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("zh", "CN"));
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("zh", "CN"));

    private static SimpleDateFormat format_1 = new SimpleDateFormat("yyyyMMdd", new Locale("zh", "CN"));

    private static SimpleDateFormat format_3 = new SimpleDateFormat("yyyy-MM-dd", new Locale("zh", "CN"));

    /**
     * @return 得到当前时间：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        return format.format(new Date());
    }

    /**
     * @return 得到当前日期：yyyyMMdd
     */
    public static String getCurrentDate() {
        return format.format(new Date());
    }


    public static Date getTimeFromat(String time, String format) {
        SimpleDateFormat formatNow = null;
        try {
            formatNow = new SimpleDateFormat(format, new Locale("zh", "CN"));
            return formatNow.parse(time);
        } catch (Exception e) {
            return new Date();
        }
    }

    //当前时间的前1分钟
    public static String getCurrentDateLastMi() {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);// 1分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return format.format(beforeD);
    }

    //当前时间的前1分钟15:44 ---15:43
    public static String getCurrentDateLastMi1() {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);// 1分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return format_2.format(beforeD);
    }

    //当前时间的前5分钟15:44 ---15:43
    public static String getCurrentDateLastMi5() {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -5);// 1分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return format_2.format(beforeD);
    }

    //得到当前日期:yyyy-MM-dd
    public static String getBalckBegainDate() {
        return format_3.format(new Date());
    }

    //自定义格式获取当前日期
    public static String getCurrentDate(String format) {
        SimpleDateFormat ft = null;
        try {
            ft = new SimpleDateFormat(format, new Locale("zh", "CN"));
        } catch (Exception e) {
            return format_3.format(new Date());
        }
        return ft.format(new Date());
    }

    //获取当时Long型日期
    public static long currentLong() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getLastDate(String format) {
        SimpleDateFormat ft = null;
        try {
            ft = new SimpleDateFormat(format, new Locale("zh", "CN"));
        } catch (Exception e) {
            return format_3.format(new Date());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return ft.format(calendar.getTime());
    }

    /**
     * @return 保存Key的时间
     */
    public static String getSaveKeyDate() {
        return format_3.format(new Date());
    }

    /**
     * 判断是否可以更新
     *
     * @param lastDate 上次存储的日期
     * @return true可更新
     */
    public static boolean isUpdate(Date lastDate) {
        return differentDays(lastDate, Calendar.getInstance().getTime()) > 7;
    }


    /**
     * 文件是否过期
     *
     * @param lastDate 存储的时间
     * @return 是否删除
     */
    public static boolean isDelFile(Date lastDate) {
        return differentDays(lastDate, Calendar.getInstance().getTime()) > 90;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param lastDate    上次存储的日期
     * @param currentDate 现在的日期
     * @return 相差的天数
     */
    private static int differentDays(Date lastDate, Date currentDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(lastDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }


    public static int getBetweenDay(Date date1, Date date2) {
        Calendar d1 = new GregorianCalendar();// 保存时候的日期
        d1.setTime(date1);
        Calendar d2 = new GregorianCalendar();// 现在的日期
        d2.setTime(date2);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        System.out.println("days=" + days);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }


    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    //
    public static String getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return format_2.format(todayStart.getTime());
    }

    //
    public static String getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return format_2.format(todayEnd.getTime());
    }


    //今天开始日期
    public static String getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return format.format(todayStart.getTime());
    }

    //今天结束日期
    public static String getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return format.format(todayEnd.getTime());
    }


    public static long getMILLISECOND(String startTime, String endTime) {
        try {
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);
            long l = startDate.getTime() / 1000 - endDate.getTime() / 1000;
            Log.d("DateUtil",
                    "filterOpenID(DateUtil.java:211)相差时间=" + l);
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String fromatDateStringTofromat2(String date) {
        String date1 = date.replace("-", "");
        date1 = date1.replace(" ", "");
        date1 = date1.replace(":", "");
        return date1;
    }

    /**
     * @param type 0：公交卡，1：微信、银联卡
     * @return .
     */
    public static String[] time(int type) {
        if (type == 0) {
            String startTime = DateUtil.getTime("yyyyMMddHHmmss", 0, 0, 0, 0);
            String endTime = DateUtil.getTime("yyyyMMddHHmmss", 23, 59, 59, 999);
            return new String[]{startTime, endTime};
        } else {
            String startTime = DateUtil.getTime("yyyy-MM-dd HH:mm:ss", 0, 0, 0, 0);
            String endTime = DateUtil.getTime("yyyy-MM-dd HH:mm:ss", 23, 59, 59, 999);
            return new String[]{startTime, endTime};
        }
    }


    /**
     * @param format            格式：公交卡：yyyyMMddHHmmss,其他yyyy-MM-dd HH:mm:ss
     * @param hour_of_day_value 小时
     * @param minute_value      分钟
     * @param second_value      秒
     * @param millisecond_value 毫秒
     * @return 当前开始时间|结束时间
     */
    public static String getTime(String format, int hour_of_day_value,
                                 int minute_value, int second_value, int millisecond_value) {
        SimpleDateFormat ft = new SimpleDateFormat(format, new Locale("zh", "CN"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour_of_day_value);
        calendar.set(Calendar.MINUTE, minute_value);
        calendar.set(Calendar.SECOND, second_value);
        calendar.set(Calendar.MILLISECOND, millisecond_value);
        return ft.format(calendar.getTime());
    }

    /**
     * @return 得到当前日期：yyyyMMdd
     */
    public static String getCurrentDate_1() {
        return format_1.format(new Date());
    }

    /**
     * 校准时间
     *
     * @param time
     * @throws ParseException
     * @throws android.os.RemoteException
     */
    public static void setTime(String time) {
        Calendar now = Calendar.getInstance();
        int Year = now.get(Calendar.YEAR);
        int Month = now.get(Calendar.MONTH) + 1;
        int Day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int Min = now.get(Calendar.MINUTE);
        int Sec = now.get(Calendar.SECOND);
        libszxb.deviceSettime(Year, Month, Day, hour, Min, Sec);
    }
}
