package com.ekakashi.greenhouse.features.device_confirmation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyTextWatcher;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.DatabaseHelper;
import com.ekakashi.greenhouse.database.dao.DeviceObject;
import com.ekakashi.greenhouse.database.dao.DeviceObjectDao;


public class EKDeviceConfirmationActivity extends BaseActivity implements View.OnClickListener, EKDeviceConfirmationInterface.View {

    private EditText editName;
    private ImageButton btnClearName;
    private RelativeLayout relWindowType;
    private RelativeLayout relForEachDevice;
    private Button btnRegister;
    private Button btnPerformInitial;

    private DatabaseHelper databaseHelper;
    private DeviceObjectDao mDeviceObjectDao;
    private DeviceObject mDeviceObject;
    private String idDevice;
    private boolean isAfterInitial = false;

    private EKDeviceConfirmationInterface.Presenter mPresenter;

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, EKDeviceConfirmationActivity.class);
        intent.putExtra("idDevice", id);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_confirmations);
        if (getIntent() != null) {
            idDevice = getIntent().getStringExtra("idDevice");
            mDeviceObject = getIntent().getParcelableExtra(Utils.Constant.EK_DEVICE_OBJECT);
            //        ((TextView) findViewById(R.id.tvDeviceId)).setText(idDevice);
        }
        initView();
        event();
    }

    private void initView() {
        mPresenter = new EKDeviceConfirmationPresenter(this);
        databaseHelper = App.getDatabaseHelper(getApplicationContext());
        mDeviceObjectDao = databaseHelper.getDeviceObject();

        editName = findViewById(R.id.ed_device_name);
        btnClearName = findViewById(R.id.clear_device_name);
        relWindowType = findViewById(R.id.rel_window_type);
        relForEachDevice = findViewById(R.id.rel_for_each_device);
        btnRegister = findViewById(R.id.btn_register);
        btnPerformInitial = findViewById(R.id.btn_perform_initial);

    }

    private void setupToolbar() {
        ImageView btnBack = mToolbar.findViewById(R.id.imgToolbarBack);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        TextView title = mToolbar.findViewById(R.id.tvToolbarCenter);
        title.setText(R.string.device_add_device);
    }

    private void event() {
        editName.addTextChangedListener(new MyTextWatcher(editName, btnClearName));
        btnClearName.setOnClickListener(this);
        relForEachDevice.setOnClickListener(this);
        relWindowType.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnPerformInitial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_device_name:
//                editName.getText().clear();
//                v.setVisibility(View.GONE);
                break;
            case R.id.rel_for_each_device:
                break;
            case R.id.rel_window_type:
                break;
            case R.id.btn_register:
                showLoadingDialog(getString(R.string.message_please_wait));
                String token = Prefs.getInstance(this).getToken();
                mPresenter.onRegisterDevice(token, "");
                break;
            case R.id.btn_perform_initial:
                break;

            case R.id.imgToolbarBack:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            isAfterInitial = true;
            btnRegister.setEnabled(true);
        }
    }

    @Override
    public void onRegisterSuccess() {
        hideLoadingDialog();
        Intent intent = getIntent();
        intent.putExtra(Utils.Constant.EK_DEVICE_OBJECT, mDeviceObject);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRegisterFail(String error) {
        hideLoadingDialog();
    }
}
