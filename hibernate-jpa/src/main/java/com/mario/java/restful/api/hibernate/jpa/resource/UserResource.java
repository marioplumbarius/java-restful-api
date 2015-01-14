package com.mario.java.restful.api.hibernate.jpa.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;

@Path("/users")
public class UserResource {

	@GET
	@Produces("application/json")
	public User getByName(@QueryParam("name") String name) {
		Session session = SessionManager.getSessionFactory().openSession();

		session.beginTransaction();

		User user = new User(name);

		session.save(user);
		session.getTransaction().commit();

		Query q = session.createQuery("From User ");
		List<User> resultList = q.list();

		return resultList.get(0);
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public User getById(@PathParam("id") Long id) {
		UserService userService = new UserService();
		User user = userService.findById(id);

		return user;
	}
}
