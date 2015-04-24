package com.mario.java.restful.api.hibernate.jpa.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.mario.java.restful.api.hibernate.jpa.application.documentation.ApplicationDocumentation;

@ApplicationScoped
@ApplicationPath("/")
public class HibernateJPAApplication extends Application {

	private ApplicationDocumentation applicationDocumentation;

	public HibernateJPAApplication(){}

	@Inject
	public HibernateJPAApplication(ApplicationDocumentation applicationDocumentation){
		this.applicationDocumentation = applicationDocumentation;

		this.applicationDocumentation.configureAndInitialize();
	}
}
