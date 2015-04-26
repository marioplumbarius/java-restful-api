package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.RepositoryJPAImpl;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

@RequestScoped
public class PetRepositoryImpl extends RepositoryJPAImpl<PetEntity, Long> implements PetRepository {

	private static final Class<PetEntity> entityClass = PetEntity.class;
	private static final EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

	public PetRepositoryImpl(){

	}

	@Override
	protected Class<PetEntity> getEntityClass() {
		return PetRepositoryImpl.entityClass;
	}
	@Override
	protected EntityManager getEntityManager() {
		return PetRepositoryImpl.entityManager;
	}
}
