package com.mario.java.restful.api.hibernate.jpa.domain.validation;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Validator;

/**
 * Domain validator.
 *
 * @author marioluan
 *
 */
public interface DomainValidator {

	/**
	 * The {@link Validator} validator of the entity.
	 * @return the validator
	 */
	public Validator getValidator();

	/**
	 * Returns the current list of errors of the entity, regarding its validation status.
	 * @return the errors from the domain
	 */
	public Map<String, Object> getErrors();

	/**
	 * Validates the entity.
	 * @param entity the entity to be validated
	 * @return whether or not the entity is valid
	 */
	public <T> boolean isValid(T entity);

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
