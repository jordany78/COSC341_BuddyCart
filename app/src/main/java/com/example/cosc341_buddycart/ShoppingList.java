package com.example.cosc341_buddycart;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShoppingList implements Serializable {
    private String name;
    private List<ShoppingItem> items;
    private Date createdDate;

    public ShoppingList(String name, List<ShoppingItem> items) {
        this.name = name;
        this.items = items;
        this.createdDate = new Date();
    }

    // Getters
    public String getName() { return name; }
    public List<ShoppingItem> getItems() { return items; }
    public Date getCreatedDate() { return createdDate; }
}