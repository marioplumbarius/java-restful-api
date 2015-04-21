package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;

public final class PetFactory {

    public static String URI = "/pets/{id}";

    public static PetEntity createInvalidPet() {
        return new PetEntity();
    }

    public static PetEntity createValidPet() {
        PetEntity pet = new PetEntity();
        pet.setName(NameFactory.createValidName());
        pet.setAge(10);
        pet.setId(new Long(1));
        pet.setUserId(new Long(11));

        return pet;
    }
}
