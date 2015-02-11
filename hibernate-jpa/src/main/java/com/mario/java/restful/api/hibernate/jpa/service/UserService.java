package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dao.implementation.UserDaoImplementation;
import com.mario.java.restful.api.hibernate.jpa.domain.User;

public class UserService {
    private UserDaoImplementation userDao;

    public UserService() {
        this(new UserDaoImplementation());
    }
    
    public UserService(UserDaoImplementation userDaoImplementation){
    	this.userDao = userDaoImplementation;
    }

    public void persist(User user) {
        this.userDao.sessionManager.openSessionWithTransaction();
        this.userDao.persist(user);
        this.userDao.sessionManager.closeSessionWithTransaction();
    }

    public void update(Long id, User user) {
        this.userDao.sessionManager.openSessionWithTransaction();
        this.userDao.update(id, user);
        this.userDao.sessionManager.closeSessionWithTransaction();
    }

    public User find(Long id) {
        this.userDao.sessionManager.openSession();
        User user = this.userDao.find(id);
        this.userDao.sessionManager.closeSession();

        return user;
    }

    public void delete(Long id) {
        this.userDao.sessionManager.openSessionWithTransaction();
        User user = this.userDao.find(id);
        this.userDao.delete(user);
        this.userDao.sessionManager.closeSessionWithTransaction();
    }

    public List<User> findAll() {
        this.userDao.sessionManager.openSession();
        List<User> users = this.userDao.findAll();
        this.userDao.sessionManager.closeSession();

        return users;
    }

    public void deleteAll() {
        this.userDao.sessionManager.openSessionWithTransaction();
        this.userDao.deleteAll();
        this.userDao.sessionManager.closeSessionWithTransaction();
    }
}
