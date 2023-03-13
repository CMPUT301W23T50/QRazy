package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity{

    Player player;
    PlayerController playerController;

    public void removeQR(int index) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // get the name of the previous activity
        String last_act = getIntent().getStringExtra("prevActivity");

        // create the header
        CustomHeader head = findViewById(R.id.header_user_leaderboard);
        head.initializeHead("User Profile", "Back to " + last_act);
        // set listener for back button in the header
        head.back_button.setOnClickListener(view -> {
            finish();
        });


        Intent it = getIntent();
        Player player = (Player) it.getParcelableExtra("player");
        PlayerController playerController = (PlayerController) it.getParcelableExtra("playerController");

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

        //RecyclerView qr_recycler = findViewById(R.id.searchview_frag_recycler);
        //qr_recycler.setLayoutManager(new LinearLayoutManager(this));
        //qr_recycler.setAdapter(new QRAdapter(getApplicationContext(), player.getQRArray(), playerController));

        // since we have a SearchViewFragment, we want to show that as the search + recycler view
        // functionality
        SearchViewFragment searchFrag = SearchViewFragment.newInstanceQR(player.getQRArray());
        androidx.fragment.app.FragmentManager fragMan = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.user_qr_frame,searchFrag,"SearchFrag");
        fragTrans.commit();
    }
}