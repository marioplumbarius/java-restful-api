package com.mario.java.restful.api.hibernate.jpa.dto.property;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;

/**
 * {@link PetEntity} properties.
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

	private PetDTOProperty(String name){
		this.propertyName = name;
	}

	public String getName(){
		return this.propertyName;
	}
}