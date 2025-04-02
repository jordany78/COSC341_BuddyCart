package com.example.cosc341_buddycart;

import java.io.Serializable;

public class ShoppingItem implements Serializable {
    private String name;
    private String quantity;
    private String notes;

    public ShoppingItem(String name, String quantity, String notes) {
        this.name = name;
        this.quantity = quantity;
        this.notes = notes;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getNotes() { return notes; }
}