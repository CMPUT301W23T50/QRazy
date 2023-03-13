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

    TextView hashView;
    TextView scoreView;
    private QRViewHolderClicks listener;

    public QRViewHolder(@NonNull View itemView, QRAdapter qrAdapter, PlayerController playerController) {
        super(itemView);
        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);


        Button button = (Button) itemView.findViewById(R.id.button2);
        button.setOnClickListener(view -> {
            playerController.removeQR(getAdapterPosition());
            qrAdapter.QRArray.clear();
            qrAdapter.QRArray = playerController.getPlayer().getQRArray();
            qrAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Version of the constructor, without needing a PlayerController
     * @param itemView View object, of each item in RecyclerView
     * @param listener QRViewHolderClicks object
     * @param context context of the QRViewHolder object
     */
    public QRViewHolder(@NonNull View itemView, QRViewHolderClicks listener, Context context) {
        super(itemView);

        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);
        this.listener = listener;

        //hashView.setOnClickListener((View.OnClickListener) listener);
        //scoreView.setOnClickListener((View.OnClickListener) listener);


    }

    @Override
    public void onClick(View view) {
        listener.onItemClick(view);
    }

    /**
     * Interface to allow the QRViewHolder to interact with QRAdapter
     */
    public interface QRViewHolderClicks {
        void onItemClick(View view);
    }

}
