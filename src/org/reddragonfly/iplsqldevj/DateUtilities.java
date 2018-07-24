package org.reddragonfly.iplsqldevj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtilities {
	
	public static int getYear() {
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	public static int getMonth() {
		GregorianCalendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	public static int getDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		int date = calendar.get(Calendar.DATE);
		return date;
	}

	public static int getDay() {
		GregorianCalendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static int getHours() {
		GregorianCalendar calendar = new GregorianCalendar();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		return hours;
	}

	public static int getMinutes() {
		GregorianCalendar calendar = new GregorianCalendar();
		int min = calendar.get(Calendar.MINUTE);
		return min;
	}

	public static int getSeconds() {
		GregorianCalendar calendar = new GregorianCalendar();
		int sec = calendar.get(Calendar.SECOND);
		return sec;
	}

	public static int getMilliSeconds() {
		GregorianCalendar calendar = new GregorianCalendar();
		int millisec = calendar.get(Calendar.MILLISECOND);
		return millisec;
	}
	
	// dateformat:yyyy-MM-dd,yyyy-M-d,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd H:m:s
	public static String getFormatDate(Date date,String dateformat) { 
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		String time = format.format(date);
		return time;
	}
	
	public static String getFormatDate(Date date) {
		return getFormatDate(date,"yyyy-MM-dd");
	}
	
	// dateformat:yyyy-MM-dd,yyyy-M-d,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd H:m:s
	public static String getFormatNowDate(String dateformat) {
		Date now = new Date();
		return getFormatDate(now,dateformat);
	}
	
	public static String getFormatNowDate() {
		return getFormatNowDate("yyyy-MM-dd");
	}
	
	// dateformat:yyyy-MM-dd,yyyy-M-d,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd H:m:s
	public static Date getParseDateString(String dateStr,String dateformat){
		try{
			SimpleDateFormat format = new SimpleDateFormat(dateformat);
			Date date = format.parse(dateStr);
			return date;
		}catch(ParseException e){
			throw new RuntimeException(e);
		}
	}
	
	public static Date getParseDateString(String dateStr){
		return getParseDateString(dateStr,"yyyy-MM-dd");
	}

	public static String getNowTime() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(now);
		return nowTime;
	}
	
	public static String getNowTimeS() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String nowTime = sdf.format(now);
		return nowTime;
	}
	
}
