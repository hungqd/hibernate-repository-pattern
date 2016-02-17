package com.fugu.database.sort;

import org.hibernate.criterion.Order;

import com.fugu.database.Criterial;

/**
 * 排序類別介面
 */
public interface Sortable extends Criterial, Iterable<Order>{
	
}
