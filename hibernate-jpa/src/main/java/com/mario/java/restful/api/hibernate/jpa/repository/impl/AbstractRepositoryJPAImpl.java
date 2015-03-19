package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.mario.java.restful.api.hibernate.jpa.repository.AbstractRepository;

public class AbstractRepositoryJPAImpl<T, ID extends Serializable> implements AbstractRepository<T, ID> {

	private Class<T> entityClass;
	protected EntityManager entityManager;

	/**
	 * Default constructor which creates a new instance.
	 * @param entityClass the entityClass of the instance
	 * @param entityManager the entityManager of the instance
	 */
	public AbstractRepositoryJPAImpl(Class<T> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}

	@Override
	public List<T> findAll(){
		String sqlQuery = "SELECT t FROM " + this.entityClass.getSimpleName() + " t";
		TypedQuery<T> typedQuery = this.entityManager.createQuery(sqlQuery, this.entityClass);
		List<T> entities = typedQuery.getResultList();

		return entities;
	}

	@Override
	public T find(ID id) {
		return this.entityManager.find(this.entityClass, id);
	}

	@Override
	public void persist(T entity) throws Exception {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.persist(entity);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void update(T entity) throws Exception {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.merge(entity);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void delete(T entity) throws Exception {
		try {
			this.entityManager.getTransaction().begin();
			this.entityManager.remove(entity);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
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
			this.entityManager.getTransaction().begin();
			for (T entity : entities) {
				this.entityManager.remove(entity);
			}
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "hiding" })
	public <SingularAttribute, Object> List<T> findAll(Map<SingularAttribute, Object> restrictions) {
		CriteriaQuery<T> criteriaQuery = this.buildCriteriaQueryWithRestrictions((Map<javax.persistence.metamodel.SingularAttribute<T, java.lang.Object>, java.lang.Object>) restrictions);
		TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
		List<T> entities = typedQuery.getResultList();

		return entities;
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> void deleteAll(Map<SingularAttribute, Object> restrictions) throws Exception {
		List<T> entities = this.findAll(restrictions);
		this.deleteAll(entities);
	}

	// TODO - move this method to another class
	/**
	 * Builds a custom {@link CriteriaQuery} criteriaQuery matching the {@link Map<SingularAttribute<T, Object>, Object>} restrictions.
	 * @param restrictions the restrictions to be applied to the criteriaQuery
	 * @return the criteriaQuery matching the restrictions
	 */
	private CriteriaQuery<T> buildCriteriaQueryWithRestrictions(Map<SingularAttribute<T, Object>, Object> restrictions) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
		Root<T> entity = criteriaQuery.from(this.entityClass);

		for(Map.Entry<SingularAttribute<T, Object>, Object> restriction : restrictions.entrySet()){
			criteriaQuery.select(entity).where(criteriaBuilder.equal(entity.get(restriction.getKey()), restriction.getValue()));
		}

		return criteriaQuery;
	}
}