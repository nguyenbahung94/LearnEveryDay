package com.ekakashi.greenhouse.features.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectLicense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingWebviewActivity extends BaseActivity {

    private WebView webView;
    private final String URL_FACEBOOK = "https://www.facebook.com/ekakashi.jp/";
    private final String URL_POLICY_JA = "https://www.pssol.co.jp/policy/personal_info/";
    private final String URL_POLICY_EN = "https://en.pssol.co.jp/policy/personal_info/";
    private final String URL_TERM_EN = "https://www.e-kakashi.com/terms/";
    private final String URL_TERM_JA = "https://www.e-kakashi.com/terms/";
    private final String URL_HELP_FORGOT_PASSWORD = "http://sfekakashidev.japaneast.cloudapp.azure.com/help/forgot-password";
    private final String URL_INTRUCTIONS = "http://sfekakashidev.japaneast.cloudapp.azure.com/help";

    private final int FACEBOOK = 1;
    private final int POLICY = 2;
    private final int TERM = 3;
    private final int LICENSE = 4;
    private String locale_poli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_webview);

        getIntentFromSettingScreen();
    }

    private void getIntentFromSettingScreen() {
        Intent intent = getIntent();
        Boolean isLang = intent.getBooleanExtra("isLang",false);
        String LangRegister = intent.getStringExtra("LangRegister");
        int setting_intent = intent.getIntExtra(Utils.Constant.SETTING_INTENT, 0);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        switch (setting_intent) {
            case Utils.Constant.SETTING_INTENT_FACEBOOK:
                addToolbar(getResources().getString(R.string.settings_ui_facebook));
                webView.loadUrl(URL_FACEBOOK);
                webView.requestFocus();
                break;
     /*       case POLICY:
                if(isLang) {
                    locale_poli = Prefs.getInstance(this).getLocale();
                }else {
            locale_poli = LangRegister;
        }*/
            case Utils.Constant.SETTING_INTENT_POLICY:
                String locale_poli = Prefs.getInstance(this).getLocale();
                if (locale_poli.equals(Utils.Name.LOCALE_JA)) {
                    addToolbar(getResources().getString(R.string.settings_ui_privacy));
                    webView.loadUrl(URL_POLICY_JA);
                    webView.requestFocus();
                } else {
                    addToolbar(getResources().getString(R.string.settings_ui_privacy));
                    webView.loadUrl(URL_POLICY_EN);
                    webView.requestFocus();
                }
                break;
            case Utils.Constant.SETTING_INTENT_TERM:
                String locale_term = Prefs.getInstance(this).getLocale();
                if (locale_term.equals(Utils.Name.LOCALE_JA)) {
                    addToolbar(getResources().getString(R.string.settings_ui_term));
                    webView.loadUrl(URL_TERM_JA);
                    webView.requestFocus();
                } else {
                    addToolbar(getResources().getString(R.string.settings_ui_term));
                    webView.loadUrl(URL_TERM_EN);
                    webView.requestFocus();
                }
                break;
            case Utils.Constant.SETTING_INTENT_LICENSE:
                addToolbar(getString(R.string.license_information));
                RecyclerView mRvLicenseInformation = findViewById(R.id.rvLicenseInformation);
                mRvLicenseInformation.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);

                LicenseAdapter mLicenseAdapter = new LicenseAdapter(listLicenseInformation());
                mRvLicenseInformation.setLayoutManager(new LinearLayoutManager(this));
                mRvLicenseInformation.setAdapter(mLicenseAdapter);
                break;
            case Utils.Constant.SETTING_INTENT_INTRUCTIONS :
                addToolbar(getResources().getString(R.string.instructions));
                webView.loadUrl(URL_INTRUCTIONS);
                webView.requestFocus();
                break;
            case Utils.Constant.INTENT_HELP_FORGOT_PASSWORD :
                addToolbar(getResources().getString(R.string.help));
                webView.loadUrl(URL_HELP_FORGOT_PASSWORD);
                webView.requestFocus();
                break;
            default:
                break;
        }
    }

    private void addToolbar(String title) {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, title, "");
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

    private List<ObjectLicense> listLicenseInformation() {
        List<ObjectLicense> licenses = new ArrayList<>();
        List<String> licenseTitles = Arrays.asList(getResources().getStringArray(R.array.licenseTitles));
        List<String> licenseContents = Arrays.asList(getResources().getStringArray(R.array.licenseContents));
        int i, size = licenseContents.size();
        for (i = 0; i < size; i++) {
            licenses.add(new ObjectLicense(licenseTitles.get(i),licenseContents.get(i)));
        }
        return licenses;
    }

}
