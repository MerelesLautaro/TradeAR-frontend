package com.lautadev.tradear.model;

import java.time.LocalDateTime;

// Clase de prueba para probar el GalleryAdapter
public class Item {
    private Long id;
    private LocalDateTime date;
    private String urlImg;
    private String user;
    private String description;

    public Item() {
    }

    public Item(Long id, LocalDateTime date, String urlImg, String user, String description) {
        this.id = id;
        this.date = date;
        this.urlImg = urlImg;
        this.user = user;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
