package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;


public final class UserFactory {

    public static String URI = "/users/{id}";

    public static UserDomain createInvalidUser() {
        return new UserDomain("invalid name, so many characters, wowwwww");
    }

    public static UserDomain createValidUser() {
        UserDomain user = new UserDomain("valid name");
        user.setId(new Long(1));

        return user;
    }
}
