package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;

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
		this.userCrud.update(id, user);
	}

	public UserDomain find(Long id) {
		UserDomain user = this.userCrud.find(id);

		return user;
	}

	// TODO - refactor
	// rename this method from findBy -> findAll
	public List<UserDomain> findBy(String key, String value){
		List<UserDomain> users = this.userCrud.findAll(key, value);
		return users;
	}

	public List<UserDomain> findAll() {
		List<UserDomain> users = this.userCrud.findAll();
		return users;
	}

	public List<UserDomain> findAll(Map<String, String> criterias){
		List<UserDomain> users = this.userCrud.findAll(criterias);
		return users;
	}

	public void delete(Long id) {
		UserDomain user = this.userCrud.find(id);

		if(user != null){
			this.deletePets(user);
			this.userCrud.delete(id, user);
		} else {
			throw new ObjectNotFoundException(id, UserDomain.class.getName());
		}
	}

	public void deleteAll() {
		this.petCrud.deleteAll();
		this.userCrud.deleteAll();
	}

	private void deletePets(UserDomain user){
		List<PetDomain> pets = this.petCrud.findAll("user.id", user.getId());

		if(pets != null) this.petCrud.deleteAll(pets);
	}
}
