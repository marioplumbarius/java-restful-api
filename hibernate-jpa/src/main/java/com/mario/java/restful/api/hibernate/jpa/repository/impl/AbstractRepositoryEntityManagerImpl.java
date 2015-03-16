package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mario.java.restful.api.hibernate.jpa.repository.AbstractRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

public class AbstractRepositoryEntityManagerImpl<T, ID extends Serializable> implements AbstractRepository<T, ID> {

	private String entityName;
	private Class<T> entityClass;
	private EntityManager entityManager;

	public AbstractRepositoryEntityManagerImpl(String entityName, Class<T> entityClass) {
		this(entityName, entityClass, EntityManagerSingleton.getInstance().getEntityManager());
	}


	public AbstractRepositoryEntityManagerImpl(String entityName, Class<T> entityClass, EntityManager entityManager) {
		this.entityName = entityName;
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		Query query = this.entityManager.createQuery("SELECT e FROM " + this.entityName + " e");
		List<T> entities = query.getResultList();

		return entities;
	}

	@Override
	public List<T> findAll(Map<String, Object> criterias) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T find(ID id) {

		// TODO Auto-generated method stub
		return null;
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

		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() throws Exception {

		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(List<T> entities) throws Exception {

		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Map<String, Object> criterias) throws Exception {

		// TODO Auto-generated method stub

	}
}
