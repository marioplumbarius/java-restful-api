package com.mario.java.restful.api.hibernate.jpa.mapper;

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;

/**
 * Mapper for {@link PetDTO DTOs} and {@link PetEntity Entities}.
 * @author msouz23
 */
public interface PetMapper extends Mapper<PetEntity, PetDTO> {

}
