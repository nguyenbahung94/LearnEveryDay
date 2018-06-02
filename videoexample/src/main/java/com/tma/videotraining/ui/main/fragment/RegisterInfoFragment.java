package com.tma.videotraining.ui.main.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.view.MainViewRegister;

import java.util.Calendar;

/**
 * Created by nbhung on 7/17/2017.
 */

public class RegisterInfoFragment extends Fragment {
    private final static int SUCCESSFUL = 201;
    TextView tvBirthday;
    RadioButton radMale, radFemale;
    private Button btnSaveInfo;
    private MainViewRegister mainViewRegister;

    Calendar calendar = Calendar.getInstance();
    int date = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_info, container, false);

        tvBirthday = (TextView) view.findViewById(R.id.tvBirthday);
        radMale = (RadioButton) view.findViewById(R.id.radMale);
        radMale.setChecked(true);
        radFemale = (RadioButton) view.findViewById(R.id.radFemale);
        btnSaveInfo = (Button) view.findViewById(R.id.btn_save_info);

        tvBirthday.setText(date + "/" + (month + 1) + "/" + year);
        tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog();
            }
        });
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewRegister.saveFragment(201);
            }
        });
        return view;
    }

    private void datePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvBirthday.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, date);
        dialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewRegister=(MainViewRegister)context;
    }
}
