package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.di.component.DaggerFragmentComponent;
import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.interactor.FragmentLoginInteractor;
import com.tma.videotraining.ui.view.MainView;
import com.tma.videotraining.ui.view.ViewFragmentLogin;

import javax.inject.Inject;

/**
 * Created by nbhung on 7/10/2017.
 */

public class FragmentLogin extends Fragment implements ViewFragmentLogin {
    @Inject
    FragmentLoginInteractor fragmentLoginInteractor;
    private EditText edtName, edtPass;
    private TextView tvRegisterAccount;
    private Button btnSubmit;
    private View view;
    private MainView mainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        event();
        return view;
    }

    private void init() {
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtPass = (EditText) view.findViewById(R.id.edt_password);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        tvRegisterAccount = (TextView) view.findViewById(R.id.tv_registerAccount);
    }

    private void event() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoginInteractor.login(edtName.getText().toString(), edtPass.getText().toString(), mainView, FragmentLogin.this);

            }
        });
        tvRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRegister fagment = new FragmentRegister();
                swap(fagment);

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }

    @Override
    public void success() {
        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
    }

    private void swap(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }
}
