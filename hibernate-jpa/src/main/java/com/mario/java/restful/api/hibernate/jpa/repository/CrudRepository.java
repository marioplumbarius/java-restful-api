package com.mario.java.restful.api.hibernate.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Restrictions;

import com.mario.java.restful.api.hibernate.jpa.domain.manager.SessionManagerSingleton;

public class CrudRepository<T, ID extends Serializable> {

	private String domainName;
	private Class<T> domainClass;
	private SessionManagerSingleton sessionManager;

	public CrudRepository(String domainName, Class<T> domainClass) {
		this(SessionManagerSingleton.getInstance(), domainName, domainClass);
	}

	public CrudRepository(SessionManagerSingleton sessionManager, String domainName, Class<T> domainClass){
		this.sessionManager = sessionManager;
		this.domainName = domainName;
		this.domainClass = domainClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		this.sessionManager.openSession();
		List<T> entities = this.sessionManager.getSession().createQuery("from "+this.domainName).list();
		this.sessionManager.closeSession();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Map<String, Object> criterias){
		List<T> entities = null;

		this.sessionManager.openSession();
		entities = this.sessionManager.getSession().createCriteria(this.domainClass).add(Restrictions.allEq(criterias)).list();
		this.sessionManager.closeSession();

		return entities;
	}

	@SuppressWarnings("unchecked")
	public T find(ID id){
		this.sessionManager.openSession();
		T entity = (T) this.sessionManager.getSession().get(this.domainClass, id);
		this.sessionManager.closeSession();
		return entity;
	}

	public void persist(T entity) {
		this.sessionManager.openSessionWithTransaction();
		this.sessionManager.getSession().save(entity);
		this.sessionManager.closeSessionWithTransaction();
	}

	public void update(T entity) {
		this.sessionManager.openSessionWithTransaction();
		this.sessionManager.getSession().update(entity);
		this.sessionManager.closeSessionWithTransaction();
	}

	public void delete(T entity) {
		this.sessionManager.openSessionWithTransaction();
		this.sessionManager.getSession().delete(entity);
		this.sessionManager.closeSessionWithTransaction();
	}

	public void deleteAll() {
		List<T> entities = this.findAll();
		this.deleteAll(entities);
	}

	public void deleteAll(Map<String, Object> criterias){
		List<T> entities = this.findAll(criterias);
		this.deleteAll(entities);
	}

	public void deleteAll(List<T> entities){
		if(entities != null){
			this.deleteAllPrivate(entities);
		} else {
			throw (new ObjectNotFoundException(null, this.domainName));
		}
	}

	private void deleteAllPrivate(List<T> entities){
		this.sessionManager.openSessionWithTransaction();
		for (T entity : entities) {
			this.sessionManager.getSession().delete(entity);
		}
		this.sessionManager.closeSessionWithTransaction();
	}
}
