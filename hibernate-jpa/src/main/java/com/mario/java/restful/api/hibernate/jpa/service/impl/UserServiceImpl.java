package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

public class UserServiceImpl implements UserService {
	private AbstractRepositoryHibernateImpl<UserDomain, Long> userCrud;
	private AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud;

	public UserServiceImpl() {
		this(new AbstractRepositoryHibernateImpl<UserDomain, Long>("UserDomain", UserDomain.class),new AbstractRepositoryHibernateImpl<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public UserServiceImpl(AbstractRepositoryHibernateImpl<UserDomain, Long> userCrud, AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud){
		this.userCrud = userCrud;
		this.petCrud = petCrud;
	}

	@Override
	public void persist(UserDomain user) {
		this.userCrud.persist(user);
	}

	@Override
	public void update(Long id, UserDomain user) {
		user.setId(id);

		try {
			this.userCrud.update(user);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, user.getClass().getName());
		}

	}

	@Override
	public UserDomain find(Long id) {
		UserDomain user = this.userCrud.find(id);

		return user;
	}

	@Override
	public List<UserDomain> findAll() {
		List<UserDomain> users = this.userCrud.findAll();
		return users;
	}

	@Override
	public List<UserDomain> findAll(Map<String, Object> criterias){
		List<UserDomain> users = this.userCrud.findAll(criterias);
		return users;
	}

	@Override
	public void delete(Long id) {
		UserDomain user = new UserDomain();
		user.setId(id);

		this.deletePets(id);

		try {
			this.userCrud.delete(user);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, user.getClass().getName());
		}
	}

	@Override
	public void deleteAll() {
		this.petCrud.deleteAll();
		this.userCrud.deleteAll();
	}

	private void deletePets(Object id){
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put("user.id", id);
		List<PetDomain> pets = this.petCrud.findAll(criterias);

		if(pets != null) this.petCrud.deleteAll(pets);
	}
}
