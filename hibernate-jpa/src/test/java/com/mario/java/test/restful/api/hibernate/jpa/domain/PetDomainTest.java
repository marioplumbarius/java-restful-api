//package com.mario.java.test.restful.api.hibernate.jpa.domain;
//
//import static com.mscharhag.oleaster.matcher.Matchers.expect;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
//
//import java.io.Serializable;
//
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
//import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
//import com.mario.java.restful.api.hibernate.jpa.entity.PetEntity;
//import com.mario.java.restful.api.hibernate.jpa.entity.validation.EntityValidator;
//import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
//import com.mario.java.test.restful.api.hibernate.jpa.factories.NameFactory;
//import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
//import com.mscharhag.oleaster.runner.OleasterRunner;
//
//@RunWith(OleasterRunner.class)
//public class PetDomainTest {
//
//	@Mock
//	private EntityValidator validator;
//	private PetEntity pet;
//	private PetEntity updatedPet;
//	private PetEntity currentPet;
//	private Long id;
//	private String name;
//	private int age;
//
//	{
//
//		beforeEach(() -> {
//			MockitoAnnotations.initMocks(this);
//
//			this.pet = PetFactory.createValidPet();
//			this.currentPet = PetFactory.createValidPet();
//			this.id = IdFactory.createValidId();
//			this.name = NameFactory.createValidName();
//			this.age = 22;
//		});
//
//		afterEach(() -> {
//			this.pet = null;
//			this.updatedPet = null;
//			this.currentPet = null;
//			this.id = null;
//			this.name = null;
//			this.age = 0;
//		});
//
//		it("is an Entity", () -> {
//			// TODO
//		});
//
//		it("represents the table 'users'", () -> {
//			// TODO
//		});
//
//		it("ignores unkown json properties", () -> {
//			boolean ignored;
//			try {
//				ObjectMapper mapper = new ObjectMapper();
//				String obj = "{\"name\":\"mario\",\"id\":12,\"createdAt\":null,\"updatedAt\":null,\"timeout\":22}";
//				mapper.readValue(obj, PetEntity.class);
//				ignored = true;
//			} catch (UnrecognizedPropertyException e) {
//				ignored = false;
//			}
//
//			expect(ignored).toBeTrue();
//		});
//
//		it("extends BaseEntity", () -> {
//			expect(this.pet instanceof BaseEntity).toBeTrue();
//		});
//
//		it("implements Serializable", () -> {
//			expect(this.pet instanceof Serializable).toBeTrue();
//		});
//
//		describe("@id", () -> {
//			it("is auto generated", () -> {
//				// TODO
//			});
//
//			it("is the primary key", () -> {
//				// TODO
//			});
//		});
//
//		describe("@name", () -> {
//
//			it("cannot be empty", () -> {
//				this.pet.setName(null);
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("cannot have less than 1 character", () -> {
//				this.pet.setName("");
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("cannot have more than 20 characters", () -> {
//				this.pet.setName(NameFactory.createName("", 21));
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("can have between 1 - 20 characters", () -> {
//				this.pet.setName(NameFactory.createName("", 15));
//				expect(this.pet.isValid()).toBeTrue();
//			});
//
//		});
//
//		describe("@age", () -> {
//			it("cannot be null", () -> {
//				this.pet.setAge(-1);
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("cannot be greater than 100", () -> {
//				this.pet.setAge(101);
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("cannot be less than 1", () -> {
//				this.pet.setAge(0);
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("can be between 1 - 100 characters", () -> {
//				this.pet.setAge(50);
//				expect(this.pet.isValid()).toBeTrue();
//			});
//		});
//
//		describe("@user", () -> {
//			it("is a json back reference", () -> {
//				// TODO
//			});
//
//			it("has an ManyToOne annotation", () -> {
//				// TODO
//			});
//
//			it("has a PrimaryKeyJoinColumn annotation", () -> {
//				// TODO
//			});
//		});
//
//		describe("@userId", () -> {
//			it("cannot be null", () -> {
//				this.pet.setUserId(null);
//				expect(this.pet.isValid()).toBeFalse();
//			});
//
//			it("is a transient property", () -> {
//				// TODO
//			});
//		});
//
//		describe("#constructor", () -> {
//			describe("()", () -> {
//				beforeEach(() -> {
//					this.pet = new PetEntity();
//				});
//
//				it("assigns a null @id", () -> {
//					expect(this.pet.getId()).toBeNull();
//				});
//
//				it("assigns a null @name", () -> {
//					expect(this.pet.getName()).toBeNull();
//				});
//
//				it("assigns a null @age", () -> {
//					expect(this.pet.getAge()).toEqual(0);
//				});
//
//				it("assigns a null @userId", () -> {
//					expect(this.pet.getUserId()).toBeNull();
//				});
//			});
//
//			describe("(name, age)", () -> {
//				beforeEach(() -> {
//					this.pet = new PetEntity(this.name, this.age);
//				});
//
//				it("assigns the provided @name", () -> {
//					expect(this.pet.getName()).toEqual(this.name);
//				});
//
//				it("assigns the provided @age", () -> {
//					expect(this.pet.getAge()).toEqual(this.age);
//				});
//
//				describe("(name, age, validator)", () -> {
//					beforeEach(() -> {
//						this.pet = new PetEntity(this.name, this.age, this.validator);
//					});
//
//					it("assigns the provided @validator", () -> {
//						// TODO
//					});
//				});
//			});
//
//		});
//
//		describe("#getName", () -> {
//			beforeEach(() -> {
//				this.pet.setName(this.name);
//			});
//
//			it("returns the name", () -> {
//				expect(this.pet.getName()).toEqual(this.name);
//			});
//		});
//
//		describe("#setName", () -> {
//			beforeEach(() -> {
//				this.name = "another name";
//				this.pet.setName(this.name);
//			});
//
//			it("sets the name", () -> {
//				expect(this.pet.getName()).toEqual(this.name);
//			});
//		});
//
//		describe("#getId", () -> {
//			beforeEach(() -> {
//				this.pet.setId(this.id);
//			});
//
//			it("returns the id", () -> {
//				expect(this.pet.getId()).toEqual(this.id);
//			});
//		});
//
//		describe("#setId", () -> {
//			beforeEach(() -> {
//				this.id = IdFactory.createValidId();
//				this.pet.setId(this.id);
//			});
//
//			it("sets the id", () -> {
//				expect(this.pet.getId()).toEqual(this.id);
//			});
//		});
//
//		describe("#getUserId", () -> {
//
//			describe("when the user is null", () -> {
//				beforeEach(() -> {
//					this.pet.setUserId(null);
//				});
//
//				it("returns null", () -> {
//					expect(this.pet.getUserId()).toBeNull();
//				});
//			});
//
//			describe("when the user is not null", () -> {
//				beforeEach(() -> {
//					this.pet.setUserId(this.id);
//				});
//
//				it("returns the user id", () -> {
//					expect(this.pet.getUserId()).toEqual(this.id);
//				});
//			});
//
//		});
//
//		describe("#setUserId", () -> {
//			beforeEach(() -> {
//				this.pet.setUserId(this.id);
//			});
//
//			it("sets the user id", () -> {
//				expect(this.pet.getUserId()).toEqual(this.id);
//			});
//		});
//
//		describe("#patch", () -> {
//			beforeEach(() -> {
//				this.updatedPet = new PetEntity();
//			});
//
//			describe("with @name", () -> {
//				describe("when it is null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setName(null);
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("overrites it with the current one", () -> {
//						expect(this.updatedPet.getName()).toEqual(this.currentPet.getName());
//					});
//				});
//
//				describe("when it is not null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setName(NameFactory.createName("birulao", 2));
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("does not overrite it", () -> {
//						expect(this.updatedPet.getName().equals(this.currentPet.getName())).toBeFalse();
//					});
//				});
//			});
//
//			describe("with @age", () -> {
//				describe("when it is null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setAge(0);
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("overrites it with the current one", () -> {
//						expect(this.updatedPet.getAge()).toEqual(this.currentPet.getAge());
//					});
//				});
//
//				describe("when it is not null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setAge(33);
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("does not overrite it", () -> {
//						expect(this.updatedPet.getAge() == this.currentPet.getAge()).toBeFalse();
//					});
//				});
//			});
//
//			describe("with @userId", () -> {
//				describe("when it is null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setUserId(null);
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("overrites it", () -> {
//						expect(this.updatedPet.getUserId()).toEqual(this.currentPet.getUserId());
//					});
//				});
//
//				describe("when it is not null", () -> {
//					beforeEach(() -> {
//						this.updatedPet.setUserId(IdFactory.createValidId());
//						this.updatedPet.patch(this.currentPet);
//					});
//
//					it("does not overrite it", () -> {
//						expect(this.updatedPet.getUserId() == this.currentPet.getUserId()).toBeFalse();
//					});
//				});
//			});
//		});
//	}
//}
