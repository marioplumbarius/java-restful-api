package com.mario.java.restful.api.hibernate.jpa.repository;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;

/**
 * Interface for generic repository operations regarding {@link PetEntity pet entities}.
 * @author marioluan
 */
public interface PetRepository extends Repository<PetEntity, Long> {

}
