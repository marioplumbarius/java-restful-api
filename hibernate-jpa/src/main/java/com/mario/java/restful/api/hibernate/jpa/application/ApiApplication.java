package com.mario.java.restful.api.hibernate.jpa.application;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/")
public class ApiApplication extends Application {
}
