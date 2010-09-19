package jtweet.util;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	public static boolean isEmptyOrNull(String value) {
		if (value == null || value.equals(""))
			return true;

		return false;
	}

	public static String getBaseURL(HttpServletRequest request) {
		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		else
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public static String humanReadableString(Date date) {
		long ms = Calendar.getInstance().getTime().getTime() - date.getTime();

		if (ms < 0) {
			return "somewhere in the future";
		}

		long seconds = ms / 1000;

		if (seconds < 60) {
			return "一秒钟之前";
		}

		long minutes = seconds / 60;

		if (minutes < 60) {
			if (minutes < 2) {
				return "一分钟之前";
			}
			if (minutes >= 2 && minutes < 3) {
				return "两分钟之前";
			}
			if (minutes > 5) {
				minutes = (minutes / 5) * 5;
			}
			if (minutes > 30) {
				minutes = (minutes / 15) * 15;
			}

			if (minutes <= 30) {
				return "半小时之前";
			}

			return minutes + "分钟之前";
		}

		long hours = minutes / 60;

		if (hours < 24) {
			if (hours < 2) {
				return "一小时之前";
			}
			if (hours == 2) {
				return "两小时之前";
			}

			if (hours == 12) {
				return "半天之前";
			}
			return hours + "小时之前";
		}

		long days = hours / 24;

		if (days >= 1 && days < 2) {
			return "昨天";
		}

		if (days >= 2 && days < 3) {
			return "两天之前";
		}

		if (days < 7) {
			return days + "天之前";
		}

		if (days < 31) {

			long weeks = days / 7;

			if (weeks < 2) {
				return "一周之前";
			}

			if (weeks > 2 && weeks < 3) {
				return "两周之前";
			}

			return weeks + "周之前";
		}

		if (days > 365) {
			long years = days / 365;
			if (years < 2) {
				return "一年之前";
			}

			if (years >= 2 && years < 3) {
				return "两年之前";
			}

			return years + "年之前";
		}

		long months = days / 31;

		if (months < 2) {
			return "一个月之前";
		}
		if (months >= 2 && months < 3) {
			return "两个月之前";
		}

		if (months <= 6) {
			return "半年之前";
		}

		return months + "个月之前";
	}
}