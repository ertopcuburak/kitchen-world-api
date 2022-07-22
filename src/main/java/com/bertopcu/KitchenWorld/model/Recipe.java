package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {
    private int id;
    private String name;
    private String description;
    private String howToMake;
    private int userId;

    @Transient
    private transient List<RecipeMaterial> materialList;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedDate;

    public Recipe() {}

    public Recipe(int id, String name, String description, String howToMake, int userId, Date createdDate, Date updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.howToMake = howToMake;
        this.userId = userId;
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

    public String getHowToMake() {
        return howToMake;
    }

    public void setHowToMake(String howToMake) {
        this.howToMake = howToMake;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Transient
    public List<RecipeMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<RecipeMaterial> materialList) {
        this.materialList = materialList;
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
