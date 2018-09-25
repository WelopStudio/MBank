package com.welop.mbank.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welop.mbank.MBank;
import com.welop.mbank.activities.LobbyActivity;
import com.welop.svlit.mbank.R;

import java.util.Random;

public class CardLobbyRecyclerAdapter extends RecyclerView.Adapter<CardLobbyRecyclerAdapter.ViewHolder> {

    private Random random = new Random();

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView walletName;
        public TextView walletBalance;
        public TextView lobbyName;
        public ImageView accountAvatar;
        //public ImageView walletColor;

        public ViewHolder(final View itemView) {
            super(itemView);
            walletName = itemView.findViewById(R.id.card_lobby_wallet_name);
            walletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            //walletColor = itemView.findViewById(R.id.card_lobby_wallet_color);
            lobbyName = itemView.findViewById(R.id.card_lobby_name);

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
        holder.lobbyName.setText(MBank.getWallets().get(position).get("lobby_name").toString());
        holder.walletName.setText(MBank.getWallets().get(position).get("name").toString());
        holder.walletBalance.setText(MBank.getWallets().get(position).get("balance").toString());
        //int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        //holder.walletColor.setColorFilter(color);
    }

    @Override
    public int getItemCount() {
        return MBank.getWallets().size();
    }
}
