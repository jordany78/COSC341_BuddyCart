package com.example.cosc341_buddycart;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class CreateListActivity extends AppCompatActivity implements ItemsAdapter.OnItemActionListener {
    private static final int ADD_ITEM_REQUEST = 1;
    private static final int EDIT_ITEM_REQUEST = 2;

    private EditText listNameEditText;
    private final List<ShoppingItem> itemsList = new ArrayList<>();
    private ItemsAdapter itemsAdapter;
    private boolean isEditMode = false;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        setupToolbar();
        initializeViews();
        handleEditMode();
        setupRecyclerView();
        setupButtonClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create New List");
        }
    }

    private void initializeViews() {
        listNameEditText = findViewById(R.id.list_name_edit_text);
    }

    private void handleEditMode() {
        if (getIntent() != null && getIntent().hasExtra("EDIT_MODE")) {
            isEditMode = getIntent().getBooleanExtra("EDIT_MODE", false);
            editPosition = getIntent().getIntExtra("LIST_POSITION", -1);
            String listJson = getIntent().getStringExtra("LIST_DATA");

            try {
                ShoppingList originalList = new Gson().fromJson(listJson, ShoppingList.class);
                if (originalList != null) {
                    listNameEditText.setText(originalList.getName());
                    if (originalList.getItems() != null) {
                        itemsList.addAll(originalList.getItems());
                    }
                    Button createButton = findViewById(R.id.create_list_button);
                    createButton.setText("Update List");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error loading list data", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setupRecyclerView() {
        RecyclerView itemsRecyclerView = findViewById(R.id.items_recycler_view);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsAdapter = new ItemsAdapter(itemsList, this);
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    private void setupButtonClickListeners() {
        findViewById(R.id.add_item_button).setOnClickListener(v ->
                startActivityForResult(new Intent(this, AddItemActivity.class),ADD_ITEM_REQUEST)
        );

        findViewById(R.id.create_list_button).setOnClickListener(v ->
                saveShoppingList()
        );
    }

    @Override
    public void onEditItemClick(ShoppingItem item, int position) {
        Intent intent = new Intent(this, AddItemActivity.class).putExtra("EDIT_ITEM_MODE", true).putExtra("ITEM_POSITION", position).putExtra("ITEM_DATA", new Gson().toJson(item));
        startActivityForResult(intent, EDIT_ITEM_REQUEST);
    }

    @Override
    public void onDeleteItemClick(ShoppingItem item, int position) {
        new AlertDialog.Builder(this).setTitle("Delete Item").setMessage("Delete " + item.getName() + "?").setPositiveButton("Delete", (d, w) -> deleteItem(position)).setNegativeButton("Cancel", null).show();
    }

    private void deleteItem(int position) {
        itemsList.remove(position);
        itemsAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            handleActivityResult(requestCode, data);
        }
    }

    private void handleActivityResult(int requestCode, Intent data) {
        try {
            if (requestCode == ADD_ITEM_REQUEST) {
                handleNewItemResult(data);
            } else if (requestCode == EDIT_ITEM_REQUEST) {
                handleEditItemResult(data);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error processing item", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNewItemResult(Intent data) {
        ShoppingItem newItem = new ShoppingItem(data.getStringExtra("ITEM_NAME"),data.getStringExtra("QUANTITY"),data.getStringExtra("NOTES"));
        itemsList.add(newItem);
        itemsAdapter.notifyItemInserted(itemsList.size() - 1);
    }

    private void handleEditItemResult(Intent data) {
        int position = data.getIntExtra("ITEM_POSITION", -1);
        ShoppingItem updatedItem = new Gson().fromJson(data.getStringExtra("UPDATED_ITEM"),ShoppingItem.class);
        if (position >= 0 && position < itemsList.size()) {
            itemsList.set(position, updatedItem);
            itemsAdapter.notifyItemChanged(position);
        }
    }

    private void saveShoppingList() {
        String listName = listNameEditText.getText().toString().trim();
        if (listName.isEmpty()) {
            listNameEditText.setError("Please enter a list name");
            return;
        }

        ShoppingList updatedList = new ShoppingList(listName, new ArrayList<>(itemsList));
        Intent resultIntent = new Intent().putExtra(isEditMode ? "UPDATED_LIST" : "NEW_LIST", new Gson().toJson(updatedList));

        if (isEditMode) {
            resultIntent.putExtra("POSITION", editPosition);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}