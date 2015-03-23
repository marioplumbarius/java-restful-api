package com.mario.java.restful.api.hibernate.jpa.resource;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mario.java.restful.api.hibernate.jpa.domain.BaseDomain;

public interface Resource<T extends BaseDomain, ID extends Serializable> {
	public Response find(ID id);
	public List<T> findAll();
	public Response create(T entity);
	public Response update(ID id, T entity);
	public Response delete(ID id);
	public Response patch(ID id, T entity);
}
