package com.mario.java.test.restful.api.hibernate.jpa.domain;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.BaseDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.DatedDomain;
import com.mario.java.restful.api.hibernate.jpa.domain.validation.DomainValidator;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class BaseDomainTest {

	// TODO
	// how to test jsonignore annotations?
	// how to test transient annotation?

	public class BaseDomainMock extends BaseDomain{
		public BaseDomainMock(DomainValidator domainValidator){
			super(domainValidator);
		}
	}

	@Mock
	private DomainValidator domainValidator;

	@InjectMocks
	private BaseDomainMock baseDomainMock = new BaseDomainMock(this.domainValidator);

	private Map<String, String> errors, errorResponse;

	private boolean response;

	{

		beforeEach(() -> {
			MockitoAnnotations.initMocks(this);

			this.errors = new HashMap<String, String>();
			this.errors.put("any", "error");
		});

		afterEach(() -> {
			this.errors = null;
			this.errorResponse = null;
		});

		it("extends DatedDomain", () -> {
			expect(this.baseDomainMock instanceof DatedDomain).toBeTrue();
		});

		describe("#isValid", () -> {
			describe("when it is valid", () -> {
				beforeEach(() -> {
					Mockito.when(this.domainValidator.isValid(this.baseDomainMock)).thenReturn(true);

					this.response = this.baseDomainMock.isValid();
				});

				it("returns true", () -> {
					expect(this.response).toBeTrue();
				});
			});

			describe("when it is not valid", () -> {
				beforeEach(() -> {
					Mockito.when(this.domainValidator.isValid(this.baseDomainMock)).thenReturn(false);

					this.response = this.baseDomainMock.isValid();
				});

				it("returns true", () -> {
					expect(this.response).toBeFalse();
				});
			});
		});

		describe("#getErrors", () -> {
			beforeEach(() -> {
				Mockito.when(this.domainValidator.getErrors()).thenReturn(this.errors);
				this.errorResponse = this.baseDomainMock.getErrors();
			});

			it("returns the validation errors", () -> {
				Mockito.verify(this.domainValidator).getErrors();
				expect(this.errorResponse).toEqual(this.errors);
			});
		});
	}
}
