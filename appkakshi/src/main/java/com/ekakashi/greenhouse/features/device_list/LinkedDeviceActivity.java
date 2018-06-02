package com.ekakashi.greenhouse.features.device_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.DatabaseHelper;
import com.ekakashi.greenhouse.database.dao.DeviceObject;
import com.ekakashi.greenhouse.database.dao.DeviceObjectDao;
import com.ekakashi.greenhouse.features.devices_detail.DevicesDetailsActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;


public class LinkedDeviceActivity extends BaseActivity implements View.OnClickListener, LinkedDeviceInterface.View, LinkedDeviceInterface.OnItemClick {
    private List<DeviceObject> deviceObjects = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterDevice adapterDeviceList;
    private boolean statusEdit = false;
    private DatabaseHelper mDatabaseHelper;
    private DeviceObjectDao mDeviceObjectDao;
    private LinearLayout llRecycleView;
    private boolean showItemDelete = false;
    private int ekFieldID;
    private String ekFieldName;

    private LinkedDeviceInterface.Presenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LinkedDeviceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ekFieldID = getIntent().getIntExtra(Utils.Constant.EK_FIELDS_ID, 0);
        ekFieldName = getIntent().getStringExtra(Utils.Constant.EK_FIELDS_NAME);
        setupToolBar();
        initView();
        event();
    }

    private void setupToolBar() {
        ImageView back = mToolbar.findViewById(R.id.imgToolbarBack);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        TextView tvTitle = mToolbar.findViewById(R.id.tvToolbarCenter);
        tvTitle.setText(R.string.txt_linked_devices);
        TextView tvTitleBottom = mToolbar.findViewById(R.id.tvToolbarCenterBottom);
        tvTitleBottom.setText(ekFieldName);
        TextView tv = mToolbar.findViewById(R.id.tvToolbarRight);
        tv.setText(R.string.txt_edit);
        tv.setVisibility(View.VISIBLE);
        tv.setOnClickListener(this);
    }

    private void initView() {
        presenter = new LinkedDevicePresenter(this);
        deviceObjects.clear();
        mDatabaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        mDatabaseHelper.getWritableDatabase();
        mDeviceObjectDao = mDatabaseHelper.getDeviceObject();
        deviceObjects.addAll(getListDeviceDatabase());
        //


        //
        recyclerView = findViewById(R.id.mRecycleView);
        llRecycleView = findViewById(R.id.llRecycleView);
        if (deviceObjects != null) {
            if (deviceObjects.size() == 0) {
                findViewById(R.id.llAdDevice).setVisibility(View.VISIBLE);
                llRecycleView.setVisibility(View.GONE);
            } else {
                findViewById(R.id.llAdDevice).setVisibility(View.GONE);
                llRecycleView.setVisibility(View.VISIBLE);
                adapterDeviceList = new AdapterDevice(deviceObjects, LinkedDeviceActivity.this);
                adapterDeviceList.setItemCLickListener(this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapterDeviceList);
            }
        }


    }

    private List<DeviceObject> getListDeviceDatabase() {

        return mDeviceObjectDao.getAll();
    }

    private void event() {
        findViewById(R.id.btnAddDevice).setOnClickListener(this);
//        findViewById(R.id.btnAddDevice2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  startActivity(new Intent(LinkedDeviceActivity.this, EKRegisterDeviceActivity.class));
//                Random rn = new Random();
//                //startActivity(new Intent(LinkedDeviceActivity.this, EKRegisterDeviceActivity.class));
////                deviceObjects.add(0, new DeviceObject(rn.nextInt(), rn.nextInt() + "", "123", "123", "123"));
//                adapterDeviceList.notifyDataSetChanged();
//            }
//        });

        presenter.getList(111);
//        Presenter.getList(Prefs.getInstance(this).getToken(), ekFieldID);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(), "position=" + position, Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.lin_add_more:
            case R.id.btnAddDevice:
               // startActivity(new Intent(this, EKRegisterDeviceActivity.class));
                return;
            case R.id.rel_content:
                startActivity(new Intent(LinkedDeviceActivity.this, DevicesDetailsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClickListener(View view, int position) {

    }

    @Override
    public void clickDeleteOk(int position) {
        deviceObjects.remove(position);
        adapterDeviceList.notifyDataSetChanged();
        Toast.makeText(this, "delete editSuccess" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void clickDeleteCancel(int position) {
        Toast.makeText(this, "cancel" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (adapterDeviceList != null)
            adapterDeviceList.clearData();
        App.releaseDatabaseHelper();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgToolbarBack:
                onBackPressed();
                break;
            case R.id.tvToolbarRight:
                if (!showItemDelete) {
                    showItemDelete = true;
                    adapterDeviceList.setShowItemDeleteTrue();
                } else {
                    adapterDeviceList.setShowItemDeleteFalse();
                    showItemDelete = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetListSuccess(List<DeviceObject> list) {
        if (list.isEmpty()) {
            return;
        }
        deviceObjects = list;
        adapterDeviceList.setList(deviceObjects);
        adapterDeviceList.notifyDataSetChanged();
        findViewById(R.id.llAdDevice).setVisibility(View.GONE);
        llRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetListError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
