package com.mario.java.restful.api.hibernate.jpa.dto;

import java.util.Date;

/**
 * DatedDTO, which contains createdAt and updatedAt attributes.
 * @author marioluan
 *
 */
public abstract class DatedDTO {

	private Date createdAt;
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
