package com.ekakashi.greenhouse.features.edit_place_name;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;

public class EKEditNameActivity extends BaseActivity implements View.OnClickListener, EKEditNameInterface.View {
    private ObjectCreateField createField;
    private EditText edtPlaceName;
    private ImageView imvClose;
    private EKEditNamePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_edit_name);
        imvClose = findViewById(R.id.imvClose);
        edtPlaceName = findViewById(R.id.edtPlaceName);
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.EDIT_NAME);
            edtPlaceName.setText(createField.getPlaceName());
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.txt_edit_place_name), createField.getPlaceName());
        }
        edtPlaceName.setSelection(edtPlaceName.getText().length());
        if (edtPlaceName.getText().length() > 1) {
            imvClose.setVisibility(View.VISIBLE);
        }
        imvClose.setOnClickListener(this);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();

            }

            @Override
            public void onToolbarRightListener() {
                updateName();
            }
        });
        edtPlaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    imvClose.setVisibility(View.GONE);
                } else
                    imvClose.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        mPresenter = new EKEditNamePresenter(this);
    }

    private void updateName() {
        if (edtPlaceName.getText().toString().trim().isEmpty()) {
            Toast.makeText(EKEditNameActivity.this, getString(R.string.require_place_name),
                    Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkOffline()) {
                return;
            }
            showLoadingDialog(getString(R.string.message_please_wait));
            createField.setPlaceName(edtPlaceName.getText().toString().trim());
            mPresenter.updateName(createField);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imvClose:
                edtPlaceName.setText("");
                imvClose.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void success() {
        hideLoadingDialog();
        Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
        Prefs.getInstance(this).saveReloadTimeline(true);
        Toast.makeText(getApplicationContext(), getString(R.string.edit_place_name_success), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Utils.Name.EDIT_NAME, createField);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void failed(String message) {
        hideLoadingDialog();
        Toast.makeText(getApplicationContext(), message != null ? message : getString(R.string.edit_place_name_failed), Toast.LENGTH_SHORT).show();
    }
}
