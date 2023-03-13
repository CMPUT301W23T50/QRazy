package com.example.qrazy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class QRViewHolder extends RecyclerView.ViewHolder {

    TextView hashView;
    TextView scoreView;

    public QRViewHolder(@NonNull View itemView) {
        super(itemView);
        this.hashView = itemView.findViewById(R.id.hash);
        this.scoreView = itemView.findViewById(R.id.qrscore);
    }

}
