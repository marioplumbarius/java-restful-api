package com.mario.java.restful.api.hibernate.jpa.resource;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mario.java.restful.api.hibernate.jpa.dto.BaseDTO;
import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.BeanParam;

/**
 * Resource layer which manages a dto.
 * @author marioluan
 *
 * @param <T> the class type of the dto which will be managed by this instance
 * @param <ID> the class type of the id of the dto which will be managed by the instance
 * @param <F> the class type of the {@link BeanParam domainBeanParam} which should be used by this instance
 */
public interface Resource<T extends BaseDTO, ID extends Serializable, F extends BeanParam> {

	/**
	 * Finds an {@link T} dto by its {@link ID} id.
	 * @param id the id of the dto
	 * @return the found dto or null
	 */
	public Response find(ID id);

	/**
	 * Finds all {@link List<T>} dtos managed by the {@link Resource} instance.
	 * @return the list of dtos found or null
	 */
	public List<T> findAll();

	/**
	 * Finds all {@link List<T>} dtos managed by the {@link Resource} instance matching the {@link F} filter.
	 * @return the list of dto found or null
	 */
	public List<T> search(F filter);

	/**
	 * Creates a new {@link T} dto.
	 * @param dto the dto to be created
	 * @return an empty response or errors
	 */
	public Response create(T dto);

	/**
	 * Udates the state of the {@link T} dto.
	 * @param id the {@link ID} id of the dto
	 * @param dto the dto whose state should be updated
	 * @return an empty response or errors
	 */
	public Response update(ID id, T dto);

	/**
	 * Deletes the {@link T} dto with the provided {@link ID} id.
	 * @param id the id of the dto which should be deleted
	 * @return an empty response or errors
	 */
	public Response delete(ID id);

	/**
	 * Partially updates the state of the {@link T} dto with the provided {@link ID} id.
	 * @param id the id of the dto
	 * @param dto the updated dto
	 * @return an empty response or errors
	 */
	public Response patch(ID id, T dto);
}
