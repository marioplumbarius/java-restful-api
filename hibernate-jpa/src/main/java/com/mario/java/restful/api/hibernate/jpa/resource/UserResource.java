package com.mario.java.restful.api.hibernate.jpa.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

@Path("/users")
public class UserResource {

    @GET
    @Produces("application/json")
    public List<User> findAll() {
        UserService userService = new UserService();
        List<User> users = userService.findAll();

        return users;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public User findById(@PathParam("id") Long id) {
        UserService userService = new UserService();
        User user = userService.findById(id);

        return user;
    }
}
