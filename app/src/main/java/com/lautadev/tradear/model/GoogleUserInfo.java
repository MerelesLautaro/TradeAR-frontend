package com.lautadev.tradear.model;

public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
    private String lastname;

    public GoogleUserInfo() {
    }

    public GoogleUserInfo(String idToken, String email, String name, String lastname) {
        this.id = idToken;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}