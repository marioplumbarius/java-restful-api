package com.mario.java.restful.api.hibernate.jpa.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

@Path("/users")
@Produces("application/json")
public class UserResource {

    @GET
    public List<User> findAll() {
        UserService userService = new UserService();
        List<User> users = userService.findAll();

        return users;
    }

    @GET
    @Path("{id}")
    public User findById(@PathParam("id") Long id) {
        UserService userService = new UserService();
        User user = userService.findById(id);

        return user;
    }

    @POST
    public String create(User user) {
        UserService userService = new UserService();
        userService.persist(user);

        return null;
    }

}
