package com.fugu.tim.hibernate_repository_pattern.db.page;

import java.io.Serializable;

/**
 * PageRequest 的抽象類別 
 * 用於 Repository 函數取得零索引的 Page 類別
 */
public abstract class AbstractPageRequest implements Serializable, Pageable {

	private static final long serialVersionUID = -1142905311505909770L;
	
	private final int page;
	private final int size;

	/**
	 * 使用零索引的頁面號碼(page)與每頁物品數量(size)建立 PageRequest 類別
	 * 
	 * @param page 頁面號碼必須大於等於0 頁面號碼採用零索引
	 * @param size 每頁物品數量必須大於1
	 */
	public AbstractPageRequest(int page, int size) {

		if (page < 0) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Page size must not be less than one!");
		}

		this.page = page;
		this.size = size;
	}

	/**
	 * {@inheritDoc}}
	 */
	public int getPageSize() {
		return size;
	}

	/**
	 * {@inheritDoc}}
	 */
	public int getPageNumber() {
		return page;
	}

	/**
	 * {@inheritDoc}}
	 */
	public int getOffset() {
		return page * size;
	}

	/**
	 * {@inheritDoc}}
	 */
	public abstract Pageable next();

	/**
	 * {@inheritDoc}}
	 */
	public abstract Pageable previous();

	/**
	 * {@inheritDoc}}
	 */
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public boolean hasPrevious() {
		return page > 0;
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public abstract Pageable first();	
}
