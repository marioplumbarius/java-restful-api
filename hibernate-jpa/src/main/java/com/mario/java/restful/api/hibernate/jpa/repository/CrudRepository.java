package com.mario.java.restful.api.hibernate.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;
import org.hibernate.criterion.Restrictions;

import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;

public class CrudRepository<T, ID extends Serializable> {
	
	private String domainName;
	private Class<T> domainClass;
	private SessionManager sessionManager;
	
	public CrudRepository(String domainName, Class<T> domainClass) {
    	this(new SessionManager(), domainName, domainClass);
	}
    
    public CrudRepository(SessionManager sessionManager, String domainName, Class<T> domainClass){
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
	public List<T> findAll(Map<String, String> criterias){
		List<T> entities = null;
    	
    	this.sessionManager.openSession();
    	entities = this.sessionManager.getSession().createCriteria(domainClass).add(Restrictions.allEq(criterias)).list();
    	this.sessionManager.closeSession();
    	
    	return entities;
	}
    
    @SuppressWarnings("unchecked")
	public final T find(ID id){
    	this.sessionManager.openSession();
		T entity = (T) this.sessionManager.getSession().get(domainClass, id);
    	this.sessionManager.closeSession();
        return entity;
	}
    
    @SuppressWarnings("unchecked")
	public List<T> findBy(String key, String value){
    	List<T> entities = null;
    	
    	this.sessionManager.openSession();
    	entities = this.sessionManager.getSession().createCriteria(domainClass).add(Restrictions.eq(key, value)).list();
    	this.sessionManager.closeSession();
    	
    	return entities;
    }
    
    public void persist(T entity) {
    	this.sessionManager.openSessionWithTransaction();
    	this.sessionManager.getSession().save(entity);
    	this.sessionManager.closeSessionWithTransaction();
    }
    
    public void update(ID id, T entity) {
        try {
        	this.sessionManager.openSessionWithTransaction();
            this.sessionManager.getSession().update(entity);
            this.sessionManager.closeSessionWithTransaction();
		} catch (StaleStateException e) {
			throw(new ObjectNotFoundException(id, entity.getClass().getName()));
		}
    }
    
    public void delete(T entity) {
    	this.sessionManager.openSessionWithTransaction();
    	this.sessionManager.getSession().delete(entity);
    	this.sessionManager.closeSessionWithTransaction();
    }
    
    public void deleteAll() {
        List<T> entities = this.findAll();
        for (T entity : entities)
            this.delete(entity);
    }
}
