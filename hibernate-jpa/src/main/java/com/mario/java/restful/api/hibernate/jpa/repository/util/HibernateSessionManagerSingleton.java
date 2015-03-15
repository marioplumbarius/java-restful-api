package com.mario.java.restful.api.hibernate.jpa.repository.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionManagerSingleton {
	private SessionFactory sessionFactory;
	private Session session;
    private Transaction transaction;
    private static HibernateSessionManagerSingleton instance = null;

    protected HibernateSessionManagerSingleton() {
    	this(HibernateSessionManagerSingleton.buildSessionFactory());
    }

    protected HibernateSessionManagerSingleton(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static HibernateSessionManagerSingleton getInstance(){
    	if(HibernateSessionManagerSingleton.instance == null){
    		HibernateSessionManagerSingleton.instance = new HibernateSessionManagerSingleton();
    	}

    	return HibernateSessionManagerSingleton.instance;
    }

    public static HibernateSessionManagerSingleton getInstance(SessionFactory sessionFactory){
    	if(HibernateSessionManagerSingleton.instance == null){
    		HibernateSessionManagerSingleton.instance = new HibernateSessionManagerSingleton(sessionFactory);
    	}

    	return HibernateSessionManagerSingleton.instance;
    }

    public static void setInstance(HibernateSessionManagerSingleton instance){
    	HibernateSessionManagerSingleton.instance = instance;
    }

    public void openSession() {
    	this.session = this.sessionFactory.openSession();
    }

    public Session getSession(){
    	return this.session;
    }

    public void closeSession() {
        this.session.close();
    }

    public void openSessionWithTransaction() {
        this.openSession();
        this.transaction = this.session.beginTransaction();
    }

    public void closeSessionWithTransaction() {
        this.transaction.commit();
        this.closeSession();
    }

	private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
