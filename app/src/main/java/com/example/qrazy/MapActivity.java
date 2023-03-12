package com.example.qrazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayControlView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView map = null;
    private final int REQUEST_PERMS = 1;
    Context context;
    private MyLocationNewOverlay mLocationOverlay;

    /**
     * onCreate function
     * Most of the code here is from the tutorial on open maps, link is
     * https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        // these are for permissions needed to make the map work properly
        List<String> permsList = Collections.singletonList(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String[] permsArray = permsList.toArray(new String[0]);

        // random-ish lat and lon, for the map
        double lat = 53;
        double lon = -100;

        // this is just a test point, preferably we would get the geolocation of the user
        // or ask the user to enter their location
        GeoPoint start = new GeoPoint(lat,lon);

        // creating an overlay for the map
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getBaseContext()),map);
        this.mLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.mLocationOverlay);

        requestPermissions(permsArray);

        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        mapController.setCenter(start);

        // want to create some dummy points, to make sure the map can actually show them
        ArrayList<OverlayItem> points = new ArrayList<OverlayItem>();
        points.add(new OverlayItem("Test","Test description",start));

        makeMapMarker(points);

        // make the back button finish the activity, and set label on back button
        TextView back_button_label = findViewById(R.id.back_button_label);
        ImageButton back_button = findViewById(R.id.back_button_map);
        String last_activity = getIntent().getStringExtra("activityName");
        back_button_label.setText("Back to " + last_activity);

        // go back to the last activity
        back_button.setOnClickListener(view -> finish());


    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * Function to handle results of permission request
     * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param requestCode code of the permissions request
     * @param permissions permissions being requested
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> perms = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));
        if (perms.size() > 0) {
            ActivityCompat.requestPermissions(this, perms.toArray(new String[0]), REQUEST_PERMS);
        }
    }

    /**
     * Function to request permissions necessary for the map to work
     * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param permissions a list of the permissions needed to make the map work
     */
    private void requestPermissions(String[] permissions) {
        ArrayList<String> perms = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED) {
                perms.add(perm);
            }
        }
        if (perms.size() > 0) {
            ActivityCompat.requestPermissions(this,perms.toArray(new String[0]),REQUEST_PERMS);
        }
    }

    /**
     * Function that takes the map and a point, and creates a map marker for the point
     * Ideally this would get geolocation data from a database, to show the user nearby codes
     * and show the user where their codes are from
     * Again, adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param points list of items to be added to an overlay, then displayed as map markers
     */
    private void makeMapMarker(ArrayList<OverlayItem> points) {
        // TODO: deal with deprecated classes from Open Maps
        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<OverlayItem>(points,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        // this code is just to test the implementation
                        String qrname = item.getTitle();
                        QRCode pressedQRCode = new QRCode(qrname);
                        switchActivityOnHold(pressedQRCode,QRLeaderboardActivity.class);
                        return false;
                    }
                }, getBaseContext());

        overlay.setFocusItemsOnTap(true);
        map.getOverlays().add(overlay);

    }

    /**
     * Function to take users to the qr code leaderboard from map markers
     *
     * @param qrCode QRCode to see leaderboard for
     */
    private void switchActivityOnHold(QRCode qrCode, Class activityClass) {
        // before opening page, ask the user if they want to go to page
        // ignore warning about using Class in the function arguments
        new AlertDialog.Builder(MapActivity.this)
                .setTitle("Test")
                .setMessage("Would you like to go to the leaderboard for this QR code?")
                .setPositiveButton("Go to leaderboard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getBaseContext(), activityClass);
                        intent.putExtra("prevActivity","map");
                        intent.putExtra("QRCode",qrCode);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
}