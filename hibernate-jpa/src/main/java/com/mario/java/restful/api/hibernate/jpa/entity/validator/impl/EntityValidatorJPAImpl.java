package com.mario.java.restful.api.hibernate.jpa.entity.validator.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.EntityValidator;

/**
 * JPA's implementation of {@link EntityValidator}.
 * @author marioluan
 */
public class EntityValidatorJPAImpl<T extends BaseEntity> implements EntityValidator<T> {

	private Set<ConstraintViolation<T>> constraintViolations;
	private Validator validator;

	/**
	 * Creates an instance with JPA's validator {@code Validation#buildDefaultValidatorFactory()#getValidator()}.
	 */
	public EntityValidatorJPAImpl() {
		this(Validation.buildDefaultValidatorFactory().getValidator());
	}
	
	/**
	 * Creates an instance with the provided {@link Validator validator}.
	 * @param validator the validator to set
	 */
	public EntityValidatorJPAImpl(Validator validator){
		this.validator = validator;
	}

	@Override
	public boolean isValid(T entity) {
		boolean valid;

		this.constraintViolations = this.validator.validate(entity);

		if (constraintViolations.isEmpty()) {
			valid = true;
		} else {
			valid = false;
		}

		return valid;
	}

	@Override
	public Set<ConstraintViolation<T>> getConstraintViolations() {
		return this.constraintViolations;
	}
}
