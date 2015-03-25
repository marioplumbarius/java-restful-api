package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.UserRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.AbstractRepositoryJPAImpl;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

@RequestScoped
public class UserRepositoryImpl extends AbstractRepositoryJPAImpl<UserDomain, Long> implements UserRepository {

	private static final Class<UserDomain> entityClass = UserDomain.class;
	private static final EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

	public UserRepositoryImpl(){

	}

	@Override
	public Class<UserDomain> getEntityClass() {
		return UserRepositoryImpl.entityClass;
	}
	@Override
	public EntityManager getEntityManager() {
		return UserRepositoryImpl.entityManager;
	}
}
