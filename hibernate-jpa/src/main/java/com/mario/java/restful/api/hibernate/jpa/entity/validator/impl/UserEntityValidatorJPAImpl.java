package com.mario.java.restful.api.hibernate.jpa.entity.validator.impl;

import javax.enterprise.inject.Model;

import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.UserEntityValidator;

/**
 * JPA implementation for {@link UserEntityValidator}.
 * @author marioluan
 *
 */
@Model
public class UserEntityValidatorJPAImpl extends EntityValidatorJPAImpl<UserEntity> implements UserEntityValidator {

}
