package com.example.warmmeal.fragment_home.view;

import java.security.PrivateKey;

public class HomeFragmentItem<T> {
    private T item;
    private ItemType itemType;


    public HomeFragmentItem(ItemType itemType, T item) {
        this.itemType = itemType;
        this.item = item;
    }

    public HomeFragmentItem(){}

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
