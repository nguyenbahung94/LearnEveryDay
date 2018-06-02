package com.ekakashi.greenhouse.common;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;


public class MyToolbar {
    private ToolbarListener toolbarListener;

    public Toolbar mToolbar;
    public ImageView imgToolbarLeft;
    public TextView tvToolbarLeft;

    public TextView tvToolbarCenter;
    public TextView tvToolbarCenterTop;
    public TextView tvToolbarCenterBottom;

    public ImageView imgToolbarRight;
    public TextView tvToolbarRight;

    public MyToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }

    public void setToolbarListener(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }

    public void setUpToolbarLeft(int type) {
        switch (type) {
            case Utils.Name.TOOLBAR_LEFT_ACCOUNT:
                imgToolbarLeft = mToolbar.findViewById(R.id.imgToolbarAccount);
                imgToolbarLeft.setVisibility(View.VISIBLE);
                imgToolbarLeft.setOnClickListener(onToolbarLeftClick);
                break;
            case Utils.Name.TOOLBAR_LEFT_BACK_BUTTON:
                imgToolbarLeft = mToolbar.findViewById(R.id.imgToolbarBack);
                imgToolbarLeft.setVisibility(View.VISIBLE);
                imgToolbarLeft.setOnClickListener(onToolbarLeftClick);
                break;
            case Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL:
                tvToolbarLeft = mToolbar.findViewById(R.id.tvToolbarLeft);
                tvToolbarLeft.setText(R.string.toolbar_cancel);
                tvToolbarLeft.setOnClickListener(onToolbarLeftClick);
                break;
            case Utils.Name.TOOLBAR_LEFT_TEXT_CLOSE:
                tvToolbarLeft = mToolbar.findViewById(R.id.tvToolbarLeft);
                tvToolbarLeft.setText(R.string.toolbar_close);
                tvToolbarLeft.setOnClickListener(onToolbarLeftClick);
                break;
            default:
                break;
        }
    }

    private View.OnClickListener onToolbarLeftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toolbarListener.onToolbarLeftListener();
        }
    };

    public void setUpTextCenter(int type, String textCenter, String textOther) {
        switch (type) {
            case Utils.Name.TOOLBAR_CENTER_TEXT_ONLY:
                break;
            case Utils.Name.TOOLBAR_CENTER_TEXT_TOP:
                tvToolbarCenterTop = mToolbar.findViewById(R.id.tvToolbarCenterTop);
                tvToolbarCenterTop.setText(textOther);
                tvToolbarCenterTop.setVisibility(View.VISIBLE);
                break;
            case Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM:
                tvToolbarCenterBottom = mToolbar.findViewById(R.id.tvToolbarCenterBottom);
                tvToolbarCenterBottom.setText(textOther);
                tvToolbarCenterBottom.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        tvToolbarCenter = mToolbar.findViewById(R.id.tvToolbarCenter);
        tvToolbarCenter.setText(textCenter);
    }

    public void setUpToolbarRight(int type, String text) {
        switch (type) {
            case Utils.Name.TOOLBAR_RIGHT_BUTTON_ADD:
                imgToolbarRight = mToolbar.findViewById(R.id.imgToolbarAdd);
                imgToolbarRight.setVisibility(View.VISIBLE);
                imgToolbarRight.setOnClickListener(onToolbarRightClick);
                break;
            case Utils.Name.TOOLBAR_RIGHT_BUTTON_INFO:
                imgToolbarRight = mToolbar.findViewById(R.id.imgToolbarInfo);
                imgToolbarRight.setVisibility(View.VISIBLE);
                imgToolbarRight.setOnClickListener(onToolbarRightClick);
                break;
            case Utils.Name.TOOLBAR_RIGHT_TEXT:
                tvToolbarRight = mToolbar.findViewById(R.id.tvToolbarRight);
                tvToolbarRight.setText(text);
                tvToolbarRight.setVisibility(text != null ? View.VISIBLE : View.GONE);
                tvToolbarRight.setOnClickListener(onToolbarRightClick);
                break;
            default:
                break;
        }
    }


    private View.OnClickListener onToolbarRightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toolbarListener.onToolbarRightListener();
        }
    };

    public void hideToolbarLine() {
        mToolbar.findViewById(R.id.toolbarLine).setVisibility(View.INVISIBLE);
    }

    public void setToolbarBackground(int color) {
        mToolbar.setBackgroundColor(color);
    }

    public interface ToolbarListener {

        void onToolbarLeftListener();

        void onToolbarRightListener();
    }
}
