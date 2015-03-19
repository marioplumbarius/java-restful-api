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
import com.mario.java.restful.api.hibernate.jpa.repository.impl.AbstractRepositoryHibernateImpl;
import com.mario.java.restful.api.hibernate.jpa.service.impl.PetServiceImpl;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class PetServiceImplTest {

    @Mock
    private PetDomain pet;

    @Mock
    private AbstractRepositoryHibernateImpl<PetDomain, Long> petCrud;

    @InjectMocks
    private PetServiceImpl petServiceImpl;

    private Long id;
    private PetDomain returnedPet;
    private List<PetDomain> returnedPets;
    private String key;
    private String value;
    private PetDomain validPet;
    private List<PetDomain> pets;
    private Map<String, Object> criterias;

    {

        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.validPet = PetFactory.createValidPet();
            this.pets = new ArrayList<PetDomain>();
            this.pets.add(this.validPet);
            this.id = IdFactory.createValidId();
            this.key = "anyKey";
            this.value = "anyValue";
            this.criterias = new HashMap<String, Object>();
            this.criterias.put(this.key, this.value);
        });

        afterEach(() -> {
            this.validPet = null;
            this.pets = null;
            this.id = null;
            this.returnedPet = null;
            this.returnedPets = null;
            this.key = null;
            this.value = null;
            this.criterias = null;
        });

        describe("#persist", () -> {
            beforeEach(() -> {
                this.petServiceImpl.persist(this.validPet);
            });

            it("persists the pets", () -> {
                Mockito.verify(this.petCrud).persist(this.validPet);
            });
        });

        describe("#update", () -> {
            beforeEach(() -> {
                this.petServiceImpl.update(this.id, this.pet);
            });

            it("sets the id of the pet", () -> {
                Mockito.verify(this.pet).setId(this.id);
            });

            describe("when the pet exists", () -> {
            	it("updates the pet", () -> {
                    Mockito.verify(this.petCrud).update(this.pet);
                });
			});

            describe("when the pet does not exist", () -> {
            	beforeEach(() -> {
					Mockito.doThrow(new StaleStateException(null)).when(this.petCrud).update(this.pet);
				});

            	it("throws an ObjectNotFoundException", () -> {
            		try {
            			this.petServiceImpl.update(this.id, this.pet);
            			expect("ObjectNotFoundException").toBeNotNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exist");
					}
				});
			});
        });

        describe("#find", () -> {
            beforeEach(() -> {
                Mockito.when(this.petCrud.find(this.id)).thenReturn(this.validPet);
            });

            it("searches for the pet by id", () -> {
                this.petServiceImpl.find(this.id);
                Mockito.verify(this.petCrud).find(this.id);
            });

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.find(this.id)).thenReturn(this.validPet);
                    this.returnedPet = this.petServiceImpl.find(this.id);
                });

                it("returned the pet found", () -> {
                    expect(this.returnedPet.getId()).toBeNotNull();
                    expect(this.returnedPet.getId()).toEqual(this.validPet.getId());
                });
            });

            describe("when the pet does not exist", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.find(this.id)).thenReturn(null);
                    this.returnedPet = this.petServiceImpl.find(this.id);
                });

                it("return null", () -> {
                    expect(this.returnedPet).toBeNull();
                });
            });
        });

        describe("#findAll", () -> {

            describe("when the search is made without criterias", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findAll()).thenReturn(null);
                    this.petServiceImpl.findAll();
                });

                it("searches for all pets", () -> {
                    Mockito.verify(this.petCrud).findAll();
                });

                describe("when there are pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll()).thenReturn(this.pets);
                        this.returnedPets = this.petServiceImpl.findAll();
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets.size()).toEqual(this.pets.size());
                        expect(this.returnedPets.get(0).getId()).toBeNotNull();
                        expect(this.returnedPets.get(0).getId()).toEqual(this.pets.get(0).getId());
                    });
                });

                describe("when there aren't pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll()).thenReturn(null);
                        this.returnedPets = this.petServiceImpl.findAll();
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets).toBeNull();
                    });
                });
            });

            describe("when the search is made with criterias", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(null);
                    this.petServiceImpl.findAll(this.criterias);
                });

                it("searches for all pets filtered by some criterias", () -> {
                    Mockito.verify(this.petCrud).findAll(this.criterias);
                });

                describe("when there are pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(this.pets);
                        this.returnedPets = this.petServiceImpl.findAll(this.criterias);
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets.size()).toEqual(this.pets.size());
                        expect(this.returnedPets.get(0).getId()).toBeNotNull();
                        expect(this.returnedPets.get(0).getId()).toEqual(this.pets.get(0).getId());
                    });
                });

                describe("when there aren't pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(null);
                        this.returnedPets = this.petServiceImpl.findAll(this.criterias);
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets).toBeNull();
                    });
                });
            });
        });

        describe("#delete", () -> {
            describe("when the pet exists", () -> {
            	beforeEach(() -> {
                    this.petServiceImpl.delete(this.id);
                });

            	it("deletes it", () -> {
                    Mockito.verify(this.petCrud).delete(Matchers.any(PetDomain.class));
            	});
			});

            describe("when the pet does not exist", () -> {
            	beforeEach(() -> {
                    Mockito.when(this.petCrud.find(this.id)).thenThrow(new StaleStateException(null));
                });

            	it("throws an ObjectNotFoundException", () -> {
            		try {
            			this.petServiceImpl.delete(this.id);
            			expect("ObjectNotFoundException").toBeNotNull();
					} catch (ObjectNotFoundException e) {
						expect(e.getMessage()).toContain("No row with the given identifier exists: " + PetDomain.class.getName() + "#" + this.id);
					}
				});
			});
        });

        describe("#deleteAll", () -> {
            beforeEach(() -> {
                this.petServiceImpl.deleteAll();
            });

            it("deletes all pets from database", () -> {
                Mockito.verify(this.petCrud).deleteAll();
            });
        });
    }
}
