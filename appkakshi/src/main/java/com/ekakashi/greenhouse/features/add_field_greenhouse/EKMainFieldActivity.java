package com.ekakashi.greenhouse.features.add_field_greenhouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.ManageNetworkUsage;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.database.model_server_response.SystemNewsCountResponse;
import com.ekakashi.greenhouse.features.login_screen.LoginActivity;
import com.ekakashi.greenhouse.features.notification_setting.MyFirebaseMessagingService;
import com.ekakashi.greenhouse.features.setting.SettingFragment;
import com.ekakashi.greenhouse.features.system_news.SystemNewsActivity;
import com.ekakashi.greenhouse.features.timeline.TimelineFragment;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;
import com.ekakashi.greenhouse.features.view_field.EKMainFieldFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EKMainFieldActivity extends BaseActivity implements EKMainInterface,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private TimelineFragment timelineFragment;
    private SettingFragment mSettingFragment;
    private BottomNavigationView navigation;
    private TextView tvBadgeSetting;
    private TextView tvBadgeTimeline;
    private Integer badgeSetting = 0;
    private Integer badgeTimeline = 0;

    // private FieldsGreenhouseFragment fieldsGreenhouseFragment;
    private EKMainFieldFragment viewAField;
    private int REQUEST_CODE_FILTER = 99;
    private int REQUEST_CODE_POST = 100;
    private int REQUEST_CODE_READ_MORE = 101;
    private final int DONT_HAVE_NOTIFICATION = 0;

    private int navigation_item_timeline = 0;
    private int navigation_item_main = 1;
    private int navigation_item_settings = 2;


    //    public DatabaseHelper mDatabaseHelper;
    public AccountDao mAccountDao;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EKMainFieldActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
        /*
         * MainActivityGallery: if send_device_token_status = true, re-call api send deviceToken to server.
         */
        if (!Prefs.getInstance(getApplicationContext()).getSendDeviceTokenStatus()) {
            if (!Prefs.getInstance(getApplicationContext()).getToken().isEmpty()) {
                APIManager.sendRegistrationToServer(
                        Prefs.getInstance(getApplicationContext()).getDeviceToken(), new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                Prefs.getInstance(getApplicationContext()).saveSendDeviceTokenStatus(true);
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            }
                        });
            }
        }

        setBadge();
        changeTitleNavigation();
    }

    private void changeTitleNavigation() {
        if(navigation!=null)
        {
            Menu menu = navigation.getMenu();
                menu.getItem(navigation_item_timeline).setTitle(getString(R.string.title_timeline));
                menu.getItem(navigation_item_main).setTitle(getString(R.string.title_main));
                menu.getItem(navigation_item_settings).setTitle(getString(R.string.settings));
        }
    }

    IntentFilter intentFilter = new IntentFilter(MyFirebaseMessagingService.TAG);
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setBadge();
        }
    };

    //show number here
    private void setBadge() {
        badgeSetting = Prefs.getInstance(this).getBadgeSetting();
        badgeTimeline = Prefs.getInstance(this).getBadgeTimeline();
        tvBadgeSetting.setVisibility(badgeSetting == 0 ? View.GONE : View.VISIBLE);
        tvBadgeSetting.setText(String.valueOf(badgeSetting));
        tvBadgeTimeline.setVisibility(badgeTimeline == 0 ? View.GONE : View.VISIBLE);
        tvBadgeTimeline.setText(String.valueOf(badgeTimeline));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //You must call this method on a background thread
                Glide.get(EKMainFieldActivity.this).clearDiskCache();
            }
        }).start();
        addControls();
        addEvents();
        getNotificationPendingIntent();
        countSystemNews();
    }

    private void countSystemNews() {
        APIManager.countSystemNews( Prefs.getInstance(this).getUserId(), new Callback<SystemNewsCountResponse>() {
            @Override
            public void onResponse(Call<SystemNewsCountResponse> call, Response<SystemNewsCountResponse> response) {
                if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                    SystemNewsCountResponse systemNew = response.body();
                    if (systemNew != null) {
                        SystemNewsCountResponse.Data data = systemNew.getData();
                        badgeSetting = data.getCount();
                        Prefs.getInstance(EKMainFieldActivity.this).saveBadgeSetting(badgeSetting);
                        setBadge();
                    }
                }
            }

            @Override
            public void onFailure(Call<SystemNewsCountResponse> call, Throwable t) {

            }
        });
    }

    private void getNotificationPendingIntent() {
        Intent intent = getIntent();
        int type;
        if (App.notificationType != null) {
            type = App.notificationType;
        } else {
            type = intent.getIntExtra(Utils.Name.NOTIFICATION_TYPE, DONT_HAVE_NOTIFICATION);
        }

        switch (type) {
            case DONT_HAVE_NOTIFICATION:
                showField();
                break;
            case Utils.Constant.NOTIFICATION_TYPE_NOTIFICATION:
                showTimeline();
                break;
            case Utils.Constant.NOTIFICATION_TYPE_ADVICE:
                showTimeline();
                break;
            case Utils.Constant.NOTIFICATION_TYPE_DIARY:
                showTimeline();
                break;
            case Utils.Constant.NOTIFICATION_TYPE_NEWS:
                showSetting();
                startActivity(new Intent(this, SystemNewsActivity.class));
                break;
            default:
                break;
        }

        App.onDestroyNotificationType();
    }

    private void addControls() {
        mAccountDao = mDatabaseHelper.getAccountDao();
        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_main);
        tvBadgeSetting = findViewById(R.id.tvBadgeSetting);
        tvBadgeTimeline = findViewById(R.id.tvBadgeTimeline);
    }

    private void addEvents() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void showField() {
        if (viewAField == null)
            viewAField = new EKMainFieldFragment();
        replaceFragment(viewAField);
    }

    private void showTimeline() {
        if (timelineFragment == null) {
            timelineFragment = new TimelineFragment();
        }
        navigation.setSelectedItemId(R.id.navigation_timeline);
        replaceFragment(timelineFragment);
        Prefs.getInstance(this).saveBadgeTimeline(0);
        setBadge();

    }

    private void showSetting() {
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        navigation.setSelectedItemId(R.id.navigation_setting);
        replaceFragment(mSettingFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_main:
                if (viewAField == null) {
                    viewAField = new EKMainFieldFragment();
                }
                replaceFragment(viewAField);
                break;
            case R.id.navigation_timeline:
                if (timelineFragment == null) {
                    timelineFragment = new TimelineFragment();
                }
                replaceFragment(timelineFragment);
                Prefs.getInstance(this).saveBadgeTimeline(0);
                setBadge();
                break;
            case R.id.navigation_setting:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                }
                if(!isNetworkOffline())
                replaceFragment(mSettingFragment);
                break;

            default:
                break;
        }
        return true;
    }

    public void logout() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.account_logout)
                .content(R.string.account_logout_confirm)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Prefs.getInstance(getApplicationContext()).saveToken("");
                        mDatabaseHelper.getAccountDao().deleteAll();
                        Intent intent = new Intent(EKMainFieldActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    private void replaceFragment(Fragment fm) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fm);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FILTER) {
                FilterModel filterModel = data.getParcelableExtra(Utils.Constant.TIMELINE_FILTER_MODEL);
                if (timelineFragment != null) {
                    timelineFragment.applyFilter(filterModel);
                }
            }
            if (requestCode == REQUEST_CODE_POST) {
                if (timelineFragment != null) {
                    timelineFragment.reloadTimeline();
                }
            }
            if (requestCode == REQUEST_CODE_READ_MORE) {
                if (timelineFragment != null) {
                    timelineFragment.reloadTimeline();
                }
            }
        }
    }

    @Override
    public void backToSettingFragment() {
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        fragmentTransaction.replace(R.id.frame_layout, mSettingFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        if (Utils.isFinishActivity(this)) {
            ManageNetworkUsage.unRegisterReceiver(getApplicationContext());
        }
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void showBottop() {
        Utils.setStatusBarGradient(this);
        navigation.setVisibility(View.VISIBLE);
    }

    public void hideBottom() {
        navigation.setVisibility(View.GONE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
        }*/
    }
}
