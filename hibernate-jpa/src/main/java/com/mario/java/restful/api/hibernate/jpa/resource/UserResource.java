package com.mario.java.restful.api.hibernate.jpa.resource;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserResource {

    private UserService service;

    public UserResource() {
        this(new UserService());
    }

    public UserResource(UserService service) {
        this.service = service;
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Response res = null;

        UserDomain user = this.service.find(id);

        if (user == null) {
            res = Response.status(Status.NOT_FOUND).build();
        } else {
            res = Response.ok(user).build();
        }

        return res;
    }

    @GET
    public List<UserDomain> findAll(@QueryParam("name") String name) {
        List<UserDomain> users = null;

        if (name != null) {
            Map<String, Object> criterias = new HashMap<String, Object>();
            criterias.put("name", name);
            users = this.service.findAll(criterias);
        } else {
            users = this.service.findAll();
        }

        return users;
    }

    @POST
    public Response create(UserDomain user) {
        Response res = null;

        if (user.isValid()) {
            this.service.persist(user);
            URI uri = URI.create("/users/" + user.getId());
            res = Response.created(uri).build();
        } else {
            res = Response.status(422).entity(user.getErrors()).build();
        }

        return res;
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserDomain user) {
        Response res = null;

        if (user.isValid()) {
        	res = this.updateHelper(id, user);
        } else {
            res = Response.status(422).entity(user.getErrors()).build();
        }

        return res;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Response res = null;

        try {
            this.service.delete(id);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
    }

    private Response updateHelper(Long id, UserDomain user){
    	Response res;

    	try {
            this.service.update(id, user);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
        }

    	return res;
    }
}
