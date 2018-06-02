package com.ekakashi.greenhouse.features.timeline.timeline_detail;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline.image.TimelineViewImageActivity;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_post.EKTimelinePostActivity;

import java.util.ArrayList;

public class TimelineDetailActivity extends BaseActivity implements TimelineDetailInterface.View {

    private TextView tvName;
    private TextView tvTime;
    private TextView tvField;
    private TextView tvContent;
    private TextView tvTitle;
    private TextView tvImageMore;
    private TextView tvTargetCrop;
    private TextView tvWorkType;
    private TextView tvDisease;
    private TextView tvPesticide;
    private TextView tvFertilizer;
    private TextView tvDateSpecification;

    private TableLayout tableData;
    private TableRow rowTargetCrop;
    private TableRow rowWorkType;
    private TableRow rowDisease;
    private TableRow rowPesticide;
    private TableRow rowFertilizer;
    private TableRow rowDateSpecification;

    private ImageView imgAvatar;
    private ImageView imgDetailToolbarInfo;

    private ImageView imgContent1;
    private ImageView imgContent21;
    private ImageView imgContent22;
    private ImageView imgContent31;
    private ImageView imgContent32;
    private ImageView imgContent33;

    private LinearLayout layoutImage2;
    private LinearLayout layoutImage3;
    private LinearLayout linContainImages;
    private RelativeLayout relContent33;
    private RelativeLayout layoutLocation;
    private View viewImage33Black;

    private TimelineResponse.ListTimeline timeline;
    private TimelineDetailPresenter mPresenter;

    private int REQUEST_CODE_POST_FROM_READ_MORE = 101;

