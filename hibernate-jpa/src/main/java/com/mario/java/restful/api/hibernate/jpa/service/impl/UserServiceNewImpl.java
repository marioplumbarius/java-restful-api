package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain_;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.AbstractRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

public class UserServiceNewImpl implements UserService {

	private AbstractRepository<UserDomain, Long> abstractRepositoryUser;
	private AbstractRepository<PetDomain, Long> abstractRepositoryPet;

	public UserServiceNewImpl(AbstractRepository<UserDomain, Long> abstractRepositoryUser, AbstractRepository<PetDomain, Long> abstractRepositoryPet){
		this.abstractRepositoryUser = abstractRepositoryUser;
		this.abstractRepositoryPet = abstractRepositoryPet;
	}

	@Override
	public void persist(UserDomain userDomain) throws Exception {
		this.abstractRepositoryUser.persist(userDomain);
	}

	@Override
	public void update(Long id, UserDomain userDomain) throws Exception, ObjectNofFoundException {
		userDomain.setId(id);

		this.abstractRepositoryUser.update(userDomain);
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNofFoundException {
		UserDomain userDomain = this.find(id);

		if(userDomain != null){
			this.deletePets(userDomain);
			this.abstractRepositoryUser.delete(userDomain);
		} else {
			throw new ObjectNofFoundException(id, UserDomain.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNofFoundException {
		this.abstractRepositoryUser.deleteAll();
		this.abstractRepositoryPet.deleteAll();
	}

	@Override
	public UserDomain find(Long id) {
		return this.abstractRepositoryUser.find(id);
	}

	@Override
	public List<UserDomain> findAll() {
		return this.abstractRepositoryUser.findAll();
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> void deleteAll(Map<SingularAttribute, Object> restrictions) throws Exception, ObjectNofFoundException {
		this.abstractRepositoryUser.deleteAll(restrictions);
		this.abstractRepositoryPet.deleteAll(restrictions);
	}

	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<UserDomain> findAll(Map<SingularAttribute, Object> restrictions) {
		return this.abstractRepositoryUser.findAll(restrictions);
	}

	private void deletePets(UserDomain userDomain) throws Exception {
		Map<javax.persistence.metamodel.SingularAttribute<PetDomain, UserDomain>, Object> restrictions = new HashMap<javax.persistence.metamodel.SingularAttribute<PetDomain,UserDomain>, Object>();
		restrictions.put(PetDomain_.user, userDomain);
		List<PetDomain> pets = this.abstractRepositoryPet.findAll(restrictions);

		if(pets != null) this.abstractRepositoryPet.deleteAll(pets);
	}
}
