package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StoreSelectionActivity extends AppCompatActivity {

    String location;
    String selectedStore = "";
    Button confirmButton;

    RadioButton radioWalmart, radioSuperstore, radioCostco, radioShoppers,
            radioIndependent, radioFreshco, radioSaveOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_selection);

        location = getIntent().getStringExtra("location");

        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setEnabled(true);

        radioWalmart = findViewById(R.id.radioWalmart);
        radioSuperstore = findViewById(R.id.radioSuperstore);
        radioCostco = findViewById(R.id.radioCostco);
        radioShoppers = findViewById(R.id.radioShoppers);
        radioIndependent = findViewById(R.id.radioIndependent);
        radioFreshco = findViewById(R.id.radioFreshco);
        radioSaveOn = findViewById(R.id.radioSaveOn);

        RadioButton[] allButtons = {
                radioWalmart, radioSuperstore, radioCostco, radioShoppers,
                radioIndependent, radioFreshco, radioSaveOn
        };

        View.OnClickListener selectionListener = v -> {

            for (RadioButton rb : allButtons) {
                rb.setChecked(false);
            }

            RadioButton clicked = (RadioButton) v;
            clicked.setChecked(true);
            confirmButton.setEnabled(true);

            int id = clicked.getId();
            if (id == R.id.radioWalmart) {
                selectedStore = "Walmart";
            } else if (id == R.id.radioSuperstore) {
                selectedStore = "Superstore";
            } else if (id == R.id.radioCostco) {
                selectedStore = "Costco";
            } else if (id == R.id.radioShoppers) {
                selectedStore = "Shoppers Drug Mart";
            } else if (id == R.id.radioIndependent) {
                selectedStore = "Independent Grocer";
            } else if (id == R.id.radioFreshco) {
                selectedStore = "FreshCo";
            } else if (id == R.id.radioSaveOn) {
                selectedStore = "Save-On-Foods";
            }
        };

        for (RadioButton rb : allButtons) {
            rb.setOnClickListener(selectionListener);
        }

        confirmButton.setOnClickListener(v -> {
            if (selectedStore.isEmpty()) {
                Toast.makeText(this, "Please select a store", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(StoreSelectionActivity.this, ConfirmationActivity.class);
                intent.putExtra("location", location);
                intent.putExtra("store", selectedStore);
                startActivity(intent);
            }
        });
    }
}