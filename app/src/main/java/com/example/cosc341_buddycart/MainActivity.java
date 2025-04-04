package com.example.cosc341_buddycart;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomMenuBar;

    private Fragment buddyShopperHome;
    private Fragment remoteShopperHome;
    private Fragment chatHome;

    private boolean hasNotification = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomMenuBar = findViewById(R.id.bottom_navigation);
        bottomMenuBar.setItemIconTintList(null); // Notification isn't red otherwise

        buddyShopperHome = new BuddyShopperHome();
        remoteShopperHome = new RemoteShopperHome();
        chatHome = new ChatHome();

        bottomMenuBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();

                if (id == R.id.menu_buddy_shopper) {

                    selectedFragment = buddyShopperHome;

                } else if (id == R.id.menu_remote_shopper) {

                    selectedFragment = remoteShopperHome;

                } else if (id == R.id.menu_chat) {

                    selectedFragment = chatHome;

                    // Reset chat icon
                    hasNotification = false;
                    updateChatIcon();

                }

                if (selectedFragment != null) {
                    showFragment(selectedFragment);
                }

                return true;

            }
        });

        // Initialization
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, buddyShopperHome)
                    .add(R.id.fragment_container, chatHome)
                    .add(R.id.fragment_container, remoteShopperHome)
                    .hide(buddyShopperHome) // Only show remote fragment on initialization
                    .hide(chatHome)
                    .commit();
        }
    }

    private void showFragment(Fragment selectedFragment) {
        // Hide other fragments
        getSupportFragmentManager().beginTransaction().hide(buddyShopperHome).hide(remoteShopperHome).hide(chatHome).commit();

        // Show selected
        getSupportFragmentManager().beginTransaction().show(selectedFragment).commit();
    }

    public void listApproved() { // EXAMPLE FUNCTION FOR LIST APPROVAL
        hasNotification = true;
        updateChatIcon();
    }

    private void updateChatIcon() {
        MenuItem chatIcon = bottomMenuBar.getMenu().findItem(R.id.menu_chat);
        if (hasNotification) {
            chatIcon.setIcon(R.drawable.chat_notification);
        } else {
            chatIcon.setIcon(R.drawable.chat);
        }
    }
}