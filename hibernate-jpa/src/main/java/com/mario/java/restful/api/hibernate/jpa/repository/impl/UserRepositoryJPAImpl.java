package com.mario.java.restful.api.hibernate.jpa.repository.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain_;

public class UserRepositoryJPAImpl extends AbstractRepositoryJPAImpl<UserDomain, Long> {

	public static final Class<UserDomain> entityClass = UserDomain.class;

	public UserRepositoryJPAImpl() {
		super(UserRepositoryJPAImpl.entityClass);
	}

	@Override
	public List<UserDomain> findAll(Map<String, Object> restrictions){
		// TODO - refactor me!!!!
		// - break up into multiple methods

		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<UserDomain> criteriaQuery = criteriaBuilder.createQuery(UserDomain.class);
		Root<UserDomain> user = criteriaQuery.from(UserDomain.class);

		Object userId = restrictions.get("id");
		Object userName = restrictions.get("name");

		if(userId != null){
			criteriaQuery.select(user).where(criteriaBuilder.equal(user.get(UserDomain_.id), userId));
		}

		if(userName != null){
			criteriaQuery.select(user).where(criteriaBuilder.equal(user.get(UserDomain_.name), userName));
		}

		TypedQuery<UserDomain> typedQuery = this.entityManager.createQuery(criteriaQuery);
		List<UserDomain> users = typedQuery.getResultList();

		return users;
	}
}