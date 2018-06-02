package com.example.designpattern.ObserverPattern.ex1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.designpattern.R;

/**
 * Created by nbhung on 3/19/2018.
 */

public class UserProfileActivity extends AppCompatActivity implements RepositoryObserver {
    private Subject mUserDataRepository;
    private EditText mTextViewUserFullName;
    private EditText mTextViewUserAge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewUserAge =  findViewById(R.id.edtFirstName);
        mTextViewUserFullName =  findViewById(R.id.edtLastName);

        mUserDataRepository = UserDataRepository.getInstance();
        //register with observer
        mUserDataRepository.registerObserver(this);

    }

    @Override
    public void onUserDataChanged(String fullname, int age) {
        mTextViewUserFullName.setText(fullname);
        mTextViewUserAge.setText(age);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserDataRepository.removeObserver(this);
    }
}
