package com.mario.java.test.restful.api.hibernate.jpa.domain;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.io.Serializable;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mario.java.restful.api.hibernate.jpa.domain.BaseDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.NameFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class PetDomainTest {

    @Mock
    private DomainValidator validator;
    private PetDomain pet;
    private Long id;
    private String name;
    private int age;
    private Long userId;

    {

        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.pet = PetFactory.createValidPet();
            this.id = IdFactory.createValidId();
            this.name = NameFactory.createValidName();
            this.age = 22;
            this.userId = IdFactory.createValidId();
        });

        afterEach(() -> {
            this.pet = null;
            this.id = null;
            this.name = null;
            this.age = 0;
            this.userId = null;
        });

        it("is an Entity", () -> {
            // TODO
        });

        it("represents the table 'users'", () -> {
            // TODO
        });

        it("ignores unkown json properties", () -> {
            boolean ignored;
            try {
                ObjectMapper mapper = new ObjectMapper();
                String obj = "{\"name\":\"mario\",\"id\":12,\"createdAt\":null,\"updatedAt\":null,\"timeout\":22}";
                mapper.readValue(obj, PetDomain.class);
                ignored = true;
            } catch (UnrecognizedPropertyException e) {
                ignored = false;
            }

            expect(ignored).toBeTrue();
        });

        it("extends BaseDomain", () -> {
            expect(this.pet instanceof BaseDomain).toBeTrue();
        });

        it("implements Serializable", () -> {
            expect(this.pet instanceof Serializable).toBeTrue();
        });

        describe("@id", () -> {
            it("is auto generated", () -> {
                // TODO
            });

            it("is the primary key", () -> {
                // TODO
            });
        });

        describe("@name", () -> {

            it("cannot be empty", () -> {
                this.pet.setName(null);
                expect(this.pet.isValid()).toBeFalse();
            });

            it("cannot have less than 1 character", () -> {
                this.pet.setName("");
                expect(this.pet.isValid()).toBeFalse();
            });

            it("cannot have more than 20 characters", () -> {
                this.pet.setName(NameFactory.createName("", 21));
                expect(this.pet.isValid()).toBeFalse();
            });

            it("can have between 1 - 20 characters", () -> {
                this.pet.setName(NameFactory.createName("", 15));
                expect(this.pet.isValid()).toBeTrue();
            });

        });

        describe("@age", () -> {
            it("cannot be null", () -> {
                this.pet.setAge(-1);
                expect(this.pet.isValid()).toBeFalse();
            });

            it("cannot be greater than 100", () -> {
                this.pet.setAge(101);
                expect(this.pet.isValid()).toBeFalse();
            });

            it("cannot be less than 1", () -> {
                this.pet.setAge(0);
                expect(this.pet.isValid()).toBeFalse();
            });

            it("can be between 1 - 100 characters", () -> {
                this.pet.setAge(50);
                expect(this.pet.isValid()).toBeTrue();
            });
        });

        describe("@user", () -> {
            it("is a json back reference", () -> {
                // TODO
            });

            it("has an ManyToOne annotation", () -> {
                // TODO
            });

            it("has a PrimaryKeyJoinColumn annotation", () -> {
                // TODO
            });
        });

        describe("@userId", () -> {
            it("cannot be null", () -> {
                this.pet.setUserId(null);
                expect(this.pet.isValid()).toBeFalse();
            });

            it("is a transient property", () -> {
                // TODO
            });
        });

        describe("#constructor", () -> {
            describe("()", () -> {
                beforeEach(() -> {
                    this.pet = new PetDomain();
                });

                it("assigns a null @id", () -> {
                    expect(this.pet.getId()).toBeNull();
                });

                it("assigns a null @name", () -> {
                    expect(this.pet.getName()).toBeNull();
                });

                it("assigns a null @age", () -> {
                    expect(this.pet.getAge()).toEqual(0);
                });

                it("assigns a null @user", () -> {
                    expect(this.pet.getUser()).toBeNull();
                });

                it("assigns a null @userId", () -> {
                    expect(this.pet.getUserId()).toBeNull();
                });
            });

            describe("(name, age)", () -> {
                beforeEach(() -> {
                    this.pet = new PetDomain(this.name, this.age);
                });

                it("assigns the provided @name", () -> {
                    expect(this.pet.getName()).toEqual(this.name);
                });

                it("assigns the provided @age", () -> {
                    expect(this.pet.getAge()).toEqual(this.age);
                });

                describe("(name, age, validator)", () -> {
                    beforeEach(() -> {
                        this.pet = new PetDomain(this.name, this.age, this.validator);
                    });

                    it("assigns the provided @validator", () -> {
                        // TODO
                    });
                });
            });

        });

        describe("#getName", () -> {
            beforeEach(() -> {
                this.pet.setName(this.name);
            });

            it("returns the name", () -> {
                expect(this.pet.getName()).toEqual(this.name);
            });
        });

        describe("#setName", () -> {
            beforeEach(() -> {
                this.name = "another name";
                this.pet.setName(this.name);
            });

            it("sets the name", () -> {
                expect(this.pet.getName()).toEqual(this.name);
            });
        });

        describe("#getId", () -> {
            beforeEach(() -> {
                this.pet.setId(this.id);
            });

            it("returns the id", () -> {
                expect(this.pet.getId()).toEqual(this.id);
            });
        });

        describe("#setId", () -> {
            beforeEach(() -> {
                this.id = IdFactory.createValidId();
                this.pet.setId(this.id);
            });

            it("sets the id", () -> {
                expect(this.pet.getId()).toEqual(this.id);
            });
        });

        describe("#getUserId", () -> {

        });

        describe("#setUserId", () -> {

        });
    }
}
