package com.welop.mbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.welop.mbank.adapters.PlayerLobbyRecyclerAdapter;
import com.welop.mbank.adapters.TransactionRecyclerAdapter;
import com.welop.svlit.mbank.R;

public class TransactionsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        initializeViews();
        initializeListeners();
        downloadTransactions();
    }

    private void downloadTransactions() {

    }

    private void initializeListeners() {

    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.transactions_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Transactions history");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.transactions_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TransactionRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
