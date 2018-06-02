package com.tma.stockmarket.ui.main.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerFragmentComponent;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.interactor.ExchangeRate.LoginFragmentInteractor;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.main.activity.RegisterActivity;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.ui.view.activity.FragmentLoginView;
import com.tma.stockmarket.ui.view.activity.MainActivityView;
import com.tma.stockmarket.utils.url;

import javax.inject.Inject;


public class LoginFragment extends Fragment implements FragmentLoginView {
    @Inject
    LoginFragmentInteractor mLoginFragmentInteractor;
    @Inject
    DBmanager mDBmanager;
    @Inject
    SharedPreferences mSharedPreferences;
    private EditText edtUserName, edtPassWord;
    private Button btnLogin;
    private TextView tvRegisterAccount;
    private MainActivityView mMainActivityView;
    private ImageView imgProfile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).netComponent(((App) getActivity().getApplicationContext()).getmNetcomponent()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginFragmentInteractor.setValueShared(mSharedPreferences, getContext());
        edtUserName = (EditText) view.findViewById(R.id.edt_name);
        edtPassWord = (EditText) view.findViewById(R.id.edt_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        tvRegisterAccount = (TextView) view.findViewById(R.id.tv_registerAccount);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        imgProfile.setVisibility(View.VISIBLE);
        mDBmanager.getListIdUser();
        event();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        imgProfile.setVisibility(View.VISIBLE);
        if (mSharedPreferences != null) {
            edtUserName.setText(mSharedPreferences.getString(getString(R.string.iduser), ""));
            edtPassWord.setText(mSharedPreferences.getString(getString(R.string.passuser), ""));
        }
    }

    private void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginFragmentInteractor.login(edtUserName.getText().toString(), edtPassWord.getText().toString(), mMainActivityView, mDBmanager, mSharedPreferences, LoginFragment.this, getContext());
            }
        });
        tvRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                imgProfile.setVisibility(View.INVISIBLE);
                shareElement();
                // explodeTransitionBycode();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivityView = (MainActivityView) context;
    }

    @Override
    public void success() {
        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {
        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void explodeTransitionBycode() {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        intent.putExtra(url.KEY_ANIM_TYPE, url.TransitionType.ExplodeJava);
        startActivity(intent, options.toBundle());
    }

    public void shareElement() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        String str = getString(R.string.name_transition);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgProfile, str);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

    }
}
