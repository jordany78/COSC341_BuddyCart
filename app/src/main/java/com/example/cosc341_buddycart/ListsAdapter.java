package com.example.cosc341_buddycart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListViewHolder> {

    List<ShoppingList> lists;
    private OnListActionListener listener;
    private int selectedPosition = -1;

    public interface OnListActionListener {
        void onEditClick(ShoppingList list, int position);
        void onDeleteClick(ShoppingList list, int position);
    }

    public ListsAdapter(List<ShoppingList> lists, OnListActionListener listener) {
        this.lists = lists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ShoppingList list = lists.get(position);
        holder.listName.setText(list.getName());
        holder.itemCount.setText(list.getItems().size() + " items");
        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

        holder.editButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onEditClick(lists.get(pos), pos);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onDeleteClick(lists.get(pos), pos);
            }
        });
    }

    public ShoppingList getSelectedList() {
        return selectedPosition != -1 ? lists.get(selectedPosition) : null;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        TextView listName;
        TextView itemCount;
        ImageButton editButton;
        ImageButton deleteButton;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radio_button);
            listName = itemView.findViewById(R.id.list_name);
            itemCount = itemView.findViewById(R.id.item_count);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}