/*
package com.ekakashi.greenhouse.features.device_registerId;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.device_confirmation.EKDeviceConfirmationActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class EKRegisterDeviceActivity extends BaseActivity implements ZXingScannerView.ResultHandler, View.OnClickListener, EKRegisterDeviceInterface.View {
    private EditText editBarCode;
    //    private ZXingScannerView mScannerView;
    private static Handler handler = new Handler();
    private EKRegisterDeviceInterface.Presenter mPresenter;
//    private ViewGroup contentFrame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceqr);
        initView();
        addToolbar();

    }

    private void initView() {
        mPresenter = new EKRegisterDevicePresenter(this);
//        edtCode = findViewById(R.id.edtDeviceId);
//        contentFrame = findViewById(R.id.content_frame);
//        contentFrame.addView(mScannerView);
//        mScannerView.setResultHandler(this);
//        mScannerView.startCamera();
        findViewById(R.id.btn_save_device).setOnClickListener(this);
        editBarCode = findViewById(R.id.edit_device_id);
    }

    private void addToolbar() {
        TextView btnBack = mToolbar.findViewById(R.id.tvToolbarLeft);
        btnBack.setText(R.string.cancel);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        TextView title = mToolbar.findViewById(R.id.tvToolbarCenter);
        title.setText(R.string.device_add_device);
    }


    @Override
    protected void onPause() {
        super.onPause();
//        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                edtCode.setText(result.getText());
//                mScannerView.resumeCameraPreview(EKRegisterDeviceActivity.this);
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvToolbarLeft:
                finish();
                break;
            case R.id.btn_scan_code:
                Intent intent = new Intent(this, EKScanBarcodeActivity.class);
                startActivityForResult(intent, Utils.Constant.EK_SCAN_BAR_CODE_ACTIVITY);
                break;
            case R.id.btnAddDevice:
                showLoadingDialog(getString(R.string.message_please_wait));
                String token = Prefs.getInstance(this).getToken();
                mPresenter.onRegisterDevice(token, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRegisterSuccess() {
        hideLoadingDialog();
        Intent intent = new Intent(this, EKDeviceConfirmationActivity.class);
        startActivityForResult(intent, Utils.Constant.EK_DEVICE_REGISTER_ACTIVITY);
    }

    @Override
    public void onRegisterFail(String message) {
        hideLoadingDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.Constant.EK_DEVICE_REGISTER_ACTIVITY && resultCode == RESULT_OK) {
            this.finish();
        } else if (requestCode == Utils.Constant.EK_SCAN_BAR_CODE_ACTIVITY && resultCode == RESULT_OK) {
            this.editBarCode.setText(data.getStringExtra(Utils.Constant.EK_SCAN_BAR_CODE));
        }
    }
}
*/
