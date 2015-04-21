package com.mario.java.restful.api.hibernate.jpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Domain class which represents an user on database.
 *
 * @author marioluan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
@ApiModel(description = "user dto", parent = BaseEntity.class)
public class UserEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(max = 20)
	@ApiModelProperty(required = true, value = "the name of the user")
	private String name;

	@OneToMany(mappedBy="user")
	private List<PetEntity> pets;

	/**
	 * Default constructor, creates an empty instance.
	 */
	public UserEntity() {
	}

	/**
	 * @param name the name to set
	 */
	public UserEntity(String name) {
		this.name = name;
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
     * Fill all null/missing attributes from the current {@link UserEntity} userDomain instance with attributes from another {@link UserEntity} userDomain instance.
     *
     * @param petDomain the petDomain to take the attributes from
     */
	public void patch(UserEntity userEntity) {

		if(this.getName() == null){
			this.setName(userEntity.getName());
		}

		this.setCreatedAt(userEntity.getCreatedAt());
	}
}
