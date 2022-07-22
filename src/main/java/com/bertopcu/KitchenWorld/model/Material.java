package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "material")
public class Material {
    private int id;
    private String name;
    private String description;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedDate;

    private String updatedBy;

    public Material() {

    }

    public Material(int id, String name, String description, Date createdDate, Date updatedDate, String updatedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
