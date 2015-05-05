package com.mario.java.restful.api.hibernate.jpa.application.documentation.impl;

import javax.enterprise.context.ApplicationScoped;

import com.mario.java.restful.api.hibernate.jpa.application.configuration.ApplicationProperties;
import com.mario.java.restful.api.hibernate.jpa.application.documentation.ApplicationDocumentation;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

@ApplicationScoped
public class ApplicationDocumentationSwaggerImpl implements ApplicationDocumentation {

	public ApplicationDocumentationSwaggerImpl(){}

	@Override
	public void configureAndInitialize() {
		BeanConfig beanConfig = new BeanConfig();

		beanConfig.setVersion(ApplicationProperties.getInstance().getProperty("api.version"));
        beanConfig.setSchemes(new String[]{ApplicationProperties.getInstance().getProperty("api.schemes")});
        beanConfig.setHost(ApplicationProperties.getInstance().getProperty("api.documentation.host"));
        beanConfig.setBasePath(ApplicationProperties.getInstance().getProperty("api.context.path"));
        beanConfig.setResourcePackage(ApplicationProperties.getInstance().getProperty("api.documentation.resource.package"));
        beanConfig.setScan(ApplicationProperties.getInstance().getProperty("api.documentation.scan").equals("true"));
	}
}
