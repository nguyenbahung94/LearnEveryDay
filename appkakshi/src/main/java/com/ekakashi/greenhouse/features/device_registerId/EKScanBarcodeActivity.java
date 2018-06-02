/*
package com.ekakashi.greenhouse.features.device_registerId;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Utils;

import java.io.IOException;


public class EKScanBarcodeActivity extends BaseActivity implements View.OnClickListener {
    private SurfaceView cameraView;

    private SurfaceHolder surfaceHolder;
    public static String resultCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView = findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        surfaceHolder = cameraView.getHolder();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if (!barcodeDetector.isOperational()) {
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }

        addToolbar();
    }

    private void addToolbar() {
        ImageView btnBack = mToolbar.findViewById(R.id.imgToolbarBack);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        TextView title = mToolbar.findViewById(R.id.tvToolbarCenter);
        title.setText(R.string.device_scan_device);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();
    }

    private void setUp() {
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(800, 480)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(EKScanBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    final Barcode barcode = barcodes.valueAt(0);
                    resultCode = barcode.rawValue;
                    Log.e("code=", resultCode);
                    Intent intent = getIntent();
                    intent.putExtra(Utils.Constant.EK_SCAN_BAR_CODE, resultCode);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgToolbarBack:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
    }
}
*/
