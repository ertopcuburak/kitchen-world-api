package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;

@Entity
@Table(name = "recipe_material")
public class RecipeMaterial {
    private int id;
    private int recipeId;
    private int materialId;
    private String quantity;
    @Column(insertable=false, updatable=false)
    private String materialName;
    @Column(insertable=false, updatable=false)
    private int isBanned;

    public RecipeMaterial() {}

    public RecipeMaterial(int id, int recipeId, int materialId, String quantity) {
        this.id = id;
        this.recipeId = recipeId;
        this.materialId = materialId;
        this.quantity = quantity;
    }

    public RecipeMaterial(int id, int recipeId, int materialId, String quantity, String materialName, int isBanned) {
        this.id = id;
        this.recipeId = recipeId;
        this.materialId = materialId;
        this.quantity = quantity;
        this.materialName = materialName;
        this.isBanned = isBanned;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(insertable=false, updatable=false)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(insertable=false, updatable=false)
    public int getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(int isBanned) {
        this.isBanned = isBanned;
    }
}
