package com.lautadev.tradear.model;

import java.util.List;

public class Inventory {
    private Long id;
    private UserSec userSec;
    private List<Item> items;

    public Inventory() {
    }

    public Inventory(Long id, UserSec userSec, List<Item> items) {
        this.id = id;
        this.userSec = userSec;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSec getUserSec() {
        return userSec;
    }

    public void setUserSec(UserSec userSec) {
        this.userSec = userSec;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
