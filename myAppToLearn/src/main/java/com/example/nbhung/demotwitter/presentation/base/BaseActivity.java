package com.example.nbhung.demotwitter.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.presentation.commonwidget.SnackbarHelper;

import java.nio.Buffer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    protected MaterialDialog materialDialog;
    protected SnackbarHelper snackbarHelper;
    P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onTakeView(this);
        getPresenter().setCompositeDisposable(new CompositeDisposable());
        snackbarHelper = new SnackbarHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void setContentView(int layoutResID) {
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
        getPresenter().unsubcribe();
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
    public P getPresenter() {
        return presenter;
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void showDialogLoading() {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(this)
                    .title(R.string.txt_loading)
                    .show();
        }
    }

    protected void hideDialogLoading() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
    }
}
