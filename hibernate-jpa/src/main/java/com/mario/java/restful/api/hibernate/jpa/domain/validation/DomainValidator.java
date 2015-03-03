package com.mario.java.restful.api.hibernate.jpa.domain.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class DomainValidator {

	private final Validator validator;

	private Map<String, String> errors;

	public DomainValidator() {
		this(Validation.buildDefaultValidatorFactory().getValidator());
	}

	public DomainValidator(Validator validator){
		this.validator = validator;
		this.errors = new HashMap<String, String>();
	}

	public Validator getValidator() {
		return this.validator;
	}

	public Map<String, String> getErrors() {
		return this.errors;
	}

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

		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String key = constraintViolation.getPropertyPath().toString();
			String value = constraintViolation.getMessage();

			this.errors.put(key, value);
		}
	}

	public static Map<String, String> buildError(String key, String value){
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(key, value);

		return errors;
	}
}
