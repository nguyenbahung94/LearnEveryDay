package com.ekakashi.greenhouse.features.timeline_add_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;

public class TimelineAddDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgTargetCrop;
    private ImageView imgWorkType;
    private ImageView imgDisease;
    private ImageView imgPesticide;
    private ImageView imgFertilizer;
    private ImageView imgDate;

    private boolean isSelectTargetCrop = false;
    private boolean isSelectWorkType = false;
    private boolean isSelectDisease = false;
    private boolean isSelectPesticide = false;
    private boolean isSelectFertilizer = false;
    private boolean isSelectDate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_add_detail);

        addToolbar();
        addControls();
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.add_details), "");
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_done));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                onSelectedDetail();
            }
        });
    }

    private void onSelectedDetail() {
        TimelineAddDetailModel addDetailModel = new TimelineAddDetailModel();
        addDetailModel.setSelectTargetCrop(isSelectTargetCrop);
        addDetailModel.setSelectWorkType(isSelectWorkType);
        addDetailModel.setSelectDisease(isSelectDisease);
        addDetailModel.setSelectPesticide(isSelectPesticide);
        addDetailModel.setSelectFertilizer(isSelectFertilizer);
        addDetailModel.setSelectDate(isSelectDate);
        finish();
    }

    private void addControls() {
        imgTargetCrop = findViewById(R.id.imgTargetCrop);
        imgWorkType = findViewById(R.id.imgWorkType);
        imgDisease = findViewById(R.id.imgDisease);
        imgPesticide = findViewById(R.id.imgPesticide);
        imgFertilizer = findViewById(R.id.imgFertilizer);
        imgDate = findViewById(R.id.imgDateSpecification);

        findViewById(R.id.layoutTargetCrop).setOnClickListener(this);
        findViewById(R.id.layoutWorkType).setOnClickListener(this);
        findViewById(R.id.layoutDisease).setOnClickListener(this);
        findViewById(R.id.layoutPesticide).setOnClickListener(this);
        findViewById(R.id.layoutFertilizer).setOnClickListener(this);
        findViewById(R.id.layoutDateSpecification).setOnClickListener(this);

        Intent intent = getIntent();
        TimelineAddDetailModel addDetailModel = intent.getParcelableExtra(Utils.Constant.TIMELINE_DETAIL_MODEL);
        if (addDetailModel != null) {
            isSelectTargetCrop = addDetailModel.isSelectTargetCrop();
            isSelectWorkType = addDetailModel.isSelectWorkType();
            isSelectPesticide = addDetailModel.isSelectPesticide();
            isSelectDisease = addDetailModel.isSelectDisease();
            isSelectFertilizer = addDetailModel.isSelectFertilizer();
            isSelectDate = addDetailModel.isSelectDate();
        }
        setImageView(imgTargetCrop, isSelectTargetCrop);
        setImageView(imgWorkType, isSelectWorkType);
        setImageView(imgDisease, isSelectDisease);
        setImageView(imgPesticide, isSelectPesticide);
        setImageView(imgFertilizer, isSelectFertilizer);
        setImageView(imgDate, isSelectDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutTargetCrop:
                isSelectTargetCrop = !isSelectTargetCrop;
                setImageView(imgTargetCrop, isSelectTargetCrop);
                break;
            case R.id.layoutWorkType:
                isSelectWorkType = !isSelectWorkType;
                setImageView(imgWorkType, isSelectWorkType);
                break;
            case R.id.layoutDisease:
                isSelectDisease = !isSelectDisease;
                setImageView(imgDisease, isSelectDisease);
                break;
            case R.id.layoutPesticide:
                isSelectPesticide = !isSelectPesticide;
                setImageView(imgPesticide, isSelectPesticide);
                break;
            case R.id.layoutFertilizer:
                isSelectFertilizer = !isSelectFertilizer;
                setImageView(imgFertilizer, isSelectFertilizer);
                break;
            case R.id.layoutDateSpecification:
                isSelectDate = !isSelectDate;
                setImageView(imgDate, isSelectDate);
                break;
            default:
                break;
        }
    }

    private void setImageView(ImageView imageView, boolean isClick) {
        if (isClick) {
            imageView.setImageResource(R.drawable.ic_checked);
        } else {
            imageView.setImageResource(R.drawable.ic_uncheck);
        }
    }
}
