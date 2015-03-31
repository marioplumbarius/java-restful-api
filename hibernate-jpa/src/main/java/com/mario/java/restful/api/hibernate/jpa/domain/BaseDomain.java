package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.impl.DomainValidatorJPAImpl;

/**
 * Abstract class which represents the base domain for all domains used by the application.<br>
 * All domains must extend this class.
 *
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class BaseDomain extends DatedDomain {

	@Transient
	private DomainValidator domainValidator;

	// TODO - move this instantiation to a injection
	public BaseDomain(){
		this(new DomainValidatorJPAImpl());
	}

	/**
	 * Constructs a new instance of {@link BaseDomain} assigning the provided {@link DomainValidator} to it.
	 *
	 * @param domainValidator the domainValidator to be used by this instance
	 */
	public BaseDomain(DomainValidator domainValidator){
		this.domainValidator = domainValidator;
	}

	/**
	 * Validates the domain and returns the validation result.
	 *
	 * @return true or false
	 */
	@JsonIgnore
	public boolean isValid(){
		return this.domainValidator.isValid(this);
	}

	/**
	 * Returns all constraint errors from this instance, found by the {@link DomainValidator} domainValidator.
	 * @return the list of validation errors or null
	 */
	@JsonIgnore
	public Map<String, Object> getErrors(){
		return this.domainValidator.getErrors();
	}
}
