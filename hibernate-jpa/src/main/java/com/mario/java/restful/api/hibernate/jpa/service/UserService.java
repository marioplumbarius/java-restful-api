package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;

public class UserService {
	private CrudRepository<UserDomain, Long> userCrud;
	private CrudRepository<PetDomain, Long> petCrud;

	public UserService() {
		this(new CrudRepository<UserDomain, Long>("UserDomain", UserDomain.class),new CrudRepository<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public UserService(CrudRepository<UserDomain, Long> userCrud, CrudRepository<PetDomain, Long> petCrud){
		this.userCrud = userCrud;
		this.petCrud = petCrud;
	}

	public void persist(UserDomain user) {
		this.userCrud.persist(user);
	}

	public void update(Long id, UserDomain user) {
		user.setId(id);

		try {
			this.userCrud.update(id, user);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, user.getClass().getName());
		}

	}

	public UserDomain find(Long id) {
		UserDomain user = this.userCrud.find(id);

		return user;
	}

	public List<UserDomain> findAll() {
		List<UserDomain> users = this.userCrud.findAll();
		return users;
	}

	public List<UserDomain> findAll(Map<String, Object> criterias){
		List<UserDomain> users = this.userCrud.findAll(criterias);
		return users;
	}

	public void delete(Long id) {
		UserDomain user = this.userCrud.find(id);

		if(user != null){
			this.deletePets(id);
			this.userCrud.delete(id, user);
		} else {
			throw new ObjectNotFoundException(id, UserDomain.class.getName());
		}
	}

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
