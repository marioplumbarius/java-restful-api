package com.mario.java.restful.api.hibernate.jpa.resource.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.resource.Resource;
import com.mario.java.restful.api.hibernate.jpa.resource.annotation.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.PetService;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;

@Path("/pets")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class PetResourceImpl implements Resource<PetDomain, Long> {

    private Service<PetDomain, Long> petService;
    private Service<UserDomain, Long> userService;
    private DomainValidator domainValidator;

    public PetResourceImpl() {
    }

    @Inject
    public PetResourceImpl(@PetService Service<PetDomain, Long> petService, @UserService Service<UserDomain, Long> userService, DomainValidator domainValidator) {
        this.petService = petService;
        this.userService = userService;
        this.domainValidator = domainValidator;
    }

    @Override
	@GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Response res = null;

        PetDomain pet = this.petService.find(id);

        if (pet == null) {
            res = Response.status(Status.NOT_FOUND).build();
        } else {
            res = Response.ok(pet).build();
        }

        return res;
    }

    @Override
	@GET
    public List<PetDomain> findAll() {
        List<PetDomain> pets = this.petService.findAll();

        return pets;
    }

    @Override
	@POST
    public Response create(PetDomain pet) {
        Response res = null;

        if (this.domainValidator.isValid(pet)) {
        	res = this.createHelper(pet);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.domainValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, PetDomain pet) {
        Response res = null;

        if (this.domainValidator.isValid(pet)) {
            res = this.updateHelper(id, pet);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.domainValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@PATCH
    @Path("{id}")
    public Response patch(@PathParam("id") Long id, PetDomain pet) {
        Response res = null;

        PetDomain currentPet = this.petService.find(id);

        if (currentPet != null) {
            pet.patch(currentPet);
            res = this.update(id, pet);
        } else {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
    }

    @Override
	@DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Response res = null;

        try {
            this.petService.delete(id);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
        } catch (Exception e) {
        	e.printStackTrace();
			res = Response.serverError().build();
		}

        return res;
    }

    private Response createHelper(PetDomain pet){
    	Response res;

    	if(this.userService.find(pet.getUserId()) != null){
    		try {
				this.petService.persist(pet);
				URI uri = URI.create("/pets/" + pet.getId());
	            res = Response.created(uri).build();
			} catch (Exception e) {
				e.printStackTrace();
				res = Response.serverError().build();
			}
    	} else {
    		res = this.buildUserIdNotFoundResponse();
    	}

    	return res;
    }

    private Response updateHelper(Long id, PetDomain pet){
    	Response res;

    	if(this.userService.find(pet.getUserId()) != null){
    		try {
                this.petService.update(id, pet);
                res = Response.noContent().build();
            } catch (ObjectNotFoundException e) {
                res = Response.status(Status.NOT_FOUND).build();
            } catch (Exception e) {
            	e.printStackTrace();
				res = Response.serverError().build();
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
