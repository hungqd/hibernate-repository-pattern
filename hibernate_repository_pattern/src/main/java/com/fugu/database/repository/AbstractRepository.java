package com.fugu.database.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

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
public abstract class AbstractRepository<T, ID extends Serializable> implements Repository<T, ID> {
	
	/**
	 * {@inheritDoc}}
	 */
	public abstract Class<T> getDomainClass();
	
	/**
	 * {@inheritDoc}}
	 */
	public Session openSession() {		
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		return session;
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public void delete(ID id) throws RuntimeException {
	
		T entity = findOne(id);
		
		Assert.notNull(entity);

		delete(entity);
	}
	
	/**
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
	 */
	public boolean exists(ID id) {

		return findOne(id) != null;
	}
	
	/**
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
	 */
	public Page<T> findAll(Pageable pageRequest) {
		
		return findAll(null, pageRequest);
	}
	
	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAll(Specifiable spec, Pageable pageRequest) {
		
		Assert.notNull(pageRequest);
		
		Session session = openSession();
		
		Class<T> classType = getDomainClass();		
		Criteria criteria = session.createCriteria(classType);
		
		criteria = buildCriteria(criteria, spec, pageRequest, null);
		
		try {
			long total = count(spec);
			List<T> content = criteria.list();
			
			Page<T> page = new PageImpl<T>(content, pageRequest, total);			
			return page;
		} catch (HibernateException he) {
			Util.logException(he);
		} finally {
			session.close();
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}}
	 */
	public long count() {
		
		return count(null);
	}
	
	/**
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
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
	 * {@inheritDoc}}
	 */
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
	 * {@inheritDoc}}
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
