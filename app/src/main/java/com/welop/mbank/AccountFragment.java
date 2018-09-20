package com.welop.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.welop.svlit.mbank.R;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStorage;

    private TextView mName;
    private TextView mEmail;
    private TextView mSex;
    private TextView mDescription;
    private ProgressBar mProgressbar;
    private View mLoadingview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
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
        mLoadingview = v.findViewById(R.id.account_loading_view);

        mProgressbar.setVisibility(View.VISIBLE);
        mLoadingview.setVisibility(View.VISIBLE);

        DocumentReference docRef = mStorage.collection("accounts").document(mAuth.getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mProgressbar.setVisibility(View.INVISIBLE);
                mLoadingview.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mName.setText(document.getString("name"));
                        mEmail.setText(document.getString("email"));
                        switch (document.getString("sex")) {
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
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
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
