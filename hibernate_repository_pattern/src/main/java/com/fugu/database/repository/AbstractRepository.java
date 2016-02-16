package com.fugu.database.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

import com.fugu.database.HibernateUtil;
import com.fugu.database.page.Page;
import com.fugu.database.page.PageImpl;
import com.fugu.database.page.Pageable;
import com.fugu.database.sort.Sort;
import com.fugu.database.specification.Specifiable;
import com.fugu.database.utility.Assert;
import com.fugu.database.utility.Util;

/**
 * 為特定 Entity Repository 執行 CRUD 的抽象類別
 * 
 * @see org.springframework.data.repository.CrudRepository
 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.domain.Sort.Direction.PageRequest
 */
public abstract class AbstractRepository<T, ID extends Serializable> {
	
	/**
	 * 強制子類別傳回 Entity Class 以建立 Criteria 進行 Query 操作
	 * 
	 * @return	Class of Repository's Entity
	 */
	public abstract Class<T> getDomainClass();
	
	/**
	 * 開啟 Hibernate Session
	 * 
	 * @return	Session
	 */
	public Session openSession() {		
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		return session;
	}
	
	/**
	 * 使用 Primary Key 刪除該筆資料
	 * 
	 * @param id 必須大於0
	 */
	public void delete(ID id) throws RuntimeException {
	
		T entity = findOne(id);
		
		Assert.notNull(entity);

		delete(entity);
	}
	
	/**
	 * 使用 Entity 作為參數刪除該筆資料
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		
		Assert.notNull(entity);
		
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		} catch (RuntimeException e) {
			Util.logException(e);
			tx.rollback();
		} finally {
			session.close();
		}

	}


	/**
	 * 使用 Iterable<Entity> 作為參數刪除多筆資料
	 * 
	 * @param entities
	 */
	public void delete(Iterable<? extends T> entities) {
		
		Assert.notNull(entities);

		Session session = openSession();
		Transaction tx = null; 

		try {
			tx = session.beginTransaction();
			entities.forEach(entity -> session.delete(entity));
			tx.commit();
		} catch (RuntimeException e) {
			Util.logException(e);
			try {
				tx.rollback();
			} catch (HibernateException he) {
				Util.logException(he);
			}
		} finally {
			session.close();
		}
	}
	
	/**
	 * 使用 Primary Key 作為參數取得該筆資料
	 * 
	 * @param id
	 * @return T Object
	 */
	@SuppressWarnings("unchecked")
	public T findOne(ID id) {
		
		Class<T> classType = getDomainClass();
		T entity = null;
		
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			entity = (T) session.get(classType, id);
			tx.commit();
		} catch (RuntimeException e) {
			Util.logException(e);
			tx.rollback();
		} finally {
			session.close();
		}		
		
		return entity;
	}

	/**
	 * 使用 Primary Key 作為參數檢查該筆資料是否存在
	 * 
	 * @param id
	 * @return 資料存在返回 true 反之 false
	 */
	public boolean exists(ID id) {

		return findOne(id) != null;
	}
	
	/**
	 * 使用 Specification 作為參數取得單筆資料
	 * 
	 * @param spec
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T findOne(Specifiable spec) {
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);		

		criteria = buildCriteria(criteria, spec, null, null);
		
		T entity;
		
		try {
			entity = (T) criteria.uniqueResult();
			return entity;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 取得所有資料
	 * 
	 * @return List<T> entities 失敗則返回 null
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		
		List<T> entities;
		
		try {
			entities = criteria.list();
			return entities;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 使用 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Sort sort) {	
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		criteria = buildCriteria(criteria, null, null, sort);
		
		List<T> entities;
		
		try {
			entities = criteria.list();
			return entities;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 使用 Specification 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Specifiable spec) {		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		criteria = buildCriteria(criteria, spec, null, null);
		
		List<T> entities;
		
		try {
			entities = criteria.list();
			return entities;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 使用 Specification 與 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Specifiable spec, Sort sort) {
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		criteria = buildCriteria(criteria, spec, null, sort);
		
		List<T> entities;
		
		try {
			entities = criteria.list();
			return entities;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 使用 PageRequest 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	public Page<T> findAll(Pageable pageable) {
		
		return findAll(null, pageable);
	}
	
	/**
	 * 使用 PageRequest 與 Sort 作為參數取得符合條件的多筆資料
	 * 
	 * @param sort
	 * @return List<T> entities 失敗則返回 null
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAll(Specifiable spec, Pageable pageable) {
		
		Assert.notNull(pageable);
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		
		criteria = buildCriteria(criteria, spec, pageable, null);
		
		try {
			long total = count(spec);
			List<T> content = criteria.list();
			
			Page<T> page = new PageImpl<T>(content, pageable, total);			
			return page;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 取得所有資料的筆數
	 * 
	 * @param sort
	 * @return long 失敗則返回 0
	 */
	public long count() {
		
		return count(null);
	}
	
	/**
	 * 使用 Specification 作為參數取得符合條件的筆數
	 * 
	 * @param sort
	 * @return long 失敗則返回 0
	 */
	public long count(Specifiable spec) {
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		criteria = buildCriteria(criteria, spec, null, null);
		criteria.setProjection(Projections.rowCount());
		
		Long rowCount;
		
		try {
			rowCount = (Long) criteria.uniqueResult();
			return rowCount;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return 0;
	}

	
	/**
	 * 保存或更新 transient 或是 detached 狀態的 Entity
	 * 
	 * @param entity
	 * @return <S extends T> S 操作失敗返回 null
	 */
	public <S extends T> S save(S entity) {
		
		Assert.notNull(entity);
		
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(entity);
			tx.commit();
			return entity;
		} catch (RuntimeException e) {
			Util.logException(e);
			tx.rollback();
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 保存或更新 transient 或是 detached 狀態的 Entities
	 * 
	 * @param entities
	 * @return <S extends T> List<S> 操作失敗返回 null
	 */
	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) {
		
		Assert.notNull(entities);
		
		List<S> result = new ArrayList<S>();

		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();					
			entities.forEach(entity -> {
				session.saveOrUpdate(entity); 
				result.add(entity);
			});			
			tx.commit();
			return result;
		} catch (RuntimeException e) {
			Util.logException(e);		
			try {
				tx.rollback();
			} catch (HibernateException he) {
				Util.logException(he);
			}
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * 使用實現 Criterial 介面的類別更新 Criteria
	 * 
	 * @param criteria
	 * @param spec
	 * @param page
	 * @param sort
	 * @return Criteria
	 */
	private Criteria buildCriteria(Criteria criteria, Specifiable spec, Pageable page, Sort sort) {
		
		if (spec != null) {
			spec.toCriteria(criteria);
		}
		
		if (page != null) {
			page.toCriteria(criteria);
		}
		
		if (sort != null) {
			sort.toCriteria(criteria);
		}
		
		return criteria;	
	}
	
}
