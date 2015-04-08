package com.mario.java.restful.api.hibernate.jpa.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.mario.java.restful.api.hibernate.jpa.application.documentation.ApiApplicationDocumentation;

@ApplicationScoped
@ApplicationPath("/")
public class ApiApplication extends Application {

	private ApiApplicationDocumentation apiApplicationDocumentation;

	public ApiApplication(){}

	@Inject
	public ApiApplication(ApiApplicationDocumentation apiApplicationDocumentation){
		this.apiApplicationDocumentation = apiApplicationDocumentation;

		this.apiApplicationDocumentation.configureAndInitialize();
	}
}
