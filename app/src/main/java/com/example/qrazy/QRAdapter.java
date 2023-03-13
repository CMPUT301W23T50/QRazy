package com.example.qrazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRViewHolder> {
    Context context;
    ArrayList<QRCode> QRArray;

    PlayerController playerController;

    public QRAdapter(Context context, ArrayList<QRCode> QRArray, PlayerController playerController) {
        this.context = context;
        this.QRArray = QRArray;
        this.playerController = playerController;
    }

    @NonNull
    @Override
    public QRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QRViewHolder(LayoutInflater.from(context).inflate(R.layout.qr_item, parent, false), this, playerController);
    }

    @Override
    public void onBindViewHolder(@NonNull QRViewHolder holder, int pos) {
        // change this to generated human readable name after halfway
        holder.hashView.setText(QRArray.get(pos).getHash().substring(0, 8));
        holder.scoreView.setText(String.valueOf(QRArray.get(pos).getScore()));
    }

    @Override
    public int getItemCount() {
        return QRArray.size();
    }
}
