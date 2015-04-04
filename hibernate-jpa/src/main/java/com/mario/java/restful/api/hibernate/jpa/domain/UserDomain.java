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
	@Size(max = 20)
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

	/**
     * Fill all null/missing attributes from the current {@link UserDomain} userDomain instance with attributes from another {@link UserDomain} userDomain instance.
     *
     * @param petDomain the petDomain to take the attributes from
     */
	public void patch(UserDomain userDomain) {

		if(this.getName() == null){
			this.setName(userDomain.getName());
		}

		this.setCreatedAt(userDomain.getCreatedAt());
	}
}
