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

import com.mario.java.restful.api.hibernate.jpa.dto.PetDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.UserDTO;
import com.mario.java.restful.api.hibernate.jpa.dto.utils.PetDTOUtils;
import com.mario.java.restful.api.hibernate.jpa.dto.validator.PetDTOValidator;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity_;
import com.mario.java.restful.api.hibernate.jpa.entity.UserEntity;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;
import com.mario.java.restful.api.hibernate.jpa.resource.Resource;
import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl.PetBeanParamImpl;
import com.mario.java.restful.api.hibernate.jpa.resource.http.method.PATCH;
import com.mario.java.restful.api.hibernate.jpa.resource.http.status.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.resource.utils.ResourceResponseUtils;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;
import com.mario.java.restful.api.hibernate.jpa.service.Service;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/pets")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
@Api(value = "/pets", description = "operation on petDTO", tags = "petDTO", protocols = "http")
public class PetResourceImpl implements Resource<PetDTO, Long, PetBeanParamImpl> {

	private static final Logger LOGGER = Logger.getLogger(PetResourceImpl.class.getName());

    private Service<PetDTO, Long> petService;
    private Service<UserDTO, Long> userService;
    private PetDTOValidator petDTOValidator;

    public PetResourceImpl() {
    }

    @Inject
    public PetResourceImpl(@PetService Service<PetDTO, Long> petService, @UserService Service<UserDTO, Long> userService, PetDTOValidator petDTOValidator) {
        this.petService = petService;
        this.userService = userService;
        this.petDTOValidator = petDTOValidator;
    }