    private int ID_NONE = 1;
    private String timelineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_detail);

        addControls();
        addEvents();
        addToolbar();
        initObject();
        initPresenter();
    }

    private void addEvents() {
        findViewById(R.id.imgDetailToolbarInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditDiary();
            }
        });
       /* imgDetailToolbarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditDiary();
            }
        });*/
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.title_timeline), "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
    }

    private void initObject() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Utils.Name.DIARY_DETAIL);
        timeline = (TimelineResponse.ListTimeline) bundle.getSerializable(Utils.Name.DIARY_ITEM);
        if (timeline != null) {
            timelineId = timeline.getTimelineId();
            if (timeline.getTimelineType() == Utils.Constant.TIMELINE_TYPE_DIARY) {
                final TimelineResponse.Diary diary = timeline.getDiary();
                if (diary != null) {
                    Glide.with(this).load(diary.getUserImageUrl()).placeholder(R.drawable.ic_user_default)
                            .error(R.drawable.ic_user_default).into(imgAvatar);
                    if (!TextUtils.isEmpty(diary.getNickName())) {
                        tvName.setText(diary.getNickName());
                    } else {
                        tvName.setText(diary.getUserName());
                    }

                    if (diary.getDiaryImageUrl() != null) {
                        //TODO fix bug
                        showImage(diary.getDiaryImageUrl());
                    } else {
                        imgContent1.setVisibility(View.GONE);
                        layoutImage2.setVisibility(View.GONE);
                        layoutImage3.setVisibility(View.GONE);
                        imgContent31.setVisibility(View.GONE);
                        imgContent32.setVisibility(View.GONE);
                        imgContent33.setVisibility(View.GONE);
                    }

                    //If this diary is user's own
                    if (Prefs.getInstance(this).getUserId() == diary.getUserId()) {
                        imgDetailToolbarInfo.setVisibility(View.VISIBLE);
                    } else {
                        imgDetailToolbarInfo.setVisibility(View.GONE);
                    }

                    //Show data
                /*Target Crop:
                Work Type:
                Disease:
                Pesticide:
                Fertilizer:
                */

                    tableData.setVisibility(View.VISIBLE);

                    if (diary.getTargetCrop() == null || TextUtils.isEmpty(diary.getTargetCrop())) {
                        rowTargetCrop.setVisibility(View.GONE);
                    } else {
                        rowTargetCrop.setVisibility(View.VISIBLE);
                        tvTargetCrop.setText(diary.getTargetCrop());
                    }

                    if (diary.getWorkType() == null || TextUtils.isEmpty(diary.getWorkType().getName())) {
                        rowWorkType.setVisibility(View.GONE);
                    } else {
                        rowWorkType.setVisibility(View.VISIBLE);
                        tvWorkType.setText(diary.getWorkType().getName());
                    }

                    if (diary.getDiseaseType() == null || TextUtils.isEmpty(diary.getDiseaseType())) {
                        rowDisease.setVisibility(View.GONE);
                    } else {
                        rowDisease.setVisibility(View.VISIBLE);
                        tvDisease.setText(diary.getDiseaseType());
                    }

                    if (diary.getPesticideType() == null || TextUtils.isEmpty(diary.getPesticideType())) {
                        rowPesticide.setVisibility(View.GONE);
                    } else {
                        rowPesticide.setVisibility(View.VISIBLE);
                        tvPesticide.setText(diary.getPesticideType());
                    }

                    if (diary.getFertilizerType() == null || TextUtils.isEmpty(diary.getFertilizerType()) || diary.getFertilizerId().equals(String.valueOf(ID_NONE))) {
                        rowFertilizer.setVisibility(View.GONE);
                    } else {
                        rowFertilizer.setVisibility(View.VISIBLE);
                        String fertilizerString = "N" + diary.getNitrogen() + ", P" + diary.getPhosphoric() + ", K" + diary.getPotossium();
                        tvFertilizer.setText(fertilizerString);
                    }
                }

                if (diary.getIssuedDate() == null || TextUtils.isEmpty(diary.getIssuedDate())) {
                    rowDateSpecification.setVisibility(View.GONE);
                } else {
                    rowDateSpecification.setVisibility(View.VISIBLE);
                    tvDateSpecification.setText(DateTimeNow.parseDateStringToLocalDateString(diary.getIssuedDate(),
                            DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, this.getString(R.string.format_date_and_time)));
                }

            } else {
                Glide.with(this).load("").placeholder(R.drawable.ic_user_default)
                        .error(R.drawable.ic_user_default).into(imgAvatar);
                //If Diary is null, image is invisible
                linContainImages.setVisibility(View.GONE);
                imgContent1.setVisibility(View.GONE);
                layoutImage2.setVisibility(View.GONE);
                layoutImage3.setVisibility(View.GONE);
                imgContent31.setVisibility(View.GONE);
                imgContent32.setVisibility(View.GONE);
                imgContent33.setVisibility(View.GONE);
                tableData.setVisibility(View.GONE);
            }

            //If this diary has field
            if (TextUtils.isEmpty(timeline.getFieldName())) {
                layoutLocation.setVisibility(View.GONE);
                tvField.setText("");
            } else {
                tvField.setText(timeline.getFieldName());
                layoutLocation.setVisibility(View.VISIBLE);
            }


            tvTime.setText(DateTimeNow.compareTime(this, timeline.getUpdatedAt()));
            tvContent.setText(timeline.getDescription());

            //User post don't have Title
            tvTitle.setVisibility(View.GONE);

        } else {
            imgContent1.setVisibility(View.GONE);
            layoutImage2.setVisibility(View.GONE);
            layoutImage3.setVisibility(View.GONE);
            imgContent31.setVisibility(View.GONE);
            imgContent32.setVisibility(View.GONE);
            imgContent33.setVisibility(View.GONE);
            layoutLocation.setVisibility(View.GONE);
            tableData.setVisibility(View.GONE);
            //holder.imgToolbarInfo.setVisibility(View.GONE);
            //
            tvName.setText(timeline.getFieldName());
            tvTime.setText(DateTimeNow.compareTime(this, timeline.getDateTimeline()));
            tvContent.setText(timeline.getDescription());
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(timeline.getTitle());

            if (timeline.getTimelineType() == Utils.Constant.TIMELINE_TYPE_NOTIFICATION) {
                Glide.with(this).load(R.drawable.ic_timeline_notification).into(imgAvatar);
            } else if (timeline.getTimelineType() == Utils.Constant.TIMELINE_TYPE_ADVICE) {
                Glide.with(this).load(R.drawable.ic_timeline_advice).into(imgAvatar);
            } else {
                //remote control.
                Glide.with(this).load(R.drawable.ic_timeline_notification).into(imgAvatar);
            }
        }
    }

    private void showImage(ArrayList<String> listImageUrl) {
        if (listImageUrl.size() == 0) {
            //gone view which content image
            linContainImages.setVisibility(View.GONE);
        } else {
            linContainImages.setVisibility(View.VISIBLE);

            switch (listImageUrl.size()) {
                case 1:
                    imgContent1.setVisibility(View.VISIBLE);
                    layoutImage2.setVisibility(View.GONE);
                    layoutImage3.setVisibility(View.GONE);
                    imgContent31.setVisibility(View.GONE);
                    imgContent32.setVisibility(View.GONE);
                    imgContent33.setVisibility(View.GONE);
                    relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(this).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent1);
                    imgContent1.setLayoutParams(getLayoutParams(dp2px(250)));

                    onImageClick(imgContent1, 0, timeline);

                    break;
                case 2:
                    imgContent1.setVisibility(View.GONE);
                    layoutImage2.setVisibility(View.VISIBLE);
                    layoutImage3.setVisibility(View.GONE);
                    imgContent31.setVisibility(View.GONE);
                    imgContent32.setVisibility(View.GONE);
                    imgContent33.setVisibility(View.GONE);
                    relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(this).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent21);
                    Glide.with(this).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent22);

                    onImageClick(imgContent21, 0, timeline);
                    onImageClick(imgContent22, 1, timeline);
                    break;
                case 3:
                    imgContent1.setVisibility(View.VISIBLE);
                    layoutImage2.setVisibility(View.GONE);
                    layoutImage3.setVisibility(View.VISIBLE);
                    imgContent31.setVisibility(View.VISIBLE);
                    imgContent32.setVisibility(View.VISIBLE);
                    imgContent33.setVisibility(View.GONE);
                    relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(this).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent1);
                    imgContent1.setLayoutParams(getLayoutParams(dp2px(150)));
                    layoutImage3.setLayoutParams(getLayoutParams(dp2px(150)));

                    Glide.with(this).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent31);
                    Glide.with(this).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent32);

                    onImageClick(imgContent1, 0, timeline);
                    onImageClick(imgContent31, 1, timeline);
                    onImageClick(imgContent32, 2, timeline);
                    break;
                case 4:
                    imgContent1.setVisibility(View.VISIBLE);
                    layoutImage2.setVisibility(View.GONE);
                    layoutImage3.setVisibility(View.VISIBLE);
                    imgContent31.setVisibility(View.VISIBLE);
                    imgContent32.setVisibility(View.VISIBLE);
                    imgContent33.setVisibility(View.VISIBLE);
                    relContent33.setVisibility(View.VISIBLE);
                    tvImageMore.setVisibility(View.GONE);
                    viewImage33Black.setVisibility(View.GONE);
                    //
                    Glide.with(this).load(listImageUrl.get(0)).error(R.drawable.tomato_placeholder).into(imgContent1);
                    imgContent1.setLayoutParams(getLayoutParams(dp2px(150)));

                    Glide.with(this).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent31);
                    Glide.with(this).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent32);
                    Glide.with(this).load(listImageUrl.get(3)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent33);

                    onImageClick(imgContent1, 0, timeline);
                    onImageClick(imgContent31, 1, timeline);
                    onImageClick(imgContent32, 2, timeline);
                    onImageClick(imgContent33, 3, timeline);
                    break;
                default:
                    imgContent1.setVisibility(View.GONE);
                    layoutImage2.setVisibility(View.VISIBLE);
                    layoutImage3.setVisibility(View.VISIBLE);
                    imgContent31.setVisibility(View.VISIBLE);
                    imgContent32.setVisibility(View.VISIBLE);
                    imgContent33.setVisibility(View.VISIBLE);
                    relContent33.setVisibility(View.VISIBLE);

                    if (listImageUrl.size() > 5) {
                        tvImageMore.setVisibility(View.VISIBLE);
                        viewImage33Black.setVisibility(View.VISIBLE);

                        String imageMore = "+" + (listImageUrl.size() - 5);
                        tvImageMore.setText(imageMore);
                    } else {
                        tvImageMore.setVisibility(View.GONE);
                        viewImage33Black.setVisibility(View.GONE);
                    }

                    //
                    Glide.with(this).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent21);
                    Glide.with(this).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent22);
                    Glide.with(this).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent31);
                    Glide.with(this).load(listImageUrl.get(3)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent32);
                    Glide.with(this).load(listImageUrl.get(4)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgContent33);

                    onImageClick(imgContent21, 0, timeline);
                    onImageClick(imgContent22, 1, timeline);
                    onImageClick(imgContent31, 2, timeline);
                    onImageClick(imgContent32, 3, timeline);
                    onImageClick(imgContent33, 4, timeline);
                    break;
            }
        }
    }

    private void onImageClick(ImageView imageView, final int position, final TimelineResponse.ListTimeline timeline) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ListTimeline", timeline);
                bundle.putInt("position", position);

                Intent intent = new Intent(TimelineDetailActivity.this, TimelineViewImageActivity.class);
                intent.putExtra("TYPE", 1);//1 is Timeline, 2 is Post/Edit
                intent.putExtra("TimelineAdapter", bundle);
                startActivity(intent);
            }
        });
    }

    private void showDialogEditDiary() {
        final MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_timeline, true)
                .cancelable(false)
                .build();
        materialDialog.show();

        TextView tvEdit = (TextView) materialDialog.findViewById(R.id.tvEdit);
        TextView tvDelete = (TextView) materialDialog.findViewById(R.id.tvDelete);
        TextView tvCancel = (TextView) materialDialog.findViewById(R.id.tvCancel);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDiary(timeline);
                materialDialog.dismiss();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                showDialogDelete(timelineId);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });

    }

    private void showDialogDelete(final String timelineId) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.alert)
                .content(R.string.diary_delete_confirm)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        mMoreInfo.onDiaryDelete(timelineId, position);
                        mPresenter.deleteDiary(timelineId);
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();

    }

    private LinearLayout.LayoutParams getLayoutParams(int height) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

    private int dp2px(int dps) {
        float scaleValue = getResources().getDisplayMetrics().density;
        return (int) (dps * scaleValue + 0.5f);
    }

    private void addControls() {
        tvName = findViewById(R.id.tvTimelineName);
        tvTime = findViewById(R.id.tvTimelineTime);
        tvContent = findViewById(R.id.tvTimelineContent);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.tv_size_14sp));
        tvField = findViewById(R.id.tvTimelineField);
        imgAvatar = findViewById(R.id.imgAvatar);
        tvImageMore = findViewById(R.id.tvImageMore);
        tvTitle = findViewById(R.id.tvTitle);
        tvTargetCrop = findViewById(R.id.tvTargetCrop);
        tvWorkType = findViewById(R.id.tvWorkType);
        tvDisease = findViewById(R.id.tvDisease);
        tvPesticide = findViewById(R.id.tvPesticide);
        tvFertilizer = findViewById(R.id.tvFertilizer);
        tvDateSpecification = findViewById(R.id.tvDateSpecification);

        tableData = findViewById(R.id.tableData);
        rowTargetCrop = findViewById(R.id.rowTargetCrop);
        rowWorkType = findViewById(R.id.rowWorkType);
        rowDisease = findViewById(R.id.rowDisease);
        rowPesticide = findViewById(R.id.rowPesticide);
        rowFertilizer = findViewById(R.id.rowFertilizer);
        rowDateSpecification = findViewById(R.id.rowDateSpecification);

        imgDetailToolbarInfo = findViewById(R.id.imgDetailToolbarInfo);
        imgContent1 = findViewById(R.id.imgContent1);
        imgContent21 = findViewById(R.id.imgContent21);
        imgContent22 = findViewById(R.id.imgContent22);
        imgContent31 = findViewById(R.id.imgContent31);
        imgContent32 = findViewById(R.id.imgContent32);
        imgContent33 = findViewById(R.id.imgContent33);

        layoutImage2 = findViewById(R.id.layoutImage2);
        layoutImage3 = findViewById(R.id.layoutImage3);
        linContainImages = findViewById(R.id.linContainImages);
        relContent33 = findViewById(R.id.relContent33);
        viewImage33Black = findViewById(R.id.viewImage33Black);
        layoutLocation = findViewById(R.id.layoutLocation);
    }

    @Override
    public void initPresenter() {
        mPresenter = new TimelineDetailPresenter(this);
    }

    @Override
    public void editDiary(TimelineResponse.ListTimeline timeline) {
        Intent intent = new Intent(this, EKTimelinePostActivity.class);
        intent.putExtra("TimelineObject", timeline);
        intent.putExtra(Utils.Constant.TIMELINE_POST_EDIT, 1);
        startActivityForResult(intent, REQUEST_CODE_POST_FROM_READ_MORE);
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDeleteFail() {
        Toast.makeText(this, getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_POST_FROM_READ_MORE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
