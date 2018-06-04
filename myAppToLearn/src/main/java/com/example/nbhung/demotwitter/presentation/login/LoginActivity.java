package com.example.nbhung.demotwitter.presentation.login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.example.nbhung.demotwitter.App;
import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.common.Prefs;
import com.example.nbhung.demotwitter.common.Utils;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;
import com.example.nbhung.demotwitter.domain.usercases.Login;
import com.example.nbhung.demotwitter.domain.usercases.Register;
import com.example.nbhung.demotwitter.presentation.base.BaseActivity;
import com.example.nbhung.demotwitter.presentation.navigation.NavigationActivity;
import com.example.nbhung.demotwitter.presentation.register.RegisterActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.btnRegister)
    AppCompatButton btnRegister;
    @BindView(R.id.edtUserName)
    AppCompatEditText edtUserName;
    @BindView(R.id.edtPassword)
    AppCompatEditText edtPassWord;
    @BindView(R.id.parentLayout)
    ConstraintLayout parentLayout;

    @Inject
    TwitterAppDataRepository twitterAppDataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new LoginPresenter(twitterAppDataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getPresenter().onTakeView(this);
        hideActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Prefs.getPrefsInstance(LoginActivity.this).IsShowNotification()) {
            getPresenter().createNotification(LoginActivity.this);
            Prefs.getPrefsInstance(LoginActivity.this).setIsShowNoti(false);
        }
        edtUserName.setText(Prefs.getPrefsInstance(LoginActivity.this).getUserName());
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    @OnClick(R.id.parentLayout)
    public void clickParent(ConstraintLayout constraintLayout) {
        Utils.hideKeyboard(constraintLayout);
        edtPassWord.clearFocus();
        edtUserName.clearFocus();
    }

    @OnClick(R.id.btnLogin)
    public void clickBtnLogin(View view) {
        Utils.hideKeyboard(view);
        getPresenter().getLogin(edtUserName.getText().toString(), edtPassWord.getText().toString());
    }

    @OnFocusChange(R.id.btnLogin)
    public void FocusChange(View view) {
        Utils.hideKeyboard(view);
    }

    @Override
    public void loginSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity();
    }

    private void startActivity() {
        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);
    }

    @OnClick(R.id.btnRegister)
    public void startNewActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void loginFailed(ErrorType error) {
        Toast.makeText(this, Utils.getErrorMessageFrom(error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        hideDialogLoading();
    }

    @Override
    public void showLoading() {
        showDialogLoading();
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        snackbarHelper.show(parentLayout, Utils.getErrorMessageFrom(errorType));
    }
}