    @Override
	@GET
    @Path("{id}")
    @ApiOperation(
    		value = "finds a petDTO by {id}",
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
    						response = PetDTO.class
					)
    		}
	)
    public Response find(@ApiParam(value = "the id of the petDTO") @PathParam("id") Long id) {
    	LOGGER.info("find(id=)".replace(":id", id.toString()));

        Response res = null;

        PetDTO petDTO = this.petService.find(id);

        if (petDTO == null) {
            res = Response.status(Status.NOT_FOUND).build();
        } else {
            res = Response.ok(petDTO).build();
        }

        return res;
    }

    @Override
	@GET
	@ApiOperation(
    		value = "finds all listPetDTO",
    		nickname = "findAll",
    		notes = ""
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 200,
    						message = "success",
    						response = PetDTO.class
					)
    		}
	)
    public List<PetDTO> findAll() {
    	LOGGER.info("findAll()");

        List<PetDTO> listPetDTO = this.petService.findAll();

        return listPetDTO;
    }

	@Override
	@GET
	@Path("search")
	@ApiOperation(
    		value = "search listPetDTO",
    		nickname = "search",
    		notes = "WARNING: documentation for this operation is under development."
	)
    @ApiResponses(
    		value = {
    				@ApiResponse(
    						code = 200,
    						message = "success",
    						response = PetDTO.class
					)
    		}
	)
    // TODO - Swagger x RESTeasy do not work well with @BeanParam.
	public List<PetDTO> search(@BeanParam PetBeanParamImpl beanParameters) {
		LOGGER.info("search(beanParameters=:beanParameters)".replace(":beanParameters", beanParameters.toString()));

		List<PetDTO> listPetDTO = new ArrayList<PetDTO>();
		
		listPetDTO = this.searchHelper(beanParameters);

        return listPetDTO;
	}

    @Override
	@POST
	@ApiOperation(
    		value = "creates a petDTO",
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
    public Response create(@ApiParam(value = "petDTO to be created", required = true) PetDTO petDTO) {
    	LOGGER.info("create(petDTO=:petDTO)".replace(":petDTO", petDTO.toString()));

        Response res = null;

        if (this.petDTOValidator.isValid(petDTO)) {
        	res = this.createHelper(petDTO);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.petDTOValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@PUT
    @Path("{id}")
    @ApiOperation(
    		value = "updates a petDTO",
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
    		@ApiParam(value = "the id of the petDTO") @PathParam("id") Long id,
    		@ApiParam(value = "the updated petDTO", required = true) PetDTO petDTO) {
    	LOGGER.info("update(id=:id, petDTO=:petDTO)".replace(":id", id.toString()).replace(":petDTO", petDTO.toString()));

        Response res = null;

        if (this.petDTOValidator.isValid(petDTO)) {
            res = this.updateHelper(id, petDTO);
        } else {
            res = Response.status(HttpStatus.UNPROCESSABLE_ENTITY).entity(this.petDTOValidator.getErrors()).build();
        }

        return res;
    }

    @Override
	@PATCH
    @Path("{id}")
    @ApiOperation(
    		value="updates a petDTO",
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
    		@ApiParam(value = "the id of the petDTO") @PathParam("id") Long id,
    		@ApiParam(value = "the petDTO with updated properties", required = true) PetDTO petDTO) {
    	LOGGER.info("patch(id=:id, petDTO=:petDTO)".replace(":id", id.toString()).replace(":petDTO", petDTO.toString()));

    	Response res = null;

        PetDTO petDTOFromDatabase = this.petService.find(id);

        if (petDTOFromDatabase != null) {
            petDTO.patch(petDTOFromDatabase);
            res = this.update(id, petDTO);
        } else {
            res = Response.status(Status.NOT_FOUND).build();
        }

        return res;
    }

    @Override
	@DELETE
    @Path("{id}")
    @ApiOperation(
    		value = "deletes a petDTO",
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
    		@ApiParam(value = "the id of the petDTO") @PathParam("id") Long id) {
    	LOGGER.info("delete(id=:id)".replace(":id", id.toString()));

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

    private Response createHelper(PetDTO petDTO){
    	Response res;

    	if(this.userService.find(petDTO.getUserId()) != null){
    		try {
				this.petService.persist(petDTO);
				URI uri = URI.create("/pets/" + petDTO.getId());
	            res = Response.created(uri).build();
			} catch (Exception e) {
				e.printStackTrace();
				res = Response.serverError().build();
			}
    	} else {
    		res = ResourceResponseUtils.buildResponseWithUserIdNotFoundError();
    	}

    	return res;
    }

    private Response updateHelper(Long id, PetDTO petDTO){
    	LOGGER.info("updateHelper(id=:id, petDTO=:petDTO)".replace(":id", id.toString()).replace(":petDTO", petDTO.toString()));
    	
    	Response res;

    	if(this.userService.find(petDTO.getUserId()) != null){
    		try {
                this.petService.update(id, petDTO);
                res = Response.noContent().build();
            } catch (ObjectNotFoundException e) {
                res = Response.status(Status.NOT_FOUND).build();
            } catch (Exception e) {
            	e.printStackTrace();
				res = Response.serverError().build();
            }
    	} else {
    		res = ResourceResponseUtils.buildResponseWithUserIdNotFoundError();
    	}

    	return res;
    }

    private List<PetDTO> searchHelper(PetBeanParamImpl beanParameters){
    	List<PetDTO> listPetDTO = new ArrayList<PetDTO>();

    	Map<SingularAttribute<PetEntity, ?>, Object> restrictions = this.mapDomainFilterToRestrictions(beanParameters);

    	if(restrictions != null && !restrictions.isEmpty()){
    		listPetDTO = this.petService.findAll(restrictions);

    		if(listPetDTO != null && !listPetDTO.isEmpty()){
    			PetDTOUtils.setPetDomainPropertiesToBeDisplayed(listPetDTO, beanParameters.getPropertiesToBeDisplayed());
        	}
    	}

    	return listPetDTO;
    }

	private Map<SingularAttribute<PetEntity, ?>, Object> mapDomainFilterToRestrictions(PetBeanParamImpl petDomainFilter){
    	Map<SingularAttribute<PetEntity, ?>, Object> restrictions = new HashMap<SingularAttribute<PetEntity, ?>, Object>();

    	if(petDomainFilter.getName() != null && !petDomainFilter.equals("")){
    		restrictions.put(PetEntity_.name, petDomainFilter.getName());
    	}

    	if(petDomainFilter.getAge() > 0){
    		restrictions.put(PetEntity_.age, petDomainFilter.getAge());
    	}

    	if(petDomainFilter.getUserId() != null){
    		UserEntity userEntity = new UserEntity();
    		userEntity.setId(petDomainFilter.getUserId());

    		restrictions.put(PetEntity_.user, userEntity);
    	}

    	return restrictions;
    }
}
