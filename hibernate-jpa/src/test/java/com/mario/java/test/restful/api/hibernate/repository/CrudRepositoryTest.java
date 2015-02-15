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
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.restful.api.hibernate.jpa.util.SessionManager;
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
	private UserDomain userDomain;

	private List<UserDomain> entities;
	private List<UserDomain> response;

	{
		beforeEach(()->{
			MockitoAnnotations.initMocks(this);

			this.entities = new ArrayList<UserDomain>();
			this.entities.add(this.userDomain);

			this.criterias = new HashMap<String, String>();
			this.criterias.put("key", "value");
		});

		afterEach(()->{
			this.entities = null;
			this.criterias = null;
		});

		describe("#findAll", ()->{
			describe("with criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.sessionManager.getSession()).thenReturn(this.session);
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
						this.response = this.crudRepository.findAll(this.criterias);
					});

					it("returns the entities found", ()->{
						expect(this.response).toEqual(this.entities);
					});
				});

				describe("when there aren't entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.criteria.list()).thenReturn(null);
						this.response = this.crudRepository.findAll(this.criterias);
					});

					it("returns null", ()->{
						expect(this.response).toBeNull();
					});
				});
			});

			describe("without criterias", ()->{
				beforeEach(()->{
					Mockito.when(this.sessionManager.getSession()).thenReturn(this.session);
					Mockito.when(this.session.createQuery("from UserDomain")).thenReturn(this.query);
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
						this.response = this.crudRepository.findAll();
					});

					it("returns the entities found", ()->{
						expect(this.response).toEqual(this.entities);
					});
				});

				describe("when there aren't entities found", ()->{
					beforeEach(()->{
						Mockito.when(this.query.list()).thenReturn(null);
						this.response = this.crudRepository.findAll();
					});

					it("returns null", ()->{
						expect(this.response).toBeNull();
					});
				});
			});
		});
	}
}
