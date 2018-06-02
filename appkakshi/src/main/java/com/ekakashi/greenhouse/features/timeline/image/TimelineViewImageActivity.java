package com.ekakashi.greenhouse.features.timeline.image;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline.adapters.PostViewImageAdapter;
import com.ekakashi.greenhouse.features.timeline.adapters.TimelineViewImageAdapter;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_post.model.ImageViewer;

import java.util.ArrayList;

public class TimelineViewImageActivity extends AppCompatActivity {

    private ViewPager vpImage;
    private TextView tvTimelineName, tvTimelineTime, tvTimelineField;
    private RelativeLayout layoutLocation;
    private ImageView imgDelete;

    private final int TIMELINE = 1;
    private final int POST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view_image);
        addControls();
        addEvents();
    }

    private void addEvents() {
        findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete();
            }
        });
    }

    private void showDialogDelete() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.alert)
                .content(R.string.image_delete_confirm)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteImage();
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    private void deleteImage() {
        Intent intent = new Intent();
        intent.putExtra(Utils.Name.DIARY_POSITION, vpImage.getCurrentItem());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void addControls() {
        vpImage = findViewById(R.id.vpImage);
        tvTimelineName = findViewById(R.id.tvTimelineName);
        tvTimelineTime = findViewById(R.id.tvTimelineTime);
        tvTimelineField = findViewById(R.id.tvTimelineField);
        layoutLocation = findViewById(R.id.layoutLocation);
        imgDelete = findViewById(R.id.imgDelete);

        Intent intent = getIntent();
        int type = intent.getIntExtra(Utils.Name.DIARY_TYPE, -1);

        if (type == TIMELINE) {
            Bundle bundle = intent.getBundleExtra("TimelineAdapter");
            TimelineResponse.ListTimeline object = (TimelineResponse.ListTimeline) bundle.getSerializable("ListTimeline");
            int position = bundle.getInt("position");
            imgDelete.setVisibility(View.GONE);

            if (object != null) {
                TimelineViewImageAdapter adapter = new TimelineViewImageAdapter(getSupportFragmentManager(), object.getDiary().getDiaryImageUrl());
                vpImage.setAdapter(adapter);
                vpImage.setCurrentItem(position);

                tvTimelineName.setText(object.getDiary().getUserName());
                tvTimelineTime.setText(DateTimeNow.compareTime(this,object.getUpdatedAt()));
                if (object.getFieldName() != null) {
                    layoutLocation.setVisibility(View.VISIBLE);
                    tvTimelineField.setText(object.getFieldName());
                }
            }
        } else if (type == POST) {
            ArrayList<ImageViewer> images = intent.getParcelableArrayListExtra(Utils.Name.DIARY_LIST_IMAGE);
            int position = intent.getIntExtra(Utils.Name.DIARY_POSITION, 0);

            if (images != null) {
                PostViewImageAdapter adapter = new PostViewImageAdapter(getSupportFragmentManager(), images);
                vpImage.setAdapter(adapter);
                vpImage.setCurrentItem(position);
            }

            tvTimelineName.setVisibility(View.GONE);
            tvTimelineTime.setVisibility(View.GONE);
            layoutLocation.setVisibility(View.GONE);
            imgDelete.setVisibility(View.VISIBLE);
        }

    }
}
