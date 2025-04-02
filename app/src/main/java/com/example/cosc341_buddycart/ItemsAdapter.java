package com.example.cosc341_buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<ShoppingItem> itemList;

    public ItemsAdapter(List<ShoppingItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ShoppingItem item = itemList.get(position);
        holder.textViewItemName.setText(item.getName());
        holder.textViewQuantity.setText("Qty: " + item.getQuantity());
        holder.textViewNotes.setText(item.getNotes().isEmpty() ? "" : "Notes: " + item.getNotes());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewQuantity, textViewNotes;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textView_item_name);
            textViewQuantity = itemView.findViewById(R.id.textView_quantity);
            textViewNotes = itemView.findViewById(R.id.textView_notes);
        }
    }
}