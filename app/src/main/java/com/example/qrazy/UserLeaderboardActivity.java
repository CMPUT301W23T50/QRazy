package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class UserLeaderboardActivity extends AppCompatActivity {

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leaderboard);

        String last_act = getIntent().getStringExtra("prevActivity");

        // create the header
        CustomHeader head = findViewById(R.id.header_user_leaderboard);
        head.initializeHead("User Leaderboard", "Back to " + last_act);
        // set listener for back button in the header
        head.back_button.setOnClickListener(view -> { finish(); });

        players = new ArrayList<>();
        Player test1 = new Player("player1","1");
        Player test2 = new Player("player2","2");
        players.add(test1);
        players.add(test2);

        // since we have a SearchViewFragment, we want to show that as the search + recycler view
        // functionality
        SearchViewFragment searchFrag = SearchViewFragment.newInstancePlayer(players);
        androidx.fragment.app.FragmentManager fragMan = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.user_lead_frame,searchFrag,"SearchFrag");
        fragTrans.commit();


    }
}