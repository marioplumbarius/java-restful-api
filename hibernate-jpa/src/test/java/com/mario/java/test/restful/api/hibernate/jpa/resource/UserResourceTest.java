package com.mario.java.test.restful.api.hibernate.jpa.resource;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.resource.UserResource;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserResourceTest {

	@InjectMocks
	private UserResource resource;

	@Mock
	private UserService service;

	@Mock
	private UserDomain validUser = UserFactory.createValidUser();

	@Mock
	private UserDomain invalidUser = UserFactory.createInvalidUser();

	private List<UserDomain> users;
	private Long id;
	private Response response;
	private String name = "anything";

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

				it("returns an empty body", () -> {
					expect(this.response.getEntity()).toEqual(null);
				});

				it("returns a 404 http status", () -> {
					expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
				});

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

			describe("when the user provide the 'name' query param", () -> {

				beforeEach(() -> {
					Mockito.when(this.service.findAll(Matchers.any())).thenReturn(null);
					this.resource.findAll(this.name);
				});

				it("filters the search by name", () -> {
					Map<String, String> criterias = new HashMap<String, String>();
					criterias.put("name", this.name);
					Mockito.verify(this.service).findAll(criterias);
				});

				describe("when there aren't records matching the criteria", () -> {
					beforeEach(() -> {
						Mockito.when(this.service.findAll(Matchers.any())).thenReturn(null);
					});

					it("returns an empty list", () -> {
						List<UserDomain> returnedUsers = this.resource.findAll(this.name);
						expect(returnedUsers).toBeNull();
					});
				});

				describe("when there are records matching the criteria", () -> {
					beforeEach(() -> {
						this.users.add(this.validUser);
						Mockito.when(this.service.findAll(Matchers.any())).thenReturn(this.users);
					});

					it("returns the list of users found", () -> {
						List<UserDomain> returnedUsers = this.resource.findAll(this.name);
						expect(returnedUsers.get(0)).toEqual(this.users.get(0));
						expect(returnedUsers.size()).toEqual(this.users.size());
					});
				});
			});

			describe("when the user does not search by name", () -> {
				beforeEach(() -> {
					Mockito.when(this.service.findAll(null)).thenReturn(null);
					this.resource.findAll(null);
				});

				it("does not filter the search by name", () -> {
					Mockito.verify(this.service).findAll();
				});

				describe("when no users are found", () -> {
					beforeEach(() -> {
						Mockito.when(this.service.findAll()).thenReturn(null);
					});

					it("returns an empty list", () -> {
						List<UserDomain> returnedUsers = this.resource.findAll(null);
						expect(returnedUsers).toBeNull();
					});
				});

				describe("when there are users found", () -> {
					beforeEach(() -> {
						this.users.add(this.validUser);
						Mockito.when(this.service.findAll()).thenReturn(this.users);
					});

					it("returns the list of users found", () -> {
						List<UserDomain> returnedUsers = this.resource.findAll(null);
						expect(returnedUsers.get(0)).toEqual(this.users.get(0));
						expect(returnedUsers.size()).toEqual(this.users.size());
					});
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

				it("does not return the user URI", () -> {
					expect(this.response.getLocation()).toBeNull();
				});

				it("returns 422 http status code", () -> {
					expect(this.response.getStatus()).toEqual(Status.BAD_REQUEST.getStatusCode());
				});

				it("returns validation errors", () -> {
					Mockito.verify(this.invalidUser).getErrors();
					expect(this.response.getEntity()).toBeNotNull();
				});
			});
		});

		describe("#update", () -> {

			beforeEach(() -> {
				Mockito.when(this.validUser.isValid()).thenReturn(true);
				Mockito.when(this.invalidUser.isValid()).thenReturn(false);
			});

			it("verifies if the user is valid", () -> {
				this.resource.create(this.validUser);
				Mockito.verify(this.validUser).isValid();
			});

			describe("when the user is valid", () -> {
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

					it("returns 404 http status code", () -> {
						expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
					});

					it("returns an empty response body", () -> {
						expect(this.response.getEntity()).toBeNull();
					});
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

				it("does not return the user URI", () -> {
					expect(this.response.getLocation()).toBeNull();
				});

				it("returns 422 http status code", () -> {
					expect(this.response.getStatus()).toEqual(Status.BAD_REQUEST.getStatusCode());
				});

				it("returns validation errors", () -> {
					Mockito.verify(this.invalidUser).getErrors();
					expect(this.response.getEntity()).toBeNotNull();
				});
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

				it("returns 404 http status code", () -> {
					expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
				});

				it("returns an empty response body", () -> {
					expect(this.response.getEntity()).toBeNull();
				});
			});
		});
	}
}
