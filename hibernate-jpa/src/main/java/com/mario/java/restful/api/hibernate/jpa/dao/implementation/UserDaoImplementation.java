package com.mario.java.restful.api.hibernate.jpa.dao.implementation;

import java.util.List;

import com.mario.java.restful.api.hibernate.jpa.dao.interfacee.UserDaoInterface;
import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;

public class UserDaoImplementation implements UserDaoInterface<User, Long> {
    public SessionManager sessionManager = new SessionManager();

    @Override
    public void persist(User entity) {
        this.sessionManager.getSession().save(entity);
    }

    @Override
    public void update(Long id, User entity) {
        entity.setId(id);
        this.sessionManager.getSession().update(entity);
    }

    @Override
    public User find(Long id) {
        User user = (User) this.sessionManager.getSession().get(User.class, id);
        return user;
    }

    @Override
    public void delete(User entity) {
        this.sessionManager.getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        List<User> users = this.sessionManager.getSession()
                .createQuery("from User").list();
        return users;
    }

    @Override
    public void deleteAll() {
        List<User> users = this.findAll();
        for (User user : users)
            this.delete(user);
    }
}
