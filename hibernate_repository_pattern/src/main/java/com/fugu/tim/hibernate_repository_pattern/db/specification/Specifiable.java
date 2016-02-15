package com.fugu.tim.hibernate_repository_pattern.db.specification;

import org.hibernate.criterion.Criterion;

import com.fugu.tim.hibernate_repository_pattern.db.Criterial;

/**
 * Specification 類別介面
 */
public interface Specifiable extends Criterial {
	  
	/**
	 * 返回 Specification 定義的 Hibernate Criterion 類別
	 */
	public Criterion toCriterion();
	
	/**
	* 返回符合聯集定義的 Specification
	*/
	public Specifiable and(final Specifiable specification);

	/**
	* 返回符合交集定義的 Specification
	*/
	public Specifiable or(final Specifiable specification);

	/**
	* 返回符合餘集定義的 Specification
	*/
	public Specifiable not(final Specifiable specification);
	
}
