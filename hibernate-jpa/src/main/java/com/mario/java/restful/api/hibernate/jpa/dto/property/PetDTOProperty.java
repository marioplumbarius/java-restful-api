package com.mario.java.restful.api.hibernate.jpa.dto.property;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;

/**
 * {@link PetDTO} properties.
 * @author marioluan
 *
 */
public enum PetDTOProperty {

	ID("id"),
	NAME("name"),
	AGE("age"),
	USER_ID("userId"),
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");

	private final String propertyName;

	private PetDTOProperty(final String name){
		this.propertyName = name;
	}

	public String getName(){
		return this.propertyName;
	}

	@Override
	public String toString(){
		return this.getName();
	}
}