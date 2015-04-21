package com.mario.java.restful.api.hibernate.jpa.resource.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.BeanParam;
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
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain_;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.resource.Resource;
import com.mario.java.restful.api.hibernate.jpa.resource.annotation.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl.UserDomainBeanParamImpl;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.impl.qualifiers.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
@Api(value = "/users", description = "operation on user", tags = "user", protocols = "http")
public class UserResourceImpl implements Resource<UserDomain, Long, UserDomainBeanParamImpl> {

	private static final Logger LOGGER = Logger.getLogger(UserResourceImpl.class.getName());

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
    @ApiOperation(
    		value = "finds an user by {id}",
    		notes = "",
    		nickname = "find"
	)
    @ApiResponses(
    		value = {
					@ApiResponse(
							code = 404,
							message = "not found"
					),
					@ApiResponse(
    						code = 200,
    						message = "found",
    						response = UserDomain.class
					)
    		}
	)
    public Response find(
    		@ApiParam(value = "the id of the user") @PathParam("id") Long id) {
    	LOGGER.info("find(id=)".replace(":id", id.toString()));

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
    @ApiOperation(
    		value = "finds all users",
    		nickname = "findAll",
    		notes = ""
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 200,
    						message = "success",
    						response = UserDomain.class
					)
    		}
	)
    public List<UserDomain> findAll() {
    	LOGGER.info("findAll()");

        List<UserDomain> users = this.service.findAll();

        return users;
    }

    @Override
    @GET
    @Path("search")
    @ApiOperation(
    		value = "search users",
    		nickname = "search",
    		notes = "WARNING: documentation for this operation is under development."
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 200,
    						message = "success",
    						response = UserDomain.class
					)
    		}
	)
    // TODO - Swagger x RESTeasy do not work well with @BeanParam.
    public List<UserDomain> search(@BeanParam UserDomainBeanParamImpl beanParameters) {
    	LOGGER.info("search(beanParameters=:beanParameters)".replace(":beanParameters", beanParameters.toString()));

    	List<UserDomain> users = this.searchHelper(beanParameters);

		return users;
	}

    @Override
    @POST
    @ApiOperation(
    		value = "creates an user",
    		nickname = "create"
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 201,
    						message = "created"
    				),
    				@ApiResponse(
    						code = 400,
    						message = "bad syntax"
    				),
    				@ApiResponse(
    						code = 415,
    						message = "media type not supported"
    				),
    				@ApiResponse(
    						code = 422,
    						message = "unprocessable entity"
    				),
    				@ApiResponse(
    						code = 500,
    						message = "internal server error"
    				)
    		}
	)
	public Response create(@ApiParam(value = "user to be created", required = true) UserDomain user) {
    	LOGGER.info("create(user=:user)".replace(":user", user.toString()));

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
    @ApiOperation(
    		value = "updates an user",
    		nickname = "update"
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 204,
    						message = "updated"
					),
					@ApiResponse(
    						code = 400,
    						message = "bad syntax"
    				),
					@ApiResponse(
    						code = 415,
    						message = "media type not supported"
    				),
					@ApiResponse(
    						code = 422,
    						message = "unprocessable entity"
					),
					@ApiResponse(
    						code = 404,
    						message = "not found"
					),
					@ApiResponse(
    						code = 500,
    						message = "internal server error"
					)
    		}
	)
    public Response update(
    		@ApiParam(value = "the id of the user") @PathParam("id") Long id,
    		@ApiParam(value = "the updated user", required = true) UserDomain user) {
    	LOGGER.info("update(id=:id, user=:user)".replace(":id", id.toString()).replace(":user", user.toString()));

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
    @ApiOperation(
    		value = "deletes an user",
    		nickname = "delete"
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 204,
    						message = "deleted"
					),
					@ApiResponse(
    						code = 404,
    						message = "not found"
					),
					@ApiResponse(
    						code = 500,
    						message = "internal server error"
					)
    		}
	)
    public Response delete(
    		@ApiParam(value = "the id of the user") @PathParam("id") Long id) {
    	LOGGER.info("delete(id=:id)".replace(":id", id.toString()));

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
    @ApiOperation(
    		value="updates an user",
    		nickname = "patch",
    		notes = "allows partial update of the entity"
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 204,
    						message = "updated"
					),
					@ApiResponse(
    						code = 422,
    						message = "unprocessable entity"
					),
					@ApiResponse(
    						code = 404,
    						message = "not found"
					),
					@ApiResponse(
    						code = 500,
    						message = "internal server error"
					)
    		}
	)
    // TODO - make this operation be indexed by swagger
	public Response patch(
			@ApiParam(value = "the id of the user") @PathParam("id") Long id,
			@ApiParam(value = "the user with updated properties", required = true) UserDomain user) {
    	LOGGER.info("patch(id=:id, user=:user)".replace(":id", id.toString()).replace(":user", user.toString()));

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

    private List<UserDomain> searchHelper(UserDomainBeanParamImpl beanParameters){
    	List<UserDomain> users = null;

    	Map<SingularAttribute<UserDomain, ?>, Object> restrictions = this.mapBeanParamToRestrictions(beanParameters);

		if(restrictions != null && !restrictions.isEmpty()){
			users = this.service.findAll(restrictions);

			if(users != null && !users.isEmpty()){
				this.setUserDomainPropertiesToBeDisplayed(users, beanParameters.getPropertiesToBeDisplayed());
			}
		}

		return users;
    }

    // TODO - move this method to another [specific] class.
    private Map<SingularAttribute<UserDomain, ?>, Object> mapBeanParamToRestrictions(UserDomainBeanParamImpl beanParameters){
    	Map<SingularAttribute<UserDomain, ?>, Object> restrictions = new HashMap<SingularAttribute<UserDomain, ?>, Object>();

    	if(beanParameters.getName() != null){
    		restrictions.put(UserDomain_.name, beanParameters.getName());
    	}

    	return restrictions;
    }

    // TODO - find a fancy class for this method
    private void setUserDomainPropertiesToBeDisplayed(List<UserDomain> users, List<String> propertiesToBeDisplayed){
    	for(UserDomain user : users){
    		user.setPropertiesToBeDisplayed(propertiesToBeDisplayed);
    	}
    }
}
