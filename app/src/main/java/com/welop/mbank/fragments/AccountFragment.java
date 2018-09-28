package com.welop.mbank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.mbank.activities.StartActivity;
import com.welop.mbank.model.Account;
import com.welop.svlit.mbank.R;

public class AccountFragment extends Fragment {

    private FirebaseFirestore mStorage;
    private TextView mName;
    private TextView mEmail;
    private TextView mSex;
    private TextView mDescription;
    private ProgressBar mProgressbar;
    private View mLoadingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorage = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);

        mName = v.findViewById(R.id.account_name);
        mEmail = v.findViewById(R.id.account_email);
        mSex = v.findViewById(R.id.account_sex);
        mDescription = v.findViewById(R.id.account_description);
        mProgressbar = v.findViewById(R.id.account_progressbar);
        mLoadingView = v.findViewById(R.id.account_loading_view);

        loadAccount();
        return v;
    }

    private void loadAccount() {
        mProgressbar.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        DocumentReference docRef = mStorage.collection("accounts").document(FirebaseAuth.getInstance().getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mProgressbar.setVisibility(View.INVISIBLE);
                mLoadingView.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Account a = new Account(
                                FirebaseAuth.getInstance().getUid(),
                                document.getString("name"),
                                document.getString("email"),
                                document.getString("sex")
                        );
                        MBank.setUser(a);
                        updateInfo();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.main_coordinator_layout), "An error occured. Try to reload page.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
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
                //Snackbar.make(getActivity().findViewById(R.id.main_coordinatorLayout), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
