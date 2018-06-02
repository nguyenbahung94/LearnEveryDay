package com.bat.firstcom.supervisorapp.presentation.pstlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.common.IntentConstant;
import com.bat.firstcom.supervisorapp.common.QuestionType;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.PSTDatum;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.DialogHelper;
import com.bat.firstcom.supervisorapp.presentation.login.LoginActivity;
import com.bat.firstcom.supervisorapp.presentation.marking.MarkingActivity;
import com.bat.firstcom.supervisorapp.presentation.outletlist.OutletListActitivy;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tung Phan on 30-Jun-17.
 * Using this activity to display PST list and Outlet List.
 * There're two flow in the app:
 * FlowType.COACHING & FlowType.CHECKING
 */

public class PSTListActitivy extends BaseActivity<PSTListPresenter>
        implements PSTListView, PSTListAdapter.CommonListAdapterListener
        , DialogHelper.ActionPickerListener, DialogHelper.YesNoDialogListener {

    @BindView(R.id.commonListRView)
    RecyclerView commonListRView;
    @BindView(R.id.colume1Title)
    AppCompatTextView colume1Title;
    @BindView(R.id.colume2Title)
    AppCompatTextView colume2Title;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;
    @BindView(R.id.tvLogout)
    AppCompatTextView tvLogout;
    @BindView(R.id.tvMainScreen)
    AppCompatTextView tvMainScreen;
    @BindView(R.id.bottomParentLayout)
    LinearLayout bottomParentLayout;
    private FlowType flowType;
    private PSTListAdapter PSTListAdapter;
    private String token;
    private int brand;
    private String userName;
    @Inject
    SupAppDataRepository dataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new PSTListPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_activity);
        getPresenter().onTakeView(this);
        showActionBar();
        processIntent();
        getDataFromSharePref();
        dialogHelper.setActionPickerListener(this);
        dialogHelper.setYesNoDialogListener(this);
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    private void backToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void processIntent() {
        if (getIntent() != null) {
            try {
                if (getIntent().getSerializableExtra(IntentConstant.FLOW_TYPE) != null) {
                    flowType = (FlowType) getIntent().getSerializableExtra(IntentConstant.FLOW_TYPE);
                }
            } catch (ClassCastException ex) {
                showToastException(ex.getMessage());
            }
        }
    }

    private void getDataFromSharePref() {
        token = App.getInstance().getStringFromSharePref(SharePref.USER_TOKEN);
        brand = App.getInstance().getIntFromSharePref(SharePref.BRAND);
        userName = App.getInstance().getStringFromSharePref(SharePref.USER_NAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        commonListRView.setLayoutManager(mLayoutManager);
        setTitle(getString(R.string.common_list_pst_title) + userName);
        bottomParentLayout.setVisibility(View.VISIBLE);
        colume1Title.setText(R.string.title_BA);
        //in the pst list screen, base on flowtype, we update ui and also api call.
        if (flowType != null && flowType == FlowType.CHECKING) {
            //update colume2Title text
            colume2Title.setText(R.string.title_scoring_times);
            //call api to get checking pst list
            getPresenter().getCheckingPSTList(token, brand);
        } else {
            colume2Title.setText(R.string.title_score);
            //call api to get coaching pst list
            getPresenter().getCoachingPSTList(token, brand);
        }

    }

    @OnClick(R.id.tvLogout)
    public void clickLogout() {
        dialogHelper.displayYesNoDialog(getString(R.string.logout_description)
                , R.string.yes
                , R.string.no);
    }

    @OnClick(R.id.tvMainScreen)
    public void clickMainScreen() {
        finish();
    }

    private void initListAdapter(List<PSTDatum> pstData) {
        PSTListAdapter = new PSTListAdapter(this, this);
        PSTListAdapter.initAdapter(flowType, pstData);
        commonListRView.setItemAnimator(new DefaultItemAnimator());
        commonListRView.setAdapter(PSTListAdapter);
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
        snackbarHelper.show(parentLayout, Utils.getErrorMessageFrom(errorType));

    }

    @Override
    public void updateCheckingPSTListAdapter(List<PSTDatum> pstData) {
        initListAdapter(pstData);
    }

    @Override
    public void updateCoachingPSTListAdapter(List<PSTDatum> pstData) {
        initListAdapter(pstData);
    }

    @Override
    public void showPickedOptionDialog(PSTDatum pstDatum) {
        dialogHelper.displayActionPickerDialog(pstDatum, flowType);
    }

    private void processAction(int actionPosition, PSTDatum pstDatum) {
        if (flowType != null && flowType == FlowType.CHECKING) {
            processActionInCheckingFlow(actionPosition, pstDatum.getId());
        } else {
            processActionInCoachingFlow(actionPosition, pstDatum.getId());
        }
    }


    private void processActionInCoachingFlow(int actionPosition, int pstId) {
        switch (actionPosition) {
            case 1://marking
                startOutletActivity(pstId);
                break;
            case 2://view report
                startMarkingActivity(QuestionType.COACHING_UNEDITABLE, pstId);
                break;
            case 3://back
                dialogHelper.hideDialog();
                break;
            default:
                break;
        }
    }

    private void processActionInCheckingFlow(int actionPosition, int pstId) {
        switch (actionPosition) {
            case 1://marking
                startOutletActivity(pstId);
                break;
            case 2://view report
                startMarkingActivity(QuestionType.CHECKING_UNEDITABLE, pstId);
                break;
            case 3://edit outlet
                startOutletActivityToEditOutlet(pstId);
            case 4://back
                dialogHelper.hideDialog();
                break;
            default:
                break;
        }
    }

    private void startOutletActivity(int pstId) {
        Intent intent = new Intent(this, OutletListActitivy.class);
        intent.putExtra(IntentConstant.FLOW_TYPE, flowType);
        intent.putExtra(IntentConstant.EDIT_OUTLET, false);
        intent.putExtra(IntentConstant.PST_ID, pstId);
        startActivity(intent);
    }

    private void startOutletActivityToEditOutlet(int pstId) {
        Intent intent = new Intent(this, OutletListActitivy.class);
        intent.putExtra(IntentConstant.FLOW_TYPE, flowType);
        intent.putExtra(IntentConstant.EDIT_OUTLET, true);
        intent.putExtra(IntentConstant.PST_ID, pstId);
        startActivity(intent);
    }

    public void startMarkingActivity(QuestionType questionType, int pstId) {
        Intent intent = new Intent(this, MarkingActivity.class);
        intent.putExtra(IntentConstant.QUESTION_TYPE, questionType);
        intent.putExtra(IntentConstant.PST_ID, pstId);
        startActivity(intent);
    }

    @Override
    public void onActionPickerItemClick(int position, PSTDatum pstDatum) {
        if (position != 0) {
            processAction(position, pstDatum);
        }
    }

    @Override
    public void onYesButtonClick() {
        backToLoginScreen();
        clearUserDataInSharePref();
    }
}
