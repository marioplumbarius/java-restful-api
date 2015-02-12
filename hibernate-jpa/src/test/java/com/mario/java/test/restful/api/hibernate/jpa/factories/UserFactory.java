package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;


public final class UserFactory {

    public static String URI = "/users/null";

    public static UserDomain createInvalidUser() {
        return new UserDomain("invalid name, so many characters, wowwwww");
    }

    public static UserDomain createValidUser() {
        return new UserDomain("valid name");
    }
}
