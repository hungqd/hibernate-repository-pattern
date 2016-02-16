package com.fugu.database.page;

import java.util.List;

import com.fugu.database.sort.Sort;

/**
 * Page 介面
 * 
 * @param <T>
 * 
 * @see org.springframework.data.domain.Page
 */
public interface Page<T> extends Iterable<T> {
	
	/**
	 * 取得頁面號碼
	 * 
	 * @return int 目前頁面號碼
	 */
	public int getNumber();

	/**
	 * 取得每頁資料筆數
	 * 
	 * @return int 頁面物品數量
	 */
	public int getSize();

	/**
	 * 取得總頁面數量
	 * 
	 * @return int 總頁數
	 */
	public int getTotalPages();

	/**
	 * 取得總資料筆數 
	 * 
	 * @return
	 */
	public long getTotalElements();

	/**
	 * 取得目前頁面資料筆數量
	 * 
	 * @return int 資料筆數
	 */
	public int getNumberOfElements();
	
	/**
	 * 是否有下一頁資料
	 * 
	 * @return 是返回 true 否返回 false
	 */
	public boolean hasNext();
	
	/**
	 * 是否有上一頁資料
	 * 
	 * @return 是返回 true 否返回 false
	 */
	public boolean hasPrevious();
	
	/**
	 * 當前頁面是否為首頁
	 * 
	 * @return 是返回 true 否返回 false
	 */
	public boolean isFirst();

	/**
	 * 當前頁面是否為最後一頁
	 * 
	 * @return 是返回 true 否返回 false
	 */
	public boolean isLast();

	/**
	 * 若有下一頁則取得下一頁的 PageRequest
	 * 
	 * @return Pageable
	 */
	public Pageable nextPageable();

	/**
	 * 若有上一頁則取得上一頁的 PageRequest
	 * 如當下已經為首頁則返回首頁 PageRequest
	 * 
	 * @return Pageable
	 */
	public Pageable previousPageable();

	/**
	 * 當下頁面的資料筆數是否不為0
	 * 
	 * @return 是返回 true 否返回 false
	 */
	public boolean hasContent();

	/**
	 * 取得當下頁面的資料筆數
	 * 
	 * @return
	 */
	public List<T> getContent();

	/**
	 * 取得當下頁面的 Sort 規則
	 * 
	 * @return Sort
	 */
	public Sort getSort();
	
}
