package com.lautadev.tradear.model;

import java.util.List;

public class UserSec {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Account account;
    private List<Exchange> exchanges;
    private List<Message> messages;
    private Inventory inventory;

    public UserSec() {
    }

    public UserSec(Long id, String name, String lastname, String email, Account account, List<Exchange> exchanges, List<Message> messages, Inventory inventory) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.account = account;
        this.exchanges = exchanges;
        this.messages = messages;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
