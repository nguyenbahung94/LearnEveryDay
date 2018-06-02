package com.tma.stockmarket.ui.main.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerFragmentComponent;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.interactor.ExchangeRate.RegisterFragmentInteractor;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.utils.url;

import javax.inject.Inject;

import at.grabner.circleprogress.CircleProgressView;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int FRAGMENT_PROFILE_RESULT_CODE = 200;
    private final static int FRAGMENT_INFO_RESULT_CODE = 201;
    private final static int FRAGMENT_LICENCE_RESULT_CODE = 202;
    @Inject
    RegisterFragmentInteractor registerInteractor;
    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    DBmanager mDBmanager;
    private url.TransitionType type;
    private CircleProgressView mCircleProgressView;
    private Animation fadeInTextView;
    private ImageView imgProfile, imgInformation, imgReadLicence, imgCompleteRegister, imgCancel;
    private ProgressBar mProgressBar1, mProgressBar2, mProgressBar3;
    private View viewRegisterProfile, viewRegisterInfor, viewRegisterLicence, viewRegisterCompleteRegister;
    private TextView tvProfile, tvDescriptionProfile, tvInformation, tvDescriptioninfor, tvReadlicence, tvDescriptionLicence, tvCompelteRegister, tvDescriptionCompeltedRegister, tvShowPercent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).netComponent(((App) getApplicationContext()).getmNetcomponent()).build().inject(this);
        init();
        //  initAnimation();
    }


    private void init() {
        viewRegisterProfile = findViewById(R.id.viewregisterprofile);
        viewRegisterProfile.setOnClickListener(this);
        viewRegisterInfor = findViewById(R.id.viewregisterInfor);
        viewRegisterInfor.setOnClickListener(this);
        viewRegisterInfor.setEnabled(false);
        viewRegisterLicence = findViewById(R.id.viewregisterlicence);
        viewRegisterLicence.setOnClickListener(this);
        viewRegisterLicence.setEnabled(false);
        viewRegisterCompleteRegister = findViewById(R.id.viewregisterCompleted);
        viewRegisterCompleteRegister.setOnClickListener(this);
        viewRegisterCompleteRegister.setEnabled(false);

        imgProfile = (ImageView) findViewById(R.id.img_profile);
        imgInformation = (ImageView) findViewById(R.id.img_information);
        imgReadLicence = (ImageView) findViewById(R.id.img_readlicence);
        imgCompleteRegister = (ImageView) findViewById(R.id.img_CompletedRegister);

        tvProfile = (TextView) findViewById(R.id.tv_profile);
        tvInformation = (TextView) findViewById(R.id.tv_information);
        tvReadlicence = (TextView) findViewById(R.id.tv_readlicence);
        tvCompelteRegister = (TextView) findViewById(R.id.tv_completedRegister);

        tvDescriptionProfile = (TextView) findViewById(R.id.tv_descriptionprofile);
        tvDescriptioninfor = (TextView) findViewById(R.id.tv_descriptionInfo);
        tvDescriptionLicence = (TextView) findViewById(R.id.tv_descriptionLicence);
        tvDescriptionCompeltedRegister = (TextView) findViewById(R.id.tv_descriptionComplete);


        mProgressBar1 = (ProgressBar) findViewById(R.id.progress_barofregister1);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progress_barofregister2);
        mProgressBar3 = (ProgressBar) findViewById(R.id.progress_barofregister3);

        fadeInTextView = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textalpha);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                registerInteractor.setValueShared(mSharedPreferences, getApplicationContext());
                finishAfterTransition();
            }
        });
        mCircleProgressView = (CircleProgressView) findViewById(R.id.CircleProgressView);

        //    type = (url.TransitionType) getIntent().getSerializableExtra(url.KEY_ANIM_TYPE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initAnimation() {
        switch (type) {
            case ExplodeJava:
                Explode enter = new Explode();
                enter.setDuration(500);
                getWindow().setEnterTransition(enter);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.viewregisterprofile:
                registerInteractor.setValueShared(mSharedPreferences, getApplicationContext());
                startActivityForResult(FRAGMENT_PROFILE_RESULT_CODE, 0);
                break;
            case R.id.viewregisterInfor:
                startActivityForResult(FRAGMENT_INFO_RESULT_CODE, 1);
                break;
            case R.id.viewregisterlicence:
                startActivityForResult(FRAGMENT_LICENCE_RESULT_CODE, 2);
                break;
            case R.id.viewregisterCompleted:
                int current = (int) mCircleProgressView.getCurrentValue();
                mCircleProgressView.setValueAnimated(current, current + 1, 100);
                registerInteractor.addAccount(mDBmanager, mSharedPreferences, getApplicationContext(), tvCompelteRegister, tvDescriptionCompeltedRegister, imgCompleteRegister);
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        finishAfterTransition();
                    }
                }.start();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FRAGMENT_PROFILE_RESULT_CODE) {
                registerInteractor.changItem(tvProfile, tvDescriptionProfile, imgProfile, imgInformation, tvInformation, tvDescriptioninfor, mProgressBar1, viewRegisterInfor, mCircleProgressView, getApplicationContext(), 1);
            }
            if (requestCode == FRAGMENT_INFO_RESULT_CODE) {
                registerInteractor.changItem(tvInformation, tvDescriptioninfor, imgInformation, imgReadLicence, tvReadlicence, tvDescriptionLicence, mProgressBar2, viewRegisterLicence, mCircleProgressView, getApplicationContext(), 2);
            }
            if (requestCode == FRAGMENT_LICENCE_RESULT_CODE) {
                registerInteractor.changItem(tvReadlicence, tvDescriptionLicence, imgReadLicence, imgCompleteRegister, tvCompelteRegister, tvDescriptionCompeltedRegister, mProgressBar3, viewRegisterCompleteRegister, mCircleProgressView, getApplicationContext(), 3);
            }
        }
    }

    private void startActivityForResult(int codeFragment, int codeFragmentStart) {
        Intent i = new Intent(RegisterActivity.this, MainRegister.class);
        i.putExtra(getString(R.string.choose_fragment), codeFragmentStart);
        startActivityForResult(i, codeFragment);
        overridePendingTransitionEnter();
    }

    private void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
