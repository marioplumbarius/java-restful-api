package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Abstract class which exposes a domain with dated attributes (createdAt, updatedAt).<br>
 *
 * All {@link BaseDomain} implementations should extend this class.
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class DatedDomain {

	@CreationTimestamp
	@Column(insertable=true, updatable=false)
	private Date createdAt;

	@UpdateTimestamp
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
}
