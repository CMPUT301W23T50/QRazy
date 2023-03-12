package com.example.qrazy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity{

    // result from qr scan
    private String scanResult;

    ActivityResultLauncher<PickVisualMediaRequest> pickImage =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    Log.d("Image","URI: "+uri);
                } else {
                    Log.d("Image","URI is null");
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // these buttons are used to switch to each new activity
        Button scanQRButton = findViewById(R.id.scan_qr_button);
        Button mapButton = findViewById(R.id.map_button);
        Button userProfileButton = findViewById(R.id.user_profile_button);
        Button userLeaderboardButton = findViewById(R.id.user_leaderboard_button);
        Button qrLeaderboardButton = findViewById(R.id.qr_leaderboard_button);

        // this allows user to either scan a code or add from gallery
        addCode(scanQRButton);


        // switch to map activity on button press
        switchToActivity(mapButton, MapActivity.class);

        // switch to map activity on button press
        switchToActivity(userProfileButton, UserProfileActivity.class);

        // switch to map activity on button press
        switchToActivity(userLeaderboardButton, UserLeaderboardActivity.class);

        // switch to map activity on button press
        switchToActivity(qrLeaderboardButton, QRLeaderboardActivity.class);

    }
    /**
     * This function allows for the app to record the result from ZXing scanner
     * @param requestCode ZXing API request code
     * @param resultCode ZXing API result code
     * @param intent instance of Intent class
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if (result != null) {
            if (result.getContents() != null) {
                // user didn't cancel scanning / adding a photo
                this.scanResult = result.getContents();
            }
        }
    }

    /**
     * Function implementing ZXing library to allow user to scan codes
     */
    protected void addCode(Button button) {
        // switch to qr code scanning activity on button press
        //switchToActivity(scanQRButton,ScanCodeActivity.class);
        button.setOnClickListener(view -> {
            CharSequence[] dialogOptions = {"Take photo","From gallery"};
            new AlertDialog.Builder(this)
                    .setTitle("Image")
                    .setItems(dialogOptions, (dialogInterface, i) -> {
                        // user clicked on the take photo dialog option
                        if(i == 0) {
                            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                            intentIntegrator.setPrompt("Scan code");
                            intentIntegrator.setOrientationLocked(true);
                            intentIntegrator.initiateScan();
                        // user clicked on the add photo option
                        } else if (i == 1) {
                            // TODO: allow user to upload images from gallery here
                            pickImage.launch(new PickVisualMediaRequest.Builder()
                                    // this code gives an error about "inconvertible types" but isn't an actual error
                                    .setMediaType((ActivityResultContracts.PickVisualMedia.VisualMediaType)
                                            ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                    .build());
                        }
                    })
                    .show();
            // if user adds a qr code, go to the scan code activity
            if (scanResult != null) {
                Intent intent = new Intent(view.getContext(), ScanCodeActivity.class);
                intent.putExtra("activityName","home");
                startActivity(intent);
            }
        });
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
            intent.putExtra("activityName","home");
            startActivity(intent);
        });
    }
}