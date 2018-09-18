package com.welop.mbank.adapters;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welop.svlit.mbank.R;

import java.util.Random;

public class CardPlayerLobbyRecyclerAdapter extends RecyclerView.Adapter<CardPlayerLobbyRecyclerAdapter.ViewHolder> {

    private Random random = new Random();

    private String[] titles = {
            "Elliot Alderson",
            "Darlene Alderson",
            "Tyrell Wellick",
            "Angela Moss",
            "Phillip Price",
            "Whiterose",
            "Gideon Goddard",
            "Shama Biswas"
    };

    private String[] subtitles = {
            "Top Hat",
            "Thimble",
            "Iron",
            "Boot",
            "Battleship",
            "Cannon",
            "Racecar",
            "Wheelbarrow"
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView accountName;
        public TextView walletName;
        public TextView walletBalance;
        public ImageView accountAvatar;
        public ImageView walletOnline;
        public ImageView walletColor;

        public ViewHolder(View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.card_player_lobby_account_name);
            walletName = itemView.findViewById(R.id.card_player_lobby_wallet_name);
            walletBalance = itemView.findViewById(R.id.card_lobby_wallet_balance);
            accountAvatar = itemView.findViewById(R.id.card_player_lobby_account_avatar);
            walletOnline = itemView.findViewById(R.id.item_online);
            walletColor = itemView.findViewById(R.id.card_lobby_wallet_color);

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
        holder.accountName.setText(titles[position]);
        holder.walletName.setText(subtitles[position]);
        holder.walletBalance.setText(Integer.toString((position + 3) * 139));
        holder.accountAvatar.setImageResource(position % 2 == 0 ? R.drawable.person2 : R.drawable.person1);
        holder.walletOnline.setImageResource(position % 3 == 0 ? R.color.colorOffline : R.color.colorOnline);
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        holder.walletColor.setColorFilter(color);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
