//package com.mario.java.test.restful.api.hibernate.jpa.domain;
//
//import static com.mscharhag.oleaster.matcher.Matchers.expect;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
//import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
//
//import java.util.Date;
//
//import org.junit.runner.RunWith;
//
//import com.mario.java.restful.api.hibernate.jpa.entity.DatedEntity;
//import com.mscharhag.oleaster.runner.OleasterRunner;
//
//@RunWith(OleasterRunner.class)
//public class DatedDomainTest {
//
//    // TODO
//    // how to test the generated createdAt and updatedAt?
//    // how to test the it has the mappedsuperclass annotation?
//
//    public class DatedDomainMock extends DatedEntity{};
//
//    private DatedDomainMock datedDomainMock;
//
//    private Date updatedAt;
//    private Date createdAt;
//
//    {
//        beforeEach(() -> {
//            this.datedDomainMock = new DatedDomainMock();
//            this.updatedAt = new Date();
//            this.createdAt = new Date();
//        });
//
//        afterEach(() -> {
//            this.updatedAt = null;
//            this.createdAt = null;
//        });
//
//        describe("@createdAt", () -> {
//            beforeEach(() -> {
//                this.datedDomainMock.setCreatedAt(this.updatedAt);
//            });
//
//            describe("#getCreatedAt", () -> {
//                it("returns the createdAt", () -> {
//                    expect(this.datedDomainMock.getCreatedAt()).toEqual(this.createdAt);
//                });
//            });
//
//            describe("#setCreatedAt", () -> {
//                it("sets the createdAt", () -> {
//                    expect(this.datedDomainMock.getCreatedAt()).toEqual(this.createdAt);
//                });
//            });
//        });
//
//        describe("@updatedAt", () -> {
//            beforeEach(() -> {
//                this.datedDomainMock.setUpdatedAt(this.updatedAt);
//            });
//
//            describe("#getUpdatedAt", () -> {
//                it("returns the updatedAt", () -> {
//                    expect(this.datedDomainMock.getUpdatedAt()).toEqual(this.updatedAt);
//                });
//            });
//
//            describe("#setUpdatedAt", () -> {
//                it("sets the updatedAt", () -> {
//                    expect(this.datedDomainMock.getUpdatedAt()).toEqual(this.updatedAt);
//                });
//            });
//        });
//    }
//}
