package com.mario.java.restful.api.hibernate.jpa.application;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/api")
public class HibernateJPAApplication extends Application {
}
