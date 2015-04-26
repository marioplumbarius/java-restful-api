package com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.mario.java.restful.api.hibernate.jpa.repository.Repository;

public abstract class RepositoryJPAImpl<T, ID extends Serializable> implements Repository<T, ID> {

	private static final Logger LOGGER = Logger.getLogger(RepositoryJPAImpl.class.getSimpleName());

	// TODO change method visibility to protected
	protected abstract Class<T> getEntityClass();
	protected abstract EntityManager getEntityManager();

	@Override
	public List<T> findAll(){
		String sqlQuery = "SELECT t FROM " + this.getEntityClass().getSimpleName() + " t";
		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(sqlQuery, this.getEntityClass());
		List<T> entities = typedQuery.getResultList();

		return entities;
	}

	@Override
	public T find(ID id) {
		return this.getEntityManager().find(this.getEntityClass(), id);
	}

	@Override
	public void persist(T entity) throws Exception {
		try {
			this.getEntityManager().getTransaction().begin();
			this.getEntityManager().persist(entity);
			this.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void update(T entity) throws Exception {
		try {
			this.getEntityManager().getTransaction().begin();
			this.getEntityManager().merge(entity);
			this.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}
	
	@Override
	public void refresh(ID id) {
		T managedEntity = this.find(id);
		
		try {
			this.getEntityManager().getTransaction().begin();
			this.getEntityManager().refresh(managedEntity);
			this.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void delete(T entity) throws Exception {
		try {
			this.getEntityManager().getTransaction().begin();
			this.getEntityManager().remove(entity);
			this.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void deleteAll() throws Exception {
		List<T> entities = this.findAll();

		if(entities != null){
			this.deleteAll(entities);
		}
	}

	@Override
	public void deleteAll(List<T> entities) throws Exception {
		try {
			this.getEntityManager().getTransaction().begin();
			for (T entity : entities) {
				this.getEntityManager().remove(entity);
			}
			this.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.getEntityManager().getTransaction().rollback();
			throw e;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "hiding" })
	public <SingularAttribute, Object> List<T> findAll(Map<SingularAttribute, Object> restrictions) {
		CriteriaQuery<T> criteriaQuery = this.buildCriteriaQueryWithRestrictions((Map<javax.persistence.metamodel.SingularAttribute<T, java.lang.Object>, java.lang.Object>) restrictions);
		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<T> entities = typedQuery.getResultList();

		return entities;
	}

	// TODO - move this method to another class
	/**
	 * Builds a custom {@link CriteriaQuery} criteriaQuery matching the {@link Map<SingularAttribute<T, Object>, Object>} restrictions.
	 * @param restrictions the restrictions to be applied to the criteriaQuery
	 * @return the criteriaQuery matching the restrictions
	 */
	private CriteriaQuery<T> buildCriteriaQueryWithRestrictions(Map<SingularAttribute<T, Object>, Object> restrictions) {
		CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.getEntityClass());
		Root<T> entity = criteriaQuery.from(this.getEntityClass());
		List<Predicate> predicates = new ArrayList<Predicate>();

		for(Map.Entry<SingularAttribute<T, Object>, Object> restriction : restrictions.entrySet()){
			Predicate predicate = criteriaBuilder.equal(entity.get(restriction.getKey()), restriction.getValue());
			predicates.add(predicate);
		}

		/**
		 * The following code is experiencing a crash when using Hibernate's metamodel class generation.
		 * bug report: https://hibernate.atlassian.net/browse/HHH-9259
		 */
		criteriaQuery.select(entity).where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));

		return criteriaQuery;
	}
}