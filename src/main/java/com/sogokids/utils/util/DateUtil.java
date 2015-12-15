package com.sogokids.utils.util;

import org.apache.commons.lang3.RandomStringUtils;

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

    public static String NowDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }

    public static String NowDateW() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static String NowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    public static void main(String[] args) {
        String ss = "2015-12-20 11:11:10";
        System.out.print(getDateStr(StrToDate(ss)));
//        System.out.print(RandomStringUtils.randomNumeric(4));
//        System.out.print(RandomStringUtils.randomNumeric(4));
//        System.out.print(RandomStringUtils.randomNumeric(4));
        //System.out.print(NowDateStr());
        //Date date = new Date();
//        String dateStr = "2015-07-18 00:00:01";
//        String datess = "2015-07-19 23:59:59";
//        List<Date> dates = new ArrayList<Date>();
//        dates.add(StrToDate(dateStr));
//        dates.add(StrToDate(datess));
//        dates.add(StrToDate("2015-06-26 12:09:59"));
//        dates.add(StrToDate("2015-07-26 10:09:59"));

//        System.out.println("字符串转日期：" + StrToDate(dateStr));
//        System.out.println("\r\n");
//        System.out.println("日期转字符串：" + DateToStr(StrToDate(dateStr)));
//        System.out.println("\r\n");
//        System.out.println("日期相差天数：" + daysBetween(StrToDate(dateStr),StrToDate(datess)));
//        System.out.println("\r\n");
//        isWeek(dateStr);
//        isWeek(datess);

//        System.out.println("ssss：" + DateToStr_Cn(datess));
//        System.out.println("\r\n");
//        boolean ss = compareDate(StrToDate(NowDateStr()),StrToDate(datess));
//        System.out.print(ss);
//        System.out.print("最大时间："+Collections.max(dates));
//        System.out.print("最小时间：" + Collections.min(dates));

//        System.out.println( System.getProperty("os.name") );
//        System.out.println( System.getProperty("os.version") );
//        System.out.println( System.getProperty("os.arch") );

//                Any("any"),
//                Linux("Linux"),
//                Mac_OS("Mac OS"),
//                Mac_OS_X("Mac OS X"),
//                Windows("Windows"),
//                OS2("OS/2"),
//                Solaris("Solaris"),
//                SunOS("SunOS"),
//                MPEiX("MPE/iX"),
//                HP_UX("HP-UX"),
//                AIX("AIX"),
//                OS390("OS/390"),
//                FreeBSD("FreeBSD"),
//                Irix("Irix"),
//                Digital_Unix("Digital Unix"),
//                NetWare_411("NetWare"),
//                OSF1("OSF1"),
//                OpenVMS("OpenVMS"),
//                Others("Others");
    }
}
