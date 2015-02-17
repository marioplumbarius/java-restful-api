package com.mario.java.test.restful.api.hibernate.jpa.util;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class SessionManagerTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Transaction transaction;

	private SessionManager sessionManager;

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			this.sessionManager = new SessionManager(this.sessionFactory);

			Mockito.when(this.sessionFactory.openSession()).thenReturn(this.session);
			Mockito.when(this.session.beginTransaction()).thenReturn(this.transaction);
		});

		afterEach(() -> {

		});

		describe("#getSession", () -> {
			describe("when the session hasn't been assigned yet", () -> {
				it("returns the null", () -> {
					expect(this.sessionManager.getSession()).toBeNull();
				});
			});

			describe("when the session has already been assigned", () -> {
				beforeEach(() -> {
					this.sessionManager.openSession();
				});

				afterEach(() -> {
					this.sessionManager.closeSession();
				});

				it("returns the session", () -> {
					expect(this.sessionManager.getSession()).toBeNotNull();
					expect(this.sessionManager.getSession() instanceof Session).toBeTrue();
				});
			});
		});

		describe("#openSession", () -> {
			beforeEach(() -> {
				this.sessionManager.openSession();
			});

			it("opens the session", () -> {
				Mockito.verify(this.sessionFactory).openSession();
			});
		});

		describe("#closeSession", () -> {
			beforeEach(() -> {
				this.sessionManager.openSession();
				this.sessionManager.closeSession();
			});

			it("closes the session", () -> {
				Mockito.verify(this.session).close();
			});
		});

		describe("#openSessionWithTransaction", () -> {
			beforeEach(() -> {
				this.sessionManager.openSessionWithTransaction();
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
				this.sessionManager.openSessionWithTransaction();
				this.sessionManager.closeSessionWithTransaction();
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
