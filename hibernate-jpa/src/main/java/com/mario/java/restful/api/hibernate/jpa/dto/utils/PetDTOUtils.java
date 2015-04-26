package com.mario.java.restful.api.hibernate.jpa.dto.utils;

import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;

/**
 * Utils with helper methods to manipulate {@link PetDTO} instances.
 * @author marioluan
 *
 */
public final class PetDTOUtils {
	
    /**
     * Sets the propertiesToBeDisplayed to a list of {@link PetDTO}.
     * @param listPetDTO
     * @param propertiesToBeDisplayed
     */
	public static void setPetDomainPropertiesToBeDisplayed(List<PetDTO> listPetDTO, List<String> propertiesToBeDisplayed){
    	for(PetDTO petDTO : listPetDTO){
    		petDTO.setPropertiesToBeDisplayed(propertiesToBeDisplayed);
    	}
    }
	
}
