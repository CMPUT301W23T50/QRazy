package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // these buttons are used to switch to each new activity
        Button scanQRButton = findViewById(R.id.scan_qr_button);
        Button mapButton = findViewById(R.id.map_button);
        Button userProfileButton = findViewById(R.id.user_profile_button);
        Button userLeaderboardButton = findViewById(R.id.user_leaderboard_button);
        Button qrLeaderboardButton = findViewById(R.id.qr_leaderboard_button);

        // switch to qr code scanning activity on button press
        switchToActivity(scanQRButton,ScanCodeActivity.class);

        // switch to map activity on button press
        switchToActivity(mapButton,MapActivity.class);

        // switch to map activity on button press
        switchToActivity(userProfileButton,UserProfileActivity.class);

        // switch to map activity on button press
        switchToActivity(userLeaderboardButton,UserLeaderboardActivity.class);

        // switch to map activity on button press
        switchToActivity(qrLeaderboardButton,QRLeaderboardActivity.class);

    }

    /**
     * Method to switch to a new activity on a button click
     * @param button the button which can be clicked to switch to a new activity
     * @param activityClass the activity to switch to
     */
    protected void switchToActivity(Button button, Class activityClass) {
        // ignore the warning "Raw use of parameterized class 'Class'" since there's no other way
        // to implement this
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), activityClass);
            startActivity(intent);
        });
    }

}