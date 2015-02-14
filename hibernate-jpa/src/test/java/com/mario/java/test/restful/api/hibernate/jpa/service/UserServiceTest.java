package com.mario.java.test.restful.api.hibernate.jpa.service;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.restful.api.hibernate.jpa.service.UserService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserServiceTest {

	@Mock
	private UserDomain user;

	@Mock
	private CrudRepository<UserDomain, Long> userCrud;

	@InjectMocks
	private UserService userService;

	private Long id;
	private UserDomain returnedUser;
	private List<UserDomain> returnedUsers;
	private String key;
	private String value;
	private UserDomain validUser;
	private List<UserDomain> users;
	private Map<String, String> criterias;

	{

		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.validUser = UserFactory.createValidUser();
			this.users = new ArrayList<UserDomain>();
			this.users.add(this.validUser);
			this.id = IdFactory.createValidId();
			this.key = "anyKey";
			this.value = "anyValue";
			this.criterias = new HashMap<String, String>();
			this.criterias.put(this.key, this.value);
		});

		afterEach(()->{
			this.validUser = null;
			this.users = null;
			this.id = null;
			this.returnedUser = null;
			this.returnedUsers = null;
			this.key = null;
			this.value = null;
			this.criterias = null;
		});

		describe("#persist", ()->{
			beforeEach(()->{
				this.userService.persist(this.validUser);
			});

			it("persists the users", ()->{
				Mockito.verify(this.userCrud).persist(this.validUser);
			});
		});

		describe("#update", ()->{
			beforeEach(()->{
				this.userService.update(this.id, this.user);
			});

			it("sets the id of the user", ()->{
				Mockito.verify(this.user).setId(this.id);
			});

			it("updates the user", ()->{
				Mockito.verify(this.userCrud).update(this.id, this.user);
			});
		});

		describe("#find", ()->{
			beforeEach(()->{
				Mockito.when(this.userCrud.find(this.id)).thenReturn(this.validUser);
			});

			it("searches for the user by id", ()->{
				this.userService.find(this.id);
				Mockito.verify(this.userCrud).find(this.id);
			});

			describe("when the user exists", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.find(this.id)).thenReturn(this.validUser);
					this.returnedUser = this.userService.find(this.id);
				});

				it("returned the user found", ()->{
					expect(this.returnedUser.getId()).toBeNotNull();
					expect(this.returnedUser.getId()).toEqual(this.validUser.getId());
				});
			});

			describe("when the user does not exist", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.find(this.id)).thenReturn(null);
					this.returnedUser = this.userService.find(this.id);
				});

				it("return null", ()->{
					expect(this.returnedUser).toBeNull();
				});
			});
		});

		describe("#findBy", ()->{
			beforeEach(()->{
				Mockito.when(this.userCrud.findBy(this.key, this.value)).thenReturn(this.users);
				this.userService.findBy(this.key, this.value);
			});

			it("searches for the user by a specific key/filter", ()->{
				Mockito.verify(this.userCrud).findBy(this.key, this.value);
			});

			describe("when there are users matching the search", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.findBy(this.key, this.value)).thenReturn(this.users);
					this.returnedUsers = this.userService.findBy(this.key, this.value);
				});

				it("returnes the users found", ()->{
					expect(this.returnedUsers.size()).toEqual(this.users.size());
					expect(this.returnedUsers.get(0).getId()).toBeNotNull();
					expect(this.returnedUsers.get(0).getId()).toEqual(this.users.get(0).getId());
				});
			});

			describe("when there aren't users matching the search", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.findBy(this.key, this.value)).thenReturn(null);
					this.returnedUsers = this.userService.findBy(this.key, this.value);
				});

				it("return null", ()->{
					expect(this.returnedUsers).toBeNull();
				});
			});
		});

		describe("#findAll", ()->{

			describe("when the search is made without criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.findAll()).thenReturn(null);
					this.userService.findAll();
				});

				it("searches for all users", ()->{
					Mockito.verify(this.userCrud).findAll();
				});

				describe("when there are users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll()).thenReturn(this.users);
						this.returnedUsers = this.userService.findAll();
					});

					it("returns all users", ()->{
						expect(this.returnedUsers.size()).toEqual(this.users.size());
						expect(this.returnedUsers.get(0).getId()).toBeNotNull();
						expect(this.returnedUsers.get(0).getId()).toEqual(this.users.get(0).getId());
					});
				});

				describe("when there aren't users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll()).thenReturn(null);
						this.returnedUsers = this.userService.findAll();
					});

					it("returns all users", ()->{
						expect(this.returnedUsers).toBeNull();
					});
				});
			});

			describe("when the search is made with criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.findAll(this.criterias)).thenReturn(null);
					this.userService.findAll(this.criterias);
				});

				it("searches for all users filtered by some criterias", ()->{
					Mockito.verify(this.userCrud).findAll(this.criterias);
				});

				describe("when there are users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll(this.criterias)).thenReturn(this.users);
						this.returnedUsers = this.userService.findAll(this.criterias);
					});

					it("returns all users", ()->{
						expect(this.returnedUsers.size()).toEqual(this.users.size());
						expect(this.returnedUsers.get(0).getId()).toBeNotNull();
						expect(this.returnedUsers.get(0).getId()).toEqual(this.users.get(0).getId());
					});
				});

				describe("when there aren't users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll(this.criterias)).thenReturn(null);
						this.returnedUsers = this.userService.findAll(this.criterias);
					});

					it("returns all users", ()->{
						expect(this.returnedUsers).toBeNull();
					});
				});
			});
		});

		describe("#delete", ()->{
			beforeEach(()->{
				Mockito.when(this.userCrud.find(this.id)).thenReturn(this.validUser);
				this.userService.delete(this.id);
			});

			it("finds the user by id", ()->{
				Mockito.verify(this.userCrud).find(this.id);
			});

			it("deletes the user", ()->{
				Mockito.verify(this.userCrud).delete(this.id, this.validUser);
			});
		});

		describe("#deleteAll", ()->{
			beforeEach(()->{
				this.userService.deleteAll();
			});

			it("deletes all users from database", ()->{
				Mockito.verify(this.userCrud).deleteAll();
			});
		});
	}
}
