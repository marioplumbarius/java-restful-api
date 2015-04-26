package com.mario.java.restful.api.hibernate.jpa.dto.validator.impl;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.validator.UserDTOValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.EntityValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.UserEntityValidator;
import com.mario.java.restful.api.hibernate.jpa.mapper.ErrorMapper;
import com.mario.java.restful.api.hibernate.jpa.mapper.Mapper;
import com.mario.java.restful.api.hibernate.jpa.mapper.UserMapper;

/**
 * Default implementation of {@link UserDTOValidator}.
 * @author marioluan
 */
@Model
public class UserDTOValidatorImpl extends DTOValidatorImpl<UserDTO, UserEntity> implements UserDTOValidator {
	
	private UserEntityValidator userEntityValidator;
	private ErrorMapper errorMapper;
	private UserMapper userMapper;
	
	@Inject
	public UserDTOValidatorImpl(UserEntityValidator userEntityValidator, ErrorMapper errorMapper, UserMapper userMapper) {
		this.userEntityValidator = userEntityValidator;
		this.errorMapper = errorMapper;
		this.userMapper = userMapper;
	}

	@Override
	protected EntityValidator<UserEntity> getEntityValidator() {
		return this.userEntityValidator;
	}

	@Override
	protected ErrorMapper getErrorMapper() {
		return this.errorMapper;
	}

	@Override
	protected Mapper<UserEntity, UserDTO> getMapper() {
		return userMapper;
	}
	
}