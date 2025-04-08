package com.buddyshopper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buddyshopper.databinding.ItemShoppingItemBinding
import com.buddyshopper.models.ShoppingItem

class ShoppingItemsAdapter(
    private val onItemSelected: (ShoppingItem, Boolean) -> Unit
) : ListAdapter<ShoppingItem, ShoppingItemsAdapter.ViewHolder>(ShoppingItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemShoppingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingItem) {
            binding.apply {
                itemName.text = item.name
                itemQuantity.text = "Quantity: ${item.quantity}"
                itemDescription.text = item.note
                itemImage.setImageResource(item.imageResource)
                itemTimePosted.text = item.timePosted

                checkbox.isChecked = item.isSelected
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    onItemSelected(item, isChecked)
                }

                root.setOnClickListener {
                    checkbox.isChecked = !checkbox.isChecked
                }
            }
        }
    }

    private class ShoppingItemDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }
    }
} 