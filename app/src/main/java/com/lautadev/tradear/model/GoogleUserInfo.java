package com.lautadev.tradear.model;

public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
    private String lastname;
    private String pictureUrl;

    public GoogleUserInfo() {
    }

    public GoogleUserInfo(String id, String email, String name, String lastname, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.pictureUrl = pictureUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}