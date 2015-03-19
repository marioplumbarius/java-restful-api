/**
 *
 */
package com.mario.java.restful.api.hibernate.jpa.repository.exception;

import java.io.Serializable;

/**
 * Thrown when an object could not be found on the persistence layer.
 * @author marioluan
 */
public class ObjectNofFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an ObjectNotFoundException using the given information.
	 * @param identifier the identifier of the entity
	 * @param entityName the name of the entity
	 */
	public ObjectNofFoundException(Serializable identifier, String entityName){
		super("No " + entityName + " with the given identifier " + identifier + " was found on the persistence layer.");
	}

	/**
	 * Constructs an ObjectNotFoundException using the given information.
	 * @param entityName the name of the entity
	 */
	public ObjectNofFoundException(String entityName){
		super("No " + entityName + " was found on the persistence layer.");
	}
}