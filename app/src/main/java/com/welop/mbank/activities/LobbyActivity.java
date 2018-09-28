package com.welop.mbank.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.welop.mbank.adapters.CardPlayerLobbyRecyclerAdapter;
import com.welop.svlit.mbank.R;

public class LobbyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        mToolbar = findViewById(R.id.lobby_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Test Lobby Name");

        mRecyclerView = findViewById(R.id.lobby_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardPlayerLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.lobby_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.lobby_menu_ready).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.lobby_menu_ready:
                if (item.isChecked()) {
                    item.setChecked(false);
                    // TODO: Этот код не работает
                    Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Checked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                else {
                    item.setChecked(true);
                    Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Unchecked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                break;
            case R.id.lobby_menu_leave:
                Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        return true;
    }
}
