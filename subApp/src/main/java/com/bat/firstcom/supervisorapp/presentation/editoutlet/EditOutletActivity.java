package com.bat.firstcom.supervisorapp.presentation.editoutlet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.IntentConstant;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangeOutletDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.Reason;
import com.bat.firstcom.supervisorapp.datalayer.model.VisitorQuantity;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class EditOutletActivity extends BaseActivity<EditOutletPresenter> implements EditOutletView {

    private int REMOVE_OUTLET_PARENT_ID = 0;
    private int REFUSE_PST_PARENT_ID = 5;
    private int CHANGE_OUTLET_DELETE = 1;
    private int CHANGE_OUTLET_TIME = 2;
    private int CHANGE_OUTLET_OWNER_INFO = 3;
    @BindView(R.id.edtOutletName)
    AppCompatEditText edtOutletName;
    @BindView(R.id.edtOutletNumber)
    AppCompatEditText edtOutletNumber;
    @BindView(R.id.btnComplete)
    AppCompatButton btnComplete;
    @BindView(R.id.spinnerCustomerAmount)
    AppCompatSpinner spinnerCustomerAmount;
    @BindView(R.id.spinnerRemoveOutlet)
    AppCompatSpinner spinnerRemoveOutlet;
    @BindView(R.id.spinnerRefusePSTReasons)
    AppCompatSpinner spinnerRefusePSTReasons;
    @BindView(R.id.spinnerFromHour)
    AppCompatSpinner spinnerFromHour;
    @BindView(R.id.spinnerFromMins)
    AppCompatSpinner spinnerFromMins;
    @BindView(R.id.spinnerToHour)
    AppCompatSpinner spinnerToHour;
    @BindView(R.id.spinnerToMins)
    AppCompatSpinner spinnerToMins;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;
    @BindView(R.id.checkboxRemoveOutlet)
    AppCompatCheckBox checkboxRemoveOutlet;
    @BindView(R.id.checkboxChangeTime)
    AppCompatCheckBox checkboxChangeTime;
    private int outletLocationId;
    private String token;
    private int brand;
    @Inject
    SupAppDataRepository dataRepository;
    private Snackbar.Callback snackBarCallback = new Snackbar.Callback() {

        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            setResult(Activity.RESULT_OK);
            finish();
        }

        @Override
        public void onShown(Snackbar snackbar) {

        }
    };

    @OnClick(R.id.parentLayout)
    public void clickParentLayout(RelativeLayout parentLayout) {
        App.getInstance().hideKeyboard(parentLayout);
        edtOutletName.clearFocus();
        edtOutletNumber.clearFocus();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new EditOutletPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_outlet_activity);
        getPresenter().onTakeView(this);
        setTitle(getString(R.string.edit_outlet_screen_title));
        getDataFromSharePref();
        processIntent();
        getPresenter().getChangeOutlet(String.valueOf(outletLocationId), token, brand);
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    private void processIntent() {
        if (getIntent() != null) {
            try {
                outletLocationId = getIntent().getIntExtra(IntentConstant.OUTLET_LOCATION_ID, -1);
            } catch (ClassCastException ex) {
                showToastException(ex.getMessage());
            }
        }
    }

    private void getDataFromSharePref() {
        token = App.getInstance().getStringFromSharePref(SharePref.USER_TOKEN);
        brand = App.getInstance().getIntFromSharePref(SharePref.BRAND);
    }

    @OnClick(R.id.btnComplete)
    public void clickBtnComplete() {
        PostChangeOutletRequest postChangeOutletRequest = new PostChangeOutletRequest();
        postChangeOutletRequest.setOutletLocationId(outletLocationId);
        postChangeOutletRequest.setOwnerName(edtOutletName.getText().toString());
        postChangeOutletRequest.setOwnerPhone(edtOutletNumber.getText().toString());
        postChangeOutletRequest.setVisitorQuantity(spinnerCustomerAmount.getSelectedItemPosition() + 1);
        if (checkboxRemoveOutlet.isChecked()) {
            postChangeOutletRequest.setRequestType(CHANGE_OUTLET_DELETE);
            postChangeOutletRequest.setReasonId(spinnerRemoveOutlet.getSelectedItemPosition() + 1);
        } else if (checkboxChangeTime.isChecked()) {
            postChangeOutletRequest.setRequestType(CHANGE_OUTLET_TIME);
            postChangeOutletRequest.setFromHour(spinnerFromHour.getSelectedItemPosition());
            postChangeOutletRequest.setFromMinute(spinnerFromMins.getSelectedItemPosition());
            postChangeOutletRequest.setToHour(spinnerToHour.getSelectedItemPosition());
            postChangeOutletRequest.setToMinute(spinnerToMins.getSelectedItemPosition());
        } else {
            postChangeOutletRequest.setRequestType(CHANGE_OUTLET_OWNER_INFO);
        }
        getPresenter().postChangeOutlet(token, brand, postChangeOutletRequest);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showLoading() {
        showLoadingDialog(getString(R.string.loading_in_progress));
    }

    @Override
    public void postChangeOutletSuccessFully() {
        snackbarHelper.show(parentLayout, R.string.btn_complete_text, snackBarCallback);
    }

    @Override
    public void updateLayoutFrom(ChangeOutletDatum changeOutletDatum) {
        initListener();
        updateOutletOwnerInfo(changeOutletDatum);
        updateCustomerAmountSpinnerValue(changeOutletDatum);
        updateReasonSpinnerValue(changeOutletDatum);
        updateTimeSpinnerValue(changeOutletDatum);
    }

    private void initListener(){
        checkboxRemoveOutlet.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if(isChecked && checkboxChangeTime.isChecked()){
                        checkboxChangeTime.toggle();
                    }
                }
        );
        checkboxChangeTime.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked && checkboxRemoveOutlet.isChecked()){
                checkboxRemoveOutlet.toggle();
            }
        }));
    }

    private void updateOutletOwnerInfo(ChangeOutletDatum changeOutletDatum) {
        if (changeOutletDatum.getOutlet() != null) {
            edtOutletName.setText(changeOutletDatum.getOutlet().getOwnerName());
            edtOutletNumber.setText(changeOutletDatum.getOutlet().getOwnerPhone());
        }
    }

    private void updateCustomerAmountSpinnerValue(ChangeOutletDatum changeOutletDatum) {
        List<VisitorQuantity> visitorQuantities = changeOutletDatum.getVisitorQuantities();
        List<String> quantityStrings = new ArrayList<>();
        for (VisitorQuantity visitorQuantity : visitorQuantities) {
            quantityStrings.add(visitorQuantity.getQuantityText());
        }
        initSpinnerValues(quantityStrings.toArray(new String[quantityStrings.size()])
                , spinnerCustomerAmount);
        selectCustomerAmountIndex(changeOutletDatum, quantityStrings);
    }

    private void selectCustomerAmountIndex(ChangeOutletDatum changeOutletDatum
            , List<String> quantityStrings) {
        //selected customer amount index = visitorquantity return from api -1
        if (changeOutletDatum.getOutlet().getVistorQuantity() > 0) {
            spinnerCustomerAmount.setSelection(changeOutletDatum.getOutlet().getVistorQuantity() - 1);
        } else if (quantityStrings.size() > 0) {
            spinnerCustomerAmount.setSelection(0);
        }
    }

    private void updateReasonSpinnerValue(ChangeOutletDatum changeOutletDatum){
        List<Reason> reasons = changeOutletDatum.getReasons();
        List<String> removeReasonStrings = new ArrayList<>();
        List<String> refustPSTReasonStrings = new ArrayList<>();
        for (Reason reason : reasons) {
            if (reason.getParentId() == REMOVE_OUTLET_PARENT_ID) {
                removeReasonStrings.add(reason.getReason());
            } else if (reason.getParentId() == REFUSE_PST_PARENT_ID) {
                refustPSTReasonStrings.add(reason.getReason());
            }
        }
        initSpinnerValues(removeReasonStrings.toArray(new String[removeReasonStrings.size()])
                , spinnerRemoveOutlet);
        if (removeReasonStrings.size() > 0) {
            spinnerRemoveOutlet.setSelection(0);
        }
        initSpinnerValues(refustPSTReasonStrings.toArray(new String[refustPSTReasonStrings.size()])
                , spinnerRefusePSTReasons);
        if (refustPSTReasonStrings.size() > 0) {
            spinnerRefusePSTReasons.setSelection(0);
        }
    }

    private void updateTimeSpinnerValue(ChangeOutletDatum changeOutletDatum){
        initSpinnerValuesFrom(R.array.hour_list, spinnerFromHour);
        spinnerFromHour.setSelection(changeOutletDatum.getOutlet().getFromHour() + 1);
        initSpinnerValuesFrom(R.array.min_list, spinnerFromMins);
        spinnerFromMins.setSelection(changeOutletDatum.getOutlet().getFromMinute() + 1);
        initSpinnerValuesFrom(R.array.hour_list, spinnerToHour);
        spinnerToHour.setSelection(changeOutletDatum.getOutlet().getToHour() + 1);
        initSpinnerValuesFrom(R.array.min_list, spinnerToMins);
        spinnerToMins.setSelection(changeOutletDatum.getOutlet().getToMinute() + 1);
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        snackbarHelper.show(parentLayout, R.string.error_occured, snackBarCallback);
    }

    private void initSpinnerValuesFrom(int arrayResourceId, AppCompatSpinner spinner) {
        String[] arrayFromResources = getResources().getStringArray(arrayResourceId);
        initSpinnerValues(arrayFromResources, spinner);
    }


    private void initSpinnerValues(String[] spinnerData, AppCompatSpinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this
                , R.layout.outlet_spinner_row, spinnerData);
        adapter.setDropDownViewResource(R.layout.product_spinner_dropdown_row);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }


}
