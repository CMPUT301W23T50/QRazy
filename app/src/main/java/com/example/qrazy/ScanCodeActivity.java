package com.example.qrazy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class ScanCodeActivity extends AppCompatActivity {

    private ImageCapture imageCapture;
    private ExecutorService cameraEx;

    private Activity activity;

    private String scanResult;

    private boolean permissionGranted;

    // this is adapted from https://developer.android.com/training/permissions/requesting
    // isGranted is the result from asking user for permission (boolean)
    private final ActivityResultLauncher<String> requestPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // do something here
                this.permissionGranted = isGranted;
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        this.activity = this;

        // back button
        ImageButton backButton = findViewById(R.id.back_button);

        // set back button to return to main activity
        backButton.setOnClickListener(view -> finish());

    }

    /**
     * This function allows for the app to record the result from ZXing scanner
     * @param requestCode ZXing API request code
     * @param resultCode ZXing API result code
     * @param intent instance of Intent class
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        // make sure a result exists
        assert result != null;
        // TODO: deal with user cancelling scanning
        assert result.getContents() != null;

        Toast.makeText(getBaseContext(),result.getContents(),Toast.LENGTH_LONG).show();
        this.scanResult = result.getContents();
    }

    /**
     * Function to take a picture with the device camera
     */
    private void takePicture() {
        // get the image button
        FloatingActionButton takePic = findViewById(R.id.scan_button);
        // this is where any data needed in the QRCode class can be initialized
        Date picDate = new Date();

        // this is for some metadata stuff
        // anything the qr code class needs can be put in the ContentValues class instance
        ContentValues vals = new ContentValues();
        vals.put(MediaStore.MediaColumns.DISPLAY_NAME, String.valueOf(picDate));
        vals.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");

        // this creates an instance of the output file options class
        // allows us to store meta data
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,vals).build();

        // check if the user clicks the camera button
        takePic.setOnClickListener(view -> {
            // take a picture when the user clicks the photo button
            imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(
                    getApplicationContext()), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Toast.makeText(getBaseContext(),"Photo captured",Toast.LENGTH_LONG)
                            .show();
                    IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                    intentIntegrator.setPrompt("Scan code");
                    intentIntegrator.initiateScan();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Toast.makeText(getBaseContext(),"Couldn't capture photo",Toast.LENGTH_LONG)
                            .show();
                }
            });
        });
    }

    /**
     * Function to start the camera on the user's device
     * Code was adapted in part from https://stackoverflow.com/a/63485232
     * and from https://developer.android.com/training/camerax/take-photo
     */
    private void startCamera(PreviewView previewView) {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {

            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                // make the camera preview
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // sets the class value for image capture
                this.imageCapture = new ImageCapture.Builder()
                        .build();

                CameraSelector selector = CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();

                Camera cam = cameraProvider.bindToLifecycle(this,selector,imageCapture,preview);
                CameraControl camControl = cam.getCameraControl();

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(this));
    }

    /**
     * Checks if the user's device has an available camera to use
     * Code was adapted from https://developer.android.com/guide/topics/media/camera
     * @param context context of the camera
     * @return boolean, true if the device has an acceptable camera, false if not
     */
    private boolean checkCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Check if the user has given permission to the app to use a camera
     * Code adapted from https://developer.android.com/training/permissions/requesting
     * @param context context of the camera
     * @return boolean, true if user has granted permission, else false
     */
    private boolean checkCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}