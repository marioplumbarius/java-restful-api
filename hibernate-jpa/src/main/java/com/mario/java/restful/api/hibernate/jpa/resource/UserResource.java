package com.mario.java.restful.api.hibernate.jpa.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.resource.exception.ValidationExceptionHandler;
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
    public User find(@PathParam("id") Long id) {
        UserService userService = new UserService();
        User user = userService.findById(id);

        return user;
    }

    @POST
    public Response create(User user) {
        Response res = null;
        UserService userService = new UserService();

        try {
            userService.persist(user);
            res = Response.created(null).build();
        } catch (Exception e) {
            ValidationExceptionHandler validator = new ValidationExceptionHandler();
            Map<String, String> errors = null;

            if (!validator.isValid(user)) errors = validator.getErrors();

            res = Response.status(Status.BAD_REQUEST).entity(errors).build();
        }

        return res;
    }
}
