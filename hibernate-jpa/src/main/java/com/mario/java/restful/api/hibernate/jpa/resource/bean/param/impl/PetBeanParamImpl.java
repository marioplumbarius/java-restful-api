package com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl;

import javax.ws.rs.QueryParam;

import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.PetBeanParam;

public class PetBeanParamImpl extends BeanParamImpl implements PetBeanParam {

	@QueryParam("name")
	private String name;

	@QueryParam("age")
	private int age;

	@QueryParam("userId")
	private Long userId;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Long getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}