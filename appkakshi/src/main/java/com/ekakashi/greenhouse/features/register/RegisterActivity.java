package com.ekakashi.greenhouse.features.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.setting.SettingWebviewActivity;


public class RegisterActivity extends BaseActivity implements RegisterInterface.View {

    private RegisterInterface.Presenter mPresenter;
    private Button btnSend;
    private EditText edEmail;
    private EditText edNickname;
    private EditText edName;
    private EditText edPassword;

    private WebView webView;
    private String urlRegister = "http://sfekakashidev.japaneast.cloudapp.azure.com/auth/register";
    private String URL_PERSONAL_INFO = "https://en.pssol.co.jp/policy/personal_info/";
    private String URL_PERSONAL_INFO_DEFAULT = "https://www.pssol.co.jp/policy/personal_info/";
    private String URL_PERSONAL_INFO_TERMS = "https://www.e-kakashi.com/service/e-kakashi-ai/terms/";
    private String URL_AUTH_LOGIN = "http://sfekakashidev.japaneast.cloudapp.azure.com/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initPresenter();
        addControls();
        //addEvents();
//        addToolbar();
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.hideToolbarLine();
//        myToolbar.setToolbarBackground(getResources().getColor(R.color.transparent));
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

    private void addControls() {
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlRegister);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(URL_PERSONAL_INFO)) {
                    Intent intent_poli = new Intent(getApplication(), SettingWebviewActivity.class);
                    intent_poli.putExtra(Utils.Register.SETTING_INTENT, 2);
                    intent_poli.putExtra(Utils.Register.LANG_REGISTER, Utils.Name.LOCALE_EN);
                    startActivity(intent_poli);

                } else if (url.equals(URL_PERSONAL_INFO_DEFAULT)) {
                    Intent intent_poli = new Intent(getApplication(), SettingWebviewActivity.class);
                    intent_poli.putExtra(Utils.Register.SETTING_INTENT, 2);
                    intent_poli.putExtra(Utils.Register.LANG_REGISTER, Utils.Name.LOCALE_JA);
                    startActivity(intent_poli);

                } else if (url.equals(URL_PERSONAL_INFO_TERMS)) {
                    Intent intent_poli = new Intent(getApplication(), SettingWebviewActivity.class);
                    intent_poli.putExtra(Utils.Register.SETTING_INTENT, 3);
                    startActivity(intent_poli);
                }
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (view.getOriginalUrl() != null)
                    if (view.getOriginalUrl().equals(URL_AUTH_LOGIN))
                        finish();
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.requestFocus();
//        btnSend = findViewById(R.id.btnSend);
//        edEmail = findViewById(R.id.edEmail);
//        edPassword = findViewById(R.id.edPassword);
//        edName = findViewById(R.id.edName);
//        edNickname = findViewById(R.id.edNickname);
    }

//    private void addEvents() {
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkInfo();
//            }
//        });
//   }

    private void checkInfo() {
        // Reset errors.
        edEmail.setError(null);
        boolean cancel = false;
        int cancelType = 0;

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        String nickname = edNickname.getText().toString();

        //Check email
        if (TextUtils.isEmpty(email)) {
            edEmail.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!mPresenter.isEmailValid(email)) {
            edEmail.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }

        //Check nickname
        if (TextUtils.isEmpty(nickname)) {
            edNickname.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        //Check password
        if (TextUtils.isEmpty(password)) {
            edPassword.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!mPresenter.isPasswordValid(password)) {
            edPassword.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }

        if (!cancel) {
            mPresenter.doSendRequest(email);
        }

    }

    @Override
    public void initPresenter() {
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    public void showDialog(String email) {
        new MaterialDialog.Builder(this)
                .title(R.string.login_check_email)
                .content(getString(R.string.login_send_email) + email + getString(R.string.login_register_text))
                .positiveText(R.string.btn_dialog_ok)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
