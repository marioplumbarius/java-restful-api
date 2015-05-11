package com.mario.java.restful.api.hibernate.jpa.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Abstract class which represents the base domain for all domains used by the application.<br>
 * All domains must extend this class.
 *
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class BaseEntity extends DatedEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Transient
	private List<String> propertiesToBeDisplayed;

	/**
	 * @return the propertiesToBeDisplayed
	 */
	public List<String> getPropertiesToBeDisplayed() {
		return this.propertiesToBeDisplayed;
	}

	/**
	 * @param propertiesToBeDisplayed the propertiesToBeDisplayed to set
	 */
	public void setPropertiesToBeDisplayed(List<String> propertiesToBeDisplayed) {
		this.propertiesToBeDisplayed = propertiesToBeDisplayed;
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
