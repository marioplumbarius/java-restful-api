package com.mario.java.restful.api.hibernate.jpa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.dto.BaseDTO;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNotFoundException;

/**
 * Service layer which manages the state of the dtos of the application.
 * @author marioluan
 *
 * @param <T> the class type of the dto to be managed
 * @param <ID> the class type of the id of the dto to be managed
 */
public interface Service<T extends BaseDTO, ID extends Serializable> {

	/**
	 * Persists the {@link T} dto.
	 * @param dto the dto to be persisted
	 * @throws Exception when it couldn't persist the dto
	 */
	public void persist(T dto) throws Exception;

	/**
	 * Updates the {@link T} dto.
	 * @param id the {@link ID} id of the dto
	 * @param dto the dto to be updated
	 * @throws Exception when it couldn't update the dto
	 * @throws ObjectNotFoundException when the dto to be updated does not exist
	 */
	public void update(ID id, T dto) throws Exception, ObjectNotFoundException;

	/**
	 * Deletes the {@link T} dto.
	 * @param id the {@link ID} id of the dto
	 * @throws Exception when it couldn't delete the dto
	 * @throws ObjectNotFoundException when the dto to be deleted does not exist
	 */
	public void delete(ID id) throws Exception, ObjectNotFoundException;

	/**
	 * Deletes all {@link T} dto.
	 * @throws Exception when it couldn't delete all dtos
	 * @throws ObjectNotFoundException when there aren't dtos to be deleted
	 */
	public void deleteAll() throws Exception, ObjectNotFoundException;

	/**
	 * Finds the {@link T} dto.
	 * @param id the {@link ID} id of the dto
	 * @return the dto found or null
	 */
	public T find(ID id);

	/**
	 * Finds all {@link T} dto.
	 * @return the list of dtos found or null
	 */
	public List<T> findAll();

	/**
	 * Finds all {@link T} dtos matching the {@link Map<K, V>} restrictions.
	 * @param restrictions the list of restrictions to be applied to the search
	 * @return the list of dtos matching the restrictions or null
	 */
	public <K, V> List<T> findAll(Map<K, V> restrictions);
}
