package com.fugu.database.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * 建立 Repository 排序類別 
 * 實現 ORDER BY column_name ASC SQL語法
 * 
 * @see org.hibernate.criterion.Order;
 */
public class Sort implements Iterable<Order>, Sortable{
	
	public static final Direction DEFAULT_DIRECTION = Direction.ASC;
	
	private final List<Order> orders;
	
	/**
	 * 使用 Hibernate Order Array 建立 Sort 
	 * 
	 * @param orders 
	 */
	public Sort(Order... orders) {
		this(Arrays.asList(orders));
	}
	
	/**
	 * 使用 Hibernate Order List 建立 Sort 
	 * 
	 * @param orders 不可為 null 或 empty
	 */
	public Sort(List<Order> orders) {

		if (null == orders || orders.isEmpty()) {
			throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
		}

		this.orders = orders;
	}
	
	/**
	 * 使用欄位作為參數建立 Sort 類別 預設使用昇冪排列 
	 * 
	 * @param properties 不可為 null 或 empty
	 */
	public Sort(String... properties) {
		this(DEFAULT_DIRECTION, properties);
	}

	/**
	 * 使用 Direction 與欄位作為參數建立 Sort 類別 
	 * 
	 * @param direction 為 null 時預設為昇冪排列
	 * @param properties 不可為 null 或 empty
	 */
	public Sort(Direction direction, String... properties) {
		this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
	}

	/**
	 * 使用 Direction 與欄位名稱的 List 作為參數建立 Sort 類別
	 * 
	 * @param direction 為 null 時預設為昇冪排列
	 * @param properties 不可為 null 或 empty
	 */
	public Sort(Direction direction, List<String> properties) {

		if (properties == null || properties.isEmpty()) {
			throw new IllegalArgumentException("You have to provide at least one property to sort by!");
		}

		this.orders = new ArrayList<Order>(properties.size());

		for (String property : properties) {			
			if (direction == Direction.ASC || direction == null) {
				this.orders.add(Order.asc(property));
			} else if (direction == Direction.DESC) {
				this.orders.add(Order.desc(property));
			}
		}
	}

	/**
	 * 返回符合合併條件的 Sort 類別
	 * 
	 * @param sort
	 * @return Sort
	 */
	public Sort and(Sort sort) {

		if (sort == null) {
			return this;
		}

		ArrayList<Order> these = new ArrayList<Order>(this.orders);

		for (Order order : sort) {
			these.add(order);
		}

		return new Sort(these);
	}
	
	public Iterator<Order> iterator() {
		return this.orders.iterator();
	}

	@Override
	public String toString() {
		return orders.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Criteria toCriteria(Criteria criteria) {
		
		for (Order order: this.orders) {
			 criteria.addOrder(order);
		}
		
		return criteria;
	}
	
	/**
	 * 排序昇冪降冪 Enumeration
	 */
	public static enum Direction {
		
		ASC, DESC;
	}
}
