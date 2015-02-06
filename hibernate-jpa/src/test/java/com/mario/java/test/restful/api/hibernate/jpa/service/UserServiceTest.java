package com.mario.java.test.restful.api.hibernate.jpa.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.dao.implementation.UserDaoImplementation;
import com.mario.java.restful.api.hibernate.jpa.domain.User;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.*;

@RunWith(OleasterRunner.class)
public class UserServiceTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserDaoImplementation userDao;

    private User user;
    private Long id;

    {
        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.service = new UserService();
        });

        afterEach(() -> {
            this.user = null;
            this.id = null;
        });

        describe("#persist", () -> {
            beforeEach(() -> {
                this.user = UserFactory.createValidUser();
                this.service.persist(this.user);
            });

            it("opens a new session with transaction", () -> {
                verify(this.userDao.sessionManager).openSessionWithTransaction();
            });

            it("closes the session", () -> {
                verify(this.userDao.sessionManager).closeSessionWithTransaction();
            });

        });
    }
}
