package com.mario.java.restful.api.hibernate.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity which contains dated attributes (createdAt, updatedAt).
 * @author marioluan
 *
 */
@MappedSuperclass
public abstract class DatedEntity {

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
	protected void prePersist(){
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void preUpdate(){
		this.updatedAt = new Date();
	}
}
