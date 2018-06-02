package com.bat.firstcom.supervisorapp.presentation.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Strings;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.DialogHelper;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.SnackbarHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    protected DialogHelper dialogHelper;
    private ProgressDialog loadingDialog;
    protected SnackbarHelper snackbarHelper;

    @Inject
    P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onTakeView(this);
        getPresenter().setCompositeDisposable(new CompositeDisposable());
        dialogHelper = new DialogHelper(this, getLayoutInflater());
        snackbarHelper = new SnackbarHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        getPresenter().onDestroyView();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().unsubscribe();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (getPresenter().getCompositeDisposable() == null) {
            getPresenter().setCompositeDisposable(new CompositeDisposable());
        }
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    final public P getPresenter() {
        return presenter;
    }

    protected void showToastException(String exceptionMessage) {
        Toast.makeText(this, getResources().getString(R.string.prefix_exception)
                + exceptionMessage, Toast.LENGTH_LONG).show();
    }

    protected void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void showActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        //Disable soft navigation back button by leave this empty
        //instead of call super.onBackPressed();
    }

    protected void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void showLoadingDialog(String message) {
        loadingDialog = ProgressDialog.show(this, Strings.EMPTY, message
                , true);
    }

    protected void clearUserDataInSharePref() {
        App.getInstance().saveStringToSharePref(SharePref.USER_TOKEN, Strings.EMPTY);
        App.getInstance().saveIntToSharePref(SharePref.BRAND, -1);
    }

    protected void saveUserDataToSharePref(String token, int brand, String userName) {
        App.getInstance().saveStringToSharePref(SharePref.USER_TOKEN, token);
        App.getInstance().saveIntToSharePref(SharePref.BRAND, brand);
        App.getInstance().saveStringToSharePref(SharePref.USER_NAME, userName);
    }
}
