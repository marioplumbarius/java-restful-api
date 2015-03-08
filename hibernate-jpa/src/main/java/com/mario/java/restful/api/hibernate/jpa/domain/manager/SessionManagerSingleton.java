package com.mario.java.restful.api.hibernate.jpa.domain.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionManagerSingleton {
	private SessionFactory sessionFactory;
	private Session session;
    private Transaction transaction;
    private static SessionManagerSingleton instance = null;

    protected SessionManagerSingleton() {
    	this(SessionManagerSingleton.buildSessionFactory());
    }

    protected SessionManagerSingleton(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static SessionManagerSingleton getInstance(){
    	if(SessionManagerSingleton.instance == null){
    		SessionManagerSingleton.instance = new SessionManagerSingleton();
    	}

    	return SessionManagerSingleton.instance;
    }

    public static SessionManagerSingleton getInstance(SessionFactory sessionFactory){
    	if(SessionManagerSingleton.instance == null){
    		SessionManagerSingleton.instance = new SessionManagerSingleton(sessionFactory);
    	}

    	return SessionManagerSingleton.instance;
    }

    public static void setInstance(SessionManagerSingleton instance){
    	SessionManagerSingleton.instance = instance;
    }

    public void openSession() {
    	this.session = this.sessionFactory.getCurrentSession();
    }

    public Session getSession(){
    	return this.session;
    }

    public void closeSession() {
        this.sessionFactory.close();
    }

    public void openSessionWithTransaction() {
        this.session = this.sessionFactory.getCurrentSession();
        this.transaction = this.session.beginTransaction();
    }

    public void closeSessionWithTransaction() {
        this.transaction.commit();
        this.sessionFactory.close();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
