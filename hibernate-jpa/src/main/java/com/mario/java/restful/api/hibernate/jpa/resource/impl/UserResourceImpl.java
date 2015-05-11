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

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
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
    public Response find(Long id) {
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
    public List<UserDTO> findAll() {
    	LOGGER.info("findAll()");

        List<UserDTO> listUserDTO = this.service.findAll();

        return listUserDTO;
    }

    @Override
    @GET
    @Path("search")
    public List<UserDTO> search(@BeanParam UserBeanParamImpl beanParameters) {
    	LOGGER.info("search(beanParameters=:beanParameters)".replace(":beanParameters", beanParameters.toString()));

    	List<UserDTO> listUserDTO = this.searchHelper(beanParameters);

		return listUserDTO;
	}

    @Override
    @POST
	public Response create(UserDTO userDTO) {
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
    public Response update(Long id, UserDTO userDTO) {
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
    public Response delete(Long id) {
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
	public Response patch(Long id, UserDTO userDTO) {
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
