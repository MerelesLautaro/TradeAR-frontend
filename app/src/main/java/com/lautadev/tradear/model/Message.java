package com.lautadev.tradear.model;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private String content;
    private String timestamp;
    private UserSec sender;
    private Chat chat;

    public Message() {
    }

    public Message(Long id, String content, String timestamp, UserSec sender, Chat chat) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserSec getSender() {
        return sender;
    }

    public void setSender(UserSec sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
