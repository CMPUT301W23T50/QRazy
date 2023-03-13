package com.example.qrazy;

import android.content.Context;
import android.util.Log;
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
public class QRLeaderboardAdapter extends RecyclerView.Adapter<QRViewHolder>
        implements QRViewHolder.QRViewHolderClicks {

    private Context context;
    private ArrayList<QRCode> qrCodes;

    private QRViewHolder.QRViewHolderClicks clickListener;

    public QRLeaderboardAdapter(Context context, ArrayList<QRCode> qrCodes) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        this.qrCodes = qrCodes;
    }

    public void setClickListener(QRViewHolder.QRViewHolderClicks listener) {
        clickListener = listener;
    }

    @NonNull
    @Override
    public QRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qr_leaderboard_content,parent,false);

        // set click listener for recycler view elements
        // this sets it for each element individually
        view.setOnClickListener((View.OnClickListener) clickListener);

        return new QRViewHolder(view,clickListener,this.context);
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

    @Override
    public void onItemClick(View view) {
        Log.d(view.toString(),"Clicked!");
    }
}
