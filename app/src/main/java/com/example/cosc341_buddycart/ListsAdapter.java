package com.example.cosc341_buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
    private List<ShoppingList> lists;

    public ListsAdapter(List<ShoppingList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList list = lists.get(position);
        holder.textListName.setText(list.getName());
        holder.textItemCount.setText(list.getItems().size() + " items");
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textListName, textItemCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textListName = itemView.findViewById(R.id.text_list_name);
            textItemCount = itemView.findViewById(R.id.text_item_count);
        }
    }
}
