package com.mario.java.restful.api.hibernate.jpa.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.resource.exception.HibernateValidationExceptionHandler;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

@Component
@Path("/users")
@Produces("application/json")
public class UserResource {

    private Map<String, String> errors;
    private HibernateValidationExceptionHandler validator = new HibernateValidationExceptionHandler();
    @Autowired
    private UserService service;

    @GET
    public List<User> findAll() {
        this.service = new UserService();
        List<User> users = this.service.findAll();

        return users;
    }

    @GET
    @Path("{id}")
    public User find(@PathParam("id") Long id) {
        this.service = new UserService();
        User user = this.service.find(id);

        return user;
    }

    @POST
    public Response create(User user) {
        Response res = null;

        if (this.validator.isValid(user)) {
            this.service.persist(user);
            URI uri = URI.create("/users/" + user.getId());
            res = Response.created(uri).build();
        } else {
            this.errors = this.validator.getErrors();
            res = Response.status(Status.BAD_REQUEST).entity(this.errors)
                    .build();
        }

        return res;
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, User user) {
        Response res = null;

        if (this.validator.isValid(user)) {
            this.service.update(id, user);
            res = Response.ok().build();
        } else {
            this.errors = this.validator.getErrors();
            res = Response.status(Status.BAD_REQUEST).entity(this.errors)
                    .build();
        }

        return res;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        this.service.delete(id);

        return Response.ok().build();
    }
}
