package model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeekManager {
	public static String getDayOfWeekName(int dayOfWeek) throws Exception {
		switch (dayOfWeek) {
			case 1 :
				return "ראשון";
			case 2 :
				return "שני";
			case 3 :
				return "שלישי";
			case 4 :
				return "רביעי";
			case 5 :
				return "חמישי";
			case 6 :
				return "שישי";
			case 7 :
				return "שבת";
			default :
				throw new Exception("No existing day");
		}
	}

	public static Calendar getCalendarForDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date getCurrentDate() {
		return fromLocalDateToDate(LocalDate.now());
	}

	public static Date fromLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate fromDateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static int getYear(Date date) {
		return getCalendarForDate(date).get(1);
	}

	public static int getWeekNumber(Date date) {
		LocalDate localDate = fromDateToLocalDate(date);
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return localDate.get(weekFields.weekOfWeekBasedYear());
	}

	public static Date getFirstDateForWeek(Date date) {
		Calendar cal = getCalendarForDate(date);
		cal.set(7, 1);
		return cal.getTime();
	}

	public static Date getLastDateForWeek(Date date) {
		Calendar cal = getCalendarForDate(date);
		cal.set(7, 7);
		return cal.getTime();
	}

	public static Date incrementDateByWeeks(Date date, int weeks) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(3, weeks);
		return calendar.getTime();
	}

	public static Date decrementDateByWeeks(Date date, int weeks) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(3, -weeks);
		return calendar.getTime();
	}

	public static Date incrementDateByWeek(Date date) {
		return incrementDateByDays(date, 7);
	}

	public static Date decrementDateByWeek(Date date) {
		return incrementDateByDays(date, -7);
	}

	public static Date incrementDateByDays(Date date, int days) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(6, days);
		return calendar.getTime();
	}

	public static Date decrementDateByDays(Date date, int days) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(6, -days);
		return calendar.getTime();
	}

	public static Date incrementDateByHours(Date date, int hours) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(11, hours);
		return calendar.getTime();
	}

	public static Date decrementDateByHours(Date date, int hours) {
		Calendar calendar = getCalendarForDate(date);
		calendar.add(11, -hours);
		return calendar.getTime();
	}

	public static int getDayOfWeek(Date date) {
		Calendar calendar = getCalendarForDate(date);
		int dayOfWeek = calendar.get(7);
		return dayOfWeek;
	}

	public static int getHourOfDay(Date date) {
		Calendar calendar = getCalendarForDate(date);
		int hour = calendar.get(11);
		return hour;
	}

	public static Date setHoursOfDay(Date date, int hours) throws Exception {
		if (hours >= 0 && hours <= 23) {
			Calendar cal = getCalendarForDate(date);
			cal.set(11, hours);
			return cal.getTime();
		} else {
			throw new Exception("Illegal hour (" + hours + ")");
		}
	}

	public static Date setDayOfWeek(Date date, int dayOfWeek) throws Exception {
		if (dayOfWeek >= 1 && dayOfWeek <= 7) {
			Calendar cal = getCalendarForDate(date);
			cal.set(7, dayOfWeek);
			return cal.getTime();
		} else {
			throw new Exception("Illegal day of Week (" + dayOfWeek + ")");
		}
	}

	public static Date setTimeInDay(Date date, int hours, int minutes) throws Exception {
		return setHoursOfDay(setMinutesOfHour(date, minutes), hours);
	}

	public static Date setMinutesOfHour(Date date, int minuts) throws Exception {
		if (minuts >= 0 && minuts <= 59) {
			Calendar cal = getCalendarForDate(date);
			cal.set(12, minuts);
			return cal.getTime();
		} else {
			throw new Exception("Illegal minute (" + minuts + ")");
		}
	}

	public static Date getCopy(Date date) {
		return new Date(date.getTime());
	}
}