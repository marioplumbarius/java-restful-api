package com.mario.java.restful.api.hibernate.jpa.resource;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.DomainBeanParam;

/**
 * Resource layer... TODO - what else?
 * @author marioluan
 *
 * @param <T> the class type of the entities which will be managed by the instance
 * @param <ID> the class type of the id of the entities which will be managed by the instance
 * @param <F> the class type of the {@link DomainBeanParam} domainFilter
 */
public interface Resource<T extends BaseEntity, ID extends Serializable, F extends DomainBeanParam> {

	/**
	 * Finds an {@link T} entity by its {@link ID} id.
	 * @param id the id of the entity
	 * @return TODO
	 */
	public Response find(ID id);

	/**
	 * Finds all {@link List<T>} entities managed by the {@link Resource} instance.
	 * @return TODO
	 */
	public List<T> findAll();

	/**
	 * Finds all {@link List<T>} entities managed by the {@link Resource} instance matching the {@link F} filter.
	 * @return TODO
	 */
	public List<T> search(F filter);

	/**
	 * Creates a new {@link T} entity.
	 * @param entity the entity to be created
	 * @return TODO
	 */
	public Response create(T entity);

	/**
	 * Udates the state of the {@link T} entity.
	 * @param id the {@link ID} id of the entity
	 * @param entity the entity whose state should be updated
	 * @return TODO
	 */
	public Response update(ID id, T entity);

	/**
	 * Deletes the {@link T} entity with the provided {@link ID} id.
	 * @param id the id of the entity which should be deleted
	 * @return TODO
	 */
	public Response delete(ID id);

	/**
	 * Partially updates the state of the {@link T} entity with the provided {@link ID} id.
	 * @param id
	 * @param entity
	 * @return TODO
	 */
	public Response patch(ID id, T entity);
}
