package com.mario.java.restful.api.hibernate.jpa.domain.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;

/**
 * JPA's implementation of a {@link DomainValidator}.
 * @author marioluan
 *
 */
public class DomainValidatorJPAImpl implements DomainValidator {

	private Validator validator;
	private Map<String, Object> errors;

	public DomainValidatorJPAImpl() {
		this(Validation.buildDefaultValidatorFactory().getValidator());
	}

	@Inject
	public DomainValidatorJPAImpl(Validator validator){
		this.validator = validator;
		this.errors = new HashMap<String, Object>();
	}

	@Override
	public Validator getValidator() {
		return this.validator;
	}

	@Override
	public Map<String, Object> getErrors() {
		return this.errors;
	}

	@Override
	public <T> boolean isValid(T entity) {
		boolean validate;

		Set<ConstraintViolation<T>> constraintViolations = this.validator
				.validate(entity);

		if (constraintViolations.size() == 0) {
			validate = true;
		} else {
			validate = false;
			this.buildErrors(constraintViolations);
		}

		return validate;
	}

	private <T> void buildErrors(Set<ConstraintViolation<T>> constraintViolations) {
		this.errors.clear();

		Map<String, String> errorList = new HashMap<String, String>();

		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String key = constraintViolation.getPropertyPath().toString();
			String value = constraintViolation.getMessage();

			errorList.put(key, value);
		}

		this.errors.put("errors", errorList);
	}
}
