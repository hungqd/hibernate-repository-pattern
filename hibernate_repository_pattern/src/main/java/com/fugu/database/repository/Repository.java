package com.fugu.database.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.fugu.database.page.Page;
import com.fugu.database.page.Pageable;
import com.fugu.database.sort.Sort;
import com.fugu.database.specification.Specifiable;

public interface Repository<T, ID extends Serializable> {
	
public abstract Class<T> getDomainClass();
	
	/**
	 * 開啟 Hibernate Session
	 * 
	 * @return	Session
	 */
	public Session openSession();
	
	/**
	 * 使用 Primary Key 刪除該筆資料
	 * 
	 * @param id 必須大於0
	 */
	public void delete(ID id) throws RuntimeException;
	
	/**
	 * 使用 Entity 作為參數刪除該筆資料
	 * 
	 * @param entity
	 */
	public void delete(T entity);


	/**
	 * 使用 Iterable<Entity> 作為參數刪除多筆資料
	 * 
	 * @param entities
	 */
	public void delete(Iterable<? extends T> entities) ;
	
	/**
	 * 使用 Primary Key 作為參數取得該筆資料
	 * 
	 * @param id
	 * @return T Object
	 */
	public T findOne(ID id);

	/**
	 * 使用 Primary Key 作為參數檢查該筆資料是否存在
	 * 
	 * @param id
	 * @return 資料存在返回 true 反之 false
	 */
	public boolean exists(ID id);
	
	/**
	 * 使用 Specification 作為參數取得單筆資料
	 * 
	 * @param spec
	 * @return T
	 */
	public T findOne(Specifiable spec);
	
	/**
	 * 取得所有資料
	 * 
	 * @return List<T> entities 失敗則返回 null
	 */
	public List<T> findAll();
	
	/**
	 * 使用 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public List<T> findAll(Sort sort);
	
	/**
	 * 使用 Specification 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public List<T> findAll(Specifiable spec);
	
	/**
	 * 使用 Specification 與 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public List<T> findAll(Specifiable spec, Sort sort);
	
	/**
	 * 使用 PageRequest 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public Page<T> findAll(Pageable pageRequest);
	
	/**
	 * 使用 PageRequest 與 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public Page<T> findAll(Specifiable spec, Pageable pageable);
	
	/**
	 * 取得所有資料的筆數
	 * 
	 * @param sort
	 * @return long 失敗則返回 0
	 */
	public long count();
	
	/**
	 * 使用 Specification 作為參數取得符合條件的筆數
	 * 
	 * @param sort
	 * @return long 失敗則返回 0
	 */
	public long count(Specifiable spec);

	
	/**
	 * 保存或更新 transient 或是 detached 狀態的 Entity
	 * 
	 * @param entity
	 * @return S 操作失敗返回 null
	 */
	public <S extends T> S save(S entity);
	
	/**
	 * 保存或更新 transient 或是 detached 狀態的 Entities
	 * 
	 * @param entities
	 * @return List 操作失敗返回 null
	 */
	public <S extends T> List<S> save(Iterable<S> entities);
	
}
