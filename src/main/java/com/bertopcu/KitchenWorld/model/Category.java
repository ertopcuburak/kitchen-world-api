package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "category")
public class Category {
    private int id;
    private String name;
    private String description;
    private String imageUrl;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedDate;

    public Category() {
    }

    public Category(int id, String name, String description, String imageUrl, Date createdDate, Date updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
