package com.lautadev.tradear.model;

public class Permission {
    private Long id;
    private String permission;

    public Permission() {
    }

    public Permission(Long id, String permission) {
        this.id = id;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
