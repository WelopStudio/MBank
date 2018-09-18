package com.welop.mbank;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class LobbyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private Toolbar toolbar;
    private CircleImageView accountStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        toolbar = findViewById(R.id.lobby_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lobby Incredible");

        accountStatus = findViewById(R.id.lobby_account_status);

        recyclerView = (RecyclerView) findViewById(R.id.lobby_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardPlayerLobbyRecyclerAdapter();
        recyclerView.setAdapter(adapter);
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
                    Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Checked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    accountStatus.setImageResource(R.color.colorOffline);
                }
                else {
                    item.setChecked(true);
                    Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Unchecked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    accountStatus.setImageResource(R.color.colorOnline);
                }
                break;
            case R.id.lobby_menu_leave:
                Snackbar.make(findViewById(R.id.lobby_coordinatorLayout), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        return true;
    }
}
