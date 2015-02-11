//package com.mario.java.test.restful.api.hibernate.jpa.service;
//
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.mario.java.restful.api.hibernate.jpa.dao.implementation.UserDaoImplementation;
//import com.mario.java.restful.api.hibernate.jpa.domain.User;
//import com.mario.java.restful.api.hibernate.jpa.service.UserService;
//import com.mscharhag.oleaster.runner.OleasterRunner;
//
//@RunWith(OleasterRunner.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserService service;
//
//    @InjectMocks
//    private UserDaoImplementation userDao;
//
//    private User user;
//    {
//        beforeEach(() -> {
//            MockitoAnnotations.initMocks(this);
//
//            this.service = new UserService();
//        });
//
//        afterEach(() -> {
//            this.user = null;
//        });
//
//        describe("#persist", () -> {
//            beforeEach(() -> {
////                this.user = UserFactory.createValidUser();
//                this.service.persist(this.user);
//            });
//
//            it("opens a new session with transaction", () -> {
////                verify(this.userDao.sessionManager).openSessionWithTransaction();
//            });
//
//            it("closes the session", () -> {
////                verify(this.userDao.sessionManager).closeSessionWithTransaction();
//            });
//
//        });
//    }
//}
