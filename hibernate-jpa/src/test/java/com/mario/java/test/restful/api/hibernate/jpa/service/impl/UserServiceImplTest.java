package com.mario.java.test.restful.api.hibernate.jpa.service.impl;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleStateException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;
import com.mario.java.restful.api.hibernate.jpa.service.impl.UserServiceImpl;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserDomain user;

	@Mock
	private PetDomain pet;

	@Mock
	private AbstractRepositoryHibernateImpl<UserDomain, Long> userCrud;

	@Mock
	private AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud;

	@InjectMocks
	private UserServiceImpl userServiceImpl = new UserServiceImpl(this.userCrud, this.petCrud);

	private Long id;
	private UserDomain returnedUser;
	private List<UserDomain> returnedUsers;
	private String key;
	private String value;
	private List<UserDomain> users;
	private List<PetDomain> pets;
	private Map<String, Object> criterias;

	private void behavesLikeDeletePets(){
		it("finds all user's pets", () -> {
			Mockito.verify(this.petCrud).findAll(this.criterias);
		});

		describe("when the user has pets", () -> {
			beforeEach(() -> {
				Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(this.pets);

				this.userServiceImpl.delete(this.id);
			});

			it("deletes all pets", () -> {
				Mockito.verify(this.petCrud).deleteAll(this.pets);
			});
		});

		describe("when the user doesnt has pets", () -> {
			beforeEach(() -> {
				this.userServiceImpl.delete(this.id);
			});

			it("does not delete the pets", () -> {
				Mockito.verify(this.petCrud, Mockito.never()).deleteAll(this.pets);
			});
		});
	}

	{

		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.users = new ArrayList<UserDomain>();
			this.pets = new ArrayList<PetDomain>();
			this.users.add(this.user);
			this.pets.add(this.pet);
			this.id = IdFactory.createValidId();
			this.key = "anyKey";
			this.value = "anyValue";
			this.criterias = new HashMap<String, Object>();
		});

		afterEach(()->{
			this.users = null;
			this.pets = null;
			this.id = null;
			this.returnedUser = null;
			this.returnedUsers = null;
			this.key = null;
			this.value = null;
			this.criterias = null;
		});

		describe("#persist", ()->{
			beforeEach(()->{
				this.userServiceImpl.persist(this.user);
			});

			it("persists the users", ()->{
				Mockito.verify(this.userCrud).persist(this.user);
			});
		});

		describe("#update", ()->{
			beforeEach(()->{
				this.userServiceImpl.update(this.id, this.user);
			});

			it("sets the id of the user", ()->{
				Mockito.verify(this.user).setId(this.id);
			});

			describe("when the user exists", () -> {
				beforeEach(()->{
					this.userServiceImpl.update(this.id, this.user);
				});

				it("updates the user", ()->{
					Mockito.verify(this.userCrud, Mockito.atLeast(2)).update(this.user);
				});
			});

			describe("when the user does not exists", () -> {
				beforeEach(() -> {
					Mockito.doThrow(new StaleStateException(null)).when(this.userCrud).update(this.user);
				});

				it("throws ObjectNotFoundException", ()->{
					try {
						this.userServiceImpl.update(this.id, this.user);
						expect("ObjectNotFoundException").toBeNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists");
					}
				});
			});
		});

		describe("#find", ()->{
			beforeEach(()->{
				Mockito.when(this.userCrud.find(this.id)).thenReturn(this.user);
			});

			it("searches for the user by id", ()->{
				this.userServiceImpl.find(this.id);
				Mockito.verify(this.userCrud).find(this.id);
			});

			describe("when the user exists", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.find(this.id)).thenReturn(this.user);
					this.returnedUser = this.userServiceImpl.find(this.id);
				});

				it("returned the user found", ()->{
					expect(this.returnedUser.getId()).toBeNotNull();
					expect(this.returnedUser.getId()).toEqual(this.user.getId());
				});
			});

			describe("when the user does not exist", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.find(this.id)).thenReturn(null);
					this.returnedUser = this.userServiceImpl.find(this.id);
				});

				it("returns null", ()->{
					expect(this.returnedUser).toBeNull();
				});
			});
		});

		describe("#findAll", ()->{

			describe("when the search is made without criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.userCrud.findAll()).thenReturn(null);
					this.userServiceImpl.findAll();
				});

				it("searches for all users", ()->{
					Mockito.verify(this.userCrud).findAll();
				});

				describe("when there are users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll()).thenReturn(this.users);
						this.returnedUsers = this.userServiceImpl.findAll();
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
						this.returnedUsers = this.userServiceImpl.findAll();
					});

					it("returns all users", ()->{
						expect(this.returnedUsers).toBeNull();
					});
				});
			});

			describe("when the search is made with criterias", ()->{
				beforeEach(()->{
					this.criterias.put(this.key, this.value);
					Mockito.when(this.userCrud.findAll(this.criterias)).thenReturn(null);
					this.userServiceImpl.findAll(this.criterias);
				});

				it("searches for all users filtered by some criterias", ()->{
					Mockito.verify(this.userCrud).findAll(this.criterias);
				});

				describe("when there are users on database", ()->{
					beforeEach(()->{
						Mockito.when(this.userCrud.findAll(this.criterias)).thenReturn(this.users);
						this.returnedUsers = this.userServiceImpl.findAll(this.criterias);
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
						this.returnedUsers = this.userServiceImpl.findAll(this.criterias);
					});

					it("returns all users", ()->{
						expect(this.returnedUsers).toBeNull();
					});
				});
			});
		});

		describe("#delete", ()->{
			beforeEach(()->{
				this.userServiceImpl.delete(this.id);
				this.criterias.put("user.id", this.id);
			});

			this.behavesLikeDeletePets();

			describe("when the user exists", () -> {
				it("deletes the user", ()->{
					Mockito.verify(this.userCrud).delete(Matchers.any(this.user.getClass()));
				});
			});

			describe("when the user does not exist", () -> {
				beforeEach(()->{
					Mockito.doThrow(new StaleStateException(null)).when(this.userCrud).delete(Matchers.any(this.user.getClass()));
				});

				it("throws ObjectNotFoundException", ()->{
					try {
						this.userServiceImpl.delete(this.id);
						expect("ObjectNotFoundException").toBeNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists");
					}
				});
			});
		});

		describe("#deleteAll", ()->{
			beforeEach(()->{
				this.userServiceImpl.deleteAll();
			});

			it("deletes all users", ()->{
				Mockito.verify(this.userCrud).deleteAll();
			});

			it("deletes all pets", () -> {
				Mockito.verify(this.petCrud).deleteAll();
			});
		});
	}
}
