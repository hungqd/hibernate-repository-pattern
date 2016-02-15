package com.fugu.tim.hibernate_repository_pattern.db.page;

import org.hibernate.Criteria;

import com.fugu.tim.hibernate_repository_pattern.db.Criterial;
import com.fugu.tim.hibernate_repository_pattern.db.sort.Sort;

/**
 * PageRequest 介面
 * 
 * @see org.springframework.data.domain.Pageable
 */
public interface Pageable extends Criterial {

	/**
	 * 取得頁面號碼
	 * 
	 * @return integer 目前頁面號碼
	 */
	int getPageNumber();

	/**
	 * 取得頁面物品數量
	 * 
	 * @return integer 頁面物品數量
	 */
	int getPageSize();

	/**
	 * 取得 SQL 用 OFFSET 數值 計算方式為頁面號碼 X 每頁物品數量
	 * 
	 * @return offset 數值
	 */
	int getOffset();

	/**
	 * 返回用於此 PageRequest 的 Sort 類別
	 * 
	 * @return Sort
	 */
	Sort getSort();

	/**
	 * 返回下一頁面號碼的 PageRequest
	 * 
	 * @return Pageable
	 */
	Pageable next();
	
	/**
	 * 返回上一頁面號碼的 PageRequest
	 * 
	 * @return Pageable
	 */
	Pageable previous();

	/**
	 * 返回前一頁面號碼的 PageRequest
	 * 若為首頁則返回首頁 PageRequest
	 * 
	 * @return Pageable
	 */
	Pageable previousOrFirst();

	/**
	 * 判斷目前頁面號碼是否有前一頁
	 * 
	 * @return 前一頁面號碼存在返回 true 首頁則返回 false 
	 */
	boolean hasPrevious();
	
	/**
	 * 返回首頁的 PageRequest
	 * 
	 * @return Pageable
	 */
	Pageable first();
	
	/**
	 * Apply pageable criteria to argument and return the criteria.
	 * 
	 * @param criteria
	 * @return
	 */
	Criteria toCriteria(Criteria criteria);
}
