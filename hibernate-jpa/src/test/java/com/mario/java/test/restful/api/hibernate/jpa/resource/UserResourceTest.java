package com.mario.java.test.restful.api.hibernate.jpa.resource;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.resource.impl.UserResourceRestEasyImpl;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.impl.UserServiceImpl;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserResourceTest {

	@InjectMocks
	private UserResourceRestEasyImpl resource;

	@Mock
	private UserServiceImpl service;

	@Mock
	private UserDomain validUser = UserFactory.createValidUser();

	@Mock
	private UserDomain invalidUser = UserFactory.createInvalidUser();

	private List<UserDomain> users;
	private Long id;
	private Response response;

	private void behavesLikeUserNotFound(){
		it("returns an empty body", () -> {
			expect(this.response.getEntity()).toEqual(null);
		});

		it("returns a 404 http status", () -> {
			expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
		});
	}

	private void behavesLikeUserInvalid(){
		it("does not return the user URI", () -> {
			expect(this.response.getLocation()).toBeNull();
		});

		it("returns 422 http status code", () -> {
			expect(this.response.getStatus()).toEqual(HttpStatus.UNPROCESSABLE_ENTITY.getStatusCode());
		});

		it("returns validation errors", () -> {
			Mockito.verify(this.invalidUser).getErrors();
			expect(this.response.getEntity()).toBeNotNull();
		});
	}

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			this.users = new ArrayList<UserDomain>();
		});

		afterEach(() -> {
			this.response = null;
			this.users = null;
		});

		describe("#find", () -> {

			beforeEach(() -> {
				this.id = IdFactory.createValidId();
				Mockito.when(this.service.find(this.id)).thenReturn(null);
			});

			it("searches for users by id", () -> {
				this.resource.find(this.id);
				Mockito.verify(this.service).find(this.id);
			});

			describe("when the user is not found", () -> {

				beforeEach(() -> {
					Mockito.when(this.service.find(this.id)).thenReturn(null);
					this.response = this.resource.find(this.id);
				});

				this.behavesLikeUserNotFound();

			});

			describe("when the user is found", () -> {
				beforeEach(() -> {
					Mockito.when(this.service.find(this.id)).thenReturn(this.validUser);
					this.response = this.resource.find(this.id);
				});

				it("returns the user in the response body", () -> {
					expect(this.response.getEntity()).toEqual(this.validUser);
				});

				it("returns a 200 http status", () -> {
					expect(this.response.getStatus()).toEqual(Status.OK.getStatusCode());
				});
			});

		});

		describe("#findAll", () -> {
			beforeEach(() -> {
				Mockito.when(this.service.findAll()).thenReturn(null);
				this.resource.findAll();
			});

			it("searches for all users", () -> {
				Mockito.verify(this.service).findAll();
			});

			describe("when no users are found", () -> {
				beforeEach(() -> {
					Mockito.when(this.service.findAll()).thenReturn(null);
				});

				it("returns an empty list", () -> {
					List<UserDomain> returnedUsers = this.resource.findAll();
					expect(returnedUsers).toBeNull();
				});
			});

			describe("when there are users found", () -> {
				beforeEach(() -> {
					this.users.add(this.validUser);
					Mockito.when(this.service.findAll()).thenReturn(this.users);
				});

				it("returns the list of users found", () -> {
					List<UserDomain> returnedUsers = this.resource.findAll();
					expect(returnedUsers.get(0)).toEqual(this.users.get(0));
					expect(returnedUsers.size()).toEqual(this.users.size());
				});
			});
		});

		describe("#create", () -> {

			beforeEach(() -> {
				Mockito.when(this.validUser.isValid()).thenReturn(true);
				Mockito.when(this.invalidUser.isValid()).thenReturn(false);
			});

			it("verifies if the user is valid", () -> {
				this.resource.create(this.validUser);
				Mockito.verify(this.validUser).isValid();
			});

			describe("when the user is valid", () -> {
				beforeEach(() -> {
					this.response = this.resource.create(this.validUser);
				});

				it("persists the user", () -> {
					Mockito.verify(this.service).persist(this.validUser);
				});

				it("returns 201 http status code", () -> {
					expect(this.response.getStatus()).toEqual(Status.CREATED.getStatusCode());
				});

				it("returns the user URI", () -> {
					expect(this.response.getLocation().toString()).toEqual(UserFactory.URI.replace("{id}", this.validUser.getId().toString()));
				});
			});

			describe("when the user is invalid", () -> {
				beforeEach(() -> {
					Mockito.when(this.validUser.isValid()).thenReturn(false);
					this.response = this.resource.create(this.invalidUser);
				});

				it("does not persist the user", () -> {
					Mockito.verify(this.service, Mockito.never()).persist(this.validUser);
				});

				this.behavesLikeUserInvalid();
			});
		});

		describe("#update", () -> {

			beforeEach(() -> {
				this.resource.create(this.validUser);
			});

			it("verifies if the user is valid", () -> {
				Mockito.verify(this.validUser).isValid();
			});

			describe("when the user is valid", () -> {
				beforeEach(() -> {
					Mockito.when(this.validUser.isValid()).thenReturn(true);
				});

				describe("when the user exists", () -> {
					beforeEach(() -> {
						this.response = this.resource.update(this.id, this.validUser);
					});

					it("updates the user", () -> {
						Mockito.verify(this.service).update(this.id, this.validUser);
					});

					it("returns 204 http status code", () -> {
						expect(this.response.getStatus()).toEqual(Status.NO_CONTENT.getStatusCode());
					});

					it("returns an empty response body", () -> {
						expect(this.response.getEntity()).toBeNull();
					});
				});

				describe("when the user does not exist", () -> {
					beforeEach(() -> {
						Mockito.doThrow(new ObjectNotFoundException(this.id, this.validUser.getClass().getName())).when(this.service).update(this.id, this.validUser);
						this.response = this.resource.update(this.id, this.validUser);
					});

					this.behavesLikeUserNotFound();
				});
			});

			describe("when the user is invalid", () -> {
				beforeEach(() -> {
					Mockito.when(this.validUser.isValid()).thenReturn(false);
					this.response = this.resource.create(this.invalidUser);
				});

				it("does not persist the user", () -> {
					Mockito.verify(this.service, Mockito.never()).persist(this.validUser);
				});

				this.behavesLikeUserInvalid();
			});
		});

		describe("#delete", () -> {

			describe("when the user exists", () -> {
				beforeEach(() -> {
					this.response = this.resource.delete(this.id);
				});

				it("deletes the user", () -> {
					Mockito.verify(this.service).delete(this.id);
				});

				it("returns 204 http status code", () -> {
					expect(this.response.getStatus()).toEqual(Status.NO_CONTENT.getStatusCode());
				});

				it("returns an empty response body", () -> {
					expect(this.response.getEntity()).toBeNull();
				});
			});

			describe("when the user does not exist", () -> {
				beforeEach(() -> {
					Mockito.doThrow(new ObjectNotFoundException(this.id, null)).when(this.service).delete(this.id);
					this.response = this.resource.delete(this.id);
				});

				this.behavesLikeUserNotFound();
			});
		});
	}
}
