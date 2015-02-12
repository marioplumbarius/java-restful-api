package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;

public class UserService {
    private CrudRepository<UserDomain, Long> userCrud;

    public UserService() {
        this(new CrudRepository<UserDomain, Long>("UserDomain", UserDomain.class));
    }
    
    public UserService(CrudRepository<UserDomain, Long> userCrud){
    	this.userCrud = userCrud;
    }

    public void persist(UserDomain user) {
        this.userCrud.persist(user);
    }

    public void update(Long id, UserDomain user) {
        user.setId(id);
    	this.userCrud.update(id, user);
    }

    public UserDomain find(Long id) {
        UserDomain user = this.userCrud.find(id);
        return user;
    }
    
    public List<UserDomain> findBy(String key, String value){
    	List<UserDomain> users = this.userCrud.findBy(key, value);
    	return users;
    }

    public List<UserDomain> findAll() {
        List<UserDomain> users = this.userCrud.findAll();
        return users;
    }
    
    public List<UserDomain> findAll(Map<String, String> criterias){
    	List<UserDomain> users = this.userCrud.findAll(criterias);
        return users;
    }
    
    public void delete(Long id) {
        UserDomain user = this.userCrud.find(id);
        this.userCrud.delete(user);
    }

    public void deleteAll() {
        this.userCrud.deleteAll();
    }
}
