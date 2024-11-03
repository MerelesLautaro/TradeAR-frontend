package com.lautadev.tradear.dto;

import java.util.List;

public class ChatDTO {
    private Long id;
    private String name;
    private List<MessageDTO> messages;

    public ChatDTO() {
    }

    public ChatDTO(Long id, String name, List<MessageDTO> messages) {
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

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
