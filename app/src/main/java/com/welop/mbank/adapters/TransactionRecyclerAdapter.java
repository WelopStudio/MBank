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

        private TextView mPayer;
        private TextView mReceiver;
        private TextView mAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            mPayer = itemView.findViewById(R.id.transaction_payer);
            mReceiver = itemView.findViewById(R.id.transaction_receiver);
            mAmount = itemView.findViewById(R.id.transaction_amount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trancastion, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = MBank.getLobby().getTransactions().get(position);
        holder.mAmount.setText(Integer.toString(transaction.getAmount()));
        holder.mPayer.setText(transaction.getPayer());
        holder.mReceiver.setText(transaction.getReceiver());
    }

    @Override
    public int getItemCount() {
        return MBank.getLobby().getTransactions().size();
    }
}
