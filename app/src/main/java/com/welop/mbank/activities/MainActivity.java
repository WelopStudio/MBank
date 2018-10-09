package com.welop.mbank.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.welop.mbank.MBank;
import com.welop.mbank.fragments.AccountFragment;
import com.welop.mbank.fragments.FriendsFragment;
import com.welop.mbank.fragments.LobbiesFragment;
import com.welop.mbank.interfaces.OnBackPressedListener;
import com.welop.mbank.model.Account;
import com.welop.svlit.mbank.R;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkAuth()) {
            setContentView(R.layout.activity_main);
            initializeViews();
            downloadData();
        }
    }

    private void downloadData() {
        final DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("accounts")
                .document(FirebaseAuth.getInstance().getUid());

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    MBank.setUser(new Account(
                            FirebaseAuth.getInstance().getUid(),
                            documentSnapshot.getString("name"),
                            documentSnapshot.getString("email"),
                            documentSnapshot.getString("sex")
                    ));
                }
            }
        });
    }

    private boolean checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
            return false;
        }
        return true;
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        BottomNavigationView navigation = findViewById(R.id.main_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.main_navigetion_lobbies);
        mCoordinatorLayout = findViewById(R.id.main_coordinator_layout);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof OnBackPressedListener) || !((OnBackPressedListener) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
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
