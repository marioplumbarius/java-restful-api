package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;

public class PetService {
	private CrudRepository<PetDomain, Long> petCrud;

	public PetService() {
		this(new CrudRepository<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public PetService(CrudRepository<PetDomain, Long> petCrud){
		this.petCrud = petCrud;
	}

	public void persist(PetDomain pet) {
		this.petCrud.persist(pet);
	}

	public void update(Long id, PetDomain pet) {
		pet.setId(id);

		try {
			this.petCrud.update(id, pet);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, pet.getClass().getName());
		}
	}

	public PetDomain find(Long id) {
		PetDomain pet = this.petCrud.find(id);

		return pet;
	}

	public List<PetDomain> findAll() {
		List<PetDomain> pets = this.petCrud.findAll();
		return pets;
	}

	public List<PetDomain> findAll(Map<String, Object> criterias){
		List<PetDomain> pets = this.petCrud.findAll(criterias);
		return pets;
	}

	public void delete(Long id) {
		PetDomain pet = this.petCrud.find(id);

		if(pet != null){
			this.petCrud.delete(id, pet);
		} else {
			throw new ObjectNotFoundException(id, PetDomain.class.getName());
		}
	}

	public void deleteAll() {
		this.petCrud.deleteAll();
	}
}
