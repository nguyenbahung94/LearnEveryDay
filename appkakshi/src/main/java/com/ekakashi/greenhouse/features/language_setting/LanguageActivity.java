package com.ekakashi.greenhouse.features.language_setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.LocaleUtils;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;

import java.util.Locale;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layoutJapan;
    private RelativeLayout layoutEnglish;
    private ImageView imgJapanSelected;
    private ImageView imgEnglishSelected;
    private Locale mLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        addToolbar();
        addControls();
        addEvents();
    }

    private void addToolbar() {
        final MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.language_setting), "");
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                showDiaLogConfirm();
            }
        });
    }

    private void addEvents() {
        layoutJapan.setOnClickListener(this);
        layoutEnglish.setOnClickListener(this);
    }

    private void addControls() {
        layoutEnglish = findViewById(R.id.layoutEnglish);
        layoutJapan = findViewById(R.id.layoutJapan);
        imgEnglishSelected = findViewById(R.id.imgEnglishSelect);
        imgJapanSelected = findViewById(R.id.imgJapanSelect);

        if (Prefs.getInstance(this).getLocale().equals(Utils.Name.LOCALE_JA)) {
            imgJapanSelected.setVisibility(View.VISIBLE);
        } else {
            imgEnglishSelected.setVisibility(View.VISIBLE);
        }

        TextView tvLanguageJa = findViewById(R.id.tvLanguageJa);
        TextView tvLanguageEn = findViewById(R.id.tvLanguageEn);
        tvLanguageJa.setText(getString(R.string.locale_ja));
        tvLanguageEn.setText(getString(R.string.locale_en));
    }

    private void showDiaLogConfirm() {
        if (mLocale == null || Prefs.getInstance(this).getLocale().equalsIgnoreCase(mLocale.getLanguage())) {
            finish();
            return;
        }
        Utils.showDiaLogConfirm(this, R.string.warning, R.string.language_setting_confirm, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                LocaleUtils.setLocale(mLocale);
                LocaleUtils.updateConfig(LanguageActivity.this);

                Prefs.getInstance(LanguageActivity.this).saveLocale(mLocale.getLanguage());
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        //Locale English by default
        String locale = Utils.Name.LOCALE_EN;
        switch (v.getId()) {
            case R.id.layoutEnglish:
                locale = Utils.Name.LOCALE_EN;
                imgEnglishSelected.setVisibility(View.VISIBLE);
                imgJapanSelected.setVisibility(View.INVISIBLE);
                break;
            case R.id.layoutJapan:
                locale = Utils.Name.LOCALE_JA;
                imgEnglishSelected.setVisibility(View.INVISIBLE);
                imgJapanSelected.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
        mLocale = new Locale(locale);
    }
}
