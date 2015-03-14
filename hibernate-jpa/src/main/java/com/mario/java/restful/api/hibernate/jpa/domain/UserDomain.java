package com.mario.java.restful.api.hibernate.jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;

/**
 * Domain class which represents an user on database.
 *
 * @author marioluan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class UserDomain extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	@Size(min = 1, max = 20)
	private String name;

	@OneToMany(mappedBy="user")
	private List<PetDomain> pets;

	/**
	 * Default constructor, creates an empty instance.
	 */
	public UserDomain() {
	}

	/**
	 * @param name the name to set
	 */
	public UserDomain(String name) {
		this.name = name;
	}

	/**
	 * @param domainValidator the domainValidator which will be used to validate the instance
	 */
	public UserDomain(DomainValidator domainValidator) {
		super(domainValidator);
	}

	/**
	 * @param name the name of the user
	 * @param domainValidator the domainValidator which will be used to validate the instance
	 */
	public UserDomain(String name, DomainValidator domainValidator) {
		super(domainValidator);
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
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
