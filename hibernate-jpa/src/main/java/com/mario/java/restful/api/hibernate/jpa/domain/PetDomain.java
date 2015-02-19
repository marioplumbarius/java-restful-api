package com.mario.java.restful.api.hibernate.jpa.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "pets")
public class PetDomain extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	@Size(min = 1, max = 20)
	private String name;

	@NotNull
	@Range(min = 1, max = 100)
	private int age;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@PrimaryKeyJoinColumn
	private UserDomain user;

	public PetDomain(){}

	public PetDomain(String name){
		this.name = name;
	}

	public PetDomain(int age){
		this.age = age;
	}

	public PetDomain(DomainValidator validator){
		super(validator);
	}

	public PetDomain(String name, int age){
		this.name = name;
		this.age = age;
	}

	public PetDomain(String name, int age, DomainValidator validator){
		super(validator);
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setUser(UserDomain user){
		this.user = user;
	}

	public UserDomain getUser(){
		return this.user;
	}
}