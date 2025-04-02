package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity {
    private EditText editTextItemName, editTextQuantity, editTextNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        findViewById(R.id.btn_back_add_item).setOnClickListener(v -> {
            finish();  // Close this activity and return to CreateListActivity
        });

        editTextItemName = findViewById(R.id.editText_item_name);
        editTextQuantity = findViewById(R.id.editText_quantity);
        editTextNotes = findViewById(R.id.editText_notes);

        findViewById(R.id.button_submit_item).setOnClickListener(v -> {
            String name = editTextItemName.getText().toString();
            String quantity = editTextQuantity.getText().toString();
            String notes = editTextNotes.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            ShoppingItem newItem = new ShoppingItem(name, quantity, notes);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newItem", newItem);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
