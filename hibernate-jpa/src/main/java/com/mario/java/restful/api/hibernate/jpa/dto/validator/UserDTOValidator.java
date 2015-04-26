package com.mario.java.restful.api.hibernate.jpa.dto.validator;

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;

/**
 * DTO validator for {@link UserDTO} instances.
 * @author marioluan
 *
 * @param <T1> the class type of the user dto
 * @param <T2> the class type of the user entity
 */
public interface UserDTOValidator extends DTOValidator<UserDTO, UserEntity> {
}