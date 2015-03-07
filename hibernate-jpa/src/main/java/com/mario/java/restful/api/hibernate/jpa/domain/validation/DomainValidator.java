package com.mario.java.restful.api.hibernate.jpa.domain.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class DomainValidator {

	private final Validator validator;

	private Map<String, Object> errors;

	public DomainValidator() {
		this(Validation.buildDefaultValidatorFactory().getValidator());
	}

	public DomainValidator(Validator validator){
		this.validator = validator;
		this.errors = new HashMap<String, Object>();
	}

	public Validator getValidator() {
		return this.validator;
	}

	public Map<String, Object> getErrors() {
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

		Map<String, String> errorList = new HashMap<String, String>();

		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String key = constraintViolation.getPropertyPath().toString();
			String value = constraintViolation.getMessage();

			errorList.put(key, value);
		}

		this.errors.put("errors", errorList);
	}

	public static Map<String, Object> buildError(String key, String value){
		Map<String, Object> errors = new HashMap<String, Object>();
		Map<String, String> error = new HashMap<String, String>();
		error.put(key, value);
		errors.put("errors", error);

		return errors;
	}
}
