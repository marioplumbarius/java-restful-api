package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mario.java.restful.api.hibernate.jpa.resource.exception.HibernateValidationExceptionHandler;

@MappedSuperclass
public abstract class BaseDomain {
	
	/**
	 * TODO [refactor]
	 * create another classes:
	 * DatedDomain, ValidatedDomain
	 */

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
    @Transient
    private HibernateValidationExceptionHandler validator;
    
    public BaseDomain(){
    	this(new HibernateValidationExceptionHandler());
    }
    
    public BaseDomain(HibernateValidationExceptionHandler validator){
    	this.validator = validator;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
