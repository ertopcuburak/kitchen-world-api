package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "favorite")
public class Favorite {

    int id;
    int userId;
    int recipeId;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date favDate;

    public Favorite() {

    }

    public Favorite(int id, int userId, int recipeId, Date favDate) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.favDate = favDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Date getFavDate() {
        return favDate;
    }

    public void setFavDate(Date favDate) {
        this.favDate = favDate;
    }
}
