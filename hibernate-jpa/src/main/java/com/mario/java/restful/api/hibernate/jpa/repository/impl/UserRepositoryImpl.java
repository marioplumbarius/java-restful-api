package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.repository.UserRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.jpa.RepositoryJPAImpl;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;

@RequestScoped
public class UserRepositoryImpl extends RepositoryJPAImpl<UserEntity, Long> implements UserRepository {

	private static final Class<UserEntity> entityClass = UserEntity.class;
	private static final EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();

	public UserRepositoryImpl(){

	}

	@Override
	public Class<UserEntity> getEntityClass() {
		return UserRepositoryImpl.entityClass;
	}
	@Override
	public EntityManager getEntityManager() {
		return UserRepositoryImpl.entityManager;
	}
}
