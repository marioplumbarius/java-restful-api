package com.mario.java.restful.api.hibernate.jpa.dto.validator.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.mario.java.restful.api.hibernate.jpa.dto.BaseDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.ErrorDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.validator.DTOValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.EntityValidator;
import com.mario.java.restful.api.hibernate.jpa.mapper.ErrorMapper;
import com.mario.java.restful.api.hibernate.jpa.mapper.Mapper;

/**
 * Default implementation for {@link DTOValidator}.
 * @author marioluan
 *
 * @param <T1> the class type of the dto
 * @param <T2> the class type of the entity
 */
public abstract class DTOValidatorImpl<T1 extends BaseDTO, T2 extends BaseEntity> implements DTOValidator<T1, T2> {
	
	private Set<ErrorDTO> errors;
	
	/**
	 * @return the entityValidator
	 */
	protected abstract EntityValidator<T2> getEntityValidator();
	
	/**
	 * @return the errorMapper
	 */
	protected abstract ErrorMapper getErrorMapper();
	
	/**
	 * @return the mapper
	 */
	protected abstract Mapper<T2, T1> getMapper();
	
	@Override
	public boolean isValid(T1 dto) {
		boolean valid;
		
		T2 entity = this.getMapper().mapFromDTOToEntity(dto);
		
		if(this.getEntityValidator().isValid(entity)) {
			valid = true;
		} else {
			valid = false;
			Set<ConstraintViolation<T2>> entityErrors = this.getEntityValidator().getConstraintViolations();
			this.errors = this.getErrorMapper().mapFromSetConstraintViolationToSetErrorDTO(entityErrors);
		}

		return valid;
	}

	@Override
	public Set<ErrorDTO> getErrors() {
		return this.errors;
	}
}