package com.mario.java.restful.api.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.mario.java.restful.api.resource.UserResource;

public class ApiApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ApiApplication() {
        this.singletons.add(new UserResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }

}
