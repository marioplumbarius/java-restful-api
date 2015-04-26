package com.mario.java.restful.api.hibernate.jpa.dto.validator.impl;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.validator.PetDTOValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.EntityValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.PetEntityValidator;
import com.mario.java.restful.api.hibernate.jpa.mapper.ErrorMapper;
import com.mario.java.restful.api.hibernate.jpa.mapper.Mapper;
import com.mario.java.restful.api.hibernate.jpa.mapper.PetMapper;

/**
 * Default implementation of {@link PetDTOValidator}.
 * @author marioluan
 */
@Model
public class PetDTOValidatorImpl extends DTOValidatorImpl<PetDTO, PetEntity> implements PetDTOValidator {
	
	private PetEntityValidator petEntityValidator;
	private ErrorMapper errorMapper;
	private PetMapper petMapper;
	
	@Inject
	public PetDTOValidatorImpl(PetEntityValidator userEntityValidator, ErrorMapper errorMapper, PetMapper userMapper) {
		this.petEntityValidator = userEntityValidator;
		this.errorMapper = errorMapper;
		this.petMapper = userMapper;
	}

	@Override
	protected EntityValidator<PetEntity> getEntityValidator() {
		return this.petEntityValidator;
	}

	@Override
	protected ErrorMapper getErrorMapper() {
		return this.errorMapper;
	}

	@Override
	protected Mapper<PetEntity, PetDTO> getMapper() {
		return petMapper;
	}
	
}