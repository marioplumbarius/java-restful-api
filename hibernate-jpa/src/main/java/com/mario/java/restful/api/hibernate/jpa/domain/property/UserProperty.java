package com.mario.java.restful.api.hibernate.jpa.domain.property;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;

/**
 * {@link UserDomain} properties.
 * @author marioluan
 *
 */
public enum UserProperty {

	ID("id"),
	NAME("name"),
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");

	private final String propertyName;

	private UserProperty(String name){
		this.propertyName = name;
	}

	public String getName(){
		return this.propertyName;
	}
}