package com.lautadev.tradear.model;

import java.time.LocalDate;

public class Item {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String link;
    private int amount;
    private Category category;

    private Inventory inventory;

    public Item() {
    }

    public Item(Long id, String name, String description, LocalDate date, String link, int amount, Category category, Inventory inventory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.link = link;
        this.amount = amount;
        this.category = category;
        this.inventory = inventory;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
