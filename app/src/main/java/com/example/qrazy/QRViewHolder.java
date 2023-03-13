package com.example.qrazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class QRViewHolder extends RecyclerView.ViewHolder{

    private Button button;
    TextView hashView;
    TextView scoreView;
    private Context context;

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

}
