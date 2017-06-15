package cn.mmdata.commons.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

	/**
	 * 字符串转日期
	 * @param str
	 * @param strFormat yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date strToDate(String str, String strFormat){
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转日期
	 * @param str
	 * @param strFormat
	 * @return
	 */
	public static Date strToDate(String str){
		return strToDate(str,"yyyy-MM-dd");
	}
	
	public static Date millsToDate(String mills){
		if(!isLong(mills)){
			return new Date();
		}
		long mil = Long.parseLong(mills);
        return new Date(mil);
	}
	/**
	  * 判断字符串是否是整数
	  */
	 private static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	 }
	/**
	 * 日期转字符串
	 * @param date
	 * @param strFormat
	 * @return
	 */
	public static String dateToStr(Date date, String strFormat){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat(strFormat);
		return format.format(date);
	}
	
	public static String dateToStr(Date date){
		return dateToStr(date,"yyyy-MM-dd");
	}
	/**
	 * 字符串转Timestamp
	 * @param date  String的类型必须形如： yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错！
	 * @return
	 */
	public static Timestamp strToTimestamp(String date){
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
        try {  
            ts = Timestamp.valueOf(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ts;
	}
	
	/**
	 * 字符串转Timestamp
	 * @param date String格式的日期
	 * @param strFormat 日期的格式
	 * @return
	 */
	public static Timestamp strToTimeStamp(String date, String strFormat){
		Date dateTemp = strToDate(date,strFormat);
		return new Timestamp(dateTemp.getTime());
	}
	/**
	 * 字符串转Timestamp
	 * @param date String格式的日期
	 * @param strFormat 日期的格式
	 * @return
	 */
	public static Timestamp dateToTimeStamp(Date date){
		return new Timestamp(date.getTime());
	}
	/**
	 * 获取日期
	 * @param n 昨天（n=-1），明天（n=1），类推
	 * @return
	 */
	public static String getDate(int n){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, n);
		Date date = calendar.getTime();
		return dateToStr(date, "yyyy-MM-dd");
	}
	
	public static String getDateTime(int n){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, n);
		Date date = calendar.getTime();
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将时间格式化成为指定格式数据
	 * @param date
	 * @param strFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date format(Date date,String strFormat){
		DateFormat format = new SimpleDateFormat(strFormat);
		
		try {
			date = format.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将当前时间格式化成制定的格式
	 * @param strFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date format(String strFormat){
		return format(new Date(),strFormat);
	}
	
	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 判断两个日期相差的秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDateInSeconds(Date startDate,Date endDate){
		long milliseconds1 = startDate.getTime();
		long milliseconds2 = endDate.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		return diffSeconds;
	}
	/**
	 * 判断两个日期相差的秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDateInSeconds(String startDate,String endDate){
		Date sDate = DateTools.strToDate(startDate,"yyyy-MM-dd HH:mm:ss");
		Date eDate = DateTools.strToDate(endDate,"yyyy-MM-dd HH:mm:ss");
		return diffDateInSeconds(sDate,eDate);
	}
	public static long diffDateInMinutes(String startDate,String endDate){
		return diffDateInSeconds(startDate,endDate)/60;
	}
	public static long diffDateInMinutes(Date startDate,Date endDate){
		return diffDateInSeconds(startDate,endDate)/60;
	}
	public static long diffDateInHours(String startDate,String endDate){
		return diffDateInSeconds(startDate,endDate)/(60 * 60);
	}
	public static long diffDateInHours(Date startDate,Date endDate){
		return diffDateInSeconds(startDate,endDate)/(60 * 60);
	}
	public static long diffDateInDays(String startDate,String endDate){
		return diffDateInSeconds(startDate,endDate)/(24 * 60 * 60);
	}
	public static long diffDateInDays(Date startDate,Date endDate){
		return diffDateInSeconds(startDate,endDate)/(24 * 60 * 60);
	}
	/**
	 * 获取当前月的最后一天
	 * @return
	 */
	public   static   Date   getLastDayOfMonth()   {  
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return ca.getTime();
	}
	/**
	 * 获取当前月的第一天
	 * @return
	 */
	public static  Date getFirstDayOfMonth(){
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
	}
	/**
	 * 根据输入的时间比较当前时间是否在本月份里
	 * @param date1
	 * @return
	 */
	public static boolean diffDateInNowMonths(Date date1){
		boolean pw =false;
		if(date1!=null){
			if(diffDateInDays(getFirstDayOfMonth(),date1)>=0&&diffDateInDays(date1,getLastDayOfMonth())>=0){
				pw =true;
			}
		}
		return pw;
	}
	public static void main(String[] args) {
	/*	String dateTest = getDate(-1);

		System.out.println(dateTest);
		Date startDate = DateTools.strToDate(dateTest + " 00:00:00",
				"yyyy-MM-dd HH:mm:ss");
		System.out.println(startDate);*/
//		Date date = strToDate(getDate(-1) + " 23:59:59","yyyy-MM-dd HH:mm:ss");
//		System.out.println(date);
//		Date date2 = strToDate(str + " 00:00:00","yyyy-MM-dd HH:mm:ss");
//		System.out.println(date2);
		System.out.println(diffDateInNowMonths(new Date()));
	}
}
