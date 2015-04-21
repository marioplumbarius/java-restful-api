package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.PetService;

@Model
@PetService
public class PetServiceImpl implements Service<PetEntity, Long> {

	private PetRepository petRepository;

	public PetServiceImpl() {
	}

	@Inject
	public PetServiceImpl(PetRepository petRepository){
		this.petRepository = petRepository;
	}

	@Override
	public void persist(PetEntity pet) throws Exception {
		this.petRepository.persist(pet);
	}

	@Override
	public void update(Long id, PetEntity pet) throws Exception, ObjectNotFoundException {

		if(this.find(id) != null){
			pet.setId(id);
			this.petRepository.update(pet);
		} else {
			throw new ObjectNotFoundException(id, PetEntity.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNotFoundException {
		PetEntity pet = this.find(id);

		if(pet != null){
			this.petRepository.delete(pet);
		} else {
			throw new ObjectNotFoundException(id, PetEntity.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNotFoundException {
		this.petRepository.deleteAll();
	}

	@Override
	public PetEntity find(Long id) {
		PetEntity pet = this.petRepository.find(id);

		return pet;
	}

	@Override
	public List<PetEntity> findAll() {
		List<PetEntity> pets = this.petRepository.findAll();
		return pets;
	}


	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<PetEntity> findAll(Map<SingularAttribute, Object> restrictions) {
		return this.petRepository.findAll(restrictions);
	}
}
