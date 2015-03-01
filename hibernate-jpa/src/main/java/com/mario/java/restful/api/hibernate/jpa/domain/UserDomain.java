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

	public UserDomain() {
	}

	public UserDomain(String name) {
		this.name = name;
	}

	public UserDomain(DomainValidator validator) {
		super(validator);
	}

	public UserDomain(String name, DomainValidator validator) {
		super(validator);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
