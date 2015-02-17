package com.mario.java.test.restful.api.hibernate.jpa.domain;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.Date;

import org.junit.runner.RunWith;

import com.mario.java.restful.api.hibernate.jpa.domain.DatedDomain;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class DatedDomainTest {

	// TODO
	// how to test the generated createdAt and updatedAt?
	// how to test the it has the mappedsuperclass annotation?

	public class DatedDomainMock extends DatedDomain{};

	private DatedDomainMock datedDomainMock;

	private Date updatedAt;

	{
		beforeEach(() -> {
			this.datedDomainMock = new DatedDomainMock();
			this.updatedAt = new Date();
		});

		afterEach(() -> {
			this.updatedAt = null;
		});

		// weak test
		describe("#getCreatedAt", () -> {
			it("returns the createdAt", () -> {
				expect(this.datedDomainMock.getCreatedAt()).toBeNull();
			});
		});

		describe("#getUpdatedAt", () -> {
			it("returns the updatedAt", () -> {
				this.datedDomainMock.setUpdatedAt(this.updatedAt);
				expect(this.datedDomainMock.getUpdatedAt()).toEqual(this.updatedAt);
			});
		});

		describe("#setUpdatedAt", () -> {
			it("sets the updatedAt", () -> {
				this.datedDomainMock.setUpdatedAt(this.updatedAt);
				expect(this.datedDomainMock.getUpdatedAt()).toEqual(this.updatedAt);
			});
		});
	}
}
