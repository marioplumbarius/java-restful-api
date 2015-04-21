package com.mario.java.restful.api.hibernate.jpa.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mario.java.restful.api.hibernate.jpa.dto.serializer.UserDTOSerializer;


/**
 * User DTO.
 * @author msouz23
 */
@JsonSerialize(using=UserDTOSerializer.class)
public class UserDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * Default constructor. Creates an empty instance.
	 */
	public UserDTO(){}

	/**
	 * Creates a instance with all attributes set.
	 * @param name the name to set
	 */
	public UserDTO(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Sets all empty/null attributes of the instance with values from the provided {@link UserDTO} userDto's attributes.
	 * @param userDTO the userDTO to get the attributes' values from
	 */
	public void patch(UserDTO userDTO){
		if(this.getName() == null){
			this.setName(userDTO.getName());
		}
	}
}