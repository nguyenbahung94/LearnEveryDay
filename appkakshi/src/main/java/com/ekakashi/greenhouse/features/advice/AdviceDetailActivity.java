package com.ekakashi.greenhouse.features.advice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;

public class AdviceDetailActivity extends BaseActivity {

    private TextView tvContent;
    private TextView tvTitle;
    private TextView tvAdviceUrl;
    private ImageView imgAdvice;
    private LinearLayout linAdviceLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_detail);

        addControls();
    }

    private void addControls() {
        tvContent = findViewById(R.id.tvContent);
        tvTitle = findViewById(R.id.tvTitle);
        tvAdviceUrl = findViewById(R.id.tvAdviceUrl);
        imgAdvice = findViewById(R.id.imgAdvice);
        linAdviceLink = findViewById(R.id.linAdviceLink);

        Intent intent = getIntent();
        TimelineResponse.Advice advice = (TimelineResponse.Advice) intent.getSerializableExtra(Utils.Name.ADVICE_ADVICE);
        String title = intent.getStringExtra(Utils.Name.ADVICE_TITLE);

        if (advice != null) {
            addToolbar(advice.getRecipeName(), advice.getCurrentStageName());
        } else {
            addToolbar("", "");
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        final AdviceDescriptionObject descriptionObject = App.descriptionObject;
        if (descriptionObject != null) {
            if (!TextUtils.isEmpty(descriptionObject.getContent())) {
                tvContent.setText(Html.fromHtml(descriptionObject.getContent()));
            }

            if (!TextUtils.isEmpty(descriptionObject.getImages())) {
                byte[] decodedString = Base64.decode(descriptionObject.getImages(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgAdvice.setVisibility(View.VISIBLE);
                imgAdvice.setImageBitmap(decodedByte);
            } else {
                imgAdvice.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(descriptionObject.getUrl())) {
                linAdviceLink.setVisibility(View.VISIBLE);
                tvAdviceUrl.setText(descriptionObject.getUrl());

                tvAdviceUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(descriptionObject.getUrl()));
                        startActivity(browserIntent);
                    }
                });
            }
        }
    }

    private void addToolbar(String recipeName, String currentStageName) {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, recipeName, currentStageName);
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

    @Override
    protected void onDestroy() {
        App.onDestroyAdvice();
        super.onDestroy();
    }
}
