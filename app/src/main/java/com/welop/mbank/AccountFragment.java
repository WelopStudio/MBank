package com.welop.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.welop.svlit.mbank.R;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.account_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.account_menu_edit:
                Snackbar.make(getActivity().findViewById(R.id.main_coordinatorLayout), "Imagine you're editing info", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.account_menu_logout:
                //Snackbar.make(getActivity().findViewById(R.id.main_coordinatorLayout), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(getActivity().findViewById(R.id.account_constraintlayout), "Imagine you have left", Snackbar.LENGTH_LONG).show();

                //TODO Create separate method for this
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finishAffinity();
                break;
        }

        return true;
    }
}
