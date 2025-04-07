package com.example.cosc341_buddycart;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {
    private String name;
    private final List<ShoppingItem> items;

    public ShoppingList(String name, List<ShoppingItem> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShoppingItem> getItems() {
        return items;
    }
}