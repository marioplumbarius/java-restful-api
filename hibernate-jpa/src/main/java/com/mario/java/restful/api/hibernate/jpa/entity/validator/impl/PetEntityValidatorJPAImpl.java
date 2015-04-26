package com.mario.java.restful.api.hibernate.jpa.entity.validator.impl;

import javax.enterprise.inject.Model;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.validator.PetEntityValidator;

/**
 * JPA implementation for {@link PetEntityValidator}.
 * @author marioluan
 *
 */
@Model
public class PetEntityValidatorJPAImpl extends EntityValidatorJPAImpl<PetEntity> implements PetEntityValidator {

}
