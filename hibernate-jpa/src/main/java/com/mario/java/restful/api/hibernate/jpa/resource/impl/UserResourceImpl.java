package com.mario.java.restful.api.hibernate.jpa.resource.impl;

import java.net.URI;
import java.util.ArrayList;
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

import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.validator.UserDTOValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity_;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.resource.Resource;
import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl.UserBeanParamImpl;
import com.mario.java.restful.api.hibernate.jpa.resource.http.method.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.http.status.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
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
public class UserResourceImpl implements Resource<UserDTO, Long, UserBeanParamImpl> {

	private static final Logger LOGGER = Logger.getLogger(UserResourceImpl.class.getName());

	private Service<UserDTO, Long> service;
	private UserDTOValidator userDtoValidator;

	public UserResourceImpl(){
	}

    @Inject
	public UserResourceImpl(@UserService Service<UserDTO, Long> service, UserDTOValidator userDtoValidator) {
        this.service = service;
        this.userDtoValidator = userDtoValidator;
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
    						response = UserDTO.class
					)
    		}
	)
    public Response find(
    		@ApiParam(value = "the id of the user") @PathParam("id") Long id) {
    	LOGGER.info("find(id=)".replace(":id", id.toString()));

    	Response res = null;

        UserDTO userDTO = this.service.find(id);

        if (userDTO == null) {
            res = Response.status(Status.NOT_FOUND).build();
        } else {
            res = Response.ok(userDTO).build();
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
    						response = UserDTO.class
					)
    		}
	)
    public List<UserDTO> findAll() {
    	LOGGER.info("findAll()");

        List<UserDTO> listUserDTO = this.service.findAll();

        return listUserDTO;
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
    						response = UserDTO.class
					)
    		}
	)
    // TODO - Swagger x RESTeasy do not work well with @BeanParam.
    public List<UserDTO> search(@BeanParam UserBeanParamImpl beanParameters) {
    	LOGGER.info("search(beanParameters=:beanParameters)".replace(":beanParameters", beanParameters.toString()));

    	List<UserDTO> listUserDTO = this.searchHelper(beanParameters);

		return listUserDTO;
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
	public Response create(@ApiParam(value = "user to be created", required = true) UserDTO userDTO) {
    	LOGGER.info("create(user=:user)".replace(":user", userDTO.toString()));

    	Response res = null;

        if (this.userDtoValidator.isValid(userDTO)) {
            res = this.createHelper(userDTO);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.userDtoValidator.getErrors()).build();
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
    		@ApiParam(value = "the updated user", required = true) UserDTO userDTO) {
    	LOGGER.info("update(id=:id, user=:user)".replace(":id", id.toString()).replace(":user", userDTO.toString()));

    	Response res = null;

        if (this.userDtoValidator.isValid(userDTO)) {
        	res = this.updateHelper(id, userDTO);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.userDtoValidator.getErrors()).build();
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
			@ApiParam(value = "the user with updated properties", required = true) UserDTO userDTO) {
    	LOGGER.info("patch(id=:id, user=:user)".replace(":id", id.toString()).replace(":user", userDTO.toString()));

    	Response res = null;

        UserDTO userDTOFromDatabase = this.service.find(id);

        if (userDTOFromDatabase != null) {
            userDTO.patch(userDTOFromDatabase);
            res = this.update(id, userDTO);
        } else {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
	}

    private Response updateHelper(Long id, UserDTO userDTO){
    	Response res;

    	try {
            this.service.update(id, userDTO);
            res = Response.noContent().build();
        } catch (ObjectNotFoundException e) {
            res = Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			e.printStackTrace();
			res = Response.serverError().build();
		}

    	return res;
    }

    private Response createHelper(UserDTO userDTO){
    	Response res;

    	try {
			this.service.persist(userDTO);
			URI uri = URI.create("/users/" + userDTO.getId());
            res = Response.created(uri).build();
		} catch (Exception e) {
			e.printStackTrace();
			res = Response.serverError().build();
		}

    	return res;
    }

    private List<UserDTO> searchHelper(UserBeanParamImpl beanParameters){
    	List<UserDTO> users = new ArrayList<UserDTO>();

    	Map<SingularAttribute<UserEntity, ?>, Object> restrictions = this.mapBeanParamToRestrictions(beanParameters);

		if(restrictions != null && !restrictions.isEmpty()){
			users = this.service.findAll(restrictions);

			if(users != null && !users.isEmpty()){
				this.setUserDomainPropertiesToBeDisplayed(users, beanParameters.getPropertiesToBeDisplayed());
			}
		}

		return users;
    }

    // TODO - move this method to another [specific] class.
    private Map<SingularAttribute<UserEntity, ?>, Object> mapBeanParamToRestrictions(UserBeanParamImpl beanParameters){
    	Map<SingularAttribute<UserEntity, ?>, Object> restrictions = new HashMap<SingularAttribute<UserEntity, ?>, Object>();

    	if(beanParameters.getName() != null){
    		restrictions.put(UserEntity_.name, beanParameters.getName());
    	}

    	return restrictions;
    }

    // TODO - find a fancy class for this method
    private void setUserDomainPropertiesToBeDisplayed(List<UserDTO> listUserDTO, List<String> propertiesToBeDisplayed){
    	for(UserDTO userDTO : listUserDTO){
    		userDTO.setPropertiesToBeDisplayed(propertiesToBeDisplayed);
    	}
    }
}
