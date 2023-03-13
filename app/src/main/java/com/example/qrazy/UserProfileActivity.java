package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent it = getIntent();
        Player player = (Player) it.getSerializableExtra("player");
        PlayerController playerController = (PlayerController) it.getSerializableExtra("playerController");

        // refactor with an array/hashmap later
        TextView usernameTxt =(TextView) findViewById(R.id.username);
        TextView highestTxt =(TextView) findViewById(R.id.highest);
        TextView totalTxt =(TextView) findViewById(R.id.total);
        TextView lowestTxt =(TextView) findViewById(R.id.lowest);
        TextView scannedTxt = (TextView) findViewById(R.id.scanned);
        usernameTxt.append(String.valueOf(player.getUsername()));
        lowestTxt.append(String.valueOf(player.getLowest()));
        highestTxt.append(String.valueOf(player.getHighest()));
        totalTxt.append(String.valueOf(player.getTotal()));
        scannedTxt.append(String.valueOf(player.getNumScanned()));

        RecyclerView qr_recycler = findViewById(R.id.qr_recycler);
        qr_recycler.setLayoutManager(new LinearLayoutManager(this));
        qr_recycler.setAdapter(new QRAdapter(getApplicationContext(), player.getQRArray()));
    }
}