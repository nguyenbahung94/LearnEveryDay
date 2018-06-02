package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.di.component.DaggerFragmentComponent;
import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.interactor.FragmentRegisterInteractor;
import com.tma.videotraining.ui.main.activity.MainRegister;
import com.tma.videotraining.ui.view.MainView;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nbhung on 7/14/2017.
 */

public class FragmentRegister extends Fragment implements View.OnClickListener {
    private final static int FRAGMENT_PROFILE_RESULT_CODE = 200;
    private final static int FRAGMENT_INFO_RESULT_CODE = 201;
    private final static int FRAGMENT_LICENCE_RESULT_CODE = 202;
    @Inject
    FragmentRegisterInteractor mFragmentRegisterInteractor;
    private MainView mainView;
    private Animation fadeInTextView;
    private ImageView imgProfile, imgInformation, imgReadLicence, imgCompleteRegister;
    private ProgressBar mProgressBar1, mProgressBar2, mProgressBar3;
    private View view, viewRegisterProfile, viewRegisterInfor, viewRegisterLicence, viewRegisterCompleteRegister;
    private TextView tvProfile, tvDescriptionProfile, tvInformation, tvDescriptioninfor, tvReadlicence, tvDescriptionLicence, tvCompelteRegister, tvDescriptionCompeltedRegister, tvShowPercent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        init();
        return view;
    }

    private void init() {
        viewRegisterProfile = view.findViewById(R.id.viewregisterprofile);
        viewRegisterProfile.setOnClickListener(this);
        viewRegisterInfor = view.findViewById(R.id.viewregisterInfor);
        viewRegisterInfor.setOnClickListener(this);
        viewRegisterInfor.setEnabled(false);
        viewRegisterLicence = view.findViewById(R.id.viewregisterlicence);
        viewRegisterLicence.setOnClickListener(this);
        viewRegisterLicence.setEnabled(false);
        viewRegisterCompleteRegister = view.findViewById(R.id.viewregisterCompleted);
        viewRegisterCompleteRegister.setOnClickListener(this);
        viewRegisterCompleteRegister.setEnabled(false);

        imgProfile = (ImageView) view.findViewById(R.id.img_profile);
        imgInformation = (ImageView) view.findViewById(R.id.img_information);
        imgReadLicence = (ImageView) view.findViewById(R.id.img_readlicence);
        imgCompleteRegister = (ImageView) view.findViewById(R.id.img_CompletedRegister);

        tvProfile = (TextView) view.findViewById(R.id.tv_profile);
        tvInformation = (TextView) view.findViewById(R.id.tv_information);
        tvReadlicence = (TextView) view.findViewById(R.id.tv_readlicence);
        tvCompelteRegister = (TextView) view.findViewById(R.id.tv_completedRegister);

        tvDescriptionProfile = (TextView) view.findViewById(R.id.tv_descriptionprofile);
        tvDescriptioninfor = (TextView) view.findViewById(R.id.tv_descriptionInfo);
        tvDescriptionLicence = (TextView) view.findViewById(R.id.tv_descriptionLicence);
        tvDescriptionCompeltedRegister = (TextView) view.findViewById(R.id.tv_descriptionComplete);


        mProgressBar1 = (ProgressBar) view.findViewById(R.id.progress_barofregister1);
        mProgressBar2 = (ProgressBar) view.findViewById(R.id.progress_barofregister2);
        mProgressBar3 = (ProgressBar) view.findViewById(R.id.progress_barofregister3);

        fadeInTextView = AnimationUtils.loadAnimation(getActivity(), R.anim.textalpha);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.viewregisterprofile:
                startActivityForResult(FRAGMENT_PROFILE_RESULT_CODE, 0);
                break;
            case R.id.viewregisterInfor:
                startActivityForResult(FRAGMENT_INFO_RESULT_CODE, 1);
                break;
            case R.id.viewregisterlicence:
                startActivityForResult(FRAGMENT_LICENCE_RESULT_CODE, 2);
                break;
            case R.id.viewregisterCompleted:
                tvCompelteRegister.setTextColor(ContextCompat.getColor(getActivity(), R.color.textYellow));
                tvCompelteRegister.startAnimation(fadeInTextView);
                tvDescriptionCompeltedRegister.setTextColor(ContextCompat.getColor(getActivity(), R.color.textYellow));
                tvDescriptionCompeltedRegister.startAnimation(fadeInTextView);
                imgCompleteRegister.setBackgroundResource(R.drawable.circlepsuccess);
                Toast.makeText(getActivity(), "congratulation register success!", Toast.LENGTH_SHORT).show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        mainView.hideNavigationDrawer();

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
                mFragmentRegisterInteractor.changItem(tvProfile, tvDescriptionProfile, imgProfile, imgInformation, tvInformation, tvDescriptioninfor, mProgressBar1, viewRegisterInfor, getActivity(), fadeInTextView);
            }
            if (requestCode == FRAGMENT_INFO_RESULT_CODE) {
                mFragmentRegisterInteractor.changItem(tvInformation, tvDescriptioninfor, imgInformation, imgReadLicence, tvReadlicence, tvDescriptionLicence, mProgressBar2, viewRegisterLicence, getActivity(), fadeInTextView);

            }
            if (requestCode == FRAGMENT_LICENCE_RESULT_CODE) {
                mFragmentRegisterInteractor.changItem(tvReadlicence, tvDescriptionLicence, imgReadLicence, imgCompleteRegister, tvCompelteRegister, tvDescriptionCompeltedRegister, mProgressBar3, viewRegisterCompleteRegister, getActivity(), fadeInTextView);
            }
        }
    }

    private void startActivityForResult(int codeFragment, int codeFragmentStart) {
        Intent i = new Intent(getActivity(), MainRegister.class);
        i.putExtra("CHOOSE_FRAGMENT", codeFragmentStart);
        startActivityForResult(i, codeFragment);
        overridePendingTransitionEnter();
    }

    private void overridePendingTransitionEnter() {
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
