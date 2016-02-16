package com.fugu.database.specification;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fugu.database.utility.Util;

/**
 * 時間資料相關 Specification
 */
public abstract class TimeSpecification {
	
	/**
	 * 使用 Criteria 實現 WHERE create_date BETWEEN (CURRENT_DATE - INTERVAL 24 HOUR) AND CURRENT_DATE SQL語法
	 * 
	 * @return Specifiable
	 */
	public static Specifiable getCreatedIn24Hour() {
		return new AbstractSpecification() {
			
			@Override
			public Criterion toCriterion() {
				Calendar calendar = Util.getCalendar();	
				
				final Date rightNow = calendar.getTime();
				calendar.add(Calendar.HOUR, -24);
				final Date oneDayBefore = calendar.getTime();
				
				return Restrictions.between("createDate", oneDayBefore, rightNow);
			}
			
		};	
	}
	
}
