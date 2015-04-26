package com.mario.java.restful.api.hibernate.jpa.dto;

/**
 * Error dto.
 * @author marioluan
 *
 */
public class ErrorDTO {
	
	private final String propertyName;
	private final String description;
	
	public ErrorDTO(final String propertyName, final String description) {
		this.propertyName = propertyName;
		this.description = description;
	}

	/**
	 * Returns the {@link String} name of the property of the dto which the error relates to.
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * Returns the {@link String} description of the error.
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}
}
