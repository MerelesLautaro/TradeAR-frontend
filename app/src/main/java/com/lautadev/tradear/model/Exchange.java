package com.lautadev.tradear.model;

import java.time.LocalDate;
import java.util.List;

public class Exchange {
    private Long id;
    private LocalDate date;
    private List<Item> itemOffered;
    private List<Item> itemRequested;
    private UserSec issuingUser;
    private UserSec receivingUser;
    private Status status;
    private Chat chat;

    public Exchange() {
    }

    public Exchange(Long id, LocalDate date, List<Item> itemOffered, List<Item> itemRequested, UserSec issuingUser, UserSec receivingUser, Status status, Chat chat) {
        this.id = id;
        this.date = date;
        this.itemOffered = itemOffered;
        this.itemRequested = itemRequested;
        this.issuingUser = issuingUser;
        this.receivingUser = receivingUser;
        this.status = status;
        this.chat = chat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Item> getItemOffered() {
        return itemOffered;
    }

    public void setItemOffered(List<Item> itemOffered) {
        this.itemOffered = itemOffered;
    }

    public List<Item> getItemRequested() {
        return itemRequested;
    }

    public void setItemRequested(List<Item> itemRequested) {
        this.itemRequested = itemRequested;
    }

    public UserSec getIssuingUser() {
        return issuingUser;
    }

    public void setIssuingUser(UserSec issuingUser) {
        this.issuingUser = issuingUser;
    }

    public UserSec getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(UserSec receivingUser) {
        this.receivingUser = receivingUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
