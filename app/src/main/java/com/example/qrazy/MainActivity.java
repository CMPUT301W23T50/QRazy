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

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * MainActivity is an activity which is the opening screen to the app (not including the login
 * screen, which the user should only have to see once)
 * This activity has buttons that lead the user to other activities within the app
 * As well, the ZXing library opens the scanning activity here, because this prevents the app from
 * switching to the ScanCodeActivity before getting permission to use the device's camera
 * TODO: move ZXing to a fragment, so that it can be used outside of MainActivity
 */
public class MainActivity extends AppCompatActivity{
     Player player = new Player("User#12345", "s8765");
     PlayerController playerController = new PlayerController(player);

    // result from qr scan
    private String scanResult;

    public String calculateHash(String content) {
        return DigestUtils.sha256Hex(content);
    }
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

                  // add qr code
                  QRCode qr = new QRCode(calculateHash(scanResult));
                  playerController.addQR(qr);

                  Toast.makeText(getBaseContext(), player.getQRArray().get(0).getHash(), Toast.LENGTH_LONG).show();

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
                            Intent getImage = new Intent(Intent.ACTION_PICK);
                            getImage.setDataAndType( android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    "image/*");
                            startActivityForResult(getImage,2);
                        }
                    })
                    .show();
            // if user adds a qr code, go to the scan code activity
            if (scanResult != null) {
                Intent intent = new Intent(view.getContext(), ScanCodeActivity.class);
                intent.putExtra("prevActivity","home");
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

            Intent intent = new Intent(this, activityClass);
            intent.putExtra("player", player);
            intent.putExtra("playerController", playerController);
            intent.putExtra("prevActivity","home");
    
            startActivity(intent);
        });
    }

    /**
     * Function to add dummy QR codes to the user profile
     */
    private void addDummyQR() {
        QRCode fakeqr1 = new QRCode("12489");
        QRCode fakeqr2 = new QRCode("832745");
        fakeqr1.setName("PlayerQR1");
        fakeqr2.setName("FakeQRCode");
        playerController.addQR(fakeqr1);
        playerController.addQR(fakeqr2);
    }

}
