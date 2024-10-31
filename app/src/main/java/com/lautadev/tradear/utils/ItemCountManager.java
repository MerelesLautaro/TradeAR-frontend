package com.lautadev.tradear.utils;

public class ItemCountManager {
    private static ItemCountManager instance;
    private int itemCount;

    private ItemCountManager() {}

    public static ItemCountManager getInstance() {
        if (instance == null) {
            instance = new ItemCountManager();
        }
        return instance;
    }

    public void setItemCount(int count) {
        this.itemCount = count;
    }

    public int getItemCount() {
        return itemCount;
    }
}
