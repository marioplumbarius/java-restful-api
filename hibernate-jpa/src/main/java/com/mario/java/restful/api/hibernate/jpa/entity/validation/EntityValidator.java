package com.mario.java.restful.api.hibernate.jpa.entity.validation;

import java.util.Map;

import javax.validation.Validator;

/**
 * Domain validator.
 *
 * @author marioluan
 *
 */
public interface EntityValidator {

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
}
