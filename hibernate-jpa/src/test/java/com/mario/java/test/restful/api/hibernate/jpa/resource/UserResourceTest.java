package com.mario.java.test.restful.api.hibernate.jpa.resource;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.runner.RunWith;
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

    private UserResource resource;

    @Mock
    private UserService service;

    private UserDomain validUser;
    // private UserDomain invalidUser;

    private Long id;
    private Response response;

    {
        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);
            this.validUser = UserFactory.createValidUser();
            // this.invalidUser = UserFactory.createInvalidUser();

            this.resource = new UserResource(this.service);
        });

        afterEach(() -> {
            this.validUser = null;
            // this.invalidUser = null;
            this.response = null;
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
    }
}
