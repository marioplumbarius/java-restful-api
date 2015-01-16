package com.mario.java.test.restful.api.hibernate.jpa.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.resource.UserResource;
import com.mario.java.restful.api.hibernate.jpa.resource.exception.HibernateValidationExceptionHandler;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService service;

    @Mock
    private HibernateValidationExceptionHandler validator = new HibernateValidationExceptionHandler();

    @Mock
    private Response response;

    @InjectMocks
    private UserResource resource = new UserResource();
    private User validUser = UserFactory.createValidUser();
    private User invalidUser = UserFactory.createInvalidUser();
    private Long validId = IdFactory.createValidId();
    private List<User> users;

    @Before
    public void setUp() throws Exception {
        this.setUpMocks();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindAll() {
        this.resource.findAll();
        Mockito.verify(this.service).findAll();
    }

    @Test
    public void testFind() {
        this.resource.find(this.validId);
        Mockito.verify(this.service).find(this.validId);
    }

    @Test
    public void testCreateWhenUserIsValid() {
        Mockito.when(this.validator.isValid(this.validUser)).thenReturn(true);

        Response res = this.resource.create(this.validUser);

        Mockito.verify(this.validator).isValid(this.validUser);
        Mockito.verify(this.service).persist(this.validUser);

        Assert.assertNotNull(res);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(),
                res.getStatus());

        // TODO
        // - assert response body (without errors)
    }

    @Test
    public void testCreateWhenUserIsInvalid() {
        Mockito.when(this.validator.isValid(this.invalidUser))
                .thenReturn(false);

        Response res = this.resource.create(this.invalidUser);

        Mockito.verify(this.validator).isValid(this.invalidUser);
        Mockito.verifyZeroInteractions(this.service);
        Mockito.verify(this.validator).getErrors();

        Assert.assertNotNull(res);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
                res.getStatus());

        // TODO
        // - assert response body (with errors)
    }

    @Test
    public void testUpdateWhenUserIsValid() {
        Mockito.when(this.validator.isValid(this.validUser)).thenReturn(true);

        Response res = this.resource.update(this.validId, this.validUser);

        Mockito.verify(this.validator).isValid(this.validUser);
        Mockito.verify(this.service).update(this.validId, this.validUser);

        Assert.assertNotNull(res);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        // TODO
        // - assert response body (without errors)
    }

    @Test
    public void testUpdateWhenUserIsInvalid() {
        Mockito.when(this.validator.isValid(this.invalidUser))
        .thenReturn(false);

        Response res = this.resource.update(this.validId, this.invalidUser);

        Mockito.verify(this.validator).isValid(this.invalidUser);
        Mockito.verifyZeroInteractions(this.service);
        Mockito.verify(this.validator).getErrors();

        Assert.assertNotNull(res);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
                res.getStatus());

        // TODO
        // - assert response body (with errors)
    }

    @Test
    public void testDelete() {
        Response res = this.resource.delete(this.validId);

        Mockito.verify(this.service).delete(this.validId);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
    }

    private void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.service.findAll()).thenReturn(this.users);
        Mockito.when(this.service.find(this.validId))
        .thenReturn(this.validUser);
    }
}
