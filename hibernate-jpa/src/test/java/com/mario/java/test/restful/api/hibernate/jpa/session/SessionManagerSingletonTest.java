package com.mario.java.test.restful.api.hibernate.jpa.session;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.session.SessionManagerSingleton;
import com.mscharhag.oleaster.runner.OleasterRunner;

@Ignore
@RunWith(OleasterRunner.class)
public class SessionManagerSingletonTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Transaction transaction;

	private SessionManagerSingleton instance;

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			this.instance = SessionManagerSingleton.getInstance(this.sessionFactory);
			SessionManagerSingleton.setInstance(this.instance);

			Mockito.when(this.sessionFactory.openSession()).thenReturn(this.session);
			Mockito.when(this.session.beginTransaction()).thenReturn(this.transaction);
		});

		afterEach(() -> {

		});

		describe("#getSession", () -> {
			describe("when the session hasn't been assigned yet", () -> {
				it("returns the null", () -> {
					expect(SessionManagerSingleton.getInstance(this.sessionFactory).getSession()).toBeNull();
				});
			});

			describe("when the session has already been assigned", () -> {
				beforeEach(() -> {
					SessionManagerSingleton.getInstance(this.sessionFactory).openSession();
				});

				afterEach(() -> {
					SessionManagerSingleton.getInstance(this.sessionFactory).closeSession();
				});

				it("returns the session", () -> {
					expect(SessionManagerSingleton.getInstance(this.sessionFactory).getSession()).toBeNotNull();
					expect(SessionManagerSingleton.getInstance(this.sessionFactory).getSession() instanceof Session).toBeTrue();
				});
			});
		});

		describe("#openSession", () -> {
			beforeEach(() -> {
				SessionManagerSingleton.getInstance(this.sessionFactory).openSession();
			});

			it("opens the session", () -> {
				Mockito.verify(this.sessionFactory).openSession();
			});
		});

		describe("#closeSession", () -> {
			beforeEach(() -> {
				SessionManagerSingleton.getInstance(this.sessionFactory).openSession();
				SessionManagerSingleton.getInstance(this.sessionFactory).closeSession();
			});

			it("closes the session", () -> {
				Mockito.verify(this.session).close();
			});
		});

		describe("#openSessionWithTransaction", () -> {
			beforeEach(() -> {
				SessionManagerSingleton.getInstance(this.sessionFactory).openSessionWithTransaction();
			});

			it("opens a transactional session", () -> {
				Mockito.verify(this.sessionFactory).openSession();
			});

			it("begins the transaction", () -> {
				Mockito.verify(this.session).beginTransaction();
			});
		});

		describe("#closeSessionWithTransaction", () -> {
			beforeEach(() -> {
				SessionManagerSingleton.getInstance(this.sessionFactory).openSessionWithTransaction();
				SessionManagerSingleton.getInstance(this.sessionFactory).closeSessionWithTransaction();
			});

			it("commits the transaction", () -> {
				Mockito.verify(this.transaction).commit();
			});

			it("closes the transactional session", () -> {
				Mockito.verify(this.session).close();
			});
		});
	}
}
