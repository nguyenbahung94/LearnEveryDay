package com.bat.firstcom.supervisorapp.presentation.outletlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.common.IntentConstant;
import com.bat.firstcom.supervisorapp.common.Permission;
import com.bat.firstcom.supervisorapp.common.QuestionType;
import com.bat.firstcom.supervisorapp.common.RequestCode;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Strings;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.OutletDatum;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.DialogHelper;
import com.bat.firstcom.supervisorapp.presentation.editoutlet.EditOutletActivity;
import com.bat.firstcom.supervisorapp.presentation.marking.MarkingActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tung Phan on 30-Jun-17.
 * Using this activity to display PST list and Outlet List. There're two flow in the app:
 * FlowType.COACHING & FlowType.CHECKING
 */

public class OutletListActitivy extends BaseActivity<OutletListPresenter>
        implements OutletListView, OutletListAdapter.CommonListAdapterListener
        , DialogHelper.YesNoDialogListener, DialogHelper.OkDialogListener {

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @BindView(R.id.commonListRView)
    RecyclerView commonListRView;
    @BindView(R.id.colume1Title)
    AppCompatTextView colume1Title;
    @BindView(R.id.colume2Title)
    AppCompatTextView colume2Title;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;
    @BindView(R.id.bottomParentLayout)
    LinearLayout bottomParentLayout;
    private FlowType flowType;
    private OutletListAdapter OutletListAdapter;
    private boolean isEditOutlet;
    private Uri imageUri;
    private String token;
    private int brand;
    private int selectedPstId;
    private OutletDatum selectedOutletDatum;
    @Inject
    SupAppDataRepository dataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new OutletListPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_activity);
        getPresenter().onTakeView(this);
        showActionBar();
        processIntent();
        getDataFromSharePref();
        initViews();
        dialogHelper.setYesNoDialogListener(this);
        dialogHelper.setOkDialogListener(this);
        //call api to get coaching outlet
        getPresenter().getOutlets(selectedPstId, token, brand);
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    private void processIntent() {
        if (getIntent() != null) {
            try {
                if (getIntent().getSerializableExtra(IntentConstant.FLOW_TYPE) != null) {
                    flowType = (FlowType) getIntent().getSerializableExtra(IntentConstant.FLOW_TYPE);
                }
                selectedPstId = getIntent().getIntExtra(IntentConstant.PST_ID, -1);
                isEditOutlet = getIntent().getBooleanExtra(IntentConstant.EDIT_OUTLET, false);
            } catch (ClassCastException ex) {
                showToastException(ex.getMessage());
            }
        }
    }

    private void getDataFromSharePref() {
        token = App.getInstance().getStringFromSharePref(SharePref.USER_TOKEN);
        brand = App.getInstance().getIntFromSharePref(SharePref.BRAND);
    }

    private void initViews() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        commonListRView.setLayoutManager(mLayoutManager);
        setTitle(getString(R.string.common_list_outlet_title));
        bottomParentLayout.setVisibility(View.GONE);
        colume1Title.setText(R.string.title_day_sort);
        colume2Title.setText(R.string.title_week_sort);
    }

    @OnClick(R.id.colume1Title)
    public void clickViewByDay() {
        getPresenter().getOutlets(selectedPstId, token, brand);
    }

    @OnClick(R.id.colume2Title)
    public void clickViewByWeek() {
        getPresenter().getOutletsByWeek(selectedPstId, token, brand);
    }

    private void initListAdapter(List<OutletDatum> outletData) {
        //OutletListAdapter constructor include: context, CommonListAdapterListener
        OutletListAdapter = new OutletListAdapter(this, this);
        OutletListAdapter.initAdapter(flowType, outletData);
        commonListRView.setItemAnimator(new DefaultItemAnimator());
        commonListRView.setAdapter(OutletListAdapter);
        if (OutletListAdapter != null && flowType == FlowType.CHECKING) {
            OutletListAdapter.setEditOutlet(isEditOutlet);
        }
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showLoading() {
        showLoadingDialog(getString(R.string.loading_get_data));
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        snackbarHelper.show(parentLayout, Utils.getErrorMessageFrom(errorType)
                , new Snackbar.Callback() {
                    @Override
                    public void onShown(Snackbar sb) {
                        super.onShown(sb);
                    }

                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        finish();
                    }
                });
    }

    @Override
    public void updateOutletListAdapter(List<OutletDatum> outletData) {
        if (outletData.size() > 0) {
            initListAdapter(outletData);
        } else {
            showErrorDialog(ErrorType.ERROR_EMPTY_OUTLET_LIST);
        }
    }

    @Override
    public void startTakingPictureActivity(OutletDatum outletDatum, FlowType flowType) {
        selectedOutletDatum = outletDatum;
        String message = getString(R.string.chose_outlet_prefix)
                + "("
                + outletDatum.getStartTime()
                + "-"
                + outletDatum.getEndTime()
                + ")"
                + outletDatum.getOutletName()
                + getString(R.string.chose_outlet_suffix);
        dialogHelper.displayYesNoDialog(message, R.string.yes, R.string.back);
    }

    @Override
    public void startEditOutletActivity(OutletDatum outletDatum) {
        Intent intent = new Intent(this, EditOutletActivity.class);
        intent.putExtra(IntentConstant.OUTLET_LOCATION_ID, outletDatum.getOutletLocationId());
        startActivityForResult(intent, RequestCode.EDIT_OUTLET);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Permission.REQUEST_READ_EXTERNAL_AND_FINE_LOCATION_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Snackbar.make(parentLayout
                            , R.string.turn_on_permissions
                            , Snackbar.LENGTH_LONG)
                            .setAction(R.string.yes_text, v1 -> App.getInstance().gotoSetting())
                            .show();
                }
            }
        }
    }

    //start camera for taking picture
    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, RequestCode.REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.START_MARKING:
                    this.finish();
                    break;
                case RequestCode.REQUEST_IMAGE_CAPTURE:
                    if (data != null && data.getData()!=null) {
                        imageUri = data.getData();
                    }else{
                        try {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            imageUri = Uri.fromFile(new File(saveImageTempFrom(bitmap)));
                        }catch (ClassCastException ex){
                            ex.printStackTrace();
                        }
                    }
                    if (flowType == FlowType.CHECKING) {
                        startCheckingMarkingActivity();
                    } else if (flowType == FlowType.COACHING) {
                        startCoachingMarkingActivity();
                    }
                    break;
                case RequestCode.TAKING_PICTURE:
                    this.finish();
                    break;
                case RequestCode.EDIT_OUTLET:
                    this.finish();
                    break;
            }
        }
    }

    public static String saveImageTempFrom(Bitmap bmp) {
        if (bmp != null) {
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SUPAPP";
            try {
                File dir = new File(filePath);
                if (!dir.exists())
                    dir.mkdirs();
                File file = new File(dir, Utils.generateRandomString(Utils.Mode.ALPHA) + ".png");
                FileOutputStream fOut = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                fOut.flush();
                fOut.close();
                return file.getAbsolutePath();
            } catch (IOException e) {
                Log.e("OutletListActivity",e.toString());
            }
        }
        return Strings.EMPTY;
    }

    private void startCheckingMarkingActivity() {
        Intent intent = new Intent(this, MarkingActivity.class);
        intent.putExtra(IntentConstant.QUESTION_TYPE, QuestionType.CHECKING_EDITABLE);
        intent.putExtra(IntentConstant.BITMAP_IMAGE_URI, imageUri.toString());
        intent.putExtra(IntentConstant.PST_ID, selectedPstId);
        intent.putExtra(IntentConstant.OUTLET_LOCATION_ID, selectedOutletDatum.getOutletLocationId());
        intent.putExtra(IntentConstant.ROUTE_ID, selectedOutletDatum.getRouteId());
        startActivityForResult(intent, RequestCode.START_MARKING);
    }


    private void startCoachingMarkingActivity() {
        Intent intent = new Intent(this, MarkingActivity.class);
        intent.putExtra(IntentConstant.QUESTION_TYPE, QuestionType.COACHING_EDITABLE);
        intent.putExtra(IntentConstant.BITMAP_IMAGE_URI, imageUri.toString());
        intent.putExtra(IntentConstant.PST_ID, selectedPstId);
        intent.putExtra(IntentConstant.OUTLET_LOCATION_ID, selectedOutletDatum.getOutletLocationId());
        intent.putExtra(IntentConstant.ROUTE_ID, selectedOutletDatum.getRouteId());
        startActivityForResult(intent, RequestCode.START_MARKING);
    }

    @Override
    public void onYesButtonClick() {
        // check read external permission and fine location
        // if permission granted => start taking picture activity
        if (Utils.hasRequiredPermissions(this, PERMISSIONS)) {
            startCamera();
        } else {
            if (Utils.shouldShowRequestPermission(this, PERMISSIONS)) {
                Snackbar.make(parentLayout
                        , R.string.turn_on_permissions
                        , Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes_text, v1 -> App.getInstance().gotoSetting())
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS,
                        Permission.REQUEST_READ_EXTERNAL_AND_FINE_LOCATION_PERMISSION);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onOkButtonClick() {

    }
}
