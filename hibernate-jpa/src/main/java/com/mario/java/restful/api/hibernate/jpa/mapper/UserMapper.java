package com.mario.java.restful.api.hibernate.jpa.mapper;

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;

/**
 * Mapper for {@link UserDTO DTOs} and {@link UserEntity Entities}.
 * @author msouz23
 */
public interface UserMapper extends Mapper<UserEntity, UserDTO> {

}
