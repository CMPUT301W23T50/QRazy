package com.example.qrazy;

import android.content.Context;
import android.content.Intent;
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

        UserViewHolder viewHolder = new UserViewHolder(view, view1 -> {
            // when user clicks item, take them to user profile page
            Intent intent = new Intent(view.getContext(),UserProfileActivity.class);
            intent.putExtra("player", (CharSequence) view);
            intent.putExtra("prevActivity","home");
            view.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Player player = players.get(position);
        TextView player_name = holder.itemView.findViewById(R.id.user_lead_name);
        TextView highest = holder.itemView.findViewById(R.id.highest_score_ulead);
        TextView lowest = holder.itemView.findViewById(R.id.lowest_score_ulead);

        player_name.setText(player.getUsername());
        highest.setText(String.valueOf(player.getHighest()));
        lowest.setText(String.valueOf(player.getLowest()));

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
