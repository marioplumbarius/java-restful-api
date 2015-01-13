package com.mario.java.restful.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.mario.java.restful.api.model.User;

@Path("/users")
public class UserResource {

    @GET
    @Produces("application/json")
    public User getByName(@QueryParam("name") String name) {
        return new User(name);
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public User getById(@PathParam("id") String id) {
        return new User("mario", id);
    }
}
