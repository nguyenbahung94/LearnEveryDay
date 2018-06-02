package com.tma.stockmarket.ui.main.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerFragmentComponent;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.view.activity.MainViewRegister;

import java.util.Calendar;

import javax.inject.Inject;


public class RegisterInfoFragment extends Fragment {
    private final static int SUCCESSFUL = 201;
    @Inject
    SharedPreferences mSharedPreferences;
    private MainViewRegister mainViewRegister;
    private Button btnOk;
    private EditText edtFullName, edtPhoneNumber;
    private TextView tvBirthDay;
    private RadioGroup rdGroup;
    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getmNetcomponent())
                .fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_info, container, false);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        edtFullName = (EditText) view.findViewById(R.id.edtFullname);
        tvBirthDay = (TextView) view.findViewById(R.id.tvbirthday);
        rdGroup = (RadioGroup) view.findViewById(R.id.rdGroup);
        edtPhoneNumber = (EditText) view.findViewById(R.id.phonenumber);
        event();
        return view;
    }

    private void event() {
        tvBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvBirthDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhoneNumber.getText().toString().equals("") || edtPhoneNumber.getText() == null || tvBirthDay.getText().toString().equals("") || edtFullName.getText() == null || edtFullName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "name or birth day cant be null", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    saveShared();
                }
            }
        });
    }

    private void saveShared() {
        mSharedPreferences.edit().putString(getString(R.string.fullname), edtFullName.getText().toString()).apply();
        int selected = rdGroup.getCheckedRadioButtonId();
        if (selected == R.id.rbtnGirl) {
            mSharedPreferences.edit().putInt(getString(R.string.sex), 0).apply();
        }
        if (selected == R.id.rdbtnBoy) {
            mSharedPreferences.edit().putInt(getString(R.string.sex), 1).apply();
        }
        mSharedPreferences.edit().putString(getString(R.string.birthday), tvBirthDay.getText().toString()).apply();
        mSharedPreferences.edit().putString(getString(R.string.phone), edtPhoneNumber.getText().toString()).apply();
        mainViewRegister.saveFragment(SUCCESSFUL);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewRegister = (MainViewRegister) context;
    }
}
