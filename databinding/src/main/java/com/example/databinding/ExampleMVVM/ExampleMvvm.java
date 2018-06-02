package com.example.databinding.ExampleMVVM;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.databinding.ExampleMVVM.Model.User;
import com.example.databinding.R;
import com.example.databinding.databinding.ActivityTestMvvmBinding;


public class ExampleMvvm extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestMvvmBinding activityTestMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_mvvm);
        User user = new User();
        user.setName("raviatadavijava");
        user.setEmail("12345@gmail.com.vn");
        activityTestMvvmBinding.setUser(user);
        activityTestMvvmBinding.setActivity(this);
    }

    public void onButtonClick(String email) {
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
    }
}
