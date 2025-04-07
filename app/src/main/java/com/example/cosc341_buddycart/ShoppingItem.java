package com.example.cosc341_buddycart;

import java.io.Serializable;

public class ShoppingItem implements Serializable {
    private String name;
    private final String quantity;
    private final String notes;

    public ShoppingItem(String name, String quantity, String notes) {
        this.name = name != null ? name : "";
        this.quantity = quantity != null ? quantity : "";
        this.notes = notes != null ? notes : "";
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    // Setters with null checks
    public void setName(String name) {
        this.name = name != null ? name : "";
    }
}