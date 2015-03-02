package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

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
		this.petCrud.update(id, pet);
	}

	public PetDomain find(Long id) {
		PetDomain pet = this.petCrud.find(id);

		return pet;
	}

	public List<PetDomain> findAll() {
		List<PetDomain> pets = this.petCrud.findAll();
		return pets;
	}

	public List<PetDomain> findAll(Map<String, String> criterias){
		List<PetDomain> pets = this.petCrud.findAll(criterias);
		return pets;
	}

	public void delete(Long id) {
		PetDomain pet = this.petCrud.find(id);
		this.petCrud.delete(id, pet);
	}

	public void deleteAll() {
		this.petCrud.deleteAll();
	}
}
