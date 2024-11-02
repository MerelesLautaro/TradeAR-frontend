package com.lautadev.tradear.dto;

public class UserSecDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private int itemCount;

    public UserSecDTO() {
    }

    public UserSecDTO(Long id, String name, String lastname, String email, int itemCount) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.itemCount = itemCount;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
