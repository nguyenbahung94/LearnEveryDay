/*
package com.ekakashi.greenhouse.features.device_registerId;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

*/
/**
 * Created by nbhung on 11/16/2017.
 *//*


public class SimpleBarcodeVision extends AppCompatActivity {
    private Button btnBarCode;
    private ImageView imageView;
    private TextView tvContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_vision);
        btnBarCode = findViewById(R.id.button);
          tvContent=findViewById(R.id.txtContent);
        imageView = findViewById(R.id.imgview);
        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);
        imageView.setImageBitmap(bitmap);

        btnBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarcodeDetector detector = new BarcodeDetector.Builder(SimpleBarcodeVision.this).setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
                if (!detector.isOperational()) {
                    tvContent.setText("Could not set up the detector");
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<Barcode> barcodeSparseArray = detector.detect(frame);
                Barcode thisCode = barcodeSparseArray.valueAt(0);
                tvContent.setText(thisCode.rawValue);
            }
        });

    }
}
*/
