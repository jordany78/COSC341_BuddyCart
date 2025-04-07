package com.example.cosc341_buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<ShoppingItem> itemsList;
    private final OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEditItemClick(ShoppingItem item, int position);
        void onDeleteItemClick(ShoppingItem item, int position);
    }

    public ItemsAdapter(@NonNull List<ShoppingItem> itemsList, @NonNull OnItemActionListener listener) {
        this.itemsList = itemsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ShoppingItem item = itemsList.get(position);
        holder.bind(item, position, listener);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName;
        private final TextView itemDetails;
        private final ImageButton editButton;
        private final ImageButton deleteButton;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDetails = itemView.findViewById(R.id.item_details);
            editButton = itemView.findViewById(R.id.edit_item_button);
            deleteButton = itemView.findViewById(R.id.delete_item_button);
        }

        void bind(ShoppingItem item, int position, OnItemActionListener listener) {
            itemName.setText(item.getName());
            itemDetails.setText(buildItemDetails(item));

            editButton.setOnClickListener(v -> listener.onEditItemClick(item, position));
            deleteButton.setOnClickListener(v -> listener.onDeleteItemClick(item, position));
        }

        private String buildItemDetails(ShoppingItem item) {
            StringBuilder details = new StringBuilder("Qty: ").append(item.getQuantity());
            if (!item.getNotes().isEmpty()) {
                details.append(" | Notes: ").append(item.getNotes());
            }
            return details.toString();
        }
    }
}