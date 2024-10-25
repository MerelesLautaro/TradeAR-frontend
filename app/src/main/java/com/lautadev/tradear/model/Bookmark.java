package com.lautadev.tradear.model;

import java.util.List;

public class Bookmark {
    private Long id;
    private List<Item> items;
    private UserSec userSec;

    public Bookmark() {
    }

    public Bookmark(Long id, List<Item> items, UserSec userSec) {
        this.id = id;
        this.items = items;
        this.userSec = userSec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public UserSec getUserSec() {
        return userSec;
    }

    public void setUserSec(UserSec userSec) {
        this.userSec = userSec;
    }
}
