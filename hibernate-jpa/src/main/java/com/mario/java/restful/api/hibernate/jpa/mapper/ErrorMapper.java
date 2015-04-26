package com.mario.java.restful.api.hibernate.jpa.mapper;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.mario.java.restful.api.hibernate.jpa.dto.ErrorDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;

/**
 * Error mapper.
 * @author marioluan
 */
public interface ErrorMapper {	
	
	/**
	 * Maps a set of {@link ConstraintViolation} to a set of {@link ErrorDTO}.
	 * @param setConstraintViolation the setConstraintViolation to be mapped to 
	 * @return the mapped set
	 */
	public <T extends BaseEntity> Set<ErrorDTO> mapFromSetConstraintViolationToSetErrorDTO(Set<ConstraintViolation<T>> setConstraintViolation);
	
	/**
	 * Maps a {@link ConstraintViolation} to an {@link ErrorDTO}.
	 * @param constraintViolation the constraintViolation to be mapped to
	 * @return the mapped instance
	 */
	public <T extends BaseEntity> ErrorDTO mapFromConstraintViolationToErrorDTO(ConstraintViolation<T> constraintViolation);
}