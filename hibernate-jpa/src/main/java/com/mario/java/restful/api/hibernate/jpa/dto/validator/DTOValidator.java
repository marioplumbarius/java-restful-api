package com.mario.java.restful.api.hibernate.jpa.dto.validator;

import java.util.Set;

import com.mario.java.restful.api.hibernate.jpa.dto.BaseDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.ErrorDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;

/**
 * DTO validator.
 * @author marioluan
 *
 */
public interface DTOValidator<T1 extends BaseDTO, T2 extends BaseEntity> {
	
	/**
	 * Verifies if the {@link T1 dto} is valid. 
	 * @param dto the dto to be validated
	 * @return whether or not the dto is valid
	 */
	public boolean isValid(T1 dto);
	
	/**
	 * Returns the set of {@link ErrorDTO errors} (if any) from the {@link T1 dto}.
	 * @param dto the dto which may have validation errors
	 * @return the list of errors or null
	 */
	public Set<ErrorDTO> getErrors();
}