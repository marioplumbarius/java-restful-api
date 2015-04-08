package com.mario.java.restful.api.hibernate.jpa.application.documentation.impl;

import javax.enterprise.context.ApplicationScoped;

import com.mario.java.restful.api.hibernate.jpa.application.documentation.ApiApplicationDocumentation;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

@ApplicationScoped
public class ApiApplicationDocumentationSwaggerImpl implements ApiApplicationDocumentation {

	public ApiApplicationDocumentationSwaggerImpl(){}

	@Override
	public void configureAndInitialize() {
		BeanConfig beanConfig = new BeanConfig();

		// TODO - move these configurations to a file and load them from there
		beanConfig.setVersion("0.0.1");
        beanConfig.setBasePath("http://localhost:8080/api");
        beanConfig.setResourcePackage("com.mario.java.restful.api.hibernate.jpa.resource");
        beanConfig.setScan(true);
	}
}
