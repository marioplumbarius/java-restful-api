package com.mario.java.test.restful.api.hibernate.jpa.service;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.mockito.Mockito.*;

import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.dao.implementation.UserDaoImplementation;
import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserServiceTest {

	@Mock
	private SessionManager sessionManager;

	@Mock
	private Session session;

	@Mock
	private UserDaoImplementation userDao;

	private UserService service;

	private User user;
	private Long id;

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			when(this.sessionManager.getSession()).thenReturn(this.session);

			this.userDao = new UserDaoImplementation(this.sessionManager);
			this.service = new UserService(this.userDao);
		});

		afterEach(() -> {
			this.user = null;
			this.id = null;
			// this.sessionManager = null;
			// this.userDao = null;
			// this.service = null;
		});

		describe(
				"#persist",
				() -> {
					beforeEach(() -> {
						this.user = UserFactory.createValidUser();
						this.service.persist(this.user);
					});

					it("opens a new session with transaction", () -> {
						verify(this.sessionManager)
						.openSessionWithTransaction();
					});

					it("persists the user to database", () -> {
						verify(this.userDao).persist(this.user);
					});

					it("closes the session", () -> {
						verify(this.userDao.sessionManager)
						.closeSessionWithTransaction();
					});

				});
	}
}
