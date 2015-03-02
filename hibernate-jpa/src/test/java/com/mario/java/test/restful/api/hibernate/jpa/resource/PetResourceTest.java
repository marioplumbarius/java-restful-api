package com.mario.java.test.restful.api.hibernate.jpa.resource;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.resource.PetResource;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class PetResourceTest {

    @InjectMocks
    private PetResource resource;

    @Mock
    private PetService service;

    @Mock
    private PetResource resourceMock;

    @Mock
    private PetDomain validPet = PetFactory.createValidPet();

    @Mock
    private PetDomain invalidUser = PetFactory.createInvalidPet();

    @Mock
    private PetDomain currentPet;

    private List<PetDomain> pets;
    private Long id;
    private Response response;
    private String name = "anything";

    {
        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.pets = new ArrayList<PetDomain>();
            this.id = IdFactory.createValidId();
        });

        afterEach(() -> {
            this.response = null;
            this.pets = null;
            this.id = null;
        });

        describe("#find", () -> {

            beforeEach(() -> {
                Mockito.when(this.service.find(this.id)).thenReturn(null);
            });

            it("searches for pets by id", () -> {
                this.resource.find(this.id);
                Mockito.verify(this.service).find(this.id);
            });

            describe("when the pet is not found", () -> {

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

            describe("when the pet is found", () -> {
                beforeEach(() -> {
                    Mockito.when(this.service.find(this.id)).thenReturn(this.validPet);
                    this.response = this.resource.find(this.id);
                });

                it("returns the pet in the response body", () -> {
                    expect(this.response.getEntity()).toEqual(this.validPet);
                });

                it("returns a 200 http status", () -> {
                    expect(this.response.getStatus()).toEqual(Status.OK.getStatusCode());
                });
            });

        });

        describe("#findAll", () -> {

            describe("when the pet provide the 'name' query param", () -> {

                beforeEach(() -> {
                    Mockito.when(this.service.findAll(Matchers.any())).thenReturn(null);
                    this.resource.findAll(this.name);
                });

                it("filters the search by name", () -> {
                    Map<String, String> criterias = new HashMap<String, String>();
                    criterias.put("name", this.name);
                    Mockito.verify(this.service).findAll(criterias);
                });

                describe("when there aren't records matching the criteria", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.service.findAll(Matchers.any())).thenReturn(null);
                    });

                    it("returns an empty list", () -> {
                        List<PetDomain> returnedUsers = this.resource.findAll(this.name);
                        expect(returnedUsers).toBeNull();
                    });
                });

                describe("when there are records matching the criteria", () -> {
                    beforeEach(() -> {
                        this.pets.add(this.validPet);
                        Mockito.when(this.service.findAll(Matchers.any())).thenReturn(this.pets);
                    });

                    it("returns the list of pets found", () -> {
                        List<PetDomain> returnedUsers = this.resource.findAll(this.name);
                        expect(returnedUsers.get(0)).toEqual(this.pets.get(0));
                        expect(returnedUsers.size()).toEqual(this.pets.size());
                    });
                });
            });

            describe("when the pet does not search by name", () -> {
                beforeEach(() -> {
                    Mockito.when(this.service.findAll(null)).thenReturn(null);
                    this.resource.findAll(null);
                });

                it("does not filter the search by name", () -> {
                    Mockito.verify(this.service).findAll();
                });

                describe("when no pets are found", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.service.findAll()).thenReturn(null);
                    });

                    it("returns an empty list", () -> {
                        List<PetDomain> returnedUsers = this.resource.findAll(null);
                        expect(returnedUsers).toBeNull();
                    });
                });

                describe("when there are pets found", () -> {
                    beforeEach(() -> {
                        this.pets.add(this.validPet);
                        Mockito.when(this.service.findAll()).thenReturn(this.pets);
                    });

                    it("returns the list of pets found", () -> {
                        List<PetDomain> returnedUsers = this.resource.findAll(null);
                        expect(returnedUsers.get(0)).toEqual(this.pets.get(0));
                        expect(returnedUsers.size()).toEqual(this.pets.size());
                    });
                });
            });

        });

        describe("#create", () -> {

            beforeEach(() -> {
                Mockito.when(this.validPet.isValid()).thenReturn(true);
                Mockito.when(this.invalidUser.isValid()).thenReturn(false);
            });

            it("verifies if the pet is valid", () -> {
                this.resource.create(this.validPet);
                Mockito.verify(this.validPet).isValid();
            });

            describe("when the pet is valid", () -> {
                beforeEach(() -> {
                    this.response = this.resource.create(this.validPet);
                });

                it("persists the pet", () -> {
                    Mockito.verify(this.service).persist(this.validPet);
                });

                it("returns 201 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.CREATED.getStatusCode());
                });

                it("returns the pet URI", () -> {
                    expect(this.response.getLocation().toString()).toEqual(PetFactory.URI.replace("{id}", this.validPet.getId().toString()));
                });
            });

            describe("when the pet is invalid", () -> {
                beforeEach(() -> {
                    Mockito.when(this.validPet.isValid()).thenReturn(false);
                    this.response = this.resource.create(this.invalidUser);
                });

                it("does not persist the pet", () -> {
                    Mockito.verify(this.service, Mockito.never()).persist(this.validPet);
                });

                it("does not return the pet URI", () -> {
                    expect(this.response.getLocation()).toBeNull();
                });

                it("returns 422 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.BAD_REQUEST.getStatusCode());
                });

                it("returns validation errors", () -> {
                    Mockito.verify(this.invalidUser).getErrors();
                    expect(this.response.getEntity()).toBeNotNull();
                });
            });
        });

        describe("#update", () -> {

            beforeEach(() -> {
                Mockito.when(this.validPet.isValid()).thenReturn(true);
                Mockito.when(this.invalidUser.isValid()).thenReturn(false);
            });

            it("verifies if the pet is valid", () -> {
                this.resource.create(this.validPet);
                Mockito.verify(this.validPet).isValid();
            });

            describe("when the pet is valid", () -> {
                describe("when the pet exists", () -> {
                    beforeEach(() -> {
                        this.response = this.resource.update(this.id, this.validPet);
                    });

                    it("updates the pet", () -> {
                        Mockito.verify(this.service).update(this.id, this.validPet);
                    });

                    it("returns 204 http status code", () -> {
                        expect(this.response.getStatus()).toEqual(Status.NO_CONTENT.getStatusCode());
                    });

                    it("returns an empty response body", () -> {
                        expect(this.response.getEntity()).toBeNull();
                    });
                });

                describe("when the pet does not exist", () -> {
                    beforeEach(() -> {
                        Mockito.doThrow(new ObjectNotFoundException(this.id, this.validPet.getClass().getName())).when(this.service).update(this.id, this.validPet);
                        this.response = this.resource.update(this.id, this.validPet);
                    });

                    it("returns 404 http status code", () -> {
                        expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
                    });

                    it("returns an empty response body", () -> {
                        expect(this.response.getEntity()).toBeNull();
                    });
                });
            });

            describe("when the pet is invalid", () -> {
                beforeEach(() -> {
                    Mockito.when(this.validPet.isValid()).thenReturn(false);
                    this.response = this.resource.create(this.invalidUser);
                });

                it("does not persist the pet", () -> {
                    Mockito.verify(this.service, Mockito.never()).persist(this.validPet);
                });

                it("does not return the pet URI", () -> {
                    expect(this.response.getLocation()).toBeNull();
                });

                it("returns 422 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.BAD_REQUEST.getStatusCode());
                });

                it("returns validation errors", () -> {
                    Mockito.verify(this.invalidUser).getErrors();
                    expect(this.response.getEntity()).toBeNotNull();
                });
            });
        });

        describe("#delete", () -> {

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    this.response = this.resource.delete(this.id);
                });

                it("deletes the pet", () -> {
                    Mockito.verify(this.service).delete(this.id);
                });

                it("returns 204 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.NO_CONTENT.getStatusCode());
                });

                it("returns an empty response body", () -> {
                    expect(this.response.getEntity()).toBeNull();
                });
            });

            describe("when the pet does not exist", () -> {
                beforeEach(() -> {
                    Mockito.doThrow(new ObjectNotFoundException(this.id, null)).when(this.service).delete(this.id);
                    this.response = this.resource.delete(this.id);
                });

                it("returns 404 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
                });

                it("returns an empty response body", () -> {
                    expect(this.response.getEntity()).toBeNull();
                });
            });
        });

        describe("#patch", () -> {
            beforeEach(() -> {
                this.response = this.resource.patch(this.id, this.validPet);
            });

            it("fetches the pet from database", () -> {
                Mockito.verify(this.service).find(this.id);
            });

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    Mockito.when(this.service.find(this.id)).thenReturn(this.currentPet);
                    this.response = this.resource.patch(this.id, this.validPet);
                });

                it("fills the missing pet's attributes", () -> {
                    Mockito.verify(this.validPet).patch(this.currentPet);
                });

                it("updates the pet", () -> {
                    // TODO
                    // how to test the following operation?

                    // Mockito.when(this.resourceMock.patch(this.id,
                    // this.validPet)).thenCallRealMethod();
                    // this.response = this.resourceMock.patch(this.id,
                    // this.validPet);
                    // Mockito.verify(this.resourceMock).update(this.id,
                    // this.validPet);
                });
            });

            describe("when the pet does not exist", () -> {
                beforeEach(() -> {
                    Mockito.when(this.service.find(this.id)).thenReturn(null);
                    this.response = this.resource.patch(this.id, this.validPet);
                });

                it("returns 404 http status code", () -> {
                    expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
                });

                it("returns an empty response body", () -> {
                    expect(this.response.getEntity()).toBeNull();
                });
            });
        });
    }
}
