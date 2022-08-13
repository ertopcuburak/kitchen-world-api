package com.bertopcu.KitchenWorld.model;

public class Login {

    private String identifier;
    private String pwd;

    public Login() {}

    public Login(String identifier, String pwd) {
        this.identifier = identifier;
        this.pwd = pwd;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
