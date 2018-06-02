package com.ekakashi.greenhouse.features.timeline_post;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.ImageHelper;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.custom_gallery.MainActivityGallery;
import com.ekakashi.greenhouse.database.model_server_response.FertilizerResponse;
import com.ekakashi.greenhouse.database.model_server_response.WorkTypeResponse;
import com.ekakashi.greenhouse.features.timeline.image.TimelineViewImageActivity;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_add_detail.TimelineAddDetailModel;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_post.model.ImageViewer;
import com.ekakashi.greenhouse.features.timeline_post.model.InputFilterFertilizer;
import com.ekakashi.greenhouse.features.timeline_post.post_desciption.PostDescriptionActivity;
import com.ekakashi.greenhouse.features.timeline_post.post_desciption.PostDescriptionItem;
import com.ekakashi.greenhouse.features.timeline_post.post_desciption.PostDescriptionModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EKTimelinePostActivity extends BaseActivity implements View.OnClickListener, ImageHelper.ImageActionListener, EKTimelinePostInterface.View {

    private ImageView imgCamera, imgGallery, imgClose, imgContent1, imgContent21, imgContent22;
    private TextView tvWorkplace, tvTargetCrop, tvWorkType, tvDisease, tvPesticide, tvFertilizer, tvDateSpecificationDate, tvDateSpecificationTime;
    private RelativeLayout relWorkDescription, relImage;
    private LinearLayout linFertilizer, layoutImage2;
    private EditText edWorkDescription, edTargetCrop, edWorkType, edDisease, edPesticide, edNitrogen, edPhosphoric, edKali, edFertilizerName;

    //Layout contain row (what user can be click) and text view title
    private LinearLayout layoutDisease, layoutTargetCrop, layoutWorkType, layoutPesticide, layoutFertilizer, layoutDateSpecification;

    private PostDescriptionModel descriptionModel = new PostDescriptionModel();
    private TimelineAddDetailModel addDetailModel = new TimelineAddDetailModel();
    private EKTimelinePostInterface.Presenter mPresenter;
    private ImageHelper imageHelper;
    private Calendar mCalendar;
    private TimelineResponse.ListTimeline timelineObject;

    private ArrayList<FilterField> workplaceList;
    private int workPlaceID = 0;
    private Integer workTypeID = null;
    private Integer fertilizerId = null;
    private int ID_NONE = 1;

    private ArrayList<ImageViewer> mImages = new ArrayList<>();
    private MyToolbar myToolbar;

    private int TYPE;
    private final int LIMIT_IMAGE = 3;
    private final int EDIT = 1;
    private final int POST = 2;

    private final int TARGET_CROP = 0;
    private final int WORK_TYPE = 1;
    private final int WORK_PLACE = 2;
    private final int DISEASE = 3;
    private final int PESTICIDE = 4;
    private final int FERTILIZER = 5;
    private final int REQUEST_CODE_POST_ACTIVITY = 22;
    private final int REQUEST_CODE_ADD_DETAIL_ACTIVITY = 23;

    private Intent intentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_post);

        intentEdit = getIntent();
        TYPE = intentEdit.getIntExtra(Utils.Constant.TIMELINE_POST_EDIT, POST);

        mDatabaseHelper = App.getDatabaseHelper(getApplicationContext());

        addToolbar();
        addControls();
        addEvents();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new EKTimelinePostPresenter(this);
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.getWorkplace(Prefs.getInstance(getApplicationContext()).getToken());
    }

    private void addToolbar() {
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
        if (TYPE == POST) {
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.timeline_post_title), "");
        } else {
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.timeline_edit_title), "");
        }
        if (TYPE == POST) {
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_post));
        } else {
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        }
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (TYPE == POST) {
                    postDiary();
                } else {
                    editDiary();
                }
            }
        });
    }

    private void editDiary() {
        int id = Integer.parseInt(timelineObject.getTimelineId());
        String work_description = edWorkDescription.getText().toString().trim();
        if (TextUtils.isEmpty(work_description)) {
            showSimpleMessage(getString(R.string.work_description_valid));
            return;
        }
        String disease = getTextData(tvDisease, edDisease);
        String pesticide = getTextData(tvPesticide, edPesticide);
        String targetCrop = getTextData(tvTargetCrop, edTargetCrop);
        Integer workTypeId = workTypeID;
        String workTypeName = getTextData(tvWorkType, edWorkType);

        String N = "";
        String P = "";
        String K = "";
        String fertilizer;
        if (!tvFertilizer.getText().toString().equalsIgnoreCase(getString(R.string.post_none))) {
            if (tvFertilizer.getText().toString().equalsIgnoreCase(getString(R.string.post_other))) {
                /*if (TextUtils.isEmpty(edNitrogen.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }
                if (TextUtils.isEmpty(edPhosphoric.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }
                if (TextUtils.isEmpty(edKali.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }*/
                if (TextUtils.isEmpty(edFertilizerName.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_fertilizer));
                    return;
                }
                fertilizer = edFertilizerName.getText().toString();
            } else {
                fertilizer = tvFertilizer.getText().toString();
            }
            N = edNitrogen.getText().toString();
            P = edPhosphoric.getText().toString();
            K = edKali.getText().toString();
        } else {
            fertilizerId = ID_NONE;
            fertilizer = tvFertilizer.getText().toString();
        }
        Calendar calYMD = Calendar.getInstance(Locale.getDefault());
        Calendar calHM = Calendar.getInstance(Locale.getDefault());

        Date dateYMD = DateTimeNow.parseStringToDateLocal(tvDateSpecificationDate.getText().toString(), getString(R.string.format_date_time));
        Date dateHM = DateTimeNow.parseStringToDateLocal(tvDateSpecificationTime.getText().toString(), getString(R.string.hh_mm));
        String dateSpecificationUTC;
        if (dateYMD != null && dateHM != null) {
            calYMD.setTimeInMillis(dateYMD.getTime());
            calHM.setTimeInMillis(dateHM.getTime());
            dateSpecificationUTC = DateTimeNow.parseTimeToUTCDateString(calYMD.get(Calendar.YEAR),
                    calYMD.get(Calendar.MONTH) + 1, calYMD.get(Calendar.DAY_OF_MONTH), calHM.get(Calendar.HOUR_OF_DAY),
                    calHM.get(Calendar.MINUTE), calHM.get(Calendar.SECOND));
        } else {
            dateSpecificationUTC = DateTimeNow.parseCurrentTimeToUTCDateString();
        }

        String imageUrl1 = "";
        String imageUrl2 = "";
        String imageUrl3 = "";
        List<String> imageFromGallery = mPresenter.getListImageStringWith(mImages, null);
        List<String> imageFromDiary = mPresenter.getListImageStringWith(mImages, ImageViewer.ImageType.Diary);
        String imageString = mPresenter.convertImageArrayToString(imageFromDiary);

        if (imageFromGallery.size() > 0) {
            if (imageFromGallery.size() == 1) {
                imageUrl1 = imageFromGallery.get(0);
            }
            if (imageFromGallery.size() == 2) {
                imageUrl1 = imageFromGallery.get(0);
                imageUrl2 = imageFromGallery.get(1);
            }
            if (imageFromGallery.size() == 3) {
                imageUrl1 = imageFromGallery.get(0);
                imageUrl2 = imageFromGallery.get(1);
                imageUrl3 = imageFromGallery.get(2);
            }
        }

        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.updateDiary(id, work_description, workPlaceID, workTypeId, workTypeName, targetCrop,
                disease, pesticide, fertilizerId, fertilizer, N, P, K, dateSpecificationUTC, imageString, imageUrl1, imageUrl2, imageUrl3);
    }

    private void postDiary() {
        String work_description = edWorkDescription.getText().toString().trim();
        if (TextUtils.isEmpty(work_description)) {
            showSimpleMessage(getString(R.string.work_description_valid));
            return;
        }
        String disease = getTextData(tvDisease, edDisease);
        String pesticide = getTextData(tvPesticide, edPesticide);
        String targetCrop = getTextData(tvTargetCrop, edTargetCrop);
        Integer workTypeId = workTypeID;
        String workTypeName = getTextData(tvWorkType, edWorkType);

        String N = "";
        String P = "";
        String K = "";
        String fertilizer;
        if (!tvFertilizer.getText().toString().equalsIgnoreCase(getString(R.string.post_none))) {
            if (tvFertilizer.getText().toString().equalsIgnoreCase(getString(R.string.post_other))) {
                if (TextUtils.isEmpty(edNitrogen.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }
                if (TextUtils.isEmpty(edPhosphoric.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }
                if (TextUtils.isEmpty(edKali.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_npk));
                    return;
                }
                if (TextUtils.isEmpty(edFertilizerName.getText().toString())) {
                    showSimpleMessage(getString(R.string.post_valid_fertilizer));
                    return;
                }
                fertilizer = edFertilizerName.getText().toString();
            } else {
                fertilizer = tvFertilizer.getText().toString();
            }
            N = edNitrogen.getText().toString();
            P = edPhosphoric.getText().toString();
            K = edKali.getText().toString();
        } else {
            fertilizerId = ID_NONE;
            fertilizer = tvFertilizer.getText().toString();
        }


        Calendar calYMD = Calendar.getInstance(Locale.getDefault());
        Calendar calHM = Calendar.getInstance(Locale.getDefault());

        Date dateYMD = DateTimeNow.parseStringToDateLocal(tvDateSpecificationDate.getText().toString(), getString(R.string.format_date_time));
        Date dateHM = DateTimeNow.parseStringToDateLocal(tvDateSpecificationTime.getText().toString(), getString(R.string.hh_mm));
        String dateSpecificationUTC;
        if (dateYMD != null && dateHM != null) {
            calYMD.setTimeInMillis(dateYMD.getTime());
            calHM.setTimeInMillis(dateHM.getTime());
            dateSpecificationUTC = DateTimeNow.parseTimeToUTCDateString(calYMD.get(Calendar.YEAR),
                    calYMD.get(Calendar.MONTH), calYMD.get(Calendar.DAY_OF_MONTH), calHM.get(Calendar.HOUR_OF_DAY),
                    calHM.get(Calendar.MINUTE), calHM.get(Calendar.SECOND));
        } else {
            //ToDo fix bug next month by default
            dateSpecificationUTC = DateTimeNow.parseCurrentTimeToUTCDateString();
        }

        String imageUrl1 = "";
        String imageUrl2 = "";
        String imageUrl3 = "";
        List<String> imageFromGallery = mPresenter.getListImageStringWith(mImages, null);
        if (imageFromGallery.size() > 0) {
            if (imageFromGallery.size() == 1) {
                imageUrl1 = imageFromGallery.get(0);
            }
            if (imageFromGallery.size() == 2) {
                imageUrl1 = imageFromGallery.get(0);
                imageUrl2 = imageFromGallery.get(1);
            }
            if (imageFromGallery.size() == 3) {
                imageUrl1 = imageFromGallery.get(0);
                imageUrl2 = imageFromGallery.get(1);
                imageUrl3 = imageFromGallery.get(2);
            }
        }

        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.createDiary(work_description, workPlaceID, workTypeId, workTypeName, targetCrop,
                disease, pesticide, fertilizerId, fertilizer, N, P, K, dateSpecificationUTC, imageUrl1, imageUrl2, imageUrl3);
    }

    private String getTextData(TextView textView, EditText editText) {
        String text = textView.getText().toString();
        if (text.equals(getString(R.string.post_other))) {
            text = editText.getText().toString().trim();
        } else if (text.equals(getString(R.string.post_none))) {
            text = "";
        }
        return text;
    }

    private void addControls() {
        imgGallery = findViewById(R.id.imgGallery);
        imgClose = findViewById(R.id.imgClose);
        imgCamera = findViewById(R.id.imgCamera);
        imgContent1 = findViewById(R.id.imgContent1);
        imgContent21 = findViewById(R.id.imgContent21);
        imgContent22 = findViewById(R.id.imgContent22);

        tvWorkplace = findViewById(R.id.tvWorkplace);
        tvDisease = findViewById(R.id.tvDisease);
        tvTargetCrop = findViewById(R.id.tvTargetCrop);
        tvWorkType = findViewById(R.id.tvWorkType);
        tvPesticide = findViewById(R.id.tvPesticide);
        tvFertilizer = findViewById(R.id.tvFertilizer);
        tvDateSpecificationDate = findViewById(R.id.tvDateSpecificationDate);
        tvDateSpecificationTime = findViewById(R.id.tvDateSpecificationTime);

        relWorkDescription = findViewById(R.id.relWorkDescription);
        layoutImage2 = findViewById(R.id.layoutImage2);
        relImage = findViewById(R.id.relImage);
        linFertilizer = findViewById(R.id.linFertilizer);

        layoutWorkType = findViewById(R.id.layoutWorkType);
        layoutTargetCrop = findViewById(R.id.layoutTargetCrop);
        layoutDisease = findViewById(R.id.layoutDisease);
        layoutPesticide = findViewById(R.id.layoutPesticide);
        layoutFertilizer = findViewById(R.id.layoutFertilizer);
        layoutDateSpecification = findViewById(R.id.layoutDateSpecification);

        edWorkDescription = findViewById(R.id.edWorkDescription);
        edWorkType = findViewById(R.id.edWorkType);
        edTargetCrop = findViewById(R.id.edTargetCrop);
        edDisease = findViewById(R.id.edDisease);
        edPesticide = findViewById(R.id.edPesticide);
        edNitrogen = findViewById(R.id.edNitrogen);
        edPhosphoric = findViewById(R.id.edPhosphoric);
        edKali = findViewById(R.id.edKali);
        edFertilizerName = findViewById(R.id.edFertilizerName);

        setFertilizerLimit();
        Utils.enableView(myToolbar.tvToolbarRight, edWorkDescription.getText().toString().length() != 0);

        imageHelper = new ImageHelper(this);
        imageHelper.setImageActionListener(this);
        mCalendar = Calendar.getInstance();
        if (TYPE == EDIT) {
            configWithEditMode();
        }

        edWorkDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.enableView(myToolbar.tvToolbarRight, edWorkDescription.getText().toString().length() != 0);
            }
        });
    }

    void configWithEditMode() {
        timelineObject = (TimelineResponse.ListTimeline) intentEdit.getSerializableExtra("TimelineObject");
        if (timelineObject != null) {
            if (!TextUtils.isEmpty(timelineObject.getDescription())) {
                edWorkDescription.setText(timelineObject.getDescription());
                Utils.enableView(myToolbar.tvToolbarRight, edWorkDescription.getText().toString().length() != 0);
            }
            if (!TextUtils.isEmpty(timelineObject.getFieldName())) {
                tvWorkplace.setText(timelineObject.getFieldName());
            }
            if (!TextUtils.isEmpty(timelineObject.getUpdatedAt())) {
                parseTime(timelineObject.getUpdatedAt());
            }
            workPlaceID = timelineObject.getEkfieldId();
            if (timelineObject.getDiary() != null) {
                TimelineResponse.Diary diary = timelineObject.getDiary();
                //show Disease Type
                if (diary.getWorkType() != null) {
                    if (!TextUtils.isEmpty(diary.getWorkType().getName())) {
                        setVisibleView(layoutWorkType, true);
                        addDetailModel.setSelectWorkType(true);
                        tvWorkType.setText(diary.getWorkType().getName());
                    }
                }
                if (!TextUtils.isEmpty(diary.getTargetCrop())) {
                    setVisibleView(layoutTargetCrop, true);
                    addDetailModel.setSelectTargetCrop(true);
                    tvTargetCrop.setText(diary.getTargetCrop());
                }
                if (!TextUtils.isEmpty(diary.getDiseaseType())) {
                    setVisibleView(layoutDisease, true);
                    addDetailModel.setSelectDisease(true);
                    tvDisease.setText(diary.getDiseaseType());
                }
                //show Pesticide Type
                if (!TextUtils.isEmpty(diary.getPesticideType())) {
                    tvPesticide.setText(diary.getPesticideType());
                    setVisibleView(layoutPesticide, true);
                    addDetailModel.setSelectPesticide(true);
                }
                //show Fertilizer Type
                if (!TextUtils.isEmpty(diary.getFertilizerType())) {
                    if (diary.getFertilizerType().equals("") || diary.getFertilizerId().equals(String.valueOf(ID_NONE))) {
                        linFertilizer.setVisibility(View.GONE);
                        tvFertilizer.setText(getString(R.string.post_none));
                        setVisibleView(layoutFertilizer, false);
                    } else {
                        tvFertilizer.setText(timelineObject.getDiary().getFertilizerType());
                        linFertilizer.setVisibility(View.VISIBLE);
                        edNitrogen.setText(String.valueOf(diary.getNitrogen()));
                        edPhosphoric.setText(String.valueOf(diary.getPhosphoric()));
                        edKali.setText(String.valueOf(diary.getPotossium()));
                        edNitrogen.setEnabled(false);
                        edKali.setEnabled(false);
                        edPhosphoric.setEnabled(false);
                        setVisibleView(layoutFertilizer, true);
                        addDetailModel.setSelectFertilizer(true);
                        fertilizerId = Integer.valueOf(diary.getFertilizerId());
                    }
                    //show Image
                    if (diary.getDiaryImageUrl() != null && diary.getDiaryImageUrl().size() > 0) {
//                        imageFromDiary = diary.getDiaryImageUrl();
                        for (String item : diary.getDiaryImageUrl()) {
                            mImages.add(new ImageViewer(item, ImageViewer.ImageType.Diary));
                        }
                        showImage(mImages);
                    }
                    //show date specification
                    if (!TextUtils.isEmpty(diary.getIssuedDate())) {
                        String date = DateTimeNow.parseDateStringToLocalDateString(diary.getIssuedDate(),
                                DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, getString(R.string.format_date_time));
                        String time = DateTimeNow.parseDateStringToLocalDateString(diary.getIssuedDate(),
                                DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, getString(R.string.hh_mm));
                        tvDateSpecificationDate.setText(date);
                        tvDateSpecificationTime.setText(time);
                        setVisibleView(layoutDateSpecification, true);
                        addDetailModel.setSelectDate(true);
                    }
                }
                handleAddDetail(true);
                findViewById(R.id.btnAddDetail).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ImageHelper.REQUEST_PICTURE_FROM_CAMERA: {
                if (ContextCompat.checkSelfPermission(EKTimelinePostActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        // the user has just denied one or all of the permissions
                        // use this message to explain why he needs to grant these permissions in order to proceed
                        //This message is displayed after the user has checked never ask again checkbox.
                        String message = "Camera permission needed. Please allow this permission in App Settings.";
                        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //this will be executed if User clicks OK button. This is gonna take the user to the App Settings
                                startAppSettingsConfigActivity();
                            }
                        };
                        new AlertDialog.Builder(EKTimelinePostActivity.this)
                                .setMessage(message)
                                .setPositiveButton(getString(R.string.btn_dialog_ok), listener)
                                .setNegativeButton(getString(R.string.cancel), null)
                                .create()
                                .show();
                    }
                    // permission denied
                    Log.d("onRequestPermissions", "permission denied");
                } else {
                    // permission was granted
                    imageHelper.takePhotoWithCamera();
                    Log.d("onRequestPermissions", "permission granted editSuccess");
                }
                break;
            }
            default:
                break;
        }
    }

    private void startAppSettingsConfigActivity() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(i);
    }

    private void addEvents() {
        findViewById(R.id.layoutWorkplace).setOnClickListener(this);
        findViewById(R.id.relTargetCrop).setOnClickListener(this);
        findViewById(R.id.relWorkType).setOnClickListener(this);
        findViewById(R.id.relDisease).setOnClickListener(this);
        findViewById(R.id.relPesticide).setOnClickListener(this);
        findViewById(R.id.relFertilizer).setOnClickListener(this);
        findViewById(R.id.relDateSpecification).setOnClickListener(this);
        findViewById(R.id.btnAddDetail).setOnClickListener(this);

        imgCamera.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        imgClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PostDescriptionActivity.class);
        descriptionModel.setWorkPlaceID(workPlaceID);
        switch (v.getId()) {
            case R.id.layoutWorkplace:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvWorkplace.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, WORK_PLACE);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, WORK_PLACE);
                break;
            case R.id.relTargetCrop:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvTargetCrop.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, TARGET_CROP);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, TARGET_CROP);
                break;
            case R.id.relWorkType:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvWorkType.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, WORK_TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, WORK_TYPE);
                break;
            case R.id.relPesticide:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvPesticide.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, PESTICIDE);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, PESTICIDE);
                break;
            case R.id.relFertilizer:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvFertilizer.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, FERTILIZER);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, FERTILIZER);
                break;
            case R.id.relDisease:
                intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, TYPE);
                intent.putExtra(Utils.Constant.TIMELINE_EDIT_DATA, tvDisease.getText().toString());
                intent.putExtra(Utils.Constant.TIMELINE_POST_TYPE, DISEASE);
                intent.putExtra(Utils.Constant.TIMELINE_POST_DATA, descriptionModel);
                startActivityForResult(intent, DISEASE);
                break;
            case R.id.relDateSpecification:
                showDatePickerDialog();
                break;
            case R.id.btnAddDetail:
                findViewById(R.id.btnAddDetail).setVisibility(View.GONE);
                /*Intent intentDetail = new Intent(this, TimelineAddDetailActivity.class);
                intentDetail.putExtra(Utils.Constant.TIMELINE_DETAIL_MODEL, addDetailModel);
                startActivityForResult(intentDetail, REQUEST_CODE_ADD_DETAIL_ACTIVITY);*/
                handleAddDetail(true);
                break;
            case R.id.imgCamera:
                if (checkImageSize(mImages)) {
                    return;
                }
                imageHelper.takePhotoWithCamera();
                break;
            case R.id.imgGallery:
                if (checkImageSize(mImages)) {
                    return;
                }
                /*Intent intentImage = new Intent(EKTimelinePostActivity.this, AlbumSelectActivity.class);
                intentImage.putExtra(Constants.INTENT_EXTRA_LIMIT, LIMIT_IMAGE - mImages.size());
                startActivityForResult(intentImage, Constants.REQUEST_CODE);*/
                Intent intentImage = new Intent(EKTimelinePostActivity.this, MainActivityGallery.class);
                intent.putExtra(Utils.Constant.IMAGE_PICK_TYPE, Utils.Constant.PICK_MULTI_IMAGE);
                startActivityForResult(intentImage, Utils.Constant.PICK_IMAGE_REQUEST_CODE);
                break;
            case R.id.imgClose:
                relImage.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                showTimePickerDialog(dayOfMonth, month, year);
            }
        };
        DatePickerDialog dialog = Utils.createDatePicker(this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH)
                , mCalendar.get(Calendar.DAY_OF_MONTH), listener);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private void showTimePickerDialog(final int day, final int month, final int year) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String dateStringUTC = DateTimeNow.parseTimeToUTCDateString(year, month, day, hourOfDay, minute, mCalendar.get(Calendar.SECOND));
                Date dateUTC = DateTimeNow.parseStringToDateLocal(dateStringUTC, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ);
                Date currentDate = DateTimeNow.parseStringToDateLocal(DateTimeNow.parseMillisecondToFormatDate(
                        String.valueOf(System.currentTimeMillis()), getString(R.string.format_date_and_time)), getString(R.string.format_date_and_time));
                if (dateUTC != null && dateUTC.after(currentDate)) {
                    Toast.makeText(EKTimelinePostActivity.this, R.string.date_specification_cannot_be_in_future, Toast.LENGTH_SHORT).show();
                    return;
                }
                tvDateSpecificationDate.setText(DateTimeNow.parseDateLocalToString(dateUTC, getString(R.string.format_date_time)));
                tvDateSpecificationTime.setText(DateTimeNow.parseDateLocalToString(dateUTC, getString(R.string.hh_mm)));
            }
        };
        Utils.createTimePicker(this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true, listener).show();
    }

    private boolean checkImageSize(ArrayList list) {
        if (list.size() >= LIMIT_IMAGE) {
            showSimpleMessage(getString(R.string.image_limit_confirm));
            return true;
        }
        return false;
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {

    }

    @Override
    public void onImageTakenFromCamera(String uri, File imageFile) {
        mImages.add(new ImageViewer(imageFile.getPath(), ImageViewer.ImageType.Camera));
        showImage(mImages);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case WORK_PLACE:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getWorkplaceList() != null)
                        for (FilterField item : descriptionModel.getWorkplaceList()) {
                            if (item.isSelected()) {
                                tvWorkplace.setText(item.getPlaceName());
                                workPlaceID = item.getId();
                            }
                        }
                    break;
                case WORK_TYPE:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getWorkTypeList() != null) {
                        for (WorkTypeResponse.WorkType item : descriptionModel.getWorkTypeList()) {
                            if (item.isSelected()) {
                                tvWorkType.setText(item.getName());
                                if (item.getName().equals(getString(R.string.post_none))) {
                                    edWorkType.setVisibility(View.GONE);
                                    workTypeID = 1;
                                } else if (item.getName().equals(getString(R.string.post_other))) {
                                    workTypeID = null;
                                    edWorkType.setVisibility(View.VISIBLE);
                                    edWorkType.requestFocus();
                                } else {
                                    edWorkType.setVisibility(View.GONE);
                                    workTypeID = item.getId();
                                }
                            }
                        }
                    }
                    break;
                case TARGET_CROP:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getTargetCropList() != null) {
                        for (PostDescriptionItem item : descriptionModel.getTargetCropList()) {
                            if (item.isSelected()) {
                                tvTargetCrop.setText(item.getName());
                                if (item.getName().equals(getString(R.string.post_other))) {
                                    edTargetCrop.setVisibility(View.VISIBLE);
                                    edTargetCrop.requestFocus();
                                } else {
                                    edTargetCrop.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                    break;
                case DISEASE:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getDiseaseList() != null) {
                        for (PostDescriptionItem item : descriptionModel.getDiseaseList()) {
                            if (item.isSelected()) {
                                tvDisease.setText(item.getName());
                                if (item.getName().equals(getString(R.string.post_other))) {
                                    edDisease.setVisibility(View.VISIBLE);
                                    edDisease.requestFocus();
                                } else {
                                    edDisease.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                    break;
                case PESTICIDE:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getPesticideList() != null) {
                        for (PostDescriptionItem item : descriptionModel.getPesticideList()) {
                            if (item.isSelected()) {
                                tvPesticide.setText(item.getName());
                                if (item.getName().equals(getString(R.string.post_other))) {
                                    edPesticide.setVisibility(View.VISIBLE);
                                    edPesticide.requestFocus();
                                } else {
                                    edPesticide.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                    break;
                case FERTILIZER:
                    descriptionModel = data.getParcelableExtra("PostData");
                    if (descriptionModel != null && descriptionModel.getFertilizerList() != null) {
                        for (FertilizerResponse.Fertilizer fertilizer : descriptionModel.getFertilizerList()) {
                            if (fertilizer.isSelected()) {
                                tvFertilizer.setText(fertilizer.getDescription());
                                if (fertilizer.getDescription().equals(getString(R.string.post_none))) {
                                    linFertilizer.setVisibility(View.GONE);
                                    fertilizerId = ID_NONE;
                                } else {
                                    linFertilizer.setVisibility(View.VISIBLE);
                                    if (fertilizer.getDescription().equals(getString(R.string.post_other))) {
                                        edFertilizerName.setVisibility(View.VISIBLE);
                                        edFertilizerName.requestFocus();
                                        edNitrogen.setText("");
                                        edKali.setText("");
                                        edPhosphoric.setText("");
                                        edNitrogen.setEnabled(true);
                                        edKali.setEnabled(true);
                                        edPhosphoric.setEnabled(true);
                                        fertilizerId = null;
                                    } else {
                                        edFertilizerName.setVisibility(View.GONE);
                                        edNitrogen.setText(String.valueOf(fertilizer.getNitrogen()));
                                        edKali.setText(String.valueOf(fertilizer.getPotassium()));
                                        edPhosphoric.setText(String.valueOf(fertilizer.getPhosphorus()));
                                        edNitrogen.setEnabled(false);
                                        edKali.setEnabled(false);
                                        edPhosphoric.setEnabled(false);
                                        fertilizerId = fertilizer.getId();
                                    }
                                }
                            }
                        }
                    }
                    break;
                case Utils.Constant.PICK_IMAGE_REQUEST_CODE:
                    ArrayList<String> listImage = data.getStringArrayListExtra(Utils.Constant.IMAGE_LIST_SELECTED);
                    for (String imagePath : listImage) {
                        mImages.add(new ImageViewer(imagePath, ImageViewer.ImageType.Gallery));
                    }
                    /*ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                    for (Image image : images) {
                        mImages.add(new ImageViewer(image.path, ImageViewer.ImageType.Gallery));
                    }*/
                    showImage(mImages);
                    break;
                case REQUEST_CODE_POST_ACTIVITY:
                    int imagePosition = data.getIntExtra(Utils.Name.DIARY_POSITION, -1);
                    if (imagePosition == -1 || imagePosition >= mImages.size()) {
                        return;
                    }
                    mImages.remove(imagePosition);
                    showImage(mImages);

                    break;
                /*case REQUEST_CODE_ADD_DETAIL_ACTIVITY:
                    addDetailModel = data.getParcelableExtra(Utils.Constant.TIMELINE_DETAIL_MODEL);
                    if (addDetailModel != null) {
                        handleAddDetail(addDetailModel);
                    }
                    break;*/
                default:
                    break;
            }
        }
    }

    private void handleAddDetail(boolean isVisible) {
        setVisibleView(layoutPesticide, isVisible);
        setVisibleView(layoutDisease, isVisible);
        setVisibleView(layoutFertilizer, isVisible);
        setVisibleView(layoutDateSpecification, isVisible);
        setVisibleView(layoutWorkType, isVisible);
        setVisibleView(layoutTargetCrop, isVisible);

        disablePostView(tvWorkType, isVisible);
        disablePostView(tvTargetCrop, isVisible);
        disablePostView(tvPesticide, isVisible);
        disablePostView(tvDisease, isVisible);
        disablePostView(tvFertilizer, isVisible);
    }


    private void showImage(ArrayList<ImageViewer> images) {
        relImage.setVisibility(View.VISIBLE);
        relWorkDescription.setVisibility(View.VISIBLE);
        if (images.size() > 0) {
            imgContent1.setVisibility(View.GONE);
            layoutImage2.setVisibility(View.GONE);
        }
        switch (images.size()) {
            case 0:
                imgContent1.setVisibility(View.GONE);
                layoutImage2.setVisibility(View.GONE);
                break;
            case 1:
                imgContent1.setVisibility(View.VISIBLE);
                Glide.with(this).load(images.get(0).getUrl()).error(R.drawable.image_placeholder).into(imgContent1);
                imgContent1.setLayoutParams(getLayoutParams(dp2px(250)));
                onImageClick(imgContent1, 0);
                break;
            case 2:
                imgContent1.setVisibility(View.GONE);
                layoutImage2.setVisibility(View.VISIBLE);
                Glide.with(this).load(images.get(0).getUrl()).error(R.drawable.image_placeholder).into(imgContent21);
                Glide.with(this).load(images.get(1).getUrl()).error(R.drawable.image_placeholder).into(imgContent22);
                onImageClick(imgContent21, 0);
                onImageClick(imgContent22, 1);
                break;
            case 3:
                imgContent1.setVisibility(View.VISIBLE);
                layoutImage2.setVisibility(View.VISIBLE);
                Glide.with(this).load(images.get(0).getUrl()).error(R.drawable.image_placeholder).into(imgContent1);
                Glide.with(this).load(images.get(1).getUrl()).error(R.drawable.image_placeholder).into(imgContent21);
                Glide.with(this).load(images.get(2).getUrl()).error(R.drawable.image_placeholder).into(imgContent22);
                imgContent1.setLayoutParams(getLayoutParams(dp2px(150)));
                onImageClick(imgContent1, 0);
                onImageClick(imgContent21, 1);
                onImageClick(imgContent22, 2);
                break;
            default:
                break;
        }
        relImage.setVisibility(View.VISIBLE);
    }

    private void onImageClick(ImageView imageView, final int position) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EKTimelinePostActivity.this, TimelineViewImageActivity.class);
                intent.putExtra(Utils.Name.DIARY_TYPE, 2);//1 is Timeline, 2 is Post/Edit
                intent.putParcelableArrayListExtra(Utils.Name.DIARY_LIST_IMAGE, mImages);
                intent.putExtra(Utils.Name.DIARY_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE_POST_ACTIVITY);
            }
        });
    }

    @Override
    public void onWorkplaceSuccess(ArrayList<FilterField> list) {
        if (list != null && !list.isEmpty()) {
            workplaceList = list;
            if (TYPE == POST) {
                workplaceList.get(0).setSelected(true);
                workPlaceID = workplaceList.get(0).getId();
                tvWorkplace.setText(workplaceList.get(0).getPlaceName());
            } else {
                for (FilterField field : workplaceList) {
                    if (workPlaceID == field.getId()) {
                        field.setSelected(true);
                    } else {
                        field.setSelected(false);
                    }
                }
            }
        } else {
            workplaceList = new ArrayList<>();
        }
        descriptionModel.setWorkplaceList(workplaceList);
        hideLoadingDialog();
    }

    @Override
    public void onWorkplaceFail() {
        hideLoadingDialog();
        showSimpleMessage(getString(R.string.cannot_get_workplaces));
    }

    @Override
    public void diaryPostSuccess() {
        hideLoadingDialog();
        Toast.makeText(this, R.string.toast_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void diaryPostFail(String error) {
        hideLoadingDialog();
        showSimpleMessage(error);
    }

    @Override
    public void diaryUpdateSuccess() {
        hideLoadingDialog();
        Toast.makeText(this, R.string.toast_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void diaryUpdateFail(String error) {
        hideLoadingDialog();
        showSimpleMessage(error);
    }

    private void parseTime(String dateTimeline) {
        String outputDay = DateTimeNow.parseDateStringToLocalDateString(dateTimeline, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, getString(R.string.format_date_time));
        String outputHour = DateTimeNow.parseDateStringToLocalDateString(dateTimeline, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, getString(R.string.hh_mm));
        tvDateSpecificationDate.setText(outputDay);
        tvDateSpecificationTime.setText(outputHour);
    }

    private int dp2px(int dp) {
        float scaleValue = getResources().getDisplayMetrics().density;
        return (int) (dp * scaleValue + 0.5f);
    }

    private LinearLayout.LayoutParams getLayoutParams(int height) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

    private void setVisibleView(View view, boolean isVisibility) {
        if (isVisibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void disablePostView(TextView textView, boolean isVisible) {
        if (!isVisible) {
            textView.setText(R.string.post_none);
        }
    }

    private void setFertilizerLimit() {
        edNitrogen.setFilters(new InputFilter[]{new InputFilterFertilizer(1, 100)});
        edPhosphoric.setFilters(new InputFilter[]{new InputFilterFertilizer(1, 100)});
        edKali.setFilters(new InputFilter[]{new InputFilterFertilizer(1, 100)});
    }

}
