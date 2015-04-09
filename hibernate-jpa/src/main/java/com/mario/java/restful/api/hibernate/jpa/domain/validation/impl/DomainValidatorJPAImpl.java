package com.mario.java.restful.api.hibernate.jpa.domain.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;

/**
 * JPA's implementation of a {@link DomainValidator}.
 * @author marioluan
 *
 */
@RequestScoped
public class DomainValidatorJPAImpl implements DomainValidator {

	private Validator validator;
	private Map<String, Object> errors;

	public DomainValidatorJPAImpl() {
		this(Validation.buildDefaultValidatorFactory().getValidator());
	}

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

	/**
	 * Builds an {@link Map<String, Object>} error, regarding the entity.
	 * @param key the key of the error to be built
	 * @param value the valud of the error to be built
	 * @return an custom error object
	 */
	public static Map<String, Object> buildError(String key, String value){
		Map<String, Object> errors = new HashMap<String, Object>();
		Map<String, String> error = new HashMap<String, String>();
		error.put(key, value);
		errors.put("errors", error);

		return errors;
	}
}
