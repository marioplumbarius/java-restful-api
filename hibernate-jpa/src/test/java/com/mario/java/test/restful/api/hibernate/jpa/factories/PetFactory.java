package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;

public final class PetFactory {

    public static String URI = "/pets/{id}";

    public static PetDomain createInvalidPet() {
        return new PetDomain(NameFactory.createName("", 21));
    }

    public static PetDomain createValidPet() {
        PetDomain pet = new PetDomain();
        pet.setName(NameFactory.createValidName());
        pet.setAge(10);
        pet.setId(new Long(1));
        pet.setUserId(new Long(11));

        return pet;
    }
}
