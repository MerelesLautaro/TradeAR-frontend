package com.lautadev.tradear.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String content;
    private String timestamp;
    private UserSecDTO sender;

    public MessageDTO() {
    }

    public MessageDTO(Long id, String content, String timestamp, UserSecDTO sender) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
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

    public UserSecDTO getSender() {
        return sender;
    }

    public void setSender(UserSecDTO sender) {
        this.sender = sender;
    }
}
