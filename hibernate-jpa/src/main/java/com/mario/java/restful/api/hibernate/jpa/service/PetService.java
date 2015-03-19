package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;

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
	 */
	public void update(Long id, PetDomain petDomain) throws Exception;

	/**
	 * Deletes the {@link PetDomain} petDomain.
	 * @param id the {@link Long} id of the petDomain
	 * @throws Exception when it couldn't delete the petDomain
	 */
	public void delete(Long id) throws Exception;

	/**
	 * Deletes all {@link PetDomain} petDomain.
	 * @throws Exception when it couldn't delete the list of petDomain
	 */
	public void deleteAll() throws Exception;

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
