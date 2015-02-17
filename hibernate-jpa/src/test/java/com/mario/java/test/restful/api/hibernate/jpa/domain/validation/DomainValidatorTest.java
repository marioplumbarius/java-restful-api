package com.mario.java.test.restful.api.hibernate.jpa.domain.validation;


import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.UserDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mario.java.test.restful.api.hibernate.jpa.factories.UserFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class DomainValidatorTest {

	@Mock
	private Validator validator;

	private UserDomain entity;

	@Mock
	private Set<ConstraintViolation<UserDomain>> constraintViolations;

	private DomainValidator domainValidator;

	private DomainValidator domainValidatorWithoutInjection;

	private boolean response;

	{
		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			this.domainValidator = new DomainValidator(this.validator);
			this.domainValidatorWithoutInjection = new DomainValidator();
		});

		afterEach(() -> {
			this.domainValidator = null;
			this.domainValidatorWithoutInjection = null;
			this.entity = null;
		});

		describe("#getValidator", () -> {
			describe("from default constructor", () -> {
				it("returns an instance of javax's Validator", () -> {
					expect(this.domainValidatorWithoutInjection.getValidator() instanceof Validator).toBeTrue();
				});
			});

			describe("from fallback constructor", () -> {
				it("returns the validator", () -> {
					expect(this.domainValidator.getValidator()).toEqual(this.validator);
				});
			});
		});

		describe("#getErrors", () -> {
			it("returns the errors", () -> {
				expect(this.domainValidator.getErrors().isEmpty()).toBeTrue();
			});
		});

		describe("#isValid", () -> {
			beforeEach(() -> {
				Mockito.when(this.validator.validate(this.entity)).thenReturn(this.constraintViolations);

				this.domainValidator.isValid(this.entity);
			});

			it("validates the entity", () -> {
				Mockito.verify(this.validator).validate(this.entity);
			});

			describe("when the entity is valid", () -> {
				beforeEach(() -> {
					this.entity = UserFactory.createValidUser();

					this.response = this.domainValidatorWithoutInjection.isValid(this.entity);
				});

				it("returns true", () -> {
					expect(this.response).toBeTrue();
				});
			});

			describe("when the entity is invalid", () -> {
				beforeEach(() -> {
					this.entity = UserFactory.createInvalidUser();

					this.response = this.domainValidatorWithoutInjection.isValid(this.entity);
				});

				it("buils an object with the errors found", () -> {
					Map<String, String> errors = this.domainValidatorWithoutInjection.getErrors();
					String errorMessage = "size must be between 1 and 20";
					expect(errors.get("name")).toEqual(errorMessage);
				});

				it("returns false", () -> {
					expect(this.response).toBeFalse();
				});
			});
		});
	}
}
