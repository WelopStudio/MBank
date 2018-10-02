package com.welop.mbank.adapters;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.welop.mbank.MBank;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

public class CardPlayerLobbyRecyclerAdapter extends RecyclerView.Adapter<CardPlayerLobbyRecyclerAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView accountName;
        private TextView walletName;
        private TextView walletBalance;
        private ImageView accountAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.card_player_lobby_account_name);
            walletName = itemView.findViewById(R.id.card_player_lobby_wallet_name);
            walletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            accountAvatar = itemView.findViewById(R.id.card_player_lobby_account_avatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    Snackbar.make(view, "Clicked item " + (position + 1), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_player_lobby, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wallet wallet = MBank.getLobby().getWallets().get(position);
        if (!wallet.getOwnerId().equals(FirebaseAuth.getInstance().getUid())) {
            holder.accountName.setText(wallet.getOwnerName());
            holder.walletName.setText(wallet.getName());
            holder.walletBalance.setText(Integer.toString(wallet.getBalance()));
            holder.accountAvatar.setImageResource(position % 2 == 0 ? R.drawable.person2 : R.drawable.person1);
        }
    }

    @Override
    public int getItemCount() {
        return MBank.getLobbyWallets().size();
    }
}
