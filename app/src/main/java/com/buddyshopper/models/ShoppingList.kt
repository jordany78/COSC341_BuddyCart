package com.buddyshopper.models

data class ShoppingList(
    val listName: String,
    val store: String,
    val items: List<ShoppingItem>,
    val timePosted: String = "Just now",
    val totalItems: Int = items.size
) 