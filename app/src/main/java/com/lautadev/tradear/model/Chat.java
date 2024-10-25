package com.lautadev.tradear.model;

import java.util.List;

public class Chat {
    private Long id;
    private String name;
    private List<Message> messages;

    public Chat() {
    }

    public Chat(Long id, String name, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.messages = messages;
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
