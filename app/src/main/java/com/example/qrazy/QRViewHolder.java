package com.example.qrazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class QRViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Button button;
    TextView hashView;
    TextView scoreView;
    private Context context;
    private QRViewHolderClicks listener;

    public QRViewHolder(@NonNull View itemView, QRAdapter qrAdapter, PlayerController playerController) {
        super(itemView);
        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);


        button = (Button) itemView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                playerController.removeQR(getAdapterPosition());
                qrAdapter.QRArray.clear();
                qrAdapter.QRArray = playerController.getPlayer().getQRArray();
                qrAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Version of the constructor, without needing a PlayerController
     * @param itemView
     * @param qrAdapter
     */
    public QRViewHolder(@NonNull View itemView, QRAdapter qrAdapter, QRViewHolderClicks listener) {
        super(itemView);
        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);
        this.listener = listener;

        hashView.setOnClickListener(this);
        scoreView.setOnClickListener(this);


    }

    public QRViewHolder(View view, QRViewHolderClicks qrViewHolderClicks) {
        super(view);
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick(view);
    }

    /**
     * Interface to allow the QRViewHolder to interact with QRAdapter
     */
    public static interface QRViewHolderClicks {
        public void onItemClick(View view);
    }

}
