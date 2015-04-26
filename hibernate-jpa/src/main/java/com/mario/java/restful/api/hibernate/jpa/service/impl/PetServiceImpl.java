package com.mario.java.restful.api.hibernate.jpa.service.impl;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.mapper.PetMapper;
import com.mario.java.restful.api.hibernate.jpa.repository.PetRepository;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;
import com.mario.java.restful.api.hibernate.jpa.service.Service;

@Model
@PetService
public class PetServiceImpl implements Service<PetDTO, Long> {

	private PetRepository petRepository;
	private PetMapper petMapper;

	public PetServiceImpl() {
	}

	@Inject
	public PetServiceImpl(PetRepository petRepository, PetMapper petMapper){
		this.petRepository = petRepository;
		this.petMapper = petMapper;
	}

	@Override
	public void persist(PetDTO petDTO) throws Exception {
		PetEntity petEntity = this.petMapper.mapFromDTOToEntity(petDTO);
		
		this.petRepository.persist(petEntity);
		
		petDTO.setId(petEntity.getId());
	}

	@Override
	public void update(Long id, PetDTO petDTO) throws Exception, ObjectNotFoundException {
		PetEntity petEntity = this.petMapper.mapFromDTOToEntity(petDTO);

		if(this.find(id) != null){
			petEntity.setId(id);
			this.petRepository.update(petEntity);
			this.petRepository.refresh(id);
		} else {
			throw new ObjectNotFoundException(id, PetDTO.class.getSimpleName());
		}
	}

	@Override
	public void delete(Long id) throws Exception, ObjectNotFoundException {
		PetEntity petEntity = this.petRepository.find(id);

		if(petEntity != null){
			this.petRepository.delete(petEntity);
		} else {
			throw new ObjectNotFoundException(id, PetDTO.class.getSimpleName());
		}
	}

	@Override
	public void deleteAll() throws Exception, ObjectNotFoundException {
		this.petRepository.deleteAll();
	}

	@Override
	public PetDTO find(Long id) {
		PetDTO petDTO = null;
		PetEntity petEntity = this.petRepository.find(id);
		
		if(petEntity != null) {
			petDTO = this.petMapper.mapFromEntityToDTO(petEntity);
		}
		

		return petDTO;
	}

	@Override
	public List<PetDTO> findAll() {
		List<PetEntity> listPetEntity = this.petRepository.findAll();
		List<PetDTO> listPetDTO = this.petMapper.mapFromEntitiesToDTOs(listPetEntity);
		
		return listPetDTO;
	}


	@SuppressWarnings("hiding")
	@Override
	public <SingularAttribute, Object> List<PetDTO> findAll(Map<SingularAttribute, Object> restrictions) {
		List<PetEntity> listPetEntity = this.petRepository.findAll(restrictions);
		List<PetDTO> listPetDTO = this.petMapper.mapFromEntitiesToDTOs(listPetEntity);
		
		return listPetDTO;
	}
}
