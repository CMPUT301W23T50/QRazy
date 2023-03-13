package com.example.qrazy;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QRLeaderboardActivity extends AppCompatActivity {

    private QRCode qrcode;
    private QRLeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrleaderboard);

        // since this is a leaderboard for a single qr code, we want to save the code
        // code is passed to this activity from the intent, which we can get
        this.qrcode = (QRCode) getIntent().getParcelableExtra("QRCode");
        String last_act = getIntent().getStringExtra("prevActivity");

        // create the header
        CustomHeader head = findViewById(R.id.header_user_leaderboard);
        head.initializeHead("QR Leaderboard", "Back to " + last_act);
        // set listener for back button in the header
        head.back_button.setOnClickListener(view -> {
            Log.d("Back button","Back button clicked");
            finish();
        });

        // adding fake qr codes to a list, for the sake of showing that it works
        QRCode testCode1 = new QRCode("Content");
        QRCode testCode2 = new QRCode("More content");
        testCode1.setName("Test1");
        testCode2.setName("Test2");
        ArrayList<QRCode> qr_array_list = new ArrayList<>();
        qr_array_list.add(testCode1);
        qr_array_list.add(testCode2);


        // make array adapter for the ArrayList of QR codes
        ArrayAdapter<QRCode> qr_adapter = new ArrayAdapter<>(this,
                R.layout.qr_leaderboard_content,R.id.user_name,qr_array_list);

        // since we have a SearchViewFragment, we want to show that as the search + recycler view
        // functionality
        SearchViewFragment searchFrag = SearchViewFragment.newInstanceQR(qr_array_list);
        androidx.fragment.app.FragmentManager fragMan = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.user_lead_frame,searchFrag,"SearchFrag");
        fragTrans.commit();

    }
}