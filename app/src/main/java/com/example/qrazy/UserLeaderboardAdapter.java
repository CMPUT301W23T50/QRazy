package com.example.qrazy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserLeaderboardAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Player> players;

    public UserLeaderboardAdapter(Context context, ArrayList<Player> players) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.players = players;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_leaderboard_content,parent,false);

        UserViewHolder viewHolder = new UserViewHolder(view, new UserViewHolder.UserViewHolderClicks() {
            public void onItemClick(View view1) {
                // implement interface method here
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Player player = players.get(position);
        TextView player_name = holder.itemView.findViewById(R.id.user_name);
        player_name.setText(player.getUsername());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void filter(ArrayList<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

}
