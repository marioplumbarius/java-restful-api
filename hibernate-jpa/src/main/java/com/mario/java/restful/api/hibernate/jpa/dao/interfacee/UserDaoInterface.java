package com.mario.java.restful.api.hibernate.jpa.dao.interfacee;

import java.io.Serializable;
import java.util.List;

public interface UserDaoInterface<T, Id extends Serializable> {

    public void persist(T entity);

    public void update(Id id, T entity);

    public T findById(Id id);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll();

}
