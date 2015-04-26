package com.mario.java.restful.api.hibernate.jpa.dto.validator;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;

/**
 * DTO validator for {@link PetDTO} instances.
 * @author marioluan
 *
 * @param <T1> the class type of the user dto
 * @param <T2> the class type of the user entity
 */
public interface PetDTOValidator extends DTOValidator<PetDTO, PetEntity> {
}