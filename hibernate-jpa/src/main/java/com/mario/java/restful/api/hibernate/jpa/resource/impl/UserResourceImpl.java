package com.mario.java.restful.api.hibernate.jpa.resource.impl;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.resource.Resource;
import com.mario.java.restful.api.hibernate.jpa.resource.annotation.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class UserResourceImpl implements Resource<UserDomain, Long> {

	private Service<UserDomain, Long> service;
	private DomainValidator domainValidator;

	public UserResourceImpl(){
	}

    @Inject
	public UserResourceImpl(@UserService Service<UserDomain, Long> service, DomainValidator domainValidator) {
        this.service = service;
        this.domainValidator = domainValidator;
    }

    @Override
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

    @Override
	@GET
    public List<UserDomain> findAll() {
        List<UserDomain> users = this.service.findAll();

        return users;
    }

    @Override
	@POST
    public Response create(UserDomain user) {
        Response res = null;

        if (this.domainValidator.isValid(user)) {
            res = this.createHelper(user);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.domainValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserDomain user) {
        Response res = null;

        if (this.domainValidator.isValid(user)) {
        	res = this.updateHelper(id, user);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.domainValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Response res = null;

        try {
            this.service.delete(id);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
        } catch (Exception e) {
        	e.printStackTrace();
        	res = Response.serverError().build();
        }

        return res;
    }

    @Override
	@PATCH
	@Path("{id}")
	public Response patch(@PathParam("id") Long id, UserDomain user) {
        Response res = null;

        UserDomain currentUser = this.service.find(id);

        if (currentUser != null) {
            user.patch(currentUser);
            res = this.update(id, user);
        } else {
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
		} catch (Exception e) {
			e.printStackTrace();
			res = Response.serverError().build();
		}

    	return res;
    }

    private Response createHelper(UserDomain userDomain){
    	Response res;

    	try {
			this.service.persist(userDomain);
			URI uri = URI.create("/users/" + userDomain.getId());
            res = Response.created(uri).build();
		} catch (Exception e) {
			e.printStackTrace();
			res = Response.serverError().build();
		}

    	return res;
    }
}
