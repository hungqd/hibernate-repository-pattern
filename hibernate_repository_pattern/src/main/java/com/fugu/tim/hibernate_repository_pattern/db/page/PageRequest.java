package com.fugu.tim.hibernate_repository_pattern.db.page;

import org.hibernate.Criteria;

import com.fugu.tim.hibernate_repository_pattern.db.sort.Sort;
import com.fugu.tim.hibernate_repository_pattern.db.sort.Sort.Direction;

public class PageRequest extends AbstractPageRequest {

	private static final long serialVersionUID = -982616655373274892L;
	
	private final Sort sort;

	/**
	 * 使用零索引的頁面號碼(page)與每頁物品數量(size)建立 PageRequest 類別
	 * 
	 * @param page 頁面號碼必須大於等於0
	 * @param size 每頁物品數量必須大於1
	 */
	public PageRequest(int page, int size) {
		this(page, size, null);
	}

	/**
	 * 使用零索引的頁面號碼(page)每頁物品數量(size)與排序(direction properties)建立 PageRequest 類別
	 * 
	 * @param page 頁面號碼必須大於等於0
	 * @param size 每頁物品數量必須大於1
	 * @param direction 參考 {@link Sort} 可為 {@literal null}
	 * @param properties 排序欄位名稱不可為 null 或空值
	 */
	public PageRequest(int page, int size, Direction direction, String... properties) {
		this(page, size, new Sort(direction, properties));
	}
	
	/**
	 * 使用零索引的頁面號碼(page)每頁物品數量(size)與排序(direction properties)建立 PageRequest 類別
	 * 
	 * @param page 頁面數量必須大於等於0
	 * @param size 每頁物品數量必須大於1
	 * @param sort 可為 {@literal null}
	 */
	public PageRequest(int page, int size, Sort sort) {
		super(page, size);
		this.sort = sort;
	}

	/**
	 * {@inheritDoc}}
	 */
	public Sort getSort() {
		return sort;
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public Pageable next() {
		return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	/**
	 * {@inheritDoc}}
	 */
	public PageRequest previous() {
		return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
	}

	/**
	 * {@inheritDoc}}
	 */
	public Pageable first() {
		return new PageRequest(0, getPageSize(), getSort());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(),
				sort == null ? null : sort.toString());
	}

	/**
	 * {@inheritDoc}}
	 */
	public Criteria toCriteria(Criteria criteria) {

		criteria.setFirstResult(getOffset()); 
		criteria.setMaxResults(getPageSize());
		
		if (sort != null) {
			sort.toCriteria(criteria);
		}		
		
		return criteria;
	}
	
}
