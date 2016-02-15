package com.fugu.tim.hibernate_repository_pattern.db;

import org.hibernate.Criteria;

/**
 * 實現此介面的類別可以新增 Hibernate <tt>Criteria</tt> 規則至既有的 <tt>Criteria</tt> 中
 * 
 * <pre>
 * 
 * 範例
 * 
 * UserIdSpecification spec = new UserIdSpecification(1);
 * 
 * Criteria criteria = session.createCriteria("user");
 * criteria = spec.toCriteria(criteria);
 * 
 * List<User> lista = criteria.list();
 * </pre>
 * 
 * @see org.hibernate.Criteria 
 */
public interface Criterial {
	
	/**
	 * 新增 Criterion 給參數的 Criteria 物件
	 * 
	 * @return 更新後的 Criteria 物件
	 */
	public Criteria toCriteria(Criteria criteria);
	
}
