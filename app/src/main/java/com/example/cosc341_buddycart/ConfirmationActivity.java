package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    TextView confirmationText;
    Button goToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        String location = getIntent().getStringExtra("location");
        String store = getIntent().getStringExtra("store");

        confirmationText = findViewById(R.id.confirmationText);
        confirmationText.setText("Grocery Run Requested!\nLocation: " + location + "\nStore: " + store);
        goToMainButton = findViewById(R.id.goToMainButton);  // Define a button in your layout
        goToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close ConfirmationActivity so it is removed from the stack
        });
    }
}
