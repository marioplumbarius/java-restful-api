package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.RepositoryJPAImpl;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

@RequestScoped
public class PetRepositoryImpl extends RepositoryJPAImpl<PetDomain, Long> implements PetRepository {

	private static final Class<PetDomain> entityClass = PetDomain.class;
	private static final EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

	public PetRepositoryImpl(){

	}

	@Override
	public Class<PetDomain> getEntityClass() {
		return PetRepositoryImpl.entityClass;
	}
	@Override
	public EntityManager getEntityManager() {
		return PetRepositoryImpl.entityManager;
	}
}
