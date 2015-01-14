package com.mario.java.restful.api.hibernate.jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionManager {
	private Session session;
	private Transaction transaction;

	public SessionManager() {
	}

	public Session openSession() {
		this.session = getSessionFactory().openSession();
		return this.session;
	}

	public Session openSessionWithTransaction() {
		this.session = getSessionFactory().openSession();
		this.transaction = this.session.beginTransaction();
		return this.session;
	}

	public void closeSession() {
		this.session.close();
	}

	public void closeSessionWithTransaction() {
		this.transaction.commit();
		this.session.close();
	}

	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(builder.build());

		return sessionFactory;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
