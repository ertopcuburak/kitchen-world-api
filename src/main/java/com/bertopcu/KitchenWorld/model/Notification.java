package com.bertopcu.KitchenWorld.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
public class Notification {
    int id;
    int userId;
    String type;
    int objectId;
    String message;
    int isRead;
    Integer triggerUser;
    int originSystem;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdTime;

    @Transient
    private transient User triggerUserObj;

    public Notification() {
    }

    public Notification(int id, int userId, String type, int objectId, String message, int isRead, Integer triggerUser, int originSystem, Date createdTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.objectId = objectId;
        this.message = message;
        this.isRead = isRead;
        this.triggerUser = triggerUser;
        this.originSystem = originSystem;
        this.createdTime = createdTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Integer getTriggerUser() {
        return triggerUser;
    }

    public void setTriggerUser(Integer triggerUser) {
        this.triggerUser = triggerUser;
    }

    public int getOriginSystem() {
        return originSystem;
    }

    public void setOriginSystem(int originSystem) {
        this.originSystem = originSystem;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Transient
    public User getTriggerUserObj() {
        return triggerUserObj;
    }

    public void setTriggerUserObj(User triggerUserObj) {
        this.triggerUserObj = triggerUserObj;
    }
}
