package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dao.implementation.UserDaoImplementation;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;

public class UserService {
    private UserDaoImplementation userDaoImplementation;

    public UserService() {
        this(new UserDaoImplementation());
    }
    
    public UserService(UserDaoImplementation userDaoImplementation){
    	this.userDaoImplementation = userDaoImplementation;
    }

    public void persist(UserDomain user) {
        this.userDaoImplementation.persist(user);
    }

    public void update(Long id, UserDomain user) {
        this.userDaoImplementation.update(id, user);
    }

    public UserDomain find(Long id) {
        UserDomain user = this.userDaoImplementation.find(id);

        return user;
    }

    public void delete(Long id) {
        UserDomain user = this.userDaoImplementation.find(id);
        this.userDaoImplementation.delete(user);
    }

    public List<UserDomain> findAll() {
        List<UserDomain> users = this.userDaoImplementation.findAll();

        return users;
    }

    public void deleteAll() {
        this.userDaoImplementation.deleteAll();
    }
}
