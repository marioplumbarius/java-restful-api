package com.mario.java.restful.api.hibernate.jpa.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.mapper.UserMapper;

public class UserMapperImpl implements UserMapper {

	@Override
	public List<UserDTO> mapFromEntitiesToDTOs(List<UserEntity> entities) {
		List<UserDTO> dtos = new ArrayList<UserDTO>();

		for(UserEntity entity : entities){
			UserDTO dto = this.mapFromEntityToDTO(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<UserEntity> mapFromDTOsToEntities(List<UserDTO> dtos) {
		List<UserEntity> entities = new ArrayList<UserEntity>();

		for(UserDTO dto : dtos){
			UserEntity entity = this.mapFromDTOToEntity(dto);
			entities.add(entity);
		}

		return entities;
	}

	@Override
	public UserDTO mapFromEntityToDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPropertiesToBeDisplayed(entity.getPropertiesToBeDisplayed());

		return null;
	}

	@Override
	public UserEntity mapFromDTOToEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setPropertiesToBeDisplayed(dto.getPropertiesToBeDisplayed());

		return entity;
	}

}
