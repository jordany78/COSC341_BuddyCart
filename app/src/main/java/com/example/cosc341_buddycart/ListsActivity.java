package com.example.cosc341_buddycart;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.util.List;

public class ListsActivity extends AppCompatActivity implements ListsAdapter.OnListActionListener {
    private static final int CREATE_LIST_REQUEST = 1;
    private static final int EDIT_LIST_REQUEST = 2;
    private ListsAdapter listsAdapter;
    private List<ShoppingList> shoppingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        setupToolbar();
        setupRecyclerView();
        setupCreateListButton();
        setupSubmitButton();
    }

    private void setupSubmitButton() {
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            ShoppingList selectedList = listsAdapter.getSelectedList();
            if (selectedList != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("SELECTED_LIST", new Gson().toJson(selectedList));
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Please select a list first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Lists");
        }
    }

    private void setupRecyclerView() {
        RecyclerView listsRecyclerView = findViewById(R.id.lists_recycler_view);
        listsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingLists = ShoppingListManager.getSavedLists(this);
        listsAdapter = new ListsAdapter(shoppingLists, this);
        listsRecyclerView.setAdapter(listsAdapter);
    }

    private void setupCreateListButton() {
        Button createListButton = findViewById(R.id.create_list_button);
        createListButton.setOnClickListener(v ->
                startActivityForResult(
                        new Intent(this, CreateListActivity.class),
                        CREATE_LIST_REQUEST
                )
        );
    }

    @Override
    public void onEditClick(ShoppingList list, int position) {
        try {
            Intent intent = new Intent(this, CreateListActivity.class)
                    .putExtra("EDIT_MODE", true)
                    .putExtra("LIST_POSITION", position)
                    .putExtra("LIST_DATA", new Gson().toJson(list));
            startActivityForResult(intent, EDIT_LIST_REQUEST);
        } catch (Exception e) {
            showErrorToast("Error opening list", e);
        }
    }

    @Override
    public void onDeleteClick(ShoppingList list, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete List")
                .setMessage("Delete " + list.getName() + "?")
                .setPositiveButton("Delete", (d, w) -> deleteList(position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteList(int position) {
        shoppingLists.remove(position);
        ShoppingListManager.saveAllLists(this, shoppingLists);
        listsAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "List deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            handleActivityResult(requestCode, data);
        }
    }

    private void handleActivityResult(int requestCode, Intent data) {
        try {
            if (requestCode == CREATE_LIST_REQUEST) {
                handleNewListResult(data);
            } else if (requestCode == EDIT_LIST_REQUEST) {
                handleEditListResult(data);
            }
        } catch (Exception e) {
            showErrorToast(requestCode == CREATE_LIST_REQUEST ?
                    "Error adding list" : "Error updating list", e);
        }
    }

    private void handleNewListResult(Intent data) throws Exception {
        ShoppingList newList = new Gson().fromJson(
                data.getStringExtra("NEW_LIST"),
                ShoppingList.class
        );
        if (newList != null) {
            shoppingLists.add(newList);
            ShoppingListManager.saveAllLists(this, shoppingLists);
            listsAdapter.notifyItemInserted(shoppingLists.size() - 1);
        }
    }

    private void handleEditListResult(Intent data) throws Exception {
        int position = data.getIntExtra("POSITION", -1);
        ShoppingList updatedList = new Gson().fromJson(
                data.getStringExtra("UPDATED_LIST"),
                ShoppingList.class
        );
        if (updatedList != null && position >= 0) {
            shoppingLists.set(position, updatedList);
            ShoppingListManager.saveAllLists(this, shoppingLists);
            listsAdapter.notifyItemChanged(position);
        }
    }

    private void showErrorToast(String message, Exception e) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLists();
    }

    private void refreshLists() {
        shoppingLists = ShoppingListManager.getSavedLists(this);
        listsAdapter.lists = shoppingLists;
        listsAdapter.notifyDataSetChanged();
    }
}