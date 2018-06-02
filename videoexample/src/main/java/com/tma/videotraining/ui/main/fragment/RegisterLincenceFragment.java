package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.view.MainViewRegister;

/**
 * Created by nbhung on 7/17/2017.
 */

public class RegisterLincenceFragment extends Fragment {
    private final static int SUCCESSFUL = 202;
    private Button btnSaveLincence;
    private MainViewRegister mainViewRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_lincence, container, false);
        btnSaveLincence = (Button) view.findViewById(R.id.btnComplete);
        btnSaveLincence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewRegister.saveFragment(SUCCESSFUL);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewRegister = (MainViewRegister) context;
    }
}
