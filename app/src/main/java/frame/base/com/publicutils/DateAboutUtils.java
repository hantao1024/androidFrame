package frame.base.com.publicutils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateAboutUtils {
	
	/**
	 * 得到今天的日期 格式:2014-10-14
	 * @return
	 */
	public static String getTodayDate(){
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    return matter1.format(dt);
	}
	
	
	public static String getTodayDateTime(){
		Date dt=new Date();
		SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return matter1.format(dt);
	}
	/**
	 * 得到今天的毫秒数
	 * @return
	 */
	public static long getTodayDateTimes(){
		Date dt=new Date();
		return dt.getTime();
	}
	/** 
	 * 时间戳转化成时间字符串
	 * 测试数据:1416568300
	 * 计算结果:2014-11-21 19:11:40
	 **/
	public static String getDateTime(String timemillins){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(Long.parseLong(timemillins)/**(时间戳)*/*1000L));
		return date;
	}
	
	/***
	 * 得到 yyyyMMdd 格式的日期
	 * @param timeStamp
	 * @return
	 */
	public static String getDateStr1(long timeStamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date(timeStamp*1000L));
		return date;
	}
	
	/***
	 * 得到 yyyy-MM-dd HH:mm:ss 格式的日期
	 * @param timeStamp
	 * @return
	 */
	public static String getDateTimeStr2(long timeStamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(timeStamp*1000L));
		return date;
	}
	
	public static String getDateSubstr(long timeStamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(timeStamp*1000L));
		return date;
	}
	
	
	
	
}
