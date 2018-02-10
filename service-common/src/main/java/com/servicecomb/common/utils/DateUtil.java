package com.servicecomb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DEFAULT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String PATTERN_DAY = "yyyy-MM-dd";
	public static final String PATTERN_DAY_SLASH = "yyyy/MM/dd";

	public DateUtil() {
	}

	public static void main(String[] args) {
		System.out.println(getPerFirstDayOfMonth());
	}

	public static final int getCurrentTime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static final long getLongCurrentTime() {
		return (System.currentTimeMillis() / 1000L);
	}

	public static final String longToDateString(long dateTime, String pattern) {
		Date date = null;
		if (String.valueOf(dateTime).length() == 10) {
			date = new Date(dateTime * 1000L);
		} else {
			date = new Date(dateTime);
		}

		return dateToString(date, pattern);
	}

	public static final String longToDateString(long dateTime) {
		return longToDateString(dateTime, "yyyy-MM-dd HH:mm:ss");
	}

	public static final String dateToString(Date date, String pattern) {
		return (new SimpleDateFormat(pattern)).format(date);
	}

	public static final String dateToString(Date date) {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
	}

	public static final Date stringToDate(String dateInString, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = null;

		try {
			date = df.parse(dateInString);
		} catch (ParseException var5) {
			var5.printStackTrace();
		}

		return date;
	}

	public static final Date stringToDate(String dateInString) {
		return stringToDate(dateInString, "yyyy-MM-dd HH:mm:ss");
	}

	public static final int stringToInt(String dateInString) {
		return (int) (stringToDate(dateInString, "yyyy-MM-dd").getTime() / 1000L);
	}

	public static String getNowTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		String lastMonth = dft.format(cal.getTime());
		return lastMonth;
	}

	public static boolean isFirstDayOfMonth() {
		boolean flag = false;
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(5);
		if (1 == today) {
			flag = true;
		}

		return flag;
	}

	public static String getMaxMonthDate() {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(5, calendar.getActualMaximum(5));
		return dft.format(calendar.getTime());
	}

	public static String getPerFirstDayOfMonth() {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(2, 1);
		calendar.set(5, calendar.getActualMinimum(5));
		return dft.format(calendar.getTime()) + " 0:0:0";
	}

	public static String getLastMaxMonthDate() {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(2, -1);
		calendar.set(5, calendar.getActualMaximum(5));
		return dft.format(calendar.getTime());
	}

	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(2, -1);
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
		String lastMonth = dft.format(cal.getTime());
		return lastMonth;
	}

	public static String getPreMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(2, 1);
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
		String preMonth = dft.format(cal.getTime());
		return preMonth;
	}

	public static boolean isLastDayOfMonth() {
		boolean flag = false;
		if (StringUtils.isNotBlank(getNowTime()) && StringUtils.isNotBlank(getMaxMonthDate())
				&& StringUtils.equals(getNowTime(), getMaxMonthDate())) {
			flag = true;
		}

		return flag;
	}

	public static String getPreMonth(String repeatDate) {
		String lastMonth = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
		int year = Integer.parseInt(repeatDate.substring(0, 4));
		String monthsString = repeatDate.substring(4, 6);
		int month;
		if ("0".equals(monthsString.substring(0, 1))) {
			month = Integer.parseInt(monthsString.substring(1, 2));
		} else {
			month = Integer.parseInt(monthsString.substring(0, 2));
		}

		cal.set(year, month, 5);
		lastMonth = dft.format(cal.getTime());
		return lastMonth;
	}

	public static String getLastMonth(String repeatDate) {
		String lastMonth = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
		int year = Integer.parseInt(repeatDate.substring(0, 4));
		String monthsString = repeatDate.substring(4, 6);
		int month;
		if ("0".equals(monthsString.substring(0, 1))) {
			month = Integer.parseInt(monthsString.substring(1, 2));
		} else {
			month = Integer.parseInt(monthsString.substring(0, 2));
		}

		cal.set(year, month - 2, 5);
		lastMonth = dft.format(cal.getTime());
		return lastMonth;
	}

	private static String getMaxMonthDate(String repeatDate) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();

		try {
			if (StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)) {
				calendar.setTime(dft.parse(repeatDate));
			}
		} catch (ParseException var4) {
			var4.printStackTrace();
		}

		calendar.set(5, calendar.getActualMaximum(5));
		return dft.format(calendar.getTime());
	}

	private static String getMinMonthDate(String repeatDate) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();

		try {
			if (StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)) {
				calendar.setTime(dft.parse(repeatDate));
			}
		} catch (ParseException var4) {
			var4.printStackTrace();
		}

		calendar.set(5, calendar.getActualMinimum(5));
		return dft.format(calendar.getTime());
	}

	public static String getModify2DaysAgo(String repeatDate) {
		Calendar cal = Calendar.getInstance();
		String daysAgo = "";
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		if (repeatDate != null && !"".equals(repeatDate)) {
			int year = Integer.parseInt(repeatDate.substring(0, 4));
			String monthsString = repeatDate.substring(4, 6);
			int month;
			if ("0".equals(monthsString.substring(0, 1))) {
				month = Integer.parseInt(monthsString.substring(1, 2));
			} else {
				month = Integer.parseInt(monthsString.substring(0, 2));
			}

			String dateString = repeatDate.substring(6, 8);
			int date;
			if ("0".equals(dateString.subSequence(0, 1))) {
				date = Integer.parseInt(dateString.substring(1, 2));
			} else {
				date = Integer.parseInt(dateString.substring(0, 2));
			}

			cal.set(year, month - 1, date - 1);
			System.out.println(dft.format(cal.getTime()));
		} else {
			cal.set(5, cal.get(5) - 2);
		}

		daysAgo = dft.format(cal.getTime());
		return daysAgo;
	}

	public static String getModifyNumDaysAgo(String repeatDate, int param) {
		Calendar cal = Calendar.getInstance();
		String daysAgo = "";
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		if (repeatDate != null && !"".equals(repeatDate)) {
			int year = Integer.parseInt(repeatDate.substring(0, 4));
			String monthsString = repeatDate.substring(4, 6);
			int month;
			if ("0".equals(monthsString.substring(0, 1))) {
				month = Integer.parseInt(monthsString.substring(1, 2));
			} else {
				month = Integer.parseInt(monthsString.substring(0, 2));
			}

			String dateString = repeatDate.substring(6, 8);
			int date;
			if ("0".equals(dateString.subSequence(0, 1))) {
				date = Integer.parseInt(dateString.substring(1, 2));
			} else {
				date = Integer.parseInt(dateString.substring(0, 2));
			}

			cal.set(year, month - 1, date - param + 1);
			System.out.println(dft.format(cal.getTime()));
		} else {
			cal.set(5, cal.get(5) - param);
		}

		daysAgo = dft.format(cal.getTime());
		return daysAgo;
	}
}
