package com.buddyshopper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buddyshopper.R
import com.buddyshopper.models.ShoppingList
import com.google.android.material.button.MaterialButton

class ShoppingListsAdapter(
    private val lists: List<ShoppingList>,
    private val onListClick: (ShoppingList) -> Unit
) : RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listNameText: TextView = view.findViewById(R.id.listNameText)
        val storeText: TextView = view.findViewById(R.id.storeText)
        val viewDetailsButton: MaterialButton = view.findViewById(R.id.viewDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = lists[position]
        holder.listNameText.text = list.listName
        holder.storeText.text = list.store
        holder.viewDetailsButton.setOnClickListener { onListClick(list) }
    }

    override fun getItemCount() = lists.size
} 