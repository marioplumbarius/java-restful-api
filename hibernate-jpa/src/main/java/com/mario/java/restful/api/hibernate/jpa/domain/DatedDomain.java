package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Abstract class which exposes a domain with dated attributes (createdAt, updatedAt).<br>
 *
 * All {@link BaseDomain} implementations should extend this class.
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class DatedDomain {

	private static final Logger LOGGER = Logger.getLogger(DatedDomain.class.getSimpleName());

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable=true, updatable=false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable=false, updatable=true)
	private Date updatedAt;

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	private void prePersist(){
		LOGGER.info("prePersist()");

		this.createdAt = new Date();
	}

	@PreUpdate
	private void preUpdate(){
		LOGGER.info("preUpdate()");

		this.updatedAt = new Date();
	}
}
