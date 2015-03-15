package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;

public class PetService {
	private AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud;

	public PetService() {
		this(new AbstractRepositoryHibernateImpl<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public PetService(AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud){
		this.petCrud = petCrud;
	}

	public void persist(PetDomain pet) {
		this.petCrud.persist(pet);
	}

	public void update(Long id, PetDomain pet) {
		pet.setId(id);

		try {
			this.petCrud.update(pet);
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
		PetDomain pet = new PetDomain();
		pet.setId(id);

		try {
			this.petCrud.delete(pet);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, PetDomain.class.getName());
		}
	}

	public void deleteAll() {
		this.petCrud.deleteAll();
	}
}
