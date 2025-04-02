package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CreateList extends AppCompatActivity {
    private EditText editTextListName;
    private RecyclerView recyclerViewItems;
    private List<ShoppingItem> itemList = new ArrayList<>();
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        findViewById(R.id.btn_back_create_list).setOnClickListener(v -> {
            finish();  // Close this activity and return to MainActivity
        });

        editTextListName = findViewById(R.id.editText_list_name);
        recyclerViewItems = findViewById(R.id.recyclerView_items);

        // Setup RecyclerView
        adapter = new ItemsAdapter(itemList);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(adapter);

        // Add Item Button
        findViewById(R.id.button_add_item).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddItem.class);
            startActivityForResult(intent, 1);
        });

        // Submit List Button
        findViewById(R.id.button_submit_list).setOnClickListener(v -> {
            String listName = editTextListName.getText().toString();
            if (!listName.isEmpty()) {
                ShoppingList newList = new ShoppingList(listName, itemList);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newList", newList);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ShoppingItem newItem = (ShoppingItem) data.getSerializableExtra("newItem");
            itemList.add(newItem);
            adapter.notifyDataSetChanged();
        }
    }

    private void saveShoppingList(String name, List<ShoppingItem> items) {
        // Implement your save logic here
        Toast.makeText(this, "List saved: " + name, Toast.LENGTH_SHORT).show();
        finish();
    }
}
