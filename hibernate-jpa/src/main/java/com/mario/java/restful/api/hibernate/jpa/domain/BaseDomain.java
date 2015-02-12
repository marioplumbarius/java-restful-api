package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mario.java.restful.api.hibernate.jpa.resource.exception.HibernateValidationExceptionHandler;

@MappedSuperclass
public abstract class BaseDomain extends DatedDomain {
    
    @Transient
    private HibernateValidationExceptionHandler validator;
    
    public BaseDomain(){
    	this(new HibernateValidationExceptionHandler());
    }
    
    public BaseDomain(HibernateValidationExceptionHandler validator){
    	this.validator = validator;
    }
    
    @JsonIgnore
    public boolean isValid(){
    	return this.validator.isValid(this);
    }
    
    @JsonIgnore
    public Map<String, String> getErrors(){
    	return this.validator.getErrors();
    }
}
