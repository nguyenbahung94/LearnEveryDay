package com.ekakashi.greenhouse.features.devices_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.DeviceDetaildResponse;

/**
 * Created by nbhung on 12/22/2017.
 */

public class DevicesDetailsActivity extends BaseActivity implements View.OnClickListener, DevicesDetailsView.View {
    private ImageView imvSystemControl;
    private ImageView imvStatus;
    private TextView tvPercent;
    private TextView tvErrorCode;
    private DevicesDetailsPresenter devicesDetailsPresenter;
    private MyToolbar myToolbar;
    private ImageView imvMain;
    private MaterialDialog materialDialog;
    private DeviceDetaildResponse deviceDetaildResponse;
    private boolean status = false;
    private boolean systemControl = false;
    private Button btnStop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_detail);
      //  initView();
    }

    private void initView() {
       /* myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, "Devices detail", "The North Field X");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();

            }

            @Override
            public void onToolbarRightListener() {

            }
        });
        materialDialog = Utils.createProgressBar(DevicesDetailsActivity.this, getString(R.string.message_pleasewait));
        materialDialog.show();
        imvMain = findViewById(R.id.imvMain);*/
        /*findViewById(R.id.btnReplaceSensor).setOnClickListener(this);
        findViewById(R.id.llOpenClose).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnResetSensor).setOnClickListener(this);*/
/*        tvErrorCode = findViewById(R.id.tvErrorCode);
        imvSystemControl = findViewById(R.id.imvSystemControl);*/
  /*      imvSystemControl.setOnClickListener(this);
        findViewById(R.id.llContainRecord).setOnClickListener(this);
        devicesDetailsPresenter = new DevicesDetailsPresenter(this);
        devicesDetailsPresenter.getDetailById("M03100000069");*/
    }

    @Override
    public void onClick(View view) {
       /* switch (view.getId()) {
            case R.id.btnResetSensor:

                break;
            case R.id.btnStop:
                Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llOpenClose:
                openDialogBottom();
                break;
            case R.id.btnReplaceSensor:
                break;
            case R.id.imvSystemControl:
                systemControl = !systemControl;
                editDeviceDetail();
                break;
            case R.id.llContainRecord:
                startActivity(new Intent(DevicesDetailsActivity.this, OperationRecordActivity.class));
                break;
        }*/
    }

    /*private void openDialogBottom() {
        final String[] arrayString = new String[]{"0%", "1%", "2%", "3%", "4%", "5%", "6%", "7%", "8%", "9%", "10%",
                "11%", "12%", "13%", "14%", "15%", "16%", "17%", "18%", "19%", "20%",
                "21%", "22%", "23%", "24%", "25%", "26%", "27%", "28%", "29%", "30%",
                "31%", "32%", "33%", "34%", "35%", "36%", "37%", "38%", "39%", "40%",
                "41%", "42%", "43%", "44%", "45%", "46%", "47%", "48%", "49%", "50%",
                "51%", "52%", "53%", "54%", "55%", "56%", "57%", "58%", "59%", "60%",
                "61%", "62%", "63%", "64%", "65%", "66%", "67%", "68%", "69%", "70%",
                "71%", "72%", "73%", "74%", "75%", "76%", "77%", "78%", "79%", "80%",
                "81%", "82%", "83%", "84%", "85%", "86%", "87%", "88%", "89%", "90%",
                "91%", "92%", "93%", "94%", "95%", "96%", "97%", "98%", "99%", "100%",};
        final BottomSheetDialog dialog = new BottomSheetDialog(DevicesDetailsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_bottom_sheet, null);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(arrayString.length - 1);
        numberPicker.setDisplayedValues(arrayString);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //set wrap true or false, try it you will know the difference
        numberPicker.setWrapSelectorWheel(false);
        *//*numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return arrayString[i];
            }
        });*//*
        dialogView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialogView.findViewById(R.id.tvExcute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = numberPicker.getValue();
                Log.e("pos====", String.valueOf(pos));
                dialog.cancel();
            }
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }*/


    /*private void editDeviceDetail() {
        if (deviceDetaildResponse != null) {
            if (materialDialog != null)
                materialDialog.show();
            int inputStatus;
            if (status)
                inputStatus = 1;
            else inputStatus = 0;
            DeviceDetailRequest deviceDetailRequest = new DeviceDetailRequest(inputStatus, deviceDetaildResponse.getId(), deviceDetaildResponse.getOpenDegree(), deviceDetaildResponse.getSnName(), systemControl);
            devicesDetailsPresenter.editDeviceDetail(deviceDetailRequest);
        }

    }*/

    @Override
    public void getDetailById(DeviceDetaildResponse deviceDetaildResponse) {
        setUpUI(deviceDetaildResponse);
    }

    @Override
    public void failed(String failed) {
        closeDialog();
        Toast.makeText(getApplicationContext(), failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editDeviceSuccess(DeviceDetaildResponse deviceDetaildResponse) {
        setUpUI(deviceDetaildResponse);
    }

    @Override
    public void tokenExpired() {
        closeDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onDestroy() {
        if (devicesDetailsPresenter != null)
            devicesDetailsPresenter.ondetroy();
        if (materialDialog != null)
            materialDialog = null;
        super.onDestroy();
    }

    private void closeDialog() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
            materialDialog.cancel();
        }
    }

    private void setUpUI(DeviceDetaildResponse deviceDetaildResponse) {

        if (deviceDetaildResponse != null) {
            this.deviceDetaildResponse = deviceDetaildResponse;
            if (deviceDetaildResponse.isAbnormal()) {
                findViewById(R.id.llTwo).setVisibility(View.VISIBLE);
                findViewById(R.id.llButton).setVisibility(View.VISIBLE);
                findViewById(R.id.llOne).setVisibility(View.GONE);
            } else {
                findViewById(R.id.llTwo).setVisibility(View.GONE);
                findViewById(R.id.llButton).setVisibility(View.GONE);
                findViewById(R.id.llOne).setVisibility(View.VISIBLE);
                if (deviceDetaildResponse.getActivationStatus() == 0) {
                    status = false;
                    imvStatus.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iconoff));
                } else if (deviceDetaildResponse.getActivationStatus() == 1) {
                    status = true;
                    imvStatus.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iconon));
                }
                if (deviceDetaildResponse.isSystemAutoControl()) {
                    systemControl = true;
                    imvSystemControl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iconon));
                } else {
                    systemControl = false;
                    imvSystemControl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iconoff));
                }
            }
            Glide.with(this).load(deviceDetaildResponse.getSnIconPath()).into(imvMain);
        }
        Toast.makeText(getApplicationContext(), "getRecipeSuccess", Toast.LENGTH_SHORT).show();
        closeDialog();
    }
}
