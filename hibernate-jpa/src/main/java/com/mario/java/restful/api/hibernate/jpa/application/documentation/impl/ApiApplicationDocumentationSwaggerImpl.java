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
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.mario.java.restful.api.hibernate.jpa.resource.impl");
        beanConfig.setScan(true);
	}
}
