package com.welop.mbank.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.mbank.model.Transaction;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TransactionDialog extends AppCompatDialogFragment {
    private View mCoordinatorLayout;
    private Snackbar mSnackbar;
    private TextView mAmount;
    private Wallet mPayerWallet;
    private Wallet mReceiverWallet;

    public Wallet getPayerWallet() {
        return mPayerWallet;
    }

    public void setPayerWallet(Wallet payerWallet) {
        mPayerWallet = payerWallet;
    }

    public Wallet getReceiverWallet() {
        return mReceiverWallet;
    }

    public void setReceiverWallet(Wallet receiverWallet) {
        mReceiverWallet = receiverWallet;
    }

    public View getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    public void setCoordinatorLayout(View mCoordinatorLayout) {
        this.mCoordinatorLayout = mCoordinatorLayout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_transaction, null);

        builder.setView(v)
                .setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSnackbar = Snackbar.make(getCoordinatorLayout(), "Processing...", Snackbar.LENGTH_INDEFINITE);
                        mSnackbar.show();
                        updateData();

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mAmount = v.findViewById(R.id.transaction_dialog_amount);
        return builder.create();
    }

    private void updateData() {
        Long amount = Long.parseLong(mAmount.getText().toString());
        Wallet payer = getPayerWallet();
        Wallet receiver = getReceiverWallet();
        // 1. Объект кошелька плательщика: -сумма
        payer.setBalance(payer.getBalance() - amount);
        // 2. Объект кошелька получателя: +сумма
        receiver.setBalance(receiver.getBalance() + amount);
        // 3. Новый объект транзакции
        Transaction transaction = new Transaction();
        transaction.setPayerWalletName(payer.getName());
        transaction.setPayerAccountName(payer.getOwnerName());
        transaction.setReceiverWalletName(receiver.getName());
        transaction.setReceiverAccountName(receiver.getOwnerName());
        transaction.setAmount(amount);
        transaction.setCreatedAt(new com.google.firebase.Timestamp(new Timestamp(new Date().getTime())));

        // 4. Загрузить объект кошелька плательщика
        DocumentReference payerWallet = FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(payer.getId());
        payerWallet
                .update("balance", payer.getBalance());

        // 5. Загрузить объект кошелька получателя
        DocumentReference receiverWallet = FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(receiver.getId());
        receiverWallet
                .update("balance", receiver.getBalance());

        // 6. Загрузить объект транзакции
        HashMap<String, Object> transactionData = new HashMap<>();

        transactionData.put("amount", transaction.getAmount());
        transactionData.put("payer_wallet_name", transaction.getPayerWalletName());
        transactionData.put("payer_account_name", transaction.getPayerAccountName());
        transactionData.put("receiver_wallet_name", transaction.getReceiverWalletName());
        transactionData.put("receiver_account_name", transaction.getReceiverAccountName());
        transactionData.put("created_at", transaction.getCreatedAt());
        DocumentReference transactionRef = FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(MBank.getLobby().getId())
                .collection("transactions")
                .document(Integer.toString(MBank.getLobby().getTransactions().size()));
        transactionRef
                .set(transactionData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        mSnackbar.dismiss();
                    }
                });
    }
}
