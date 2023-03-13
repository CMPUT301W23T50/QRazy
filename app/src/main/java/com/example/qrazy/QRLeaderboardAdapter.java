package com.example.qrazy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter used for the QR Leaderboard
 * Can't use QR adapter since it requires a PlayerController
 */
public class QRLeaderboardAdapter extends RecyclerView.Adapter<QRViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<QRCode> qrCodes;

    public QRLeaderboardAdapter(Context context, ArrayList<QRCode> qrCodes) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.qrCodes = qrCodes;
    }

    @NonNull
    @Override
    public QRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qr_leaderboard_content,parent,false);

        QRViewHolder viewHolder = new QRViewHolder(view, new QRViewHolder.QRViewHolderClicks() {
            public void onItemClick(View view1) {
                // implement interface method here
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QRViewHolder holder, int position) {
        QRCode qrCode = qrCodes.get(position);
        TextView qr_name = holder.itemView.findViewById(R.id.qr_name);
        TextView qr_score = holder.itemView.findViewById(R.id.score_text_qrlead);
        qr_name.setText(qrCode.getHash());
        qr_score.setText(String.valueOf(qrCode.getScore()));
    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    public void filter(ArrayList<QRCode> qrCodes1) {
        qrCodes = qrCodes1;
        notifyDataSetChanged();
    }
}
