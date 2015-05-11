package com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.mario.java.restful.api.hibernate.jpa.repository.Repository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.util.RepositoryJPAImplUtils;

public abstract class RepositoryJPAImpl<T, ID extends Serializable> implements Repository<T, ID> {

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
		CriteriaQuery<T> criteriaQuery = RepositoryJPAImplUtils.buildCriteriaQueryFromRestrictions((Map<javax.persistence.metamodel.SingularAttribute<T, java.lang.Object>, java.lang.Object>) restrictions, this.getEntityManager(), this.getEntityClass());
		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<T> entities = typedQuery.getResultList();

		return entities;
	}
}