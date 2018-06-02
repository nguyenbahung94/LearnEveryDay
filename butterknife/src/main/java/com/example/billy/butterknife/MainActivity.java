package com.example.billy.butterknife;

import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvone)
    TextView tvOne;
    @BindView(R.id.imv)
    ImageView imv;
    @BindDrawable(R.drawable.image1)
    Drawable drawable1;
    @BindDrawable(R.drawable.image2)
    Drawable drawable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvOne.setText("hello");
        imv.setImageDrawable(drawable1);
        ButterKnife.apply(imv, View.ALPHA, 0.5f);

    }

    @OnClick(R.id.btnClick)
    void click() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        imv.setImageDrawable(drawable2);
    }

    @OnLongClick(R.id.btnClick)
    boolean clickview(View view) {
        ButterKnife.apply(imv, GONE);
        return true;
    }

    @OnClick({R.id.btnClick, R.id.imv, R.id.tvone})
    void getViewClickEvent(View view) {
        switch (view.getId()) {
            case R.id.imv:
                tvOne.setText("imv clicked");

                break;
            case R.id.tvone:
                ButterKnife.apply(imv, Custom,View.VISIBLE);
                tvOne.setText("tvone clicked");
                break;
        }
    }

    public static final ButterKnife.Action<View> GONE = new ButterKnife.Action<View>() {
        @Override
        public void apply(@NonNull View view, int index) {
            view.setVisibility(View.GONE);
        }
    };
    static final ButterKnife.Action<EditText> FOR_EMPTRY_PASSWORD = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(EditText editText, int index) {
            editText.setError("Password should not be blank");
        }
    };
    public static final ButterKnife.Setter<View,Integer>Custom=new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(@NonNull View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };

}
