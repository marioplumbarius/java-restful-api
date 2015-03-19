package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;

public interface PetService {

	/**
	 * Persists the {@link PetDomain} petDomain.
	 * @param petDomain the petDomain to be persisted
	 * @throws Exception when it couldn't persist the petDomain
	 */
	public void persist(PetDomain petDomain) throws Exception;

	/**
	 * Updates the {@link PetDomain} petDomain.
	 * @param id the {@link Long} id of the petDomain
	 * @param petDomain the petDomain to be updated
	 * @throws Exception when it couldn't update the petDomain
	 * @throws ObjectNofFoundException when the petDomain to be updated does not exist
	 */
	public void update(Long id, PetDomain petDomain) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes the {@link PetDomain} petDomain.
	 * @param id the {@link Long} id of the petDomain
	 * @throws Exception when it couldn't delete the petDomain
	 * @throws ObjectNofFoundException when the petDomain to be deleted does not exist
	 */
	public void delete(Long id) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes all {@link PetDomain} petDomain.
	 * @throws Exception when it couldn't delete the list of petDomain
	 * @throws ObjectNofFoundException when there aren't petDomain to be deleted
	 */
	public void deleteAll() throws Exception, ObjectNofFoundException;

	/**
	 * Finds the {@link PetDomain} petDomain.
	 * @param id the {@link Long} id of the petDomain
	 * @return the petDomain found or null
	 */
	public PetDomain find(Long id);

	/**
	 * Finds all {@link PetDomain} petDomain.
	 * @return the list of petDomain found or null
	 */
	public List<PetDomain> findAll();

	/**
	 * Finds all {@link PetDomain} petDomain matching the {@link Map<String, Object>} restrictions.
	 * @param restrictions the list of restrictions to be applied to the search
	 * @return the list of petDomain matching the restrictions or null
	 */
	public List<PetDomain> findAll(Map<String, Object> restrictions);
}
