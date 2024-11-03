package com.lautadev.tradear.dto;

import com.lautadev.tradear.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public class ExchangeDTO {
    private Long id;
    private String date;
    private List<ItemDTO> itemOffered;
    private List<ItemDTO> itemRequested;
    private UserSecDTO issuingUser;
    private UserSecDTO receivingUser;
    private Status status;
    private ChatDTO chat;

    public ExchangeDTO() {
    }

    public ExchangeDTO(Long id, String date, List<ItemDTO> itemOffered, List<ItemDTO> itemRequested, UserSecDTO issuingUser, UserSecDTO receivingUser, Status status, ChatDTO chat) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItemDTO> getItemOffered() {
        return itemOffered;
    }

    public void setItemOffered(List<ItemDTO> itemOffered) {
        this.itemOffered = itemOffered;
    }

    public List<ItemDTO> getItemRequested() {
        return itemRequested;
    }

    public void setItemRequested(List<ItemDTO> itemRequested) {
        this.itemRequested = itemRequested;
    }

    public UserSecDTO getIssuingUser() {
        return issuingUser;
    }

    public void setIssuingUser(UserSecDTO issuingUser) {
        this.issuingUser = issuingUser;
    }

    public UserSecDTO getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(UserSecDTO receivingUser) {
        this.receivingUser = receivingUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ChatDTO getChat() {
        return chat;
    }

    public void setChat(ChatDTO chat) {
        this.chat = chat;
    }
}
