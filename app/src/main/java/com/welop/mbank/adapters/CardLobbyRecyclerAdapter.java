package com.welop.mbank.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welop.mbank.MBank;
import com.welop.mbank.activities.LobbyActivity;
import com.welop.svlit.mbank.R;

public class CardLobbyRecyclerAdapter extends RecyclerView.Adapter<CardLobbyRecyclerAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mWalletName;
        private TextView mWalletBalance;
        private TextView mLobbyName;

        public ViewHolder(final View itemView) {
            super(itemView);
            mWalletName = itemView.findViewById(R.id.card_lobby_wallet_name);
            mWalletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            mLobbyName = itemView.findViewById(R.id.card_lobby_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent lobbyIntent = new Intent(view.getContext(), LobbyActivity.class);
                    view.getContext().startActivity(lobbyIntent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lobby, parent, false);
        CardLobbyRecyclerAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mLobbyName.setText(MBank.getUserWallets().get(position).getLobbyName());
        holder.mWalletName.setText(MBank.getUserWallets().get(position).getName());
        holder.mWalletBalance.setText(Integer.toString(MBank.getUserWallets().get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return MBank.getUserWallets().size();
    }
}
