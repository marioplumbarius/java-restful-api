package com.mario.java.restful.api.hibernate.jpa.dto.exception;

/**
 * Exception for dto.
 * @author marioluan
 */
public class DTOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Errors messages which the underlying exception may throw.
	 * @author marioluan
	 */
	public enum Error {
		NOT_FOUND("Could not find dto.");
		
		private final String name;
		
		private Error(final String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public String getName() {
			return this.toString();
		}
	}
	
	/**
	 * Default Constructor.</br>
	 * Creates a instance assigning its {@link #error}.
	 * @param error the error to set
	 */
	public DTOException(Error error) {
		super(error.toString());
	}
}