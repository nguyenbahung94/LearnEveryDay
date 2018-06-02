package com.example.nbhung.demotwitter.presentation.register;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.nbhung.demotwitter.App;
import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.common.Prefs;
import com.example.nbhung.demotwitter.common.Utils;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;
import com.example.nbhung.demotwitter.presentation.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {
    @BindView(R.id.parentLayoutRegister)
    ConstraintLayout parentLayout;
    @BindView(R.id.edtEmailRegister)
    AppCompatEditText edtEmail;
    @BindView(R.id.edtUserNameRegister)
    AppCompatEditText edtUserName;
    @BindView(R.id.edtPassWordRegister)
    AppCompatEditText edtPassWord;
    @BindView(R.id.edtConfirmPassWord)
    AppCompatEditText edtConfirmPassWord;

    @Inject
    TwitterAppDataRepository twitterAppDataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        setPresenter(new RegisterPresenter(twitterAppDataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");

        }
    }

    private void initInject() {
        App.getAppComponent(this).inject(this);
    }

    @OnClick(R.id.btnRegisterAccount)
    public void clickBtnRegister(View view) {
        getPresenter().registerAccount(edtUserName.getText().toString(), edtEmail.getText().toString(), edtPassWord.getText().toString(), edtConfirmPassWord.getText().toString());

    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @OnFocusChange(R.id.btnRegisterAccount)
    public void focusChangeBtnRegister(View view) {
        Utils.hideKeyboard(view);
    }

    @OnClick(R.id.parentLayoutRegister)
    public void clickParent(View view) {
        Utils.hideKeyboard(view);
    }

    @Override
    public void registerSuccess(String message) {
        Prefs.getPrefsInstance(RegisterActivity.this).saveUserName(edtUserName.getText().toString());
        Prefs.getPrefsInstance(RegisterActivity.this).savePassWord(edtConfirmPassWord.getText().toString());
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
        Prefs.getPrefsInstance(RegisterActivity.this).setIsShowNoti(true);
        onBackPressed();
    }

    @Override
    public void registerFailed(ErrorType errorType) {
        Prefs.getPrefsInstance(RegisterActivity.this).saveUserName("");
        Prefs.getPrefsInstance(RegisterActivity.this).savePassWord("");
        Prefs.getPrefsInstance(RegisterActivity.this).setIsShowNoti(false);
        Toast.makeText(RegisterActivity.this, Utils.getErrorMessageFrom(errorType), Toast.LENGTH_SHORT).show();
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
