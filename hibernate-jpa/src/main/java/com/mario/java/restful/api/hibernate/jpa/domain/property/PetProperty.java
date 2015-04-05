package com.mario.java.restful.api.hibernate.jpa.domain.property;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;

/**
 * {@link PetDomain} properties.
 * @author marioluan
 *
 */
public enum PetProperty {

	ID("id"),
	NAME("name"),
	AGE("age"),
	USER_ID("userId"),
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");

	private final String propertyName;

	private PetProperty(String name){
		this.propertyName = name;
	}

	public String getName(){
		return this.propertyName;
	}
}