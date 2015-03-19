package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.ObjectNofFoundException;

public interface UserService {

	/**
	 * Persists the {@link UserDomain} userDomain
	 * @param userDomain the userDomain to be persisted
	 */
	public void persist(UserDomain userDomain);

	/**
	 * Updates the {@link UserDomain} userDomain
	 * @param id the {@link Long} id of the userDomain
	 * @param userDomain the userDomain to be updated
	 * @throws ObjectNofFoundException when it couldn't find the userDomain to be updated
	 */
	public void update(Long id, UserDomain userDomain) throws ObjectNofFoundException;

	/**
	 * Deletes the {@link UserDomain} userDomain
	 * @param id the {@link Long} id of the userDomain
	 * @throws ObjectNofFoundException when it couldn't find the userDomain to be deleted
	 */
	public void delete(Long id) throws ObjectNofFoundException;

	/**
	 * Deletes all {@link UserDomain} userDomain
	 */
	public void deleteAll();

	/**
	 * Finds the {@link UserDomain} userDomain
	 * @param id the {@link Long} id of the userDomain
	 * @return the userDomain found or null
	 */
	public UserDomain find(Long id);

	/**
	 * Finds all {@link UserDomain} userDomain
	 * @return the list of userDomain found or null
	 */
	public List<UserDomain> findAll();

	/**
	 * Finds all {@link UserDomain} userDomain matching the {@link Map<String, Object>} restrictions
	 * @param restrictions the list of restrictions to be applied to the search
	 * @return the list of userDomain matching the restrictions or null
	 */
	public List<UserDomain> findAll(Map<String, Object> restrictions);
}