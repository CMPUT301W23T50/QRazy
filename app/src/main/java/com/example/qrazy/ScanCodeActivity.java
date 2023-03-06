package com.example.qrazy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.qrazy.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class ScanCodeActivity extends AppCompatActivity {

    private ImageCapture imageCapture;
    private ExecutorService cameraEx;

    // this is adapted from https://developer.android.com/training/permissions/requesting
    // used to store user permissions
    private final ActivityResultLauncher<String> requestPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // user granted permission in request launcher

                } else {
                    // user did not grant permission

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // back button
        ImageButton backButton = findViewById(R.id.back_button);
        // add qr code button
        FloatingActionButton addCodeButton = findViewById(R.id.scan_button);

        // set back button to return to main activity
        backButton.setOnClickListener(view -> {
            finish();
        });

        // check if the user's device has a usable camera
        if(checkCamera(ScanCodeActivity.this)) {
            // device has a usable camera
            if(checkCameraPermission(ScanCodeActivity.this)) {
                // user has given permission for the camera to be used
                // so no need to request anything
                startCamera();
            } else {
                // user has not given permission for the camera to be used
                // request permission from user
                requestPermission.launch(Manifest.permission.CAMERA);
            }
        } else {
            // device doesn't have a usable camera
            createAlertDialog(ScanCodeActivity.this,"Incompatible device",
                    "Device does not have a sufficient camera","OK","");
        }

        // on click listener for the add code button
        addCodeButton.setOnClickListener(view -> {
            // if clicked, camera can take a picture
            takePicture();
        });

    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {

            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                CameraSelector selector = CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this,selector,preview);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(this));
    }

    private void takePicture() {}

    /**
     * Checks if the user's device has an available camera to use
     * @param context context of the camera
     * @return boolean, true if the device has an acceptable camera, false if not
     */
    private boolean checkCamera(Context context) {
        // code was adapted from https://developer.android.com/guide/topics/media/camera
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private boolean checkCameraPermission(Context context) {
        // code adapted from https://developer.android.com/training/permissions/requesting
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void createAlertDialog(Context context, String title, String message, String positive, String negative) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something
            }
        }).setNegativeButton(negative, null).show();
    }

}