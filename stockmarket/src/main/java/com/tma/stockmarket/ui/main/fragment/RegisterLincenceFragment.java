package com.tma.stockmarket.ui.main.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerFragmentComponent;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.view.activity.MainViewRegister;

import javax.inject.Inject;


public class RegisterLincenceFragment extends Fragment {
    private final static int SUCCESSFUL = 202;
    private MainViewRegister mainViewRegister;
    @Inject
    SharedPreferences mSharedPreferences;
    private CheckBox checkBox;
    private Button btnOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getmNetcomponent())
                .fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_lincence, container, false);
        checkBox = (CheckBox) view.findViewById(R.id.cbrule);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        event();
        return view;
    }

    private void event() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    saveShared();
                } else {
                    Toast.makeText(getActivity(), "Need to checked rule", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveShared() {
        mSharedPreferences.edit().putBoolean(getString(R.string.rule), true).apply();
        mainViewRegister.saveFragment(SUCCESSFUL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewRegister = (MainViewRegister) context;
    }
}
