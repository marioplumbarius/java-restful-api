package com.mario.java.restful.api.hibernate.jpa.dao.implementation;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.dao.interfacee.UserDaoInterface;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;

public class UserDaoImplementation implements UserDaoInterface<UserDomain, Long> {
    
	private SessionManager sessionManager;
    
    public UserDaoImplementation() {
    	this(new SessionManager());
	}
    
    public UserDaoImplementation(SessionManager sessionManager){
    	this.sessionManager = sessionManager;
    }

    @Override
    public void persist(UserDomain entity) {
    	this.sessionManager.openSessionWithTransaction();
    	this.sessionManager.getSession().save(entity);
    	this.sessionManager.closeSessionWithTransaction();
    }

    @Override
    public void update(Long id, UserDomain entity) {
    	entity.setId(id);
        
        try {
        	this.sessionManager.openSessionWithTransaction();
            this.sessionManager.getSession().update(entity);
            this.sessionManager.closeSessionWithTransaction();
		} catch (StaleStateException e) {
			throw(new ObjectNotFoundException(id, entity.getClass().getName()));
		}
    }

    @Override
    public UserDomain find(Long id) {
        this.sessionManager.openSession();
    	UserDomain user = (UserDomain) this.sessionManager.getSession().get(UserDomain.class, id);
    	this.sessionManager.closeSession();
        return user;
    }

    @Override
    public void delete(UserDomain entity) {
    	this.sessionManager.openSessionWithTransaction();
    	this.sessionManager.getSession().delete(entity);
    	this.sessionManager.closeSessionWithTransaction();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDomain> findAll() {
    	this.sessionManager.openSession();
        List<UserDomain> users = this.sessionManager.getSession()
                .createQuery("from UserDomain").list();
        this.sessionManager.closeSession();
        return users;
    }

    @Override
    public void deleteAll() {
        List<UserDomain> users = this.findAll();
        for (UserDomain user : users)
            this.delete(user);
    }
}
