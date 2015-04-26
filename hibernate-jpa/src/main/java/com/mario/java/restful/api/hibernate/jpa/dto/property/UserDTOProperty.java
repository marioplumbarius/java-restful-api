package com.mario.java.restful.api.hibernate.jpa.dto.property;

import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;

/**
 * {@link UserEntity} properties.
 * @author marioluan
 *
 */
public enum UserDTOProperty {

	ID("id"),
	NAME("name"),
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");

	private final String propertyName;

	private UserDTOProperty(final String name){
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