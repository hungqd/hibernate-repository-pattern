package com.fugu.database.specification;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 玩家資料相關 Specification 
 */
public abstract class PlayerSpecification {
	
	/**
	 * 使用 Criteria 實現 WHERE nin_id = ? SQL語法
	 * 
	 * @param ninId
	 * @return Specifiable
	 */
	public static Specifiable getNinIdSpec(int ninId) {
		return new AbstractSpecification() {
			@Override
			public Criterion toCriterion() {
				return Restrictions.eq("ninId", ninId);
			}			
		};	
	}
	
	/**
	 * 使用 Criteria 實現 WHERE server_id = ? SQL語法
	 * 
	 * @param serverId
	 * @return Specifiable
	 */
	public static Specifiable getServerId(int serverId) {
		
		return new AbstractSpecification() {			
			@Override
			public Criterion toCriterion() {
				return Restrictions.eq("serverId", serverId);
			}			
		};	
	}
}
