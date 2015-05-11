package com.mario.java.restful.api.hibernate.jpa.repository;

import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;

/**
 * Interface for generic repository operations regarding {@link UserEntity user entities}.
 * @author marioluan
 */
public interface UserRepository extends Repository<UserEntity, Long> {

}