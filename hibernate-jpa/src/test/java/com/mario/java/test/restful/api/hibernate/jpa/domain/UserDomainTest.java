package com.mario.java.test.restful.api.hibernate.jpa.domain;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mario.java.restful.api.hibernate.jpa.domain.BaseDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.NameFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class UserDomainTest {

    @Mock
    private DomainValidator domainValidator;

    @Mock
    private PetDomain pet;

    private UserDomain userDomain;

    private String name;
    private Long id;
    private List<PetDomain> pets;

    {

        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.userDomain = new UserDomain();
            this.name = "any name";
            this.id = IdFactory.createValidId();
            this.pets = new ArrayList<PetDomain>();
            this.pets.add(this.pet);
        });

        afterEach(() -> {
            this.userDomain = null;
            this.name = null;
            this.id = null;
            this.pets = null;
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
                String obj = "{\"name\":\"mario\",\"id\":12,\"createdAt\":null,\"updatedAt\":null,\"age\":22}";
                mapper.readValue(obj, UserDomain.class);
                ignored = true;
            } catch (UnrecognizedPropertyException e) {
                ignored = false;
            }

            expect(ignored).toBeTrue();
        });

        it("extends BaseDomain", () -> {
            expect(this.userDomain instanceof BaseDomain).toBeTrue();
        });

        it("implements Serializable", () -> {
            expect(this.userDomain instanceof Serializable).toBeTrue();
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
                this.userDomain.setName(null);
                expect(this.userDomain.isValid()).toBeFalse();
            });

            it("cannot have less than 1 character", () -> {
                this.userDomain.setName("");
                expect(this.userDomain.isValid()).toBeFalse();
            });

            it("cannot have more than 20 characters", () -> {
                this.userDomain.setName(NameFactory.createName("", 21));
                expect(this.userDomain.isValid()).toBeFalse();
            });

            it("can have between 1 - 20 characters", () -> {
                this.userDomain.setName(NameFactory.createName("", 15));
                expect(this.userDomain.isValid()).toBeTrue();
            });

        });

        describe("@pets", () -> {
            it("is a json managed reference", () -> {
                // TODO
            });

            it("has an OneToMany annotation", () -> {
                // TODO
            });
        });

        describe("#constructor (default)", () -> {
            beforeEach(() -> {
                this.userDomain = new UserDomain();
            });

            it("assigns a null id", () -> {
                expect(this.userDomain.getId()).toBeNull();
            });

            it("assigns a null name", () -> {
                expect(this.userDomain.getName()).toBeNull();
            });
        });

        describe("#constructor (name)", () -> {
            beforeEach(() -> {
                this.userDomain = new UserDomain(this.name);
            });

            it("assigns a null id", () -> {
                expect(this.userDomain.getId()).toBeNull();
            });

            it("assigns the provided name", () -> {
                expect(this.userDomain.getName()).toEqual(this.name);
            });
        });

        describe("#getName", () -> {
            beforeEach(() -> {
                this.userDomain.setName(this.name);
            });

            it("returns the name", () -> {
                expect(this.userDomain.getName()).toEqual(this.name);
            });
        });

        describe("#setName", () -> {
            beforeEach(() -> {
                this.name = "another name";
                this.userDomain.setName(this.name);
            });

            it("sets the name", () -> {
                expect(this.userDomain.getName()).toEqual(this.name);
            });
        });

        describe("#getId", () -> {
            beforeEach(() -> {
                this.userDomain.setId(this.id);
            });

            it("returns the id", () -> {
                expect(this.userDomain.getId()).toEqual(this.id);
            });
        });

        describe("#setId", () -> {
            beforeEach(() -> {
                this.id = IdFactory.createValidId();
                this.userDomain.setId(this.id);
            });

            it("sets the id", () -> {
                expect(this.userDomain.getId()).toEqual(this.id);
            });
        });
    }
}
