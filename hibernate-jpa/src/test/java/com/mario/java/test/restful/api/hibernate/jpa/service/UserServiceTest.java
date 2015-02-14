package com.mario.java.test.restful.api.hibernate.jpa.service;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserServiceTest {

	@Mock
	private UserDomain user;

	@Mock
	private CrudRepository<UserDomain, Long> userCrud;

	private UserService userService;

	private Long id;

	{

		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.userService = new UserService(this.userCrud);
			this.id = IdFactory.createValidId();
		});

		describe("#persist", ()->{
			beforeEach(()->{
				this.userService.persist(this.user);
			});

			it("persists the users", ()->{
				Mockito.verify(this.userCrud).persist(this.user);
			});
		});

		describe("#update", ()->{
			beforeEach(()->{
				this.userService.update(this.id, this.user);
			});

			it("sets the id of the user", ()->{
				Mockito.verify(this.user).setId(this.id);
			});

			it("updates the user", ()->{
				Mockito.verify(this.userCrud).update(this.id, this.user);
			});
		});
	}
}
