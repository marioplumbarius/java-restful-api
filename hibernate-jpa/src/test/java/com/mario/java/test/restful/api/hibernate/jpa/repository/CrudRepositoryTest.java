package com.mario.java.test.restful.api.hibernate.jpa.repository;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.manager.SessionManagerSingleton;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class CrudRepositoryTest {

	@Mock
	private SessionManagerSingleton sessionManager;

	@InjectMocks
	private CrudRepository<UserDomain, Long> crudRepository = new CrudRepository<UserDomain, Long>(this.sessionManager, "UserDomain", UserDomain.class);

	@Mock
	private Session session;

	@Mock
	private Query query;

	@Mock
	private Criteria criteria;

	private Map<String, Object> criterias;

	@Mock
	private UserDomain entity;

	private Long id;

	private List<UserDomain> entities;
	private List<UserDomain> listResponse;
	private UserDomain singleResponse;

	{
		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.entities = new ArrayList<UserDomain>();
			this.entities.add(this.entity);

			this.criterias = new HashMap<String, Object>();
			this.criterias.put("key", "value");

			this.id = IdFactory.createValidId();

			Mockito.when(this.sessionManager.getSession()).thenReturn(this.session);
			Mockito.when(this.session.createQuery("from UserDomain")).thenReturn(this.query);
			Mockito.when(this.session.createCriteria(UserDomain.class)).thenReturn(this.criteria);
			Mockito.when(this.criteria.add(Matchers.any(Criterion.class))).thenReturn(this.criteria);
		});

		afterEach(()->{
			this.entities = null;
			this.criterias = null;
			this.id = null;
			this.listResponse = null;
			this.singleResponse = null;
		});

		describe("#findAll", ()->{
			describe("with criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.session.createCriteria(UserDomain.class)).thenReturn(this.criteria);
					Mockito.when(this.criteria.add(Matchers.any())).thenReturn(this.criteria);
					Mockito.when(this.criteria.list()).thenReturn(this.entities);
					this.crudRepository.findAll(this.criterias);
				});

				it("opens a session", ()->{
					Mockito.verify(this.sessionManager).openSession();
				});

				it("makes a findAll query with criterias", ()->{
					Mockito.verify(this.sessionManager).getSession();
					Mockito.verify(this.session).createCriteria(UserDomain.class);
					Mockito.verify(this.criteria).add(Matchers.any());
					Mockito.verify(this.criteria).list();
				});

				it("closes the session", ()->{
					Mockito.verify(this.sessionManager).closeSession();
				});

				describe("when there're entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.criteria.list()).thenReturn(this.entities);
						this.listResponse = this.crudRepository.findAll(this.criterias);
					});

					it("returns the entities found", ()->{
						expect(this.listResponse).toEqual(this.entities);
					});
				});

				describe("when there aren't entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.criteria.list()).thenReturn(null);
						this.listResponse = this.crudRepository.findAll(this.criterias);
					});

					it("returns null", ()->{
						expect(this.listResponse).toBeNull();
					});
				});
			});

			describe("without criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.query.list()).thenReturn(null);
					this.crudRepository.findAll();
				});

				it("opens a session", ()->{
					Mockito.verify(this.sessionManager).openSession();
				});

				it("makes a findAll query", ()->{
					Mockito.verify(this.sessionManager).getSession();
					Mockito.verify(this.session).createQuery("from UserDomain");
					Mockito.verify(this.query).list();
				});

				it("closes the session", ()->{
					Mockito.verify(this.sessionManager).closeSession();
				});

				describe("when there're entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.query.list()).thenReturn(this.entities);
						this.listResponse = this.crudRepository.findAll();
					});

					it("returns the entities found", ()->{
						expect(this.listResponse).toEqual(this.entities);
					});
				});

				describe("when there aren't entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.query.list()).thenReturn(null);
						this.listResponse = this.crudRepository.findAll();
					});

					it("returns null", ()->{
						expect(this.listResponse).toBeNull();
					});
				});
			});
		});

		describe("#find", ()->{
			beforeEach(()->{
				Mockito.when(this.session.get(UserDomain.class, this.id)).thenReturn(this.entity);
				this.crudRepository.find(this.id);
			});

			it("opens a session", ()->{
				Mockito.verify(this.sessionManager).openSession();
			});

			it("makes a find query by id", ()->{
				Mockito.verify(this.sessionManager).getSession();
				Mockito.verify(this.session).get(UserDomain.class, this.id);
			});

			it("closes the session", ()->{
				Mockito.verify(this.sessionManager).closeSession();
			});

			describe("when the entity is found", ()->{
				beforeEach(()->{
					Mockito.when(this.session.get(UserDomain.class, this.id)).thenReturn(this.entity);
					this.singleResponse = this.crudRepository.find(this.id);
				});

				it("returns the entity found", ()->{
					expect(this.singleResponse).toEqual(this.entity);
				});
			});

			describe("when the entity is not found", ()->{
				beforeEach(()->{
					Mockito.when(this.session.get(UserDomain.class, this.id)).thenReturn(null);
					this.singleResponse = this.crudRepository.find(this.id);
				});

				it("returns null", ()->{
					expect(this.singleResponse).toBeNull();
				});
			});
		});


		describe("#persist", () -> {
			beforeEach(() -> {
				this.crudRepository.persist(this.entity);
			});

			it("open a transactional session", () -> {
				Mockito.verify(this.sessionManager).openSessionWithTransaction();
			});

			it("persists the entity", () -> {
				Mockito.verify(this.session).save(this.entity);
			});

			it("closes the transactional section", () -> {
				Mockito.verify(this.sessionManager).closeSessionWithTransaction();
			});
		});

		describe("#update", () -> {
			beforeEach(() -> {
				this.crudRepository.update(this.entity);
			});

			it("open a transactional session", () -> {
				Mockito.verify(this.sessionManager).openSessionWithTransaction();
			});

			it("updates the entity", () -> {
				Mockito.verify(this.session).update(this.entity);
			});

			it("closes the transactional session", () -> {
				Mockito.verify(this.sessionManager).closeSessionWithTransaction();
			});
		});

		describe("#delete", () -> {
			beforeEach(() -> {
				this.crudRepository.delete(this.entity);
			});

			it("open a transactional session", () -> {
				Mockito.verify(this.sessionManager).openSessionWithTransaction();
			});

			it("deletes the entity", () -> {
				Mockito.verify(this.session).delete(this.entity);
			});

			it("closes the transactional session", () -> {
				Mockito.verify(this.sessionManager).closeSessionWithTransaction();
			});
		});

		describe("#deleteAll", () -> {
			beforeEach(() -> {
				Mockito.when(this.query.list()).thenReturn(this.entities);

				this.crudRepository.deleteAll();
			});

			it("finds all entities", () -> {
				Mockito.verify(this.session).createQuery("from UserDomain");
				Mockito.verify(this.query).list();
			});

			describe("when there're entities found", () -> {
				it("opens the session with transaction", () -> {
					Mockito.verify(this.sessionManager).openSessionWithTransaction();
				});

				it("deletes all entities", () -> {
					for(UserDomain entity : this.entities){
						Mockito.verify(this.session).delete(entity);
					}
				});

				it("closes session with transaction", () -> {
					Mockito.verify(this.sessionManager).closeSessionWithTransaction();
				});
			});

			describe("when there aren't entities found", () -> {
				beforeEach(() -> {
					Mockito.when(this.query.list()).thenReturn(null);
				});

				it("throws an ObjectNotFoundException", () -> {
					try {
						this.crudRepository.deleteAll();
						expect("ObjectNotFoundException").toBeNotNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});

			});
		});

		describe("#deleteAll(Map<String, Object>)", () -> {
			beforeEach(() -> {
				Mockito.when(this.criteria.list()).thenReturn(this.entities);

				this.crudRepository.deleteAll(this.criterias);
			});

			it("finds all entities", () -> {
				Mockito.verify(this.session).createCriteria(UserDomain.class);
				Mockito.verify(this.criteria).add(Matchers.any(Criterion.class));
				Mockito.verify(this.criteria).list();
			});

			describe("when there're entities found", () -> {
				it("opens the session with transaction", () -> {
					Mockito.verify(this.sessionManager).openSessionWithTransaction();
				});

				it("deletes all entities", () -> {
					for(UserDomain entity : this.entities){
						Mockito.verify(this.session).delete(entity);
					}
				});

				it("closes session with transaction", () -> {
					Mockito.verify(this.sessionManager).closeSessionWithTransaction();
				});
			});

			describe("when there aren't entities found", () -> {
				beforeEach(() -> {
					Mockito.when(this.criteria.list()).thenReturn(null);
				});

				it("throws an ObjectNotFoundException", () -> {
					try {
						this.crudRepository.deleteAll(this.criterias);
						expect("ObjectNotFoundException").toBeNotNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});
			});
		});

		describe("#deleteAll(List<T>)", () -> {
			beforeEach(() -> {
				this.crudRepository.deleteAll(this.entities);
			});

			describe("when the entities is not null", () -> {
				it("opens the session with transaction", () -> {
					Mockito.verify(this.sessionManager).openSessionWithTransaction();
				});

				it("deletes all entities", () -> {
					for(UserDomain entity : this.entities){
						Mockito.verify(this.session).delete(entity);
					}
				});

				it("closes session with transaction", () -> {
					Mockito.verify(this.sessionManager).closeSessionWithTransaction();
				});
			});

			describe("when the entities is null", () -> {
				beforeEach(() -> {
					this.entities = null;
				});

				it("throws an ObjectNotFoundException", () -> {
					try {
						this.crudRepository.deleteAll(this.criterias);
						expect("ObjectNotFoundException").toBeNotNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});
			});
		});
	}
}