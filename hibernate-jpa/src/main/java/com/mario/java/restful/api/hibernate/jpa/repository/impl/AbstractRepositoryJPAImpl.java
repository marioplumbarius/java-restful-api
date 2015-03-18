package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mario.java.restful.api.hibernate.jpa.repository.AbstractRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.ObjectNofFoundException;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

public class AbstractRepositoryJPAImpl<T, ID extends Serializable> implements AbstractRepository<T, ID> {

	private String entityName;
	private Class<T> entityClass;
	private EntityManager entityManager;
	private String baseQuery;

	public AbstractRepositoryJPAImpl(String entityName, Class<T> entityClass) {
		this(entityName, entityClass, EntityManagerSingleton.getInstance().getEntityManager());
	}

	public AbstractRepositoryJPAImpl(String entityName, Class<T> entityClass, EntityManager entityManager) {
		this.entityName = entityName;
		this.entityClass = entityClass;
		this.entityManager = entityManager;
		this.baseQuery = "SELECT t FROM " + this.entityName + " t";
	}

	@Override
	public List<T> findAll(){
		TypedQuery<T> typedQuery = this.entityManager.createQuery(this.baseQuery, this.entityClass);
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
		this.deleteAll(entities);
	}

	@Override
	public void deleteAll(List<T> entities) throws Exception {
		if(entities != null){
			this.deleteAllHelper(entities);
		} else {
			throw new ObjectNofFoundException(this.entityName);
		}
	}

	private void deleteAllHelper(List<T> entities) throws Exception {
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
	public List<T> findAll(Map<String, Object> restrictions) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Map<String, Object> restrictions) throws Exception {

		// TODO Auto-generated method stub

	}
}