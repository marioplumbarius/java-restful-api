package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;


public final class UserFactory {

    public static String URI = "/users/{id}";

    public static UserEntity createInvalidUser() {
        return new UserEntity(NameFactory.createName("", 21));
    }

    public static UserEntity createValidUser() {
        UserEntity user = new UserEntity("valid name");
        user.setId(new Long(1));

        return user;
    }
}
