package com.mario.java.restful.api.hibernate.jpa.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class DatedDomain {
	
	@CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
	
}
