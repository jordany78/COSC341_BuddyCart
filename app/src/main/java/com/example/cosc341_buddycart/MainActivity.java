package com.example.cosc341_buddycart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText locationInput;
    Button nextButton, useLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInput = findViewById(R.id.locationInput);
        nextButton = findViewById(R.id.nextButton);
        useLocationButton = findViewById(R.id.useLocationButton); // new button

        nextButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString().trim();
            if (location.isEmpty()) {
                Toast.makeText(this, "Please enter your location", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, StoreSelectionActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });

        useLocationButton.setOnClickListener(v -> {
            locationInput.setText("123 Main St, Vancouver, BC");
        });
    }
}
