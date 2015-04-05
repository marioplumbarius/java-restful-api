package com.mario.java.restful.api.hibernate.jpa.resource.bean.param.impl;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.mario.java.restful.api.hibernate.jpa.resource.bean.param.DomainBeanParam;

public class DomainBeanParamImpl implements DomainBeanParam {

	@QueryParam("displayProperty")
	private List<String> propertiesToBeDisplayed;

	@Override
	public void setPropertiesToBeDisplayed(List<String> propertiesToBeDisplayed) {
		this.propertiesToBeDisplayed = propertiesToBeDisplayed;
	}

	@Override
	public List<String> getPropertiesToBeDisplayed() {
		return this.propertiesToBeDisplayed;
	}
}
