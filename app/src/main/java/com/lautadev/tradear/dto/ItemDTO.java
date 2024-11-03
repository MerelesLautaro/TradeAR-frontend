package com.lautadev.tradear.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String date;
    private String link;
    private int amount;
    private String category;
    private UserSecDTO userSecDTO;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, String description, String date, String link, int amount, String category, UserSecDTO userSecDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.link = link;
        this.amount = amount;
        this.category = category;
        this.userSecDTO = userSecDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserSecDTO getUserSecDTO() {
        return userSecDTO;
    }

    public void setUserSecDTO(UserSecDTO userSecDTO) {
        this.userSecDTO = userSecDTO;
    }
}
