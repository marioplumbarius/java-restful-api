package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

public class UserServiceImpl implements UserService {
	private AbstractRepositoryHibernateImpl<UserDomain, Long> abstractRepositoryHibernateImplUser;
	private AbstractRepositoryHibernateImpl<PetDomain, Long> abstractRepositoryHibernateImplPet;

	public UserServiceImpl() {
		this(new AbstractRepositoryHibernateImpl<UserDomain, Long>("UserDomain", UserDomain.class),new AbstractRepositoryHibernateImpl<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public UserServiceImpl(AbstractRepositoryHibernateImpl<UserDomain, Long> abstractRepositoryHibernateImplUser, AbstractRepositoryHibernateImpl<PetDomain, Long> abstractRepositoryHibernateImplPet){
		this.abstractRepositoryHibernateImplUser = abstractRepositoryHibernateImplUser;
		this.abstractRepositoryHibernateImplPet = abstractRepositoryHibernateImplPet;
	}

	@Override
	public void persist(UserDomain user) {
		this.abstractRepositoryHibernateImplUser.persist(user);
	}

	@Override
	public void update(Long id, UserDomain user) {
		user.setId(id);

		try {
			this.abstractRepositoryHibernateImplUser.update(user);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, user.getClass().getName());
		}

	}

	@Override
	public UserDomain find(Long id) {
		UserDomain user = this.abstractRepositoryHibernateImplUser.find(id);

		return user;
	}

	@Override
	public List<UserDomain> findAll() {
		List<UserDomain> users = this.abstractRepositoryHibernateImplUser.findAll();
		return users;
	}

	@SuppressWarnings("hiding")
	@Override
	public <String, Object> List<UserDomain> findAll(Map<String, Object> restrictions){
		List<UserDomain> users = this.abstractRepositoryHibernateImplUser.findAll(restrictions);
		return users;
	}

	@Override
	public void delete(Long id) {
		UserDomain user = new UserDomain();
		user.setId(id);

		this.deletePets(id);

		try {
			this.abstractRepositoryHibernateImplUser.delete(user);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, user.getClass().getName());
		}
	}

	@Override
	public void deleteAll() {
		this.abstractRepositoryHibernateImplPet.deleteAll();
		this.abstractRepositoryHibernateImplUser.deleteAll();
	}

	@SuppressWarnings("hiding")
	@Override
	public <String, Object> void deleteAll(Map<String, Object> restrictions) throws Exception, ObjectNofFoundException {
		this.deleteAll(restrictions);
	}

	private void deletePets(Object id){
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put("user.id", id);
		List<PetDomain> pets = this.abstractRepositoryHibernateImplPet.findAll(criterias);

		if(pets != null) this.abstractRepositoryHibernateImplPet.deleteAll(pets);
	}
}