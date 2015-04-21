package com.mario.java.restful.api.hibernate.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mario.java.restful.api.hibernate.jpa.dto.serializer.PetDTOSerializer;

/**
 * Pet DTO.
 * @author msouz23
 */
@JsonSerialize(using=PetDTOSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Long userId;

	/**
	 * Default constructor. Creates an empty instance;
	 */
	public PetDTO(){}

	/**
	 * Creates a instance with all attributes set.
	 * @param name the name to set
	 * @param age the age to set
	 * @param userId the userId to set
	 */
	public PetDTO(String name, int age, Long userId) {
		this.name = name;
		this.age = age;
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Sets all empty/null attributes of the instance with values from the provided {@link PetDTO} petDTO attributes.
	 * @param petDTO the petDTO to get the attributes' values from
	 */
    public void patch(PetDTO petDTO){
    	if(this.getName() == null){
    		this.setName(petDTO.getName());
    	}

    	if(this.getAge() == 0){
    		this.setAge(petDTO.getAge());
    	}
    }
}