package com.example.cosc341_buddycart;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    TextView confirmationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        String location = getIntent().getStringExtra("location");
        String store = getIntent().getStringExtra("store");

        confirmationText = findViewById(R.id.confirmationText);
        confirmationText.setText("Grocery Run Requested!\nLocation: " + location + "\nStore: " + store);
    }
}
