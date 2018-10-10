package com.welop.mbank.adapters;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welop.mbank.MBank;
import com.welop.mbank.dialogs.TransactionDialog;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

public class PlayerLobbyRecyclerAdapter extends RecyclerView.Adapter<PlayerLobbyRecyclerAdapter.ViewHolder> {
    AppCompatActivity mContext;

    public PlayerLobbyRecyclerAdapter(AppCompatActivity context) {
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mAccountName;
        private TextView mWalletName;
        private TextView mWalletBalance;
        private ImageView mAccountAvatar;
        private ImageView mAdmin;
        private int mPosition;

        public ViewHolder(final View itemView) {
            super(itemView);
            mAccountName = itemView.findViewById(R.id.card_player_lobby_account_name);
            mWalletName = itemView.findViewById(R.id.card_player_lobby_wallet_name);
            mWalletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            mAccountAvatar = itemView.findViewById(R.id.card_player_lobby_account_avatar);
            mAdmin = itemView.findViewById(R.id.card_player_lobby_admin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = mContext.findViewById(R.id.lobby_coordinator_layout_bottom_sheet);
                    TransactionDialog transactionDialog = new TransactionDialog();
                    transactionDialog.setCoordinatorLayout(v);
                    transactionDialog.setPayerWallet(MBank.getWallet());
                    transactionDialog.setReceiverWallet(MBank.getLobby().getWallets().get(mPosition));
                    transactionDialog.show(mContext.getSupportFragmentManager(), "TAG");
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
        holder.mWalletBalance.setText(Long.toString(wallet.getBalance()));
        holder.mAccountAvatar.setImageResource(position % 2 == 0 ? R.drawable.person2 : R.drawable.person1);
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return MBank.getLobby().getWallets().size();
    }
}
