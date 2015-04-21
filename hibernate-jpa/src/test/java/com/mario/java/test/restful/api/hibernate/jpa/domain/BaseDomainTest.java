//package com.mario.java.test.restful.api.hibernate.jpa.domain;
//
//import static com.mscharhag.oleaster.matcher.Matchers.expect;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import com.mario.java.restful.api.hibernate.jpa.entity.BaseEntity;
//import com.mario.java.restful.api.hibernate.jpa.entity.DatedEntity;
//import com.mario.java.restful.api.hibernate.jpa.entity.validation.EntityValidator;
//import com.mscharhag.oleaster.runner.OleasterRunner;
//
//@RunWith(OleasterRunner.class)
//public class BaseDomainTest {
//
//	// TODO
//	// how to test jsonignore annotations?
//	// how to test transient annotation?
//
//	public class BaseDomainMock extends BaseEntity{
//		public BaseDomainMock(EntityValidator domainValidator){
//			super(domainValidator);
//		}
//	}
//
//	@Mock
//	private EntityValidator domainValidator;
//
//	@InjectMocks
//	private BaseDomainMock baseDomainMock = new BaseDomainMock(this.domainValidator);
//
//	private Map<String, Object> errors, errorResponse;
//
//	private Map<String, String> errorList;
//
//	private boolean response;
//
//	{
//
//		beforeEach(() -> {
//			MockitoAnnotations.initMocks(this);
//
//			this.errors = new HashMap<String, Object>();
//			this.errorList = new HashMap<String, String>();
//			this.errorList.put("name", "invalid");
//			this.errors.put("errors", this.errorList);
//		});
//
//		afterEach(() -> {
//			this.errors = null;
//			this.errorList = null;
//			this.errorResponse = null;
//		});
//
//		it("extends DatedEntity", () -> {
//			expect(this.baseDomainMock instanceof DatedEntity).toBeTrue();
//		});
//
//		describe("#isValid", () -> {
//			describe("when it is valid", () -> {
//				beforeEach(() -> {
//					Mockito.when(this.domainValidator.isValid(this.baseDomainMock)).thenReturn(true);
//
//					this.response = this.baseDomainMock.isValid();
//				});
//
//				it("returns true", () -> {
//					expect(this.response).toBeTrue();
//				});
//			});
//
//			describe("when it is not valid", () -> {
//				beforeEach(() -> {
//					Mockito.when(this.domainValidator.isValid(this.baseDomainMock)).thenReturn(false);
//
//					this.response = this.baseDomainMock.isValid();
//				});
//
//				it("returns true", () -> {
//					expect(this.response).toBeFalse();
//				});
//			});
//		});
//
//		describe("#getErrors", () -> {
//			beforeEach(() -> {
//				Mockito.when(this.domainValidator.getErrors()).thenReturn(this.errors);
//				this.errorResponse = this.baseDomainMock.getErrors();
//			});
//
//			it("returns the validation errors", () -> {
//				Mockito.verify(this.domainValidator).getErrors();
//				expect(this.errorResponse).toEqual(this.errors);
//			});
//		});
//	}
//}
