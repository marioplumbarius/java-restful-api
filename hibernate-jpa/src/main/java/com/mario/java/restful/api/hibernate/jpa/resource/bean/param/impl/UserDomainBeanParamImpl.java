package com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl;

import javax.ws.rs.QueryParam;

import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.UserDomainBeanParam;

public class UserDomainBeanParamImpl implements UserDomainBeanParam {

	@QueryParam("name")
	private String name;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}