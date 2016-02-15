package com.fugu.tim.hibernate_repository_pattern.db.specification;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Specification 類別繼承此抽象類 實現聯集交集餘集功能 
 */
public abstract class AbstractSpecification implements Specifiable {

	/**
	* {@inheritDoc}
	*/
	public abstract Criterion toCriterion();
	
	/**
	* {@inheritDoc}
	*/
	public Specifiable and(final Specifiable specification) {
		
		return new AndSpecification(this, specification);
	}

	/**
	* {@inheritDoc}
	*/
	public Specifiable or(final Specifiable specification) {
		
		return new OrSpecification(this, specification);
	}

	/**
	* {@inheritDoc}
	*/
	public Specifiable not(final Specifiable specification) {
		
		return new NotSpecification(specification);
	}
	
	/**
	* {@inheritDoc}
	*/
	public Criteria toCriteria(Criteria criteria) {
		return criteria.add(toCriterion());
	}
	
	/**
	 * 返回符合類別與參數聯集條件的 Specification 
	 */
	private class AndSpecification extends AbstractSpecification {

		private Specifiable spec1;
		private Specifiable spec2;

		public AndSpecification(final Specifiable spec1, final Specifiable spec2) {
			this.spec1 = spec1;
			this.spec2 = spec2;
		}

		/**
		* {@inheritDoc}
		*/
		public Criterion toCriterion() {
			return Restrictions.and(this.spec1.toCriterion(), this.spec2.toCriterion());
		}
	}
	
	/**
	 * 返回符合類別與參數交集條件的 Specification 
	 */
	private class OrSpecification extends AbstractSpecification {

		private Specifiable spec1;
		private Specifiable spec2;

		public OrSpecification(final Specifiable spec1, final Specifiable spec2) {
			
			this.spec1 = spec1;
			this.spec2 = spec2;
		}

		/**
		* {@inheritDoc}
		*/
		public Criterion toCriterion() {
			
			return Restrictions.or(this.spec1.toCriterion(), this.spec2.toCriterion());
		}
	}
	
	/**
	 * 返回符合類別餘集條件的 Specification 
	 */
	private class NotSpecification extends AbstractSpecification {

	    private Specifiable spec1;

	    public NotSpecification(final Specifiable spec1) {

	        this.spec1 = spec1;
	    }

		/**
		* {@inheritDoc}
		*/
	    public Criterion toCriterion() {
	    
	        return Restrictions.not(this.spec1.toCriterion());
	    }
	}
}
