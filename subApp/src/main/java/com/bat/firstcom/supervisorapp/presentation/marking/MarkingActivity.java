package com.bat.firstcom.supervisorapp.presentation.marking;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.ImageEncoding;
import com.bat.firstcom.supervisorapp.common.IntentConstant;
import com.bat.firstcom.supervisorapp.common.LocationConstant;
import com.bat.firstcom.supervisorapp.common.QuestionType;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.Answer;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.ReportDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.QuestionDatum;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.DialogHelper;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.QuestionAnswerListFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class MarkingActivity extends BaseActivity<MarkingPresenter> implements MarkingView
        , QuestionAnswerListFragment.QAListFragmentListener
        , LocationListener, DialogHelper.OkDialogListener {
    @BindView(R.id.btnBack)
    AppCompatButton btnBack;
    @BindView(R.id.btnNext)
    AppCompatButton btnNext;
    @BindView(R.id.btnComplete)
    AppCompatButton btnComplete;
    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;
    @Inject
    SupAppDataRepository dataRepository;
    //
    private int currentGroupId;
    private int nextGroupId;
    private int maxGroupId;
    private int minGroupId;
    //
    private QuestionType questionType;
    private QuestionAnswerListFragment currentQandAFragment;
    private String token;
    private int brand;
    private Bundle savedInstanceState;
    private List<QuestionDatum> questionData = new ArrayList<>();
    private List<ReportDatum> reportData = new ArrayList<>();
    //This object content the data to send test result to server.
    //Take a look inside to get better understanding.
    private CoachingRequest coachingRequest;
    private CheckingRequest checkingRequest;
    private int outletLocationId;
    private int routeId;
    private int pstId;
    //this flag temporary using to separate between set/get "before" location and "after" location
    private boolean isBeforeLocation;
    private Uri imageUri;
    private String encodedImage;
    private LocationManager locationManager;
    private Location currentLocation;
    private Snackbar.Callback snackBarCallback = new Snackbar.Callback() {

        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            finishActivity();
        }

        @Override
        public void onShown(Snackbar snackbar) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new MarkingPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marking_activity);
        getPresenter().onTakeView(this);
        this.savedInstanceState = savedInstanceState;
        getDataFromSharePref();
        processIntent();
        initViewsVisibility();
        updateViewStatus();
        if (questionType == QuestionType.COACHING_EDITABLE) {
            showConfirmCurrentLocation();
            initCoachingRequest();
            getPresenter().getCoachingQuestions(token, brand);
        } else if (questionType == QuestionType.COACHING_UNEDITABLE) {
            getPresenter().getCoachingReport(String.valueOf(pstId), token, brand);
        } else if (questionType == QuestionType.CHECKING_EDITABLE) {
            showConfirmCurrentLocation();
            initCheckingRequest();
            getPresenter().getCheckingQuestions(token, brand);
        } else if(questionType == QuestionType.CHECKING_UNEDITABLE){
            getPresenter().getCheckingReport(String.valueOf(pstId), token, brand);
        }
        //init location manager to get current location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        dialogHelper.setOkDialogListener(this);
    }

    private void getDataFromSharePref() {
        token = App.getInstance().getStringFromSharePref(SharePref.USER_TOKEN);
        brand = App.getInstance().getIntFromSharePref(SharePref.BRAND);
    }


    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    @OnClick(R.id.parentLayout)
    public void clickParentLayout(View view) {
        App.getInstance().hideKeyboard(view);
    }

    private void processIntent() {
        if (getIntent() != null) {
            try {
                if (getIntent().getSerializableExtra(IntentConstant.QUESTION_TYPE) != null) {
                    questionType = (QuestionType) getIntent()
                            .getSerializableExtra(IntentConstant.QUESTION_TYPE);
                }
                routeId = getIntent().getIntExtra(IntentConstant.ROUTE_ID, -1);
                outletLocationId = getIntent().getIntExtra(IntentConstant.OUTLET_LOCATION_ID, -1);
                pstId = getIntent().getIntExtra(IntentConstant.PST_ID, -1);
                if (getIntent().getStringExtra(IntentConstant.BITMAP_IMAGE_URI) != null) {
                    imageUri = Uri.parse(getIntent().getStringExtra(IntentConstant.BITMAP_IMAGE_URI));
                }
            } catch (ClassCastException ex) {
                showToastException(ex.getMessage());
            }
        }
    }

    @OnClick(R.id.btnBack)
    public void clickBtnBack() {
        if (currentGroupId != minGroupId) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                revertGroupId();
                updateViewStatus();
                //set current fragment is the QAndA fragment which is visible on the view
                Fragment fragment = getSupportFragmentManager()
                        .findFragmentByTag(QuestionAnswerListFragment.TAG);
                if (fragment instanceof QuestionAnswerListFragment) {
                    currentQandAFragment = (QuestionAnswerListFragment) fragment;
                    currentQandAFragment.emptyQuestionAnswers();
                }
            }
        }
    }

    @OnClick(R.id.btnNext)
    public void clickBtnNext() {
        if (questionType == QuestionType.COACHING_UNEDITABLE) {
            if (currentGroupId != maxGroupId) {
                moveToNextFragment();
            } else {
                finishActivity();
            }
        } else if (questionType == QuestionType.CHECKING_EDITABLE) {
            if (allCheckingAnswerChecked() && allAnswerExplained()) {
                finishActivity();
            } else {
                dialogHelper.showDialog(getString(R.string.please_fill_and_explain_all_answer));
            }
        } else if (allCoachingAnswerChecked() && allAnswerExplained()) {
            //Show alert confirm location
            if (coachingRequest.getAnswers().size() < questionData.size()) {
                saveCoachingAnswers();
            }
            if (currentGroupId != maxGroupId) {
                moveToNextFragment();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    dialogHelper.displayOkDialog(R.string.confirm_current_location
                            , R.string.ok);
                } else {
                    Snackbar.make(parentLayout
                            , R.string.turn_on_permissions
                            , Snackbar.LENGTH_LONG)
                            .setAction(R.string.yes_text, v1 -> App.getInstance().gotoSetting())
                            .show();
                }
            }
        } else {
            dialogHelper.showDialog(getString(R.string.please_fill_and_explain_all_answer));
        }
    }

    public void postCoaching() {
        InputStream ims = null;
        try {
            ims = getContentResolver().openInputStream(imageUri);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        encodedImage = ImageEncoding.encode(ImageEncoding.decodeBitmapFrom(ims));
        coachingRequest.setImage(encodedImage);//
        getPresenter().postCoaching(token, brand, coachingRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , LocationConstant.REFRESH_TIME
                    , LocationConstant.REFRESH_DISTANCE, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , LocationConstant.REFRESH_TIME
                    , LocationConstant.REFRESH_DISTANCE, this);
        } else {
            Snackbar.make(parentLayout
                    , R.string.turn_on_permissions
                    , Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes_text, v1 -> App.getInstance().gotoSetting())
                    .show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @OnClick(R.id.btnComplete)
    public void clickBtnComplete() {
        if (questionType == QuestionType.CHECKING_EDITABLE) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (allCheckingAnswerChecked() && allAnswerExplained()) {
                    dialogHelper.displayOkDialog(R.string.confirm_current_location
                            , R.string.ok);
                } else {
                    dialogHelper.showDialog(getString(R.string.please_fill_and_explain_all_answer));
                }
            } else {
                Snackbar.make(parentLayout
                        , R.string.turn_on_permissions
                        , Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes_text, v1 -> App.getInstance().gotoSetting())
                        .show();
            }
        } else {
            finishActivity();
        }
    }

    //post checking result to server
    private void postChecking() {
        InputStream ims = null;
        try {
            ims = getContentResolver().openInputStream(imageUri);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        encodedImage = ImageEncoding.encode(ImageEncoding.decodeBitmapFrom(ims));
        checkingRequest.setImage(encodedImage);
        checkingRequest.setSuggestion(currentQandAFragment.getEdtSuggestion().getText().toString());
        if (checkingRequest.getAnswers().size() < questionData.size()) {
            saveCheckingAnswers();
        }
        //post checking to server
        getPresenter().postChecking(token, brand, checkingRequest);
    }

    /**
     * if currentGroupId != maxGroupId (current page is not final page)
     * move to the next coaching questions fragment screen
     */

    private void moveToNextFragment() {
        updateGroupId();
        replaceFragment();
        updateViewStatus();
    }

    //save answers of all questions in the current fragment
    private void saveCoachingAnswers() {
        List<Answer> currentAnswers = currentQandAFragment.getAllAnswers();
        if (currentAnswers.size() > 0) {
            for (Answer answer : currentQandAFragment.getAllAnswers()) {
                coachingRequest.getAnswers().add(answer);
            }
        }
    }

    //save answers of all questions in the current fragment
    private void saveCheckingAnswers() {
        List<Answer> currentAnswers = currentQandAFragment.getAllAnswers();
        if (currentAnswers.size() > 0) {
            for (Answer answer : currentQandAFragment.getAllAnswers()) {
                checkingRequest.getAnswers().add(answer);
            }
        }
    }

    private boolean allCoachingAnswerChecked() {
        if (currentQandAFragment != null) {
            return currentQandAFragment.isAllCoachingAnswerChecked();
        } else {
            return true;
        }
    }

    private boolean allAnswerExplained() {
        return currentQandAFragment.isAllAnswerExplained();
    }

    private boolean allCheckingAnswerChecked() {
        if (currentQandAFragment != null) {
            return currentQandAFragment.isAllCheckingAnswerChecked();
        } else {
            return true;
        }
    }

    private void finishActivity() {
        setResult(Activity.RESULT_OK);
        this.finish();
    }

    private void initViewsVisibility() {
        switch (questionType) {
            case COACHING_EDITABLE:
                btnBack.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                btnComplete.setVisibility(View.GONE);
                break;
            case COACHING_UNEDITABLE:
                btnBack.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                btnComplete.setVisibility(View.GONE);
                break;
            case CHECKING_EDITABLE:
                btnBack.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                btnComplete.setVisibility(View.VISIBLE);
                break;
            case CHECKING_UNEDITABLE:
                btnBack.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                btnComplete.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void updateViewStatus() {
        updateTitle();
        updateButtonVisibility();
    }

    private void updateTitle() {
        if (questionType == QuestionType.COACHING_EDITABLE) {
            for (QuestionDatum questionDatum : questionData) {
                if (questionDatum.getGroupId() == currentGroupId) {
                    setTitle(getString(R.string.title_sup_test) + questionDatum.getGroupName());
                }
            }
        } else if (questionType == QuestionType.COACHING_UNEDITABLE) {
            for (ReportDatum reportDatum : reportData) {
                if (reportDatum.getGroupId() == currentGroupId) {
                    setTitle(getString(R.string.title_sup_test) + reportDatum.getGroupName());
                }
            }
        }
    }

    private void updateButtonVisibility() {
        if (currentGroupId == minGroupId) {
            btnBack.setVisibility(View.GONE);
        } else if (currentGroupId == maxGroupId) {
            btnNext.setText(R.string.btn_complete_text);
        } else {
            btnBack.setVisibility(View.VISIBLE);
            btnNext.setText(R.string.btn_next_text);
        }
    }

    private void showConfirmCurrentLocation(){
        isBeforeLocation = true;
        dialogHelper.displayOkDialog(R.string.confirm_current_location
                , R.string.ok);
    }

    private void initCoachingRequest() {
        coachingRequest = new CoachingRequest();
        coachingRequest.setBaId(pstId);
        coachingRequest.setOutletLocationId(outletLocationId);
        coachingRequest.setRouteId(routeId);
        coachingRequest.setAnswers(new ArrayList<>());
    }

    private void initCheckingRequest() {
        checkingRequest = new CheckingRequest();
        checkingRequest.setBaId(pstId);
        checkingRequest.setOutletLocationId(outletLocationId);
        checkingRequest.setRouteId(routeId);
        checkingRequest.setAnswers(new ArrayList<>());
    }

    private void replaceFragment() {
//        previousQandAFragment = currentQandAFragment;
        currentQandAFragment = QuestionAnswerListFragment.newInstance();
        currentQandAFragment.setCurrentGroupId(nextGroupId);
        currentQandAFragment.setQuestionType(questionType);
        currentQandAFragment.setListener(this);
        currentQandAFragment.setHasSuggestionEdt(false);
        if (questionType == QuestionType.COACHING_EDITABLE) {
            currentQandAFragment.setQuestionData(questionData);
        } else if (questionType == QuestionType.COACHING_UNEDITABLE) {
            currentQandAFragment.setReportData(reportData);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.questionAnswerListFragment, currentQandAFragment
                        , QuestionAnswerListFragment.TAG)
                .addToBackStack(null)
                .commit();
        currentGroupId = nextGroupId;
    }

    //if user click next, nextGroupId increase by 1
    private void updateGroupId() {
        if (currentGroupId < maxGroupId) {
            nextGroupId = currentGroupId + 1;
        }
    }

    //if user click back, nextGroupId decrease by 1
    private void revertGroupId() {
        if (currentGroupId > minGroupId) {
            currentGroupId = currentGroupId - 1;
        }
    }

    @Override
    public void updateReportListAdapter(List<ReportDatum> reportData
            , int smallestGroupId, int biggestGroupId) {
        this.reportData = reportData;
        currentGroupId = smallestGroupId;
        maxGroupId = biggestGroupId;
        minGroupId = smallestGroupId;
        if (savedInstanceState == null) {
            currentQandAFragment = QuestionAnswerListFragment.newInstance();
            if (questionType == QuestionType.COACHING_UNEDITABLE
                    || questionType == QuestionType.CHECKING_UNEDITABLE) {
                currentQandAFragment.setReportData(reportData);
            }
            currentQandAFragment.setCurrentGroupId(currentGroupId);
            currentQandAFragment.setQuestionType(questionType);
            currentQandAFragment.setListener(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.questionAnswerListFragment, currentQandAFragment
                            , QuestionAnswerListFragment.TAG)
                    .commit();
        }
    }


    @Override
    public void updateQuestionList(List<QuestionDatum> questions
            , int smallestGroupId
            , int biggestGroupId) {
        questionData = questions;
        questionData = Utils.sortListQuestionDatum(questionData);
        currentGroupId = smallestGroupId;
        maxGroupId = biggestGroupId;
        minGroupId = smallestGroupId;
        if (savedInstanceState == null) {
            currentQandAFragment = QuestionAnswerListFragment.newInstance();
            currentQandAFragment.setQuestionData(questionData);
            currentQandAFragment.setCurrentGroupId(currentGroupId);
            currentQandAFragment.setQuestionType(questionType);
            currentQandAFragment.setListener(this);
            if (questionType == QuestionType.CHECKING_UNEDITABLE || questionType == QuestionType.CHECKING_EDITABLE) {
                currentQandAFragment.setHasSuggestionEdt(true);
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.questionAnswerListFragment, currentQandAFragment
                            , QuestionAnswerListFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        int errorMessage = Utils.getErrorMessageFrom(errorType);
        //TODO: thinking about using snackbarhelper
        Snackbar snackbar = Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG);
        if (errorType == ErrorType.ERROR_RESPONSE_POST_COACHING
                || errorType == ErrorType.ERROR_REQUEST_POST_COACHING) {
            snackbar.addCallback(snackBarCallback);
        }
        snackbar.show();
    }

    @Override
    public void postCoachingSuccessfully() {
        snackbarHelper.show(parentLayout, R.string.notify_post_coaching_sucessfully, snackBarCallback);
    }

    @Override
    public void postCheckingSuccessfully() {
        snackbarHelper.show(parentLayout, R.string.notify_post_coaching_sucessfully, snackBarCallback);
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
    public void onLoadViewFinish() {
        hideLoadingDialog();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        Log.e("Tung", "lat: " + location.getLatitude() + " long: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onOkButtonClick() {
        if(questionType == QuestionType.CHECKING_EDITABLE){
            if(isBeforeLocation){
                if (currentLocation != null) {
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();
                    checkingRequest.setLatBefore(latitude);
                    checkingRequest.setLngBefore(longitude);
                } else {
                    checkingRequest.setLatBefore(0.00);
                    checkingRequest.setLngBefore(0.00);
                }
                isBeforeLocation = false;
            }else{
                if (currentLocation != null) {
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();
                    checkingRequest.setLatAfter(latitude);
                    checkingRequest.setLngAfter(longitude);
                } else {
                    checkingRequest.setLatAfter(0.00);
                    checkingRequest.setLngAfter(0.00);
                }
                postChecking();
            }
        }else if( questionType == QuestionType.COACHING_EDITABLE){
            if(isBeforeLocation){
                if (currentLocation != null) {
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();
                    coachingRequest.setLatBefore(latitude);
                    coachingRequest.setLngBefore(longitude);
                } else {
                    coachingRequest.setLatBefore(0.00);
                    coachingRequest.setLngBefore(0.00);
                }
                isBeforeLocation = false;
            }else{
                if (currentLocation != null) {
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();
                    coachingRequest.setLatAfter(latitude);
                    coachingRequest.setLngAfter(longitude);
                } else {
                    coachingRequest.setLatAfter(0.00);
                    coachingRequest.setLngAfter(0.00);
                }
                postCoaching();
            }
        }
    }
}
