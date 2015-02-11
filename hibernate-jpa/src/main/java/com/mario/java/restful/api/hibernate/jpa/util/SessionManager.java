package com.mario.java.restful.api.hibernate.jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionManager {
	private SessionFactory sessionFactory;
	private Session session;
    private Transaction transaction;

    public SessionManager() {
    	this(SessionManager.getSessionFactory());
    }
    
    public SessionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public Session getSession(){
    	return this.session;
    }

    public void openSession() {
        this.session = this.sessionFactory.openSession();
    }

    public void closeSession() {
        this.session.close();
    }
    
    public void openSessionWithTransaction() {
        this.session = this.sessionFactory.openSession();
        this.transaction = this.session.beginTransaction();
    }
    
    public void closeSessionWithTransaction() {
        this.transaction.commit();
        this.session.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
