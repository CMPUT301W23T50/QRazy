package com.example.qrazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView hashView;
    TextView scoreView;
    private Context context;
    private UserViewHolder.UserViewHolderClicks listener;

    /**
     * Constructor method
     * @param itemView
     * @param userAdapter
     * @param listener
     */
    public UserViewHolder(@NonNull View itemView, UserLeaderboardAdapter userAdapter, UserViewHolder.UserViewHolderClicks listener) {
        super(itemView);
        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);
        this.listener = listener;

        hashView.setOnClickListener(this);
        scoreView.setOnClickListener(this);
    }

    public UserViewHolder(View view, UserViewHolder.UserViewHolderClicks userViewHolderClicks) {
        super(view);
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick(view);
    }

    /**
     * Interface to allow the QRViewHolder to interact with QRAdapter
     */
    public static interface UserViewHolderClicks {
        public void onItemClick(View view);
    }

}
