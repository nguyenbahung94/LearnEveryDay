package com.ekakashi.greenhouse.features.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.features.account_setting.EKAccountSettingActivity;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.language_setting.LanguageActivity;
import com.ekakashi.greenhouse.features.login_screen.LoginActivity;
import com.ekakashi.greenhouse.features.notification_setting.NotificationSettingActivity;
import com.ekakashi.greenhouse.features.system_news.SystemNewsActivity;


public class SettingFragment extends Fragment implements SettingInterface.View, View.OnClickListener {
    private RecyclerView rvSetting;
    private ImageView imgUser;
    private Toolbar mToolbar;
    private TextView tv_system_new_notice;
    private View mView;
    private Account mUser;
    private SettingInterface.Presenter mPresenter;
    private EKMainFieldActivity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SettingPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (EKMainFieldActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_setting, container, false);
        imgUser = mView.findViewById(R.id.img_user);
//        relRecipe = mView.findViewById(R.id.rel_recipe);
//        relRecipe.setOnClickListener(this);
        mView.findViewById(R.id.rel_account_setting).setOnClickListener(this);
        mView.findViewById(R.id.rel_system_news).setOnClickListener(this);
        mView.findViewById(R.id.relLanguage).setOnClickListener(this);
        mView.findViewById(R.id.rel_notification_settings).setOnClickListener(this);
        mView.findViewById(R.id.btn_log_out).setOnClickListener(this);
        mView.findViewById(R.id.rel_facebook).setOnClickListener(this);
        mView.findViewById(R.id.rel_poli).setOnClickListener(this);
        mView.findViewById(R.id.rel_term_of_service).setOnClickListener(this);
        mView.findViewById(R.id.rel_license).setOnClickListener(this);
        mView.findViewById(R.id.rel_instructions).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser = getAccountDao().getAccount();
        addToolbar();
        addControls();
        int badge_system_news = Prefs.getInstance(getActivity()).getBadgeSetting();
        tv_system_new_notice = mView.findViewById(R.id.tv_system_new_notice);
        tv_system_new_notice.setVisibility(badge_system_news == 0 ? View.GONE : View.VISIBLE);
        tv_system_new_notice.setText(String.valueOf(badge_system_news));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_account_setting:
                startActivity(new Intent(getContext(), EKAccountSettingActivity.class));
//                transitionFragment(new EKAccountSettingActivity());
                break;
            case R.id.rel_system_news:
                startActivity(new Intent(getContext(), SystemNewsActivity.class));
                break;
            case R.id.relLanguage:
                startActivity(new Intent(getContext(), LanguageActivity.class));
                break;
            case R.id.rel_notification_settings:
                startActivity(new Intent(getContext(), NotificationSettingActivity.class));
                break;
            case R.id.rel_facebook:
                Intent intent_fb = new Intent(getContext(), SettingWebviewActivity.class);
                intent_fb.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.SETTING_INTENT_FACEBOOK);
                startActivity(intent_fb);
                break;
            case R.id.rel_poli:
                Intent intent_poli = new Intent(getContext(), SettingWebviewActivity.class);
                intent_poli.putExtra("setting_intent", 2);
                intent_poli.putExtra("isLang", true);
                intent_poli.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.SETTING_INTENT_POLICY);
                startActivity(intent_poli);
                break;
            case R.id.rel_term_of_service:
                Intent intent_term = new Intent(getContext(), SettingWebviewActivity.class);
                intent_term.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.SETTING_INTENT_TERM);
                startActivity(intent_term);
                break;
            case R.id.btn_log_out:
                showConfirmLogOut();
                break;
            case R.id.rel_license:
                Intent intent_license = new Intent(getContext(), SettingWebviewActivity.class);
                intent_license.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.SETTING_INTENT_LICENSE);
                startActivity(intent_license);
                break;
            case R.id.rel_instructions:
                Intent intent_instructions = new Intent(getContext(), SettingWebviewActivity.class);
                intent_instructions.putExtra(Utils.Constant.SETTING_INTENT, Utils.Constant.SETTING_INTENT_INTRUCTIONS);
                startActivity(intent_instructions);
                break;

            default:
                break;
        }
    }

    private void showConfirmLogOut() {
        if (getActivity() == null) return;

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.account_logout)
                .content(R.string.account_logout_confirm)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mActivity.showLoadingDialog(getString(R.string.message_please_wait));
                        mPresenter.logout();
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    private void logOut() {
        if (getActivity() == null) return;

        Prefs.getInstance(App.getsInstance()).saveToken("");
        Prefs.getInstance(App.getsInstance()).saveUserId(-1);
        Prefs.getInstance(App.getsInstance()).saveLocale("");
        Prefs.getInstance(App.getsInstance()).saveUUID("");
        Prefs.getInstance(App.getsInstance()).saveDeviceToken("");
        App.getDatabaseHelper(getActivity()).getAccountDao().deleteAll();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    private void addToolbar() {
        mToolbar = mView.findViewById(R.id.toolbar_main);
        TextView title = mToolbar.findViewById(R.id.tvToolbarCenter);
        title.setText(R.string.settings);
    }


    private void addControls() {
        if (this.mUser == null) {
            return;
        }
        TextView tvNickName = mView.findViewById(R.id.tv_user_name);
        TextView tv_account_setting = mView.findViewById(R.id.tv_account_setting);
        TextView tv_app_setting = mView.findViewById(R.id.tv_title_app_settings);
        TextView tv_system_new = mView.findViewById(R.id.tv_system_new);
        TextView tv_setting_type = mView.findViewById(R.id.tv_setting_type);
        TextView tv_system_notifications = mView.findViewById(R.id.tv_system_notifications);
        TextView tv_system_language = mView.findViewById(R.id.tv_system_language);
        TextView tv_system_instructions = mView.findViewById(R.id.tv_system_instructions);
        TextView tv_system_facebook = mView.findViewById(R.id.tv_system_facebook);
        TextView tv_system_term = mView.findViewById(R.id.tv_system_term);
        TextView tv_system_privacy = mView.findViewById(R.id.tv_system_privacy);
        TextView btn_log_out = mView.findViewById(R.id.btn_log_out);

        tv_account_setting.setText(getString(R.string.settings_ui_account));
        tv_system_new.setText(getString(R.string.system_news));
        tv_app_setting.setText(R.string.settings_ui_app_setting);
        tv_setting_type.setText(getString(R.string.settings_ui_setting_type));
        tv_system_notifications.setText(getString(R.string.settings_ui_notification));
        tv_system_language.setText(getString(R.string.settings_ui_language));
        tv_system_instructions.setText(getString(R.string.settings_ui_instructions));
        tv_system_facebook.setText(getString(R.string.settings_ui_facebook));
        tv_system_term.setText(getString(R.string.settings_ui_term));
        tv_system_privacy.setText(getString(R.string.settings_ui_privacy));
        btn_log_out.setText(getString(R.string.settings_ui_logout));

        tvNickName.setText((mUser.nickName != null && !mUser.nickName.trim().isEmpty()) ? mUser.nickName : mUser.userName);
        Glide.with(this).load(mUser.image).error(R.drawable.ic_user_default).into(imgUser);
    }

    public void transitionFragment(Fragment fragment) {
        if (getActivity() == null) {
            return;
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public AccountDao getAccountDao() {
        if (getActivity() == null) {
            return null;
        }
        return ((EKMainFieldActivity) getActivity()).mAccountDao;
    }

    @Override
    public void onLogoutSuccess() {
        mActivity.hideLoadingDialog();
        logOut();
    }

    @Override
    public void onLogoutFailed(String errorMessage) {
        mActivity.hideLoadingDialog();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
