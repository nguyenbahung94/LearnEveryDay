package com.ekakashi.greenhouse.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.database.DatabaseHelper;

public class BaseActivity extends AppCompatActivity {

    private MaterialDialog mLoadingDialog;

    protected Toolbar mToolbar;

    protected DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mDatabaseHelper = App.getDatabaseHelper(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        Utils.setStatusBarGradient(this);
        super.setContentView(layoutResID);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        LocaleUtils.updateConfig(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
    }

    /**
     * Show a loading dialog with message
     *
     * @param message message of popup
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = Utils.createProgressBar(this, message);
        }

        mLoadingDialog.show();
    }

    /**
     * Hide loading dialog
     */
    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * Show a simple dialog with message
     *
     * @param message message of popup
     */
    public void showSimpleMessage(String message) {
        new MaterialDialog.Builder(this)
                .content(message)
                .cancelable(false)
                .positiveText(R.string.btn_dialog_ok).positiveColor(Utils.getColor(this, R.color.bg_green_btn))
                .show();
    }

    public boolean isNetworkOffline() {
        if (ManageNetworkUsage.isNetworkOnline(getApplicationContext())) {
            return false;
        }
        showSimpleMessage(getString(R.string.network_offine));
        return true;
    }

    public void customConfirmDialog(@NonNull String title,@NonNull String messageConfirm) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(messageConfirm)
                .cancelable(false)
                .positiveText(R.string.btn_dialog_ok).positiveColor(Utils.getColor(this, R.color.bg_green_btn))
                .show();
    }

    public Boolean isShowingDialog() {
        if (mLoadingDialog.isCancelled())
            return true;
        return false;
    }


}
