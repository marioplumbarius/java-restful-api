package com.mario.java.restful.api.hibernate.jpa.resource;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;

@Path("/pets")
@Produces("application/json")
public class PetResource {

	private PetService service;

	public PetResource() {
		this(new PetService());
	}

	public PetResource(PetService service) {
		this.service = service;
	}

	@GET
	@Path("{id}")
	public Response find(@PathParam("id") Long id) {
		Response res = null;

		PetDomain pet = this.service.find(id);

		if (pet == null) {
			res = Response.status(Status.NOT_FOUND).build();
		} else {
			res = Response.ok(pet).build();
		}

		return res;
	}

	@GET
	@Path("findBy/{key}/{value}")
	public List<PetDomain> findBy(@PathParam("key") String key, @PathParam("value") String value) {
		List<PetDomain> pets = this.service.findBy(key, value);

		return pets;
	}

	@GET
	public List<PetDomain> findAll(@QueryParam("name") String name) {
		List<PetDomain> pets = null;

		if (name != null) {
			Map<String, String> criterias = new HashMap<String, String>();
			criterias.put("name", name);
			pets = this.service.findAll(criterias);
		} else {
			pets = this.service.findAll();
		}

		return pets;
	}

	@POST
	public Response create(PetDomain pet) {
		Response res = null;

		if (pet.isValid()) {
			this.service.persist(pet);
			URI uri = URI.create("/pets/" + pet.getId());
			res = Response.created(uri).build();
		} else {
			res = Response.status(Status.BAD_REQUEST).entity(pet.getErrors()).build();
		}

		return res;
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") Long id, PetDomain pet) {
		Response res = null;

		if(pet.isValid()){
			try {
				this.service.update(id, pet);
				res = Response.noContent().build();
			} catch (ObjectNotFoundException e) {
				res = Response.status(Status.NOT_FOUND).build();
			}
		} else {
			res = Response.status(Status.BAD_REQUEST).entity(pet.getErrors()).build();
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
}
