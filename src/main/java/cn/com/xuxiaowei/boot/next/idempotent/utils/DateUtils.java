package cn.com.xuxiaowei.boot.next.idempotent.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class DateUtils {

	/**
	 * 最小
	 */
	private static final LocalTime LOCAL_TIME_MIN = LocalTime.MIN;

	/**
	 * 最大
	 */
	private static final LocalTime LOCAL_TIME_MAX = LocalTime.MAX;

	/**
	 * Java 8 LocalDateTime 转 String
	 * @param localDateTime Java 8 LocalDateTime
	 * @param pattern 目标格式
	 * @return 返回 目标格式的时间
	 */
	public static String localDateTimeFormat(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(dateTimeFormatter);
	}

	/**
	 * Java 8 LocalDate 转 String
	 * @param localDate Java 8 LocalDate
	 * @param pattern 目标格式
	 * @return 返回 目标格式的时间
	 */
	public static String localDateFormat(LocalDate localDate, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(dateTimeFormatter);
	}

	/**
	 * Java 8 LocalTime 转 String
	 * @param localTime Java 8 LocalTime
	 * @param pattern 目标格式
	 * @return 返回 目标格式的时间
	 */
	public static String localTimeFormat(LocalTime localTime, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return localTime.format(dateTimeFormatter);
	}

	/**
	 * 指定日期的最小时间
	 * @param localDate 指定日期
	 * @return 返回 指定日期的最小时间
	 */
	public static LocalDateTime localDateTimeMin(LocalDate localDate) {
		return localDate.atTime(LOCAL_TIME_MIN);
	}

	/**
	 * 指定日期的最大时间
	 * @param localDate 指定日期
	 * @return 返回 指定日期的最大时间
	 */
	public static LocalDateTime localDateTimeMax(LocalDate localDate) {
		return localDate.atTime(LOCAL_TIME_MAX);
	}

	/**
	 * 根据指定日期时间和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的日期时间
	 * @param localDateTime 指定日期时间
	 * @param daysToSubtract 减去的天数（正数为之前，负数为之后）
	 * @return 返回 根据指定日期时间和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 */
	public static LocalDateTime minusDays(LocalDateTime localDateTime, long daysToSubtract) {
		return localDateTime.minusDays(daysToSubtract);
	}

	/**
	 * 根据指定日期和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的日期
	 * @param localDate 指定日期
	 * @param daysToSubtract 减去的天数（正数为之前，负数为之后）
	 * @return 返回 根据指定日期和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 */
	public static LocalDate minusDays(LocalDate localDate, long daysToSubtract) {
		return localDate.minusDays(daysToSubtract);
	}

	/**
	 * 根据指定日期时间和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 * @param localDateTime 指定日期时间
	 * @param monthsToSubtract 要减去的月份（正数为之前，负数为之后）
	 * @return 返回 根据指定日期时间和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 */
	public static LocalDateTime minusMonths(LocalDateTime localDateTime, long monthsToSubtract) {
		return localDateTime.minusMonths(monthsToSubtract);
	}

	/**
	 * 根据指定日期和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 * @param localDate 指定日期
	 * @param monthsToSubtract 要减去的月份（正数为之前，负数为之后）
	 * @return 返回 根据指定日期和天数（正数为之前，负数为之后），返回之前（正数为之前，负数为之后）的时间
	 */
	public static LocalDate minusMonths(LocalDate localDate, long monthsToSubtract) {
		return localDate.minusMonths(monthsToSubtract);
	}

	/**
	 * 获取指定日期所在的周的周几对应的日期
	 * @param localDate 指定日期
	 * @param dayOfWeek 周几
	 * @return 返回 获取指定日期所在的周的周几对应的日期
	 */
	public static LocalDate dayOfWeek(LocalDate localDate, DayOfWeek dayOfWeek) {
		return localDate.with(TemporalAdjusters.previous(dayOfWeek));
	}

	/**
	 * 获取指定日期所在月的第几天对应的日期
	 * @param localDate 指定日期
	 * @param dayOfMonth 第几天
	 * @return 返回获取指定日期所在月的第几天对应的日期
	 */
	public static LocalDate dayOfMonth(LocalDate localDate, int dayOfMonth) {
		return LocalDate.of(localDate.getYear(), localDate.getMonth(), dayOfMonth);
	}

	/**
	 * 指定天数内的随机时间
	 * @param day 指定天数， 正数：未来 负数：过去
	 * @return 返回 指定天数内的随机时间
	 */
	public static LocalDateTime randomOfDay(int day) {
		long timestamp = System.currentTimeMillis();
		timestamp += Math.random() * 1000 * 60 * 60 * 24 * day;
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
	}

	/**
	 * 日期与时间拼接
	 * @param localDate 日期
	 * @param localTime 时间
	 * @return 返回 日期与时间拼接 结果
	 */
	public static LocalDateTime atTime(LocalDate localDate, LocalTime localTime) {
		return localDate.atTime(localTime);
	}

}
