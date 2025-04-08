package com.buddyshopper.data

import com.buddyshopper.R
import com.buddyshopper.adapters.ShoppingItemsAdapter
import com.buddyshopper.models.ShoppingItem
import com.buddyshopper.models.ShoppingList

object MockDataProvider {
    val shoppingLists = listOf(
        ShoppingList(
            listName = "Saksham's Grocery List",
            store = "Walmart",
            items = listOf(
                ShoppingItem(
                    name = "Milk",
                    quantity = "2 gallons",
                    note = "Whole milk",
                    imageResource = R.drawable.ic_milk,
                    timePosted = "2h ago"
                ),
                ShoppingItem(
                    name = "Bananas",
                    quantity = "6",
                    note = "Ripe bananas",
                    imageResource = R.drawable.ic_cheese,
                    timePosted = "4h ago"
                ),
                ShoppingItem(
                    name = "Eggs",
                    quantity = "1 dozen",
                    note = "Large eggs",
                    imageResource = R.drawable.ic_eggs,
                    timePosted = "1h ago"
                )
            )
        ),
        ShoppingList(
            listName = "Mike's Weekly Shop",
            store = "Target",
            items = listOf(
                ShoppingItem(
                    name = "eggs",
                    quantity = "5",
                    note = "Big eggs",
                    imageResource = R.drawable.ic_eggs,
                    timePosted = "3h ago"
                ),
                ShoppingItem(
                    name = "Cheese",
                    quantity = "1 block",
                    note = "Cheddar cheese",
                    imageResource = R.drawable.ic_cheese,
                    timePosted = "2h ago"
                )
            )
        ),
        ShoppingList(
            listName = "Sarah's Essentials",
            store = "Kroger",
            items = listOf(
                ShoppingItem(
                    name = "Bread",
                    quantity = "2 loaves",
                    note = "Whole wheat bread",
                    imageResource = R.drawable.ic_bread,
                    timePosted = "5h ago"
                ),
                ShoppingItem(
                    name = "golden eggs",
                    quantity = "1 bag",
                    note = "Brown rice",
                    imageResource = R.drawable.ic_eggs,
                    timePosted = "4h ago"
                )
            )
        )
    )

    fun getShoppingItemsAdapter(): ShoppingItemsAdapter {
        return ShoppingItemsAdapter { item, isSelected ->
            // Handle item selection here
            item.isSelected = isSelected
        }
    }
} 