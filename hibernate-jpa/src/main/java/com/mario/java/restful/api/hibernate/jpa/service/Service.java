package com.mario.java.restful.api.hibernate.jpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.BaseDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;

/**
 * Service layer which makes operations regarding the state of the managed entity.
 * @author marioluan
 *
 * @param <T> the class type of the entity to be managed
 * @param <ID> the class type of the entity's id which will be managed
 */
public interface Service<T extends BaseDomain, ID extends Serializable> {

	/**
	 * Persists the {@link T} entity
	 * @param entity the entity to be persisted
	 * @throws Exception when it couldn't persist the entity
	 */
	public void persist(T entity) throws Exception;

	/**
	 * Updates the {@link T} entity
	 * @param id the {@link ID} id of the entity
	 * @param entity the entity to be updated
	 * @throws Exception when it couldn't update the entity
	 * @throws ObjectNofFoundException when the entity to be updated does not exist
	 */
	public void update(ID id, T entity) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes the {@link T} entity
	 * @param id the {@link ID} id of the entity
	 * @throws Exception when it couldn't delete the entity
	 * @throws ObjectNofFoundException when the entity to be deleted does not exist
	 */
	public void delete(ID id) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes all {@link T} entity
	 * @throws Exception when it couldn't delete all entity
	 * @throws ObjectNofFoundException when there aren't entity to be deleted
	 */
	public void deleteAll() throws Exception, ObjectNofFoundException;

	/**
	 * Finds the {@link T} entity
	 * @param id the {@link ID} id of the entity
	 * @return the entity found or null
	 */
	public T find(ID id);

	/**
	 * Finds all {@link T} entity
	 * @return the list of entity found or null
	 */
	public List<T> findAll();

	/**
	 * Finds all {@link T} entities matching the {@link Map<K, V>} restrictions
	 * @param restrictions the list of restrictions to be applied to the search
	 * @return the list of entity matching the restrictions or null
	 */
	public <K, V> List<T> findAll(Map<K, V> restrictions);
}
