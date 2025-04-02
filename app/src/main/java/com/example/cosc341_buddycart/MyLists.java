package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyLists extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListsAdapter adapter;
    private List<ShoppingList> lists = new ArrayList<>();
    private TextView emptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        findViewById(R.id.btn_back_my_lists).setOnClickListener(v -> {
            onBackPressed(); // This provides the proper back navigation animation
        });

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView_lists);
        emptyStateView = findViewById(R.id.text_empty_state);

        // Setup RecyclerView
        adapter = new ListsAdapter(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Create List Button
        findViewById(R.id.button_create_new_list).setOnClickListener(v -> {
            startActivityForResult(new Intent(this, CreateList.class), 1);
        });

        updateEmptyState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ShoppingList newList = (ShoppingList) data.getSerializableExtra("newList");
            lists.add(newList);
            adapter.notifyDataSetChanged();
            updateEmptyState();
        }
    }

    private void updateEmptyState() {
        emptyStateView.setVisibility(lists.isEmpty() ? View.VISIBLE : View.GONE);
    }
}