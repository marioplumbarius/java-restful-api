package com.mario.java.test.restful.api.hibernate.repository;

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
import org.hibernate.StaleStateException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class CrudRepositoryTest {

	@Mock
	private SessionManager sessionManager;

	@InjectMocks
	private CrudRepository<UserDomain, Long> crudRepository = new CrudRepository<UserDomain, Long>(this.sessionManager, "UserDomain", UserDomain.class);

	@Mock
	private Session session;

	@Mock
	private Query query;

	@Mock
	private Criteria criteria;

	private Map<String, String> criterias;

	@Mock
	private UserDomain entity;

	private Long id;
	private String key, value;

	private List<UserDomain> entities;
	private List<UserDomain> listResponse;
	private UserDomain singleResponse;

	{
		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.entities = new ArrayList<UserDomain>();
			this.entities.add(this.entity);

			this.criterias = new HashMap<String, String>();
			this.criterias.put("key", "value");

			this.id = IdFactory.createValidId();
			this.key = "anyKey";
			this.value = "anyValue";

			Mockito.when(this.sessionManager.getSession()).thenReturn(this.session);
			Mockito.when(this.session.createQuery("from UserDomain")).thenReturn(this.query);
		});

		afterEach(()->{
			this.entities = null;
			this.criterias = null;
			this.id = null;
			this.key = null;
			this.value = null;
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

		describe("#findBy", () -> {
			beforeEach(() -> {
				Mockito.when(this.session.createCriteria(UserDomain.class)).thenReturn(this.criteria);
				Mockito.when(this.criteria.add(Matchers.any())).thenReturn(this.criteria);
				Mockito.when(this.criteria.list()).thenReturn(null);
				this.crudRepository.findBy(this.key, this.value);
			});

			it("opens a session", () -> {
				Mockito.verify(this.sessionManager).openSession();
			});

			it("makes a find query with a single criteria", () -> {
				Mockito.verify(this.session).createCriteria(UserDomain.class);
				Mockito.verify(this.criteria).add(Matchers.any());
				Mockito.verify(this.criteria).list();
			});

			it("closes the session", () -> {
				Mockito.verify(this.sessionManager).closeSession();
			});

			describe("when there're entities matching the search", () -> {
				beforeEach(() -> {
					Mockito.when(this.criteria.list()).thenReturn(this.entities);
					this.listResponse = this.crudRepository.findBy(this.key, this.value);
				});

				it("returns the found entities", () -> {
					expect(this.listResponse).toEqual(this.entities);
				});
			});

			describe("when there aren't entities matching the search", () -> {
				beforeEach(() -> {
					Mockito.when(this.criteria.list()).thenReturn(null);
					this.listResponse = this.crudRepository.findBy(this.key, this.value);
				});

				it("returns null", () -> {
					expect(this.listResponse).toBeNull();
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
				this.crudRepository.update(this.id, this.entity);
			});

			it("open a transactional session", () -> {
				Mockito.verify(this.sessionManager).openSessionWithTransaction();
			});

			describe("when the entity exists on database", () -> {
				it("updates the entity", () -> {
					Mockito.verify(this.session).update(this.entity);
				});

				it("closes the transactional session", () -> {
					Mockito.verify(this.sessionManager).closeSessionWithTransaction();
				});
			});

			describe("when the entity does not exist on database", () -> {
				beforeEach(() -> {
					Mockito.doThrow(new StaleStateException(null)).when(this.session).update(this.entity);
				});
				it("throws an 'object not found' exception", () -> {
					try {
						this.crudRepository.update(this.id, this.entity);
						expect(true).toBeFalse();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});
			});
		});

		describe("#delete", () -> {
			beforeEach(() -> {
				this.crudRepository.delete(this.id, this.entity);
			});

			it("open a transactional session", () -> {
				Mockito.verify(this.sessionManager).openSessionWithTransaction();
			});

			describe("when the entity exists on database", () -> {
				it("deletes the entity", () -> {
					Mockito.verify(this.session).delete(this.entity);
				});

				it("closes the transactional session", () -> {
					Mockito.verify(this.sessionManager).closeSessionWithTransaction();
				});
			});

			describe("when the entity does not exist on database", () -> {
				beforeEach(() -> {
					Mockito.doThrow(new IllegalArgumentException()).when(this.session).delete(this.entity);
				});

				it("throws an 'object not found' exception", () -> {
					try {
						this.crudRepository.delete(this.id, this.entity);
						expect(true).toBeFalse();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});
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
				it("deletes all entities", () -> {
					for(UserDomain entity : this.entities){
						Mockito.verify(this.session).delete(entity);
					}
				});
			});

			describe("when there aren't entities found", () -> {
				beforeEach(() -> {
					Mockito.when(this.query.list()).thenReturn(null);
				});

				it("throws an 'object not found' exception", () -> {
					try {
						this.crudRepository.deleteAll();
						expect(true).toBeFalse();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists:");
					}
				});

			});
		});
	}
}