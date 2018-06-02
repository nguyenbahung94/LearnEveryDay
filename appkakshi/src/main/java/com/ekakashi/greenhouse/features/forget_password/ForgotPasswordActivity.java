package com.ekakashi.greenhouse.features.forget_password;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.setting.SettingWebviewActivity;


public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordInterface.View {

    private ForgotPasswordInterface.Presenter mPresenter;
    private Button btnSend;
    private EditText edEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initPresenter();
        addToolbar();

        edEmail = findViewById(R.id.edEmail);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });

        findViewById(R.id.btn_back_to_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_help = new Intent(ForgotPasswordActivity.this, SettingWebviewActivity.class);
                intent_help.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.INTENT_HELP_FORGOT_PASSWORD);
                startActivity(intent_help);
            }
        });

    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.hideToolbarLine();
        myToolbar.setToolbarBackground(getResources().getColor(R.color.transparent));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
    }

    private void checkEmail() {
        // Reset errors.
        edEmail.setError(null);

        boolean cancel = false;
        String email = edEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edEmail.setError(getString(R.string.error_email_is_required));
            cancel = true;
        } else if (!Utils.isEmailValid(email)) {
            edEmail.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }

        if (cancel) {
            // There was an error
            edEmail.requestFocus();
        } else {
            if (isNetworkOffline()) {
                return;
            }
            showLoadingDialog(getString(R.string.message_please_wait));
            mPresenter.doSendRequest(email);
        }
    }


    @Override
    public void initPresenter() {
        mPresenter = new ForgotPasswordPresenter(this);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void showDialog(String email, Boolean isError) {
        hideLoadingDialog();
        String titleEmail = getString(R.string.message_check_the_email);
        String emailFull = getString(R.string.login_send_email_full, email);
//        if (isError) {
//            titleEmail = getString(R.string.message_incorrect_email);
//            emailFull = getString(R.string.message_please_insert_valid_email);
//        }
        //customConfirmDialog(titleEmail, emailFull);
        showDialogEmail(titleEmail, emailFull);
    }

    private void showDialogEmail(String titleEmail, String emailFull) {
        new MaterialDialog.Builder(this)
                .title(titleEmail)
                .content(emailFull)
                .cancelable(false)
                .positiveText(R.string.btn_dialog_ok).positiveColor(Utils.getColor(this, R.color.bg_green_btn))
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
