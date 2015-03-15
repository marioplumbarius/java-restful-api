/**
 *
 */
package com.mario.java.restful.api.hibernate.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * DOCUMENT ME!!!
 * @author marioluan
 *
 * @param <T>
 * @param <ID>
 */
public interface AbstractRepository<T, ID extends Serializable> {

	public List<T> findAll();
	public List<T> findAll(Map<String, Object> criterias);
	public T find(ID id);
	public void persist(T entity) throws Exception;
	public void update(T entity) throws Exception;
	public void delete(T entity) throws Exception;
	public void deleteAll() throws Exception;
	public void deleteAll(List<T> entities) throws Exception;
	public void deleteAll(Map<String, Object> criterias) throws Exception;

}