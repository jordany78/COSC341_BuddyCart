package com.buddyshopper.models

data class ShoppingItem(
    val name: String,
    val quantity: String,
    val note: String = "",
    val imageResource: Int,
    var isSelected: Boolean = false,
    val timePosted: String = "Just now"
) 