package com.fugu.tim.hibernate_repository_pattern.utility;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log 工具類別
 */
public abstract class Util {

	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
    public static void logInfo(final Object obj) {
    	logger.info(String.format("%s", obj));
    }
    
    public static void logWarn(final Object obj) {
    	logger.warn(String.format("%s", obj));
    }
    
    public static void logDebug(final Object obj) {
    	logger.debug(String.format("%s", obj));
    }
    
    public static void logError(final Object obj) {
    	logger.error(String.format("%s", obj));
    }
	
	public static void logException(final Exception e) {
    	logError(e);
    	Arrays.asList(e.getStackTrace()).forEach(element -> logError(element));
    }
    
    public static Date getCurrentDate() {
    	Calendar calendar = getCalendar();
    	return calendar.getTime();
    }

    public static Calendar getCalendar() {    	
    	final TimeZone tz = TimeZone.getTimeZone("Asia/Taipei");
    	final Locale loc = Locale.TAIWAN;
    	
    	return Calendar.getInstance(tz, loc);
    }
    
}
