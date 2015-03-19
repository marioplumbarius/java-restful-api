package com.mario.java.test.restful.api.hibernate.jpa.resource;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.restful.api.hibernate.jpa.resource.PetResource;
import com.mario.java.restful.api.hibernate.jpa.resource.response.HttpStatus;
import com.mario.java.restful.api.hibernate.jpa.service.impl.PetServiceImpl;
import com.mario.java.restful.api.hibernate.jpa.service.impl.UserServiceImpl;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class PetResourceTest {

    @InjectMocks
    private PetResource resource;

    @Mock
    private PetServiceImpl petServiceImpl;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private PetResource resourceMock;

    @Mock
    private PetDomain validPet = PetFactory.createValidPet();

    @Mock
    private PetDomain invalidPet = PetFactory.createInvalidPet();

    @Mock
    private UserDomain user;

    @Mock
    private PetDomain currentPet;

    private List<PetDomain> pets;
    private Long id;
    private Response response;

    private void behavesLikePetNotFound(){
    	it("returns an empty body", () -> {
            expect(this.response.getEntity()).toEqual(null);
        });

        it("returns a 404 http status", () -> {
            expect(this.response.getStatus()).toEqual(Status.NOT_FOUND.getStatusCode());
        });
    }

    private void behavesLikeUpdate(){
    	it("verifies if the pet is valid", () -> {
            Mockito.verify(this.validPet).isValid();
        });

        describe("when the pet is valid", () -> {
        	beforeEach(() -> {
		         Mockito.when(this.validPet.isValid()).thenReturn(true);
			});

        	describe("when the user exist", () -> {
        		beforeEach(() -> {
					Mockito.when(this.userServiceImpl.find(this.validPet.getUserId())).thenReturn(this.user);
				});
        		describe("when the pet exists", () -> {
                    beforeEach(() -> {
                        this.response = this.resource.update(this.id, this.validPet);
                    });

                    it("updates the pet", () -> {
                        Mockito.verify(this.petServiceImpl).update(this.id, this.validPet);
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
                        Mockito.doThrow(new ObjectNotFoundException(this.id, this.validPet.getClass().getName())).when(this.petServiceImpl).update(this.id, this.validPet);
                        this.response = this.resource.update(this.id, this.validPet);
                    });

                    this.behavesLikePetNotFound();
                });
			});

        	describe("when the user does not exist", () -> {
        		beforeEach(() -> {
					Mockito.when(this.userServiceImpl.find(this.validPet.getUserId())).thenReturn(null);

					this.response = this.resource.update(this.id, this.validPet);
				});

        		it("returns HttpStatus.UNPROCESSABLE_ENTITY http status code", () -> {
        			expect(this.response.getStatus()).toEqual(HttpStatus.UNPROCESSABLE_ENTITY.getStatusCode());
				});

        		it("returns 'user not found' error", () -> {
        			Map<String, Object> errors = DomainValidator.buildError("userId", "not found");
        			expect(this.response.getEntity()).toEqual(errors);
				});
			});

        });

        describe("when the pet is invalid", () -> {
            beforeEach(() -> {
                Mockito.when(this.invalidPet.isValid()).thenReturn(false);
                this.response = this.resource.create(this.invalidPet);
            });

            it("returns HttpStatus.UNPROCESSABLE_ENTITY http status code", () -> {
                expect(this.response.getStatus()).toEqual(HttpStatus.UNPROCESSABLE_ENTITY.getStatusCode());
            });

            it("returns validation errors", () -> {
                Mockito.verify(this.invalidPet).getErrors();
                expect(this.response.getEntity()).toBeNotNull();
            });
        });
    }

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
                Mockito.when(this.petServiceImpl.find(this.id)).thenReturn(null);
            });

            it("searches for pets by id", () -> {
                this.resource.find(this.id);
                Mockito.verify(this.petServiceImpl).find(this.id);
            });

            describe("when the pet is not found", () -> {

                beforeEach(() -> {
                    Mockito.when(this.petServiceImpl.find(this.id)).thenReturn(null);
                    this.response = this.resource.find(this.id);
                });

                this.behavesLikePetNotFound();
            });

            describe("when the pet is found", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petServiceImpl.find(this.id)).thenReturn(this.validPet);
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
        	beforeEach(() -> {
                Mockito.when(this.petServiceImpl.findAll(null)).thenReturn(null);
                this.resource.findAll();
            });

            it("searches for all pets", () -> {
                Mockito.verify(this.petServiceImpl).findAll();
            });

            describe("when no pets are found", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petServiceImpl.findAll()).thenReturn(null);
                });

                it("returns an empty list", () -> {
                    List<PetDomain> returnedUsers = this.resource.findAll();
                    expect(returnedUsers).toBeNull();
                });
            });

            describe("when there are pets found", () -> {
                beforeEach(() -> {
                    this.pets.add(this.validPet);
                    Mockito.when(this.petServiceImpl.findAll()).thenReturn(this.pets);
                });

                it("returns the list of pets found", () -> {
                    List<PetDomain> returnedUsers = this.resource.findAll();
                    expect(returnedUsers.get(0)).toEqual(this.pets.get(0));
                    expect(returnedUsers.size()).toEqual(this.pets.size());
                });
            });
        });

        describe("#create", () -> {

            beforeEach(() -> {
                this.resource.create(this.validPet);
            });

            it("verifies if the pet is valid", () -> {
                Mockito.verify(this.validPet).isValid();
            });

            describe("when the pet is valid", () -> {
            	beforeEach(() -> {
					Mockito.when(this.validPet.isValid()).thenReturn(true);
				});

                describe("when the pet's user exists", () -> {
                	beforeEach(() -> {
                		Mockito.when(this.userServiceImpl.find(this.validPet.getUserId())).thenReturn(this.user);

                		this.response = this.resource.create(this.validPet);
					});

                    it("persists the pet", () -> {
                        Mockito.verify(this.petServiceImpl).persist(this.validPet);
                    });

                    it("returns 201 http status code", () -> {
                        expect(this.response.getStatus()).toEqual(Status.CREATED.getStatusCode());
                    });

                    it("returns the pet URI", () -> {
                        expect(this.response.getLocation().toString()).toEqual(PetFactory.URI.replace("{id}", this.validPet.getId().toString()));
                    });
				});

                describe("when the pet's user does not exist", () -> {
                	beforeEach(() -> {
						this.response = this.resource.create(this.validPet);
					});

                	it("returns HttpStatus.UNPROCESSABLE_ENTITY http status code", () -> {
                		expect(this.response.getStatus()).toEqual(HttpStatus.UNPROCESSABLE_ENTITY.getStatusCode());
					});

                	it("returns 'user not found' error", () -> {
                		Map<String, Object> errors = DomainValidator.buildError("userId", "not found");
                		expect(this.response.getEntity()).toBeNotNull();
                		expect(this.response.getEntity()).toEqual(errors);
					});
				});
            });

            describe("when the pet is invalid", () -> {
                beforeEach(() -> {
                	Mockito.when(this.invalidPet.isValid()).thenReturn(false);
                	this.response = this.resource.create(this.invalidPet);
                });

                it("returns HttpStatus.UNPROCESSABLE_ENTITY http status code", () -> {
                    expect(this.response.getStatus()).toEqual(HttpStatus.UNPROCESSABLE_ENTITY.getStatusCode());
                });

                it("returns validation errors", () -> {
                    this.invalidPet.isValid();
                    expect(this.response.getEntity()).toBeNotNull();
                    expect(this.response.getEntity()).toEqual(this.invalidPet.getErrors());
                });
            });
        });

        describe("#update", () -> {

            beforeEach(() -> {
                this.resource.update(this.id, this.validPet);
            });

            this.behavesLikeUpdate();
        });

        describe("#delete", () -> {

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    this.response = this.resource.delete(this.id);
                });

                it("deletes the pet", () -> {
                    Mockito.verify(this.petServiceImpl).delete(this.id);
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
                    Mockito.doThrow(new ObjectNotFoundException(this.id, null)).when(this.petServiceImpl).delete(this.id);
                    this.response = this.resource.delete(this.id);
                });

                this.behavesLikePetNotFound();
            });
        });

        describe("#patch", () -> {
            beforeEach(() -> {
                this.response = this.resource.patch(this.id, this.validPet);
            });

            it("fetches the pet from database", () -> {
                Mockito.verify(this.petServiceImpl).find(this.id);
            });

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petServiceImpl.find(this.id)).thenReturn(this.currentPet);

                    this.response = this.resource.patch(this.id, this.validPet);
                });

                it("fills the missing pet's attributes", () -> {
                    Mockito.verify(this.validPet).patch(this.currentPet);
                });

                this.behavesLikeUpdate();
            });

            describe("when the pet does not exist", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petServiceImpl.find(this.id)).thenReturn(null);

                    this.response = this.resource.patch(this.id, this.validPet);
                });

                this.behavesLikePetNotFound();
            });
        });
    }
}
