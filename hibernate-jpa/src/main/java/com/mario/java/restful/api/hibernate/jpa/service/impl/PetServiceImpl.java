package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;

public class PetServiceImpl implements PetService {
	private AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud;

	public PetServiceImpl() {
		this(new AbstractRepositoryHibernateImpl<PetDomain, Long>("PetDomain", PetDomain.class));
	}

	public PetServiceImpl(AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud){
		this.petCrud = petCrud;
	}

	@Override
	public void persist(PetDomain pet) {
		this.petCrud.persist(pet);
	}

	@Override
	public void update(Long id, PetDomain pet) {
		pet.setId(id);

		try {
			this.petCrud.update(pet);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, pet.getClass().getName());
		}
	}

	@Override
	public PetDomain find(Long id) {
		PetDomain pet = this.petCrud.find(id);

		return pet;
	}

	@Override
	public List<PetDomain> findAll() {
		List<PetDomain> pets = this.petCrud.findAll();
		return pets;
	}

	@Override
	public List<PetDomain> findAll(Map<String, Object> criterias){
		List<PetDomain> pets = this.petCrud.findAll(criterias);
		return pets;
	}

	@Override
	public void delete(Long id) {
		PetDomain pet = new PetDomain();
		pet.setId(id);

		try {
			this.petCrud.delete(pet);
		} catch (StaleStateException e) {
			throw new ObjectNotFoundException(id, PetDomain.class.getName());
		}
	}

	@Override
	public void deleteAll() {
		this.petCrud.deleteAll();
	}
}
