package com.mario.java.restful.api.hibernate.jpa.application;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.AbstractRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryJPAImpl;
import com.mario.java.restful.api.hibernate.jpa.repository.util.EntityManagerSingleton;
import com.mario.java.restful.api.hibernate.jpa.resource.PetResource;
import com.mario.java.restful.api.hibernate.jpa.resource.UserResource;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.restful.api.hibernate.jpa.service.impl.UserServiceNewImpl;

public class ApiApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ApiApplication() {
		this.singletons.add(new UserResource(this.buildUserService()));
		this.singletons.add(new PetResource());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}

	// TODO - move this to a bean configuration and inject them
	private UserService buildUserService(){
		EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManager();
		AbstractRepository<UserDomain, Long> abstractRepositoryUser = new AbstractRepositoryJPAImpl<UserDomain, Long>(UserDomain.class, entityManager);
		AbstractRepository<PetDomain, Long> abstractRepositoryPet = new AbstractRepositoryJPAImpl<PetDomain, Long>(PetDomain.class, entityManager);

		return new UserServiceNewImpl(abstractRepositoryUser, abstractRepositoryPet);
	}
}
