package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.Gson;

public class AddItemActivity extends AppCompatActivity {
    private boolean isEditMode = false;
    private int editPosition = -1;
    private EditText itemNameEditText, quantityEditText, notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        setupToolbar();
        initializeViews();
        handleEditMode();
        setupButtonClickListener();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(isEditMode ? "Edit Item" : "Add Item");
        }
    }

    private void initializeViews() {
        itemNameEditText = findViewById(R.id.item_name_edit_text);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        notesEditText = findViewById(R.id.notes_edit_text);
    }

    private void handleEditMode() {
        if (getIntent() != null && getIntent().hasExtra("EDIT_ITEM_MODE")) {
            isEditMode = getIntent().getBooleanExtra("EDIT_ITEM_MODE", false);
            editPosition = getIntent().getIntExtra("ITEM_POSITION", -1);
            String itemJson = getIntent().getStringExtra("ITEM_DATA");

            try {
                ShoppingItem existingItem = new Gson().fromJson(itemJson, ShoppingItem.class);
                if (existingItem != null) {
                    populateFields(existingItem);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error loading item data", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void populateFields(ShoppingItem item) {
        itemNameEditText.setText(item.getName());
        quantityEditText.setText(item.getQuantity());
        notesEditText.setText(item.getNotes());
    }

    private void setupButtonClickListener() {
        findViewById(R.id.add_item_button).setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String itemName = itemNameEditText.getText().toString().trim();
        String quantity = quantityEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        // Validate item name
        if (itemName.isEmpty()) {
            itemNameEditText.setError("Item name is required");
            itemNameEditText.requestFocus();
            return;
        }

        // Validate item quantity
        if (quantity.isEmpty()) {
            quantityEditText.setError("Quantity is required");
            quantityEditText.requestFocus();
            return;
        }

        Intent resultIntent = new Intent()
                .putExtra("ITEM_NAME", itemName)
                .putExtra("QUANTITY", quantity)
                .putExtra("NOTES", notes);

        if (isEditMode) {
            resultIntent.putExtra("ITEM_POSITION", editPosition).putExtra("UPDATED_ITEM", new Gson().toJson(new ShoppingItem(itemName, quantity, notes)));
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}