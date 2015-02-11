//package com.mario.java.test.restful.api.hibernate.jpa.resource;
//
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.core.Response;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import com.mario.java.restful.api.hibernate.jpa.domain.User;
//import com.mario.java.restful.api.hibernate.jpa.resource.UserResource;
//import com.mario.java.restful.api.hibernate.jpa.resource.exception.HibernateValidationExceptionHandler;
//import com.mario.java.restful.api.hibernate.jpa.service.UserService;
//import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
//import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
//import com.mscharhag.oleaster.runner.OleasterRunner;
//
//import static com.mscharhag.oleaster.matcher.Matchers.*;
//
//@RunWith(OleasterRunner.class)
//public class UserResourceJasmineTest {
//
//    @Mock
//    private UserService service;
//
//    @Mock
//    private HibernateValidationExceptionHandler validator = new HibernateValidationExceptionHandler();
//
//    @InjectMocks
//    private UserResource resource = new UserResource();
//
//    private User validUser;
//    private User invalidUser;
//    private List<User> users;
//    private User user;
//    private Long id;
//    private Response response;
//
//    {
//        beforeEach(() -> {
//            this.validUser = UserFactory.createValidUser();
//            this.invalidUser = UserFactory.createInvalidUser();
//            this.users = new ArrayList<User>();
//
//            MockitoAnnotations.initMocks(this);
//        });
//
//        afterEach(() -> {
//            this.validUser = null;
//            this.invalidUser = null;
//            this.users = null;
//            this.user = null;
//            this.response = null;
//            this.id = null;
//        });
//
//        describe("#findAll", () -> {
//
//            beforeEach(() -> {
//                Mockito.when(this.service.findAll()).thenReturn(this.users);
//            });
//
//            it("searches for all users", () -> {
//                this.resource.findAll();
//                Mockito.verify(this.service).findAll();
//            });
//
//            describe("when there are users on database", () -> {
//                beforeEach(() -> {
//                    this.users.add(this.validUser);
//                    Mockito.when(this.service.findAll()).thenReturn(this.users);
//                });
//
//                it("returns the found list of users", () -> {
//                    List<User> returnedUsers = this.resource.findAll();
//                    expect(returnedUsers).toEqual(this.users);
//                    expect(returnedUsers.size()).equals(this.users.size());
//                });
//            });
//
//            describe("when there are no users on database", () -> {
//
//                it("returns an empty list", () -> {
//                    List<User> returnedUsers = this.resource.findAll();
//                    expect(returnedUsers).toEqual(this.users);
//                    expect(returnedUsers.size()).equals(this.users.size());
//                });
//            });
//        });
//
//        describe("#find", () -> {
//            beforeEach(() -> {
//                this.id = IdFactory.createValidId();
//                Mockito.when(this.service.find(this.id)).thenReturn(this.validUser);
//            });
//
//            it("searches for users by id", () -> {
//                this.resource.find(this.id);
//                Mockito.verify(this.service).find(this.id);
//            });
//
//            describe("when the user is found", () -> {
//                beforeEach(() -> {
//                    Mockito.when(this.service.find(this.id)).thenReturn(this.validUser);
//                });
//
//                it("returns the found user", () -> {
//                    User returnedUser = this.resource.find(this.id);
//                    expect(returnedUser).toEqual(this.validUser);
//                });
//            });
//
//            describe("when the user is not found", () -> {
//
//                beforeEach(() -> {
//                    Mockito.when(this.service.find(this.id)).thenReturn(null);
//                });
//
//                it("returns null", () -> {
//                    User returnedUser = this.resource.find(this.id);
//                    expect(returnedUser).toBeNull();
//                });
//            });
//        });
//
//        describe("#create", () -> {
//
//            beforeEach(() -> {
//                this.user = this.validUser;
//                Mockito.when(this.validator.isValid(this.user)).thenReturn(true);
//            });
//
//            it("verifies if the user is valid", () -> {
//                this.resource.create(this.user);
//                Mockito.verify(this.validator).isValid(this.user);
//            });
//
//            describe("when the user is valid", () -> {
//
//                it("persists the user", () -> {
//                    this.resource.create(this.user);
//                    Mockito.verify(this.service).persist(this.user);
//                });
//
//                it("returns an http response", () -> {
//                    Response res = this.resource.create(this.user);
//                    expect(res).toBeNotNull();
//                });
//
//                it("returns 201 http status code", () -> {
//                    Response res = this.resource.create(this.user);
//                    expect(res.getStatus()).toEqual(Response.Status.CREATED.getStatusCode());
//                });
//
//                it("does not catch validation errors", () -> {
//                    Mockito.verify(this.validator, Mockito.never()).getErrors();
//                });
//
//                it("returns the user's URI", () -> {
//                    Response res = this.resource.create(this.user);
//                    expect(res.getLocation().toString()).toEqual(UserFactory.URI);
//                });
//
//                it("does not return errors", () -> {
//                    Response res = this.resource.create(this.user);
//                    expect(res.getEntity()).toBeNull();
//                });
//            });
//
//            describe("when the user is invalid", () -> {
//                beforeEach(() -> {
//                    this.user = this.invalidUser;
//                    Mockito.when(this.validator.isValid(this.user)).thenReturn(false);
//                    this.response = this.resource.create(this.user);
//                });
//
//                it("does not persist the user", () -> {
//                    Mockito.verify(this.service, Mockito.never()).persist(this.user);
//                });
//
//                it("returns an http response", () -> {
//                    expect(this.response).toBeNotNull();
//                });
//
//                it("returns 400 http status code", () -> {
//                    expect(this.response.getStatus()).toEqual(Response.Status.BAD_REQUEST.getStatusCode());
//                });
//
//                it("catches validation errors", () -> {
//                    Mockito.verify(this.validator).getErrors();
//                });
//
//                it("does not return the user's URI", () -> {
//                    expect(this.response.getLocation()).toBeNull();
//                });
//
//                it("returns the validation errors", () -> {
//                    expect(this.response.getEntity()).toBeNotNull();
//                });
//            });
//        });
//
//        describe("#update", () -> {
//            beforeEach(() -> {
//                this.user = this.validUser;
//                this.id = IdFactory.createValidId();
//                Mockito.when(this.validator.isValid(this.user)).thenReturn(true);
//            });
//
//            it("verifies if the user is valid", () -> {
//                this.resource.create(this.user);
//                Mockito.verify(this.validator).isValid(this.user);
//            });
//
//            describe("when the user is valid", () -> {
//
//                beforeEach(() -> {
//                    this.response = this.resource.update(this.id, this.user);
//                });
//
//                it("updates the user", () -> {
//                    Mockito.verify(this.service).update(this.id, this.user);
//                });
//
//                it("returns an http response", () -> {
//                    expect(this.response).toBeNotNull();
//                });
//
//                it("returns 200 http status code", () -> {
//                    expect(this.response.getStatus()).toEqual(Response.Status.OK.getStatusCode());
//                });
//
//                it("does not catch validation errors", () -> {
//                    Mockito.verify(this.validator, Mockito.never()).getErrors();
//                });
//
//                it("does not return errors", () -> {
//                    expect(this.response.getEntity()).toBeNull();
//                });
//            });
//
//            describe("when the user is invalid", () -> {
//                beforeEach(() -> {
//                    this.user = this.invalidUser;
//                    Mockito.when(this.validator.isValid(this.user)).thenReturn(false);
//                    this.response = this.resource.update(this.id, this.user);
//                });
//
//                it("does not persist the user", () -> {
//                    Mockito.verify(this.service, Mockito.never()).update(this.id, this.user);
//                });
//
//                it("returns an http response", () -> {
//                    expect(this.response).toBeNotNull();
//                });
//
//                it("returns 400 http status code", () -> {
//                    expect(this.response.getStatus()).toEqual(Response.Status.BAD_REQUEST.getStatusCode());
//                });
//
//                it("catches validation errors", () -> {
//                    Mockito.verify(this.validator).getErrors();
//                });
//
//                it("returns the validation errors", () -> {
//                    expect(this.response.getEntity()).toBeNotNull();
//                });
//            });
//        });
//
//        describe("#delete", () -> {
//            beforeEach(() -> {
//                this.id = IdFactory.createValidId();
//                this.user = UserFactory.createValidUser();
//                this.response = this.resource.delete(this.id);
//            });
//
//            it("deletes the user from database", () -> {
//                Mockito.verify(this.service).delete(this.id);
//            });
//
//            it("returns an htto response", () -> {
//                expect(this.response).toBeNotNull();
//            });
//
//            it("returns 200 http status code", () -> {
//                expect(this.response.getStatus()).toEqual(Response.Status.OK.getStatusCode());
//            });
//        });
//
//    }
//}
