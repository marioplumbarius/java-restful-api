package com.mario.java.restful.api.hibernate.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Domain class which represents a pet on database.
 * @author marioluan
 *
 */
@Entity
@Table(name = "pets")
@ApiModel(description = "pet dto", parent = BaseEntity.class)
public class PetEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(min = 1, max = 20)
    @ApiModelProperty(value = "the name of the pet", required = true)
    private String name;

    @NotNull
    @Range(min = 1, max = 100)
    @ApiModelProperty(value = "the age of the pet", required = true)
    private int age;

    @NotNull
    @ManyToOne(optional=false)
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Transient
    @ApiModelProperty(value = "the id of the pet's user", required = true)
    private Long userId;

    /**
     * Default constructor, creates an empty instance.
     */
    public PetEntity(){}

    /**
     * @param name the name to set
     * @param age the age to set
     */
    public PetEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @param user the user to set
     */
    private void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * @return the user
     */
    private UserEntity getUser() {
        return this.user;
    }

    /**
     * @return the userId
     */
    public Long getUserId(){
        UserEntity user = this.getUser();
        if(user != null) return user.getId();
        else return null;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId){
        UserEntity user = new UserEntity();
        user.setId(userId);
        this.setUser(user);
        this.userId = userId;
    }

    /**
     * Fill all null/missing attributes from the current {@link PetEntity} petDomain instance with attributes from another {@link PetEntity} petDomain instance.
     *
     * @param petEntity the petDomain to take the attributes from
     */
    public void patch(PetEntity petEntity) {
        if (this.getName() == null) {
            this.setName(petEntity.getName());
        }

        if (this.getAge() < 1) {
            this.setAge(petEntity.getAge());
        }

        if (this.getUserId() == null) {
            this.setUserId(petEntity.getUserId());
        }

        this.setCreatedAt(petEntity.getCreatedAt());
    }
}