package com.mario.java.test.restful.api.hibernate.jpa.factories;

import com.mario.java.restful.api.hibernate.jpa.domain.User;

public final class UserFactory {
    public static User createInvalidUser() {
        return new User("invalid name, so many characters, wowwwww");
    }

    public static User createValidUser() {
        return new User("valid name");
    }
}
