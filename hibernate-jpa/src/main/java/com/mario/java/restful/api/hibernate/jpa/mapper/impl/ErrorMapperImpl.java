package com.mario.java.restful.api.hibernate.jpa.mapper.impl;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Model;
import javax.validation.ConstraintViolation;

import com.mario.java.restful.api.hibernate.jpa.dto.ErrorDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
import com.mario.java.restful.api.hibernate.jpa.mapper.ErrorMapper;

/**
 * The default implementation of {@link ErrorMapper}.
 * @author marioluan
 *
 */
@Model
public class ErrorMapperImpl implements ErrorMapper {

	@Override
	public <T extends BaseEntity> Set<ErrorDTO> mapFromSetConstraintViolationToSetErrorDTO(Set<ConstraintViolation<T>> setConstraintViolation) {
		Set<ErrorDTO> setErrors = new HashSet<ErrorDTO>();
		
		for(ConstraintViolation<T> constraintViolation : setConstraintViolation) {
			setErrors.add(this.mapFromConstraintViolationToErrorDTO(constraintViolation));
		}
		
		return setErrors;
	}

	@Override
	public <T extends BaseEntity> ErrorDTO mapFromConstraintViolationToErrorDTO(ConstraintViolation<T> constraintViolation) {
		String propertyName = constraintViolation.getPropertyPath().toString();
		String errorDescription = constraintViolation.getMessage();
		ErrorDTO errorDTO = new ErrorDTO(propertyName, errorDescription);
		
		return errorDTO;
	}

}
