package com.fugu.tim.hibernate_repository_pattern.utility;

/**
 * 驗證參數工具類別
 *  
 * @see org.springframework.util.Assert
 */
public abstract class Assert {
	
	/**
	 * 驗證物件不是 null 失敗則拋出例外 
	 * 
	 * @param obj
	 */
	public static void notNull(Object obj) {
		if(obj == null) {
			throw new IllegalArgumentException("[Assertion failed] - the object argument must not be null");			
		}		
	}	
}
