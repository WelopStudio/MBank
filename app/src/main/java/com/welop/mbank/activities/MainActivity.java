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
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.mbank.fragments.AccountFragment;
import com.welop.mbank.fragments.FriendsFragment;
import com.welop.mbank.fragments.LobbiesFragment;
import com.welop.mbank.interfaces.OnBackPressedListener;
import com.welop.mbank.model.Account;
import com.welop.svlit.mbank.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;
    private ProgressBar mProgressBar;
    private View mLoadingView;

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
        loading(true);
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("accounts")
                .document(FirebaseAuth.getInstance().getUid().toString());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        MBank.setUser(new Account(
                                FirebaseAuth.getInstance().getUid(),
                                document.getString("name"),
                                document.getString("email"),
                                document.getString("sex")
                        ));
                    }
                    loading(false);
                } else {
                    Snackbar.make(mCoordinatorLayout, "An error occured. Try to reload page.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
        mLoadingView.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);

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
        mProgressBar = findViewById(R.id.main_progress_bar);
        mLoadingView = findViewById(R.id.main_loading_view);
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
