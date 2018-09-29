package com.welop.mbank.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.welop.mbank.MBank;
import com.welop.mbank.activities.StartActivity;
import com.welop.svlit.mbank.R;

public class AccountFragment extends Fragment {

    private TextView mName;
    private TextView mEmail;
    private TextView mSex;
    private TextView mDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        initializeViews(v);
        updateInfo();
        return v;
    }

    private void initializeViews(View v) {
        mName = v.findViewById(R.id.account_name);
        mEmail = v.findViewById(R.id.account_email);
        mSex = v.findViewById(R.id.account_sex);
        mDescription = v.findViewById(R.id.account_description);
    }

    private void updateInfo() {
        mName.setText(MBank.getUser().getName());
        mEmail.setText(MBank.getUser().getEmail());
        switch (MBank.getUser().getSex()) {
            case "male":
                mSex.setText("Male");
                break;
            case "female":
                mSex.setText("Female");
                break;
            default:
                mSex.setText("Not stated");
                break;
        }
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
                Snackbar.make(getActivity().findViewById(R.id.main_coordinator_layout), "Imagine you're editing info", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.account_menu_logout:
                Snackbar.make(getActivity().findViewById(R.id.account_constraintlayout), "Imagine you have left", Snackbar.LENGTH_LONG).show();

                //TODO Create separate method for this
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finishAffinity();
                break;
        }

        return true;
    }
}
