package com.mario.java.restful.api.hibernate.jpa.service;

import java.util.List;
import java.util.Map;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.exception.ObjectNofFoundException;

public interface UserService {

	/**
	 * Persists the {@link UserDomain} userDomain
	 * @param userDomain the userDomain to be persisted
	 * @throws Exception when it couldn't persist the userDomain
	 */
	public void persist(UserDomain userDomain) throws Exception;

	/**
	 * Updates the {@link UserDomain} userDomain
	 * @param id the {@link Long} id of the userDomain
	 * @param userDomain the userDomain to be updated
	 * @throws Exception when it couldn't update the userDomain
	 * @throws ObjectNofFoundException when the userDomain to be updated does not exist
	 */
	public void update(Long id, UserDomain userDomain) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes the {@link UserDomain} userDomain
	 * @param id the {@link Long} id of the userDomain
	 * @throws Exception when it couldn't delete the userDomain
	 * @throws ObjectNofFoundException when the userDomain to be deleted does not exist
	 */
	public void delete(Long id) throws Exception, ObjectNofFoundException;

	/**
	 * Deletes all {@link UserDomain} userDomain
	 * @throws Exception when it couldn't delete all userDomain
	 * @throws ObjectNofFoundException when there aren't userDomain to be deleted
	 */
	public void deleteAll() throws Exception, ObjectNofFoundException;

	/**
	 * Deletes all {@link UserDomain} userDomain matching the {@link Map<K, V>} restrictions.
	 * @throws Exception when it couldn't delete all userDomain
	 * @throws ObjectNofFoundException when there aren't userDomain to be deleted
	 */
	public <K, V> void deleteAll(Map<K, V> restrictions) throws Exception, ObjectNofFoundException;

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
	 * Finds all {@link UserDomain} userDomain matching the {@link Map<K, V>} restrictions
	 * @param restrictions the list of restrictions to be applied to the search
	 * @return the list of userDomain matching the restrictions or null
	 */
	public <K, V> List<UserDomain> findAll(Map<K, V> restrictions);
}