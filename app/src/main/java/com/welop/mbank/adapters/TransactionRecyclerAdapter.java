package com.welop.mbank.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welop.mbank.MBank;
import com.welop.mbank.model.Transaction;
import com.welop.svlit.mbank.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPayerWalletName;
        private TextView mPayerAccountName;
        private TextView mReceiverWalletName;
        private TextView mReceiverAccountName;
        private TextView mAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            mPayerWalletName = itemView.findViewById(R.id.transaction_payer_wallet_name);
            mPayerAccountName = itemView.findViewById(R.id.transaction_payer_account_name);
            mReceiverWalletName = itemView.findViewById(R.id.transaction_receiver_wallet_name);
            mReceiverAccountName = itemView.findViewById(R.id.transaction_receiver_account_name);
            mAmount = itemView.findViewById(R.id.transaction_amount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = MBank.getLobby().getTransactions().get(position);
        holder.mAmount.setText(Integer.toString(transaction.getAmount()));
        holder.mPayerWalletName.setText(transaction.getPayerWalletName());
        holder.mPayerAccountName.setText(transaction.getPayerAccountName());
        holder.mReceiverWalletName.setText(transaction.getReceiverWalletName());
        holder.mReceiverAccountName.setText(transaction.getReceiverAccountName());
    }

    @Override
    public int getItemCount() {
        return MBank.getLobby().getTransactions().size();
    }
}
