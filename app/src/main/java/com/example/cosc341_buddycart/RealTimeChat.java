package com.example.cosc341_buddycart;

import static android.app.PendingIntent.getActivity;
import static android.text.TextUtils.isEmpty;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RealTimeChat extends AppCompatActivity {

    private EditText messageEditText;
    private TextView chatNameText;

    private Button sendButton;
    private ImageButton backButton;

    private RecyclerView recyclerView;
    private MessageListAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();

    private DatabaseReference databaseReference;
    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_real_time_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            chatId = bundle.getString("chatId");
        }

        messageEditText = findViewById(R.id.editTextForTexting);

        backButton = findViewById(R.id.backButton);
        sendButton = findViewById(R.id.sendButton);

        chatNameText = findViewById(R.id.chatNameText);

        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("messages").child(chatId);

        chatNameText.setText(chatId);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);

                if (message != null) {
                    messageList.add(message);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);

                    // Scroll to bottom w/ new message
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("RealTimeChat", "loadPost:onCancelled", error.toException());
            }
        });

        // Set up back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Send button listener
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    // User message is sent!
    private void sendMessage() {
        String text = messageEditText.getText().toString().trim();
        if (isEmpty(text)) {
            Toast.makeText(this, "Empty msg.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = "2";  // FirebaseAuth.getInstance().getCurrentUser().getUid();
        String currentUserName = "User Name"; // Replace with user name

        Message message = new Message(text, currentUserId, currentUserName, System.currentTimeMillis());

        // Push to firebase database
        databaseReference.push().setValue(message);

        // Reset edit text
        messageEditText.setText("");
    }

    public void beginTransaction(View view)
    {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }


    public void reportConversation(View view) {

        // 1. Instantiate an AlertDialog.Builder with its constructor.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater.
        LayoutInflater inflater = LayoutInflater.from(this);

        View dialogView = inflater.inflate(R.layout.dialog_report_user, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Dialog background set to transparent, so rounded corners show
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

        Button confirmButton = (Button) dialogView.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(v.getContext(), "User reported.", Toast.LENGTH_SHORT).show();
                // Actually reporting users is beyond the scope of this project.

            }
        });

        dialog.show();


    }
}
