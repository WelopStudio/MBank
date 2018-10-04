package com.welop.mbank.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.welop.svlit.mbank.R;

public class TransactionsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

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
    }
}
