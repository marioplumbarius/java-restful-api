package com.mario.java.restful.api.hibernate.jpa.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.mapper.Mapper;

public class PetMapperImpl implements Mapper<PetEntity, PetDTO> {

	@Override
	public List<PetDTO> mapFromEntitiesToDTOs(List<PetEntity> entities) {
		List<PetDTO> dtos = new ArrayList<PetDTO>();

		for(PetEntity entity : entities){
			PetDTO dto = this.mapFromEntityToDTO(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<PetEntity> mapFromDTOsToEntities(List<PetDTO> dtos) {
		List<PetEntity> entities = new ArrayList<PetEntity>();

		for(PetDTO dto : dtos){
			PetEntity entity = this.mapFromDTOToEntity(dto);
			entities.add(entity);
		}

		return entities;
	}

	@Override
	public PetDTO mapFromEntityToDTO(PetEntity entity) {
		PetDTO dto = new PetDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAge(entity.getAge());
		dto.setUserId(entity.getUserId());
		dto.setPropertiesToBeDisplayed(entity.getPropertiesToBeDisplayed());

		return null;
	}

	@Override
	public PetEntity mapFromDTOToEntity(PetDTO dto) {
		PetEntity entity = new PetEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setAge(dto.getAge());
		entity.setUserId(dto.getUserId());
		entity.setPropertiesToBeDisplayed(dto.getPropertiesToBeDisplayed());

		return entity;
	}

}
