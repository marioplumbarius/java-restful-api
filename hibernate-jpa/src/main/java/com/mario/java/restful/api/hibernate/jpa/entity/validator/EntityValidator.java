package com.mario.java.restful.api.hibernate.jpa.entity.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;

/**
 * Entity validator.
 *
 * @author marioluan
 *
 */
public interface EntityValidator<T extends BaseEntity> {

	/**
	 * @return the constraintViolations
	 */
	public Set<ConstraintViolation<T>> getConstraintViolations();

	/**
	 * Verifies is the entity is valid.
	 * @param entity the entity to be verified
	 * @return whether or not the entity is valid
	 */
	public boolean isValid(T entity);
}
