package cn.momia.admin.web.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                System.out.println("日期:[" + currentDate + "] 是双休日");
                System.out.println("\r\n");

                reData = true;
            }else{
                System.out.println("日期:[" + currentDate + "] 不是双休日");
                System.out.println("\r\n");
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

    public static void main(String[] args) {

        //Date date = new Date();
        String dateStr = "2015-07-25 00:00:00";
        String datess = "2015-07-26 00:00:00";

        System.out.println("字符串转日期：" + StrToDate(dateStr));
        System.out.println("\r\n");
        System.out.println("日期转字符串：" + DateToStr(StrToDate(dateStr)));
        System.out.println("\r\n");
        System.out.println("日期相差天数：" + daysBetween(StrToDate(dateStr),StrToDate(datess)));
        System.out.println("\r\n");
        isWeek(dateStr);
        isWeek(datess);

    }
}
