package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.ObjectNotFoundException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.PetService;

@Model
@PetService
public class PetServiceImpl implements Service<PetDomain, Long> {

	private PetRepository petRepository;

	public PetServiceImpl() {
	}

	@Inject
	public PetServiceImpl(PetRepository petRepository){
		this.petRepository = petRepository;
	}

	@Override
	public void persist(PetDomain pet) throws Exception {
		this.petRepository.persist(pet);
	}

	@Override
	public void update(Long id, PetDomain pet) throws Exception, ObjectNotFoundException {

		if(this.find(id) != null){
			pet.setId(id);
			this.petRepository.update(pet);
		} else {
			throw new ObjectNofFoundException(id, PetDomain.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNofFoundException {
		PetDomain pet = this.find(id);

		if(pet != null){
			this.petRepository.delete(pet);
		} else {
			throw new ObjectNofFoundException(id, PetDomain.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNofFoundException {
		this.petRepository.deleteAll();
	}

	@Override
	public PetDomain find(Long id) {
		PetDomain pet = this.petRepository.find(id);

		return pet;
	}

	@Override
	public List<PetDomain> findAll() {
		List<PetDomain> pets = this.petRepository.findAll();
		return pets;
	}

	@Override
	public <K, V> List<PetDomain> findAll(Map<K, V> restrictions) {

		// TODO Auto-generated method stub
		return null;
	}
}
