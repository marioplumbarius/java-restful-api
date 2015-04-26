package com.mario.java.restful.api.hibernate.jpa.resource.utils;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.mario.java.restful.api.hibernate.jpa.dto.ErrorDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.property.PetDTOProperty;
import com.mario.java.restful.api.hibernate.jpa.resource.http.status.HttpStatus;

/**
 * Utils with helper methods for generating pre-defined responses.
 * @author marioluan
 *
 */
public final class ResourceResponseUtils {
	
	public enum Error {
		NOT_FOUND("not found");
		
		private final String name;
		
		private Error(final String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	public static Response buildResponseWithUserIdNotFoundError(){
		String propertyName = PetDTOProperty.USER_ID.toString();
		String errorDescription = Error.NOT_FOUND.toString();
		Set<ErrorDTO> setErrorDTO = new HashSet<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO(propertyName, errorDescription);
		setErrorDTO.add(errorDTO);
		
		Response response = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(setErrorDTO).build(); 
		
		return response; 
    }
	
}
