package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class QRLeaderboardActivity extends AppCompatActivity {

    QRCode qrcode;
    String last_activity_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrleaderboard);

        // since this is a leaderboard for a single qr code, we want to save the code
        // code is passed to this activity from the intent, which we can get
        this.qrcode = (QRCode) getIntent().getParcelableExtra("QRCode");
        String last_act = getIntent().getStringExtra("prevActivity");

        // create the header
        CustomHeader head = findViewById(R.id.header_qr_leaderboard);
        head.initializeHead("QR Leaderboard", "Back to " + last_act);
        // set listener for back button in the header
        head.back_button.setOnClickListener(view -> {
            Log.d("Back button","Back button clicked");
            finish();
        });

    }
}