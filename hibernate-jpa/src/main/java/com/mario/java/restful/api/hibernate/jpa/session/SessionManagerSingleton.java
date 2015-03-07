package com.mario.java.restful.api.hibernate.jpa.session;

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
    	this(SessionManagerSingleton.getSessionFactory());
    }

    protected SessionManagerSingleton(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static SessionManagerSingleton getInstance(){
    	if(SessionManagerSingleton.instance == null){
    		SessionManagerSingleton.instance = new SessionManagerSingleton();
    	}

    	return instance;
    }

    public static void setInstance(SessionManagerSingleton instance){
    	SessionManagerSingleton.instance = instance;
    }

    public static SessionManagerSingleton getInstance(SessionFactory sessionFactory){
    	if(instance == null){
    		instance = new SessionManagerSingleton(sessionFactory);
    	}

    	return instance;
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
