package com.sogokids.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by hoze on 15/7/22.
 */
public class DateUtil {

    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断是否双休日
     * @param currentDate
     * @return
     */
    public static boolean isWeek(String currentDate) {
        boolean reData = false;
        DateFormat df = new SimpleDateFormat("yy-MM-dd");//日期格式化辅助类
        Date d;
        try {
            d = df.parse(currentDate);//格式化日期
            //判断是不是双休日
            if (d.getDay() == 0 || d.getDay() == 6) {
//                System.out.println("日期:[" + currentDate + "] 是双休日");
//                System.out.println("\r\n");

                reData = true;
            }else{
//                System.out.println("日期:[" + currentDate + "] 不是双休日");
//                System.out.println("\r\n");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reData;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate=sdf.parse(sdf.format(smdate));
            bdate=sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 日期时间转换中文 可以带星期
     * @param str
     * @return
     */
    public static String DateToStr_Cn(String str){
        //可根据不同样式请求显示不同日期格式，要显示星期可以添加E参数

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            if (str.length() > 10) {
                date = format2.parse(str);
            }else{
                date = format1.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format3 = new SimpleDateFormat("MM月dd日 E");
        SimpleDateFormat format4 = new SimpleDateFormat("yyyy年MM月dd日 E kk点mm分");
        String reStr = "";
        if (str.length() > 10) {
            reStr = format4.format(date);
        }else{
            reStr = format3.format(date);
        }


        return reStr;
    }

    /**
     * 获取加减之后的日期
     * @param date
     * @param day
     * @return
     */
    public static String getDateIntStr(Date date,int day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果

        String reStr = format.format(date);

        return reStr;
    }

    /**
     * 获取当前日期前一个月的日期
     * @return
     */
    public static String getDateSYM(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH,-1);
        date = calendar.getTime();

        String reStr = format.format(date);

        return reStr;
    }

    /**
     * 获取当前日期前一天的0点格式
     * @return
     */
    public static String getDateDay(String dateTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = StrToDate(dateTime);
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_MONTH,-1);
        date = calendar.getTime();

        String reStr = format.format(date) + " 00:00:00";

        return reStr;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    /**
     * 获取当前时间 格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String NowDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前时间 格式 yyyy-MM-dd
     * @return
     */
    public static String NowDateW() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当前时间 格式 yyyyMMdd
     * @return
     */
    public static String NowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 根据传入的日期转换 格式 MM月dd日 HH:mm
     * @param date
     * @return
     */
    public static String getDateTimeStr_cn(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm");
        return df.format(date);
    }

    /**
     * 根据传入的日期转换 格式 MM-dd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }

    /**
     * 根据传入的日期转换 格式 MM月dd日
     * @param date
     * @return
     */
    public static String getDateStr_cn(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        return df.format(date);
    }

    /**
     * 根据传入的日期转换 格式 HH:mm
     * @param date
     * @return
     */
    public static String getTimeStr_cn(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }

    /**
     * 计算时间的毫秒
     * @param time
     * @return
     */
    public static long getDateExpired(int time) {
        Date date = new Date();
        long int_time = time * 60*1000;
        long expired = date.getTime() + int_time;
        return expired;
    }

    /**
     * 根据出生日期计算年龄
     * @param birthStr
     * @return
     */
    public static int getAge(String birthStr) {
        SimpleDateFormat format_str = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        try {
            birthDate = format_str.parse(birthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int age = 0;

        Date now = new Date();

        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");

        String birth_year = format_y.format(birthDate);
        String this_year = format_y.format(now);

        String birth_month = format_M.format(birthDate);
        String this_month = format_M.format(now);

        // 初步，估算
        age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

        // 如果未到出生月份，则age - 1
        if (this_month.compareTo(birth_month) < 0)
            age -= 1;
        if (age < 0)
            age = 0;
        return age;
    }

    public static void main(String[] args) {
        String birthDate = "1983-09-01";
        System.out.print(getAge(birthDate));
    }
}
