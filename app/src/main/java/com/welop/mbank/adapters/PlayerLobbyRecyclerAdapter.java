package com.welop.mbank.adapters;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welop.mbank.MBank;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

public class PlayerLobbyRecyclerAdapter extends RecyclerView.Adapter<PlayerLobbyRecyclerAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mAccountName;
        private TextView mWalletName;
        private TextView mWalletBalance;
        private ImageView mAccountAvatar;
        private ImageView mAdmin;

        public ViewHolder(View itemView) {
            super(itemView);
            mAccountName = itemView.findViewById(R.id.card_player_lobby_account_name);
            mWalletName = itemView.findViewById(R.id.card_player_lobby_wallet_name);
            mWalletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            mAccountAvatar = itemView.findViewById(R.id.card_player_lobby_account_avatar);
            mAdmin = itemView.findViewById(R.id.card_player_lobby_admin);

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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wallet wallet = MBank.getLobby().getWallets().get(position);
        holder.mAdmin.setVisibility(MBank.getLobby().getAdminId().equals(wallet.getOwnerId()) ? View.VISIBLE : View.INVISIBLE);
        holder.mAccountName.setText(wallet.getOwnerName());
        holder.mWalletName.setText(wallet.getName());
        holder.mWalletBalance.setText(Integer.toString(wallet.getBalance()));
        holder.mAccountAvatar.setImageResource(position % 2 == 0 ? R.drawable.person2 : R.drawable.person1);
    }

    @Override
    public int getItemCount() {
        return MBank.getLobby().getWallets().size();
    }
}
