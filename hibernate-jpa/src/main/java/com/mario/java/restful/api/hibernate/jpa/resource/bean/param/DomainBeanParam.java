package com.mario.java.restful.api.hibernate.jpa.resource.bean.param;

import java.util.List;

//TODO - document me!
public interface DomainBeanParam {
	public void setPropertiesToBeDisplayed(List<String> propertiesToBeDisplayed);
	public List<String> getPropertiesToBeDisplayed();
}
