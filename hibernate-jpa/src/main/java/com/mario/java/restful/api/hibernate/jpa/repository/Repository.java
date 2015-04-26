/**
 *
 */
package com.mario.java.restful.api.hibernate.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface for generic repository operations.
 * @author marioluan
 *
 * @param <T> the entity's type
 * @param <ID> the entity's primary key type
 */
public interface Repository<T, ID extends Serializable> {

	/**
	 * Finds all entities
	 * @return all entities found or null
	 */
	public List<T> findAll();

	/**
	 * Finds all {@link Map<K, K>} entities matching the restrictions
	 * @param restrictions the restrictions to be applied to the search
	 * @return the entities found or null
	 */
	public <K, V> List<T> findAll(Map<K, V> restrictions);

	/**
	 * Finds an {@link T} entity by its {@link ID} id
	 * @param id the id of the entity
	 * @return the entity found or null
	 */
	public T find(ID id);

	/**
	 * Persists an {@link T} entity
	 * @param entity the entity to be persisted
	 * @throws Exception when the entity could not be persisted
	 */
	public void persist(T entity) throws Exception;

	/**
	 * Updates the {@link T} entity
	 * @param entity the entity to override the existing one from database with the same id
	 * @throws Exception when the entity could not be updated
	 */
	public void update(T entity) throws Exception;

	/**
	 * Deletes the {@link T} entity
	 * @param entity the entity to be deleted
	 * @throws Exception when the entity could not be deleted
	 */
	public void delete(T entity) throws Exception;

	/**
	 * Deletes all entities
	 * @throws Exception when the entities could not be deleted
	 */
	public void deleteAll() throws Exception;

	/**
	 * Deletes all entities
	 * @param entities the entities to be deleted
	 * @throws Exception when the entities could not be deleted
	 */
	public void deleteAll(List<T> entities) throws Exception;
	
	/**
	 * Refreshes the state of the entity with the provided id from database.
	 * @param entity the entity to have its state refreshed
	 */
	public void refresh(ID id);
}