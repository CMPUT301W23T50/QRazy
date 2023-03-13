package com.example.qrazy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchViewFragment#newInstanceQR} factory method to
 * create an instance of this fragment.
 */
public class SearchViewFragment extends Fragment {


    private static final String QR_LIST_ARG = "QRList";
    private static final String PLAYER_LIST_ARG = "PlayerList";

    private ArrayList<QRCode> arrayList;
    private ArrayList<Player> playerList;
    private QRLeaderboardAdapter qrLeaderboardAdapter;
    private UserLeaderboardAdapter userLeaderboardAdapter;

    public SearchViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param qrList an ArrayList, containing QRCode objects
     * @return A new instance of fragment SearchViewFragment.
     */
    public static SearchViewFragment newInstanceQR(ArrayList<QRCode> qrList) {
        SearchViewFragment fragment = new SearchViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(QR_LIST_ARG, qrList);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new instance of SearchViewFragment, using an ArrayList with Player objects
     * @param playerList ArrayList of players
     * @return SearchViewFragment
     */
    public static SearchViewFragment newInstancePlayer(ArrayList<Player> playerList) {
        SearchViewFragment fragment = new SearchViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PLAYER_LIST_ARG, playerList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_view, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchview_frag_recycler);
        // create a layout manager to deal with the recycler view
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        // set values for recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        // these if else statements make sure that we're using the write ArrayList<> type and
        // make sure that arguments are passed to this fragment from an activity
        // inside the if else statements, we then call functions based on the class of objects
        if (getArguments() != null) {
            // check if bundle has a ArrayList<QRCode>
            if (getArguments().containsKey(QR_LIST_ARG)) {
                this.arrayList = getArguments().getParcelableArrayList(QR_LIST_ARG);
                // get a leaderboard adapter
                qrLeaderboardAdapter = new QRLeaderboardAdapter(this.getContext(),arrayList);
                recyclerView.setAdapter(qrLeaderboardAdapter);
                // call search items to allow user to search the recycler view
                searchItems(view);

            // check if bundle has an ArrayList<Player>
            } else if (getArguments().containsKey(PLAYER_LIST_ARG)) {

                this.playerList = getArguments().getParcelableArrayList(PLAYER_LIST_ARG);
                userLeaderboardAdapter = new UserLeaderboardAdapter(this.getContext(),playerList);
                recyclerView.setAdapter(userLeaderboardAdapter);
                // call search items to allow user to search the recycler view
                searchItems(view);

            }
        } else {
            this.arrayList = new ArrayList<>();
        }
        return view;

    }

    /**
     * Function to deal with the SearchView
     * @param view current view
     */
    public void searchItems(View view) {
        // get the SearchView
        SearchView searchView = view.findViewById(R.id.searchview_frag_search);
        // listeners for the search view, for different cases
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String s){
            // user types a search query, hits enter
            // we probably don't want to do anything here, since we'll get the results of the
            // search dynamically as the user types
                return false;
            }
            @Override
            public boolean onQueryTextChange (String s){
            // user types anything into the search box
            // want to filter the list view to display search results
                filterText(s);
                return true;
            }
        });
    }

    /**
     * Function to filter search results from the qrLeaderboardAdapter
     * Adapted in part from https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
     * @param text search query
     */
    public void filterText(String text) {

        // check if we're looking at qr codes or user names
        assert getArguments() != null;
        if (getArguments().containsKey(QR_LIST_ARG)) {
            ArrayList<QRCode> filtered = new ArrayList<>();

            for (QRCode code : arrayList) {
                if (code.getHash().toLowerCase().contains(text.toLowerCase())) {
                    filtered.add(code);
                }
            }
            qrLeaderboardAdapter.filter(filtered);

        } else if (getArguments().containsKey(PLAYER_LIST_ARG) &&
                !getArguments().containsKey(QR_LIST_ARG)) {

            ArrayList<Player> filtered = new ArrayList<>();

            for (Player player : playerList) {
                if (player.getUsername().toLowerCase().contains(text.toLowerCase())) {
                    filtered.add(player);
                }
            }
            userLeaderboardAdapter.filter(filtered);
        }
    }
}