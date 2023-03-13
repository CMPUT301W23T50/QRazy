package com.example.qrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;

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
        CustomHeader head = findViewById(R.id.header_qr_fragment);
        head.initializeHead("QR Leaderboard", "Back to " + last_act);
        // set listener for back button in the header
        head.back_button.setOnClickListener(view -> {
            Log.d("Back button","Back button clicked");
            finish();
        });

        // adding fake qr codes to a list, for the sake of showing that it works
        ArrayList<QRCode> temp_list = new ArrayList<>();
        temp_list.add(new QRCode("Content"));
        temp_list.add(new QRCode("More content"));

        // make array adapter for the ArrayList of QR codes
        ArrayAdapter<QRCode> qr_adapter = new ArrayAdapter<>(this,
                R.layout.qr_leaderboard_content,R.id.qr_code_name,temp_list);
        // get list view to display the qr leaderboard
        ListView qr_leaderboard_list = findViewById(R.id.qr_leaderboard_list);
        qr_leaderboard_list.setAdapter(qr_adapter);
        qr_leaderboard_list.setTextFilterEnabled(true);

        // get the searchview
        SearchView searchView = findViewById(R.id.qr_leaderboard_searchview);
        // listeners for the search view, for different cases
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // user types a search query, hits enter
                // we probably don't want to do anything here, since we'll get the results of the
                // search dynamically as the user types
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // user types anything into the search box
                // want to filter the list view to display search results
                qr_adapter.getFilter().filter(s);
                return true;
            }
        });

        // we want to let users click on the QR codes in the list
        qr_leaderboard_list.setOnItemClickListener((adapterView, view, i, l) -> {

            // condition to allow for user to open fragment, showing an individual QR code
            if (savedInstanceState == null) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("QRCode",qr_adapter.getItem(i));

                QRCodeFragment frag = new QRCodeFragment();
                frag.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.qr_lead_list_con, frag, "QRFrag")
                        .addToBackStack(null)
                        .show(frag)
                        .commit();

                // need to hide the view fragment replaces
                qr_leaderboard_list.setVisibility(View.GONE);
            }
        });

        // close fragment by clicking on the searchview
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SearchView","SearchView clicked");
                FragmentManager fragMan = getFragmentManager();
                android.app.Fragment fragment = fragMan.findFragmentByTag("QRCode");
                // this is from https://stackoverflow.com/a/16652217
                if (fragment != null && fragment.isVisible()) {
                    FragmentTransaction fragTrans = fragMan.beginTransaction();
                    fragTrans.remove(fragment);
                    fragTrans.commit();
                    qr_leaderboard_list.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}