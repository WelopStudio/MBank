package com.welop.mbank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.welop.mbank.fragments.AccountFragment;
import com.welop.mbank.fragments.FriendsFragment;
import com.welop.mbank.fragments.LobbiesFragment;
import com.welop.svlit.mbank.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.main_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.main_navigetion_lobbies);

        auth = FirebaseAuth.getInstance();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
            //
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.main_navigation_account:
                    getSupportActionBar().setTitle("Account settings");
                    selectedFragment = new AccountFragment();
                    break;
                case R.id.main_navigetion_lobbies:
                    getSupportActionBar().setTitle("Lobbies");
                    selectedFragment = new LobbiesFragment();
                    break;
                case R.id.main_navigation_friends:
                    getSupportActionBar().setTitle("Friends");
                    selectedFragment = new FriendsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, selectedFragment).commit();

            return true;
        }
    };

}
