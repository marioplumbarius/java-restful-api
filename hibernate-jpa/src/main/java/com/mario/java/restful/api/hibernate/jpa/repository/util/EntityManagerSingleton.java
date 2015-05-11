package com.mario.java.restful.api.hibernate.jpa.repository.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Singleton which holds the entity manager instance for the entire application.
 * @author marioluan
 */
public class EntityManagerSingleton {

	private EntityManager entityManager;
	private static EntityManagerSingleton instance = null;

	protected EntityManagerSingleton(){
		this(Persistence.createEntityManagerFactory("PersistenceUnit").createEntityManager());
	}

	protected EntityManagerSingleton(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public static EntityManagerSingleton getInstance(){
		if(EntityManagerSingleton.instance == null){
			EntityManagerSingleton.instance = new EntityManagerSingleton();
		}

		return EntityManagerSingleton.instance;
	}

	public static EntityManagerSingleton getInstance(EntityManager entityManager){
		if(EntityManagerSingleton.instance == null){
			EntityManagerSingleton.instance = new EntityManagerSingleton(entityManager);
		}

		return EntityManagerSingleton.instance;
	}

	public EntityManager getEntityManager(){
		return this.entityManager;
	}

}