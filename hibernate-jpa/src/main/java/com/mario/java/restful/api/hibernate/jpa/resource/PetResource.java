package com.mario.java.restful.api.hibernate.jpa.resource;

import java.net.URI;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.resource.annotation.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.impl.PetServiceImpl;
import com.mario.java.restful.api.hibernate.jpa.service.impl.UserServiceImpl;

@Path("/pets")
@Consumes("application/json")
@Produces("application/json")
public class PetResource {

    private PetServiceImpl petServiceImpl;
    private UserServiceImpl userServiceImpl;

    public PetResource() {
        this(new PetServiceImpl(), new UserServiceImpl());
    }

    public PetResource(PetServiceImpl petServiceImpl, UserServiceImpl userServiceImpl) {
        this.petServiceImpl = petServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Response res = null;

        PetDomain pet = this.petServiceImpl.find(id);

        if (pet == null) {
            res = Response.status(Status.NOT_FOUND).build();
        } else {
            res = Response.ok(pet).build();
        }

        return res;
    }

    @GET
    public List<PetDomain> findAll() {
        List<PetDomain> pets = this.petServiceImpl.findAll();

        return pets;
    }

    @POST
    public Response create(PetDomain pet) {
        Response res = null;

        if (pet.isValid()) {
        	res = this.createHelper(pet);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(pet.getErrors()).build();
        }

        return res;
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, PetDomain pet) {
        Response res = null;

        if(pet.isValid()){
            res = this.updateHelper(id, pet);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(pet.getErrors()).build();
        }

        return res;
    }

    @PATCH
    @Path("{id}")
    public Response patch(@PathParam("id") Long id, PetDomain pet) {
        Response res = null;

        PetDomain currentPet = this.petServiceImpl.find(id);

        if (currentPet != null) {
            pet.patch(currentPet);
            res = this.update(id, pet);
        } else {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Response res = null;

        try {
            this.petServiceImpl.delete(id);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
    }

    private Response createHelper(PetDomain pet){
    	Response res;

    	if(this.userServiceImpl.find(pet.getUserId()) != null){
    		this.petServiceImpl.persist(pet);
            URI uri = URI.create("/pets/" + pet.getId());
            res = Response.created(uri).build();
    	} else {
    		res = this.buildUserIdNotFoundResponse();
    	}

    	return res;
    }

    private Response updateHelper(Long id, PetDomain pet){
    	Response res;

    	if(this.userServiceImpl.find(pet.getUserId()) != null){
    		try {
                this.petServiceImpl.update(id, pet);
                res = Response.noContent().build();
            } catch (ObjectNotFoundException e) {
                res = Response.status(Status.NOT_FOUND).build();
            }
    	} else {
    		res = this.buildUserIdNotFoundResponse();
    	}

    	return res;
    }

    private Response buildUserIdNotFoundResponse(){
    	// TODO
		// move error defined messages to a file
		Map<String, Object> errors = DomainValidator.buildError("userId", "not found");
		return Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(errors).build();
    }
}
