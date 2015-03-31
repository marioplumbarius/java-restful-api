package com.mario.java.restful.api.hibernate.jpa.domain;

import javax.persistence.MappedSuperclass;

/**
 * Abstract class which represents the base domain for all domains used by the application.<br>
 * All domains must extend this class.
 *
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class BaseDomain extends DatedDomain {

	/**
	 * Default constructor. Creates an empty instance.
	 */
	public BaseDomain(){}
}
