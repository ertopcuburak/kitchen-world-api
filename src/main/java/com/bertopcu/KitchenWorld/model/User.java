package com.bertopcu.KitchenWorld.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    private int id;
    private String uName;
    private String email;
    @JsonIgnore
    private String pwd;
    private int type;
    private String firstName;
    private String lastName;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdDate;


    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedDate;

    private String updatedBy;

    public User() {
    }

    public User(int id, String uName, String email, String pwd, int type, String firstName, String lastName, java.util.Date createdDate, java.util.Date updatedDate, String updatedBy) {
        this.id = id;
        this.uName = uName;
        this.email = email;
        this.pwd = pwd;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(java.util.Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    //other setters and getters
}