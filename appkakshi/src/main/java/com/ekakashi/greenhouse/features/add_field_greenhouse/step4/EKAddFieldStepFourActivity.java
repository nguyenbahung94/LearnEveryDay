package com.ekakashi.greenhouse.features.add_field_greenhouse.step4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.common.stepcreatefield.ThreeStepCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.map.CustomLatLngObject;
import com.ekakashi.greenhouse.map.CustomScrollView;
import com.ekakashi.greenhouse.map.WorkaroundMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class EKAddFieldStepFourActivity extends BaseActivity implements View.OnClickListener, WorkaroundMapFragment.OnTouchListener,
        OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.CancelableCallback, EKAddFieldStepFourInterface.View {
    private Toolbar toolbar;
    private GoogleMap mMap;
    private List<CustomLatLngObject> listPolygon;

    private LinearLayout llContainRecipes;
    private List<ResponseDetailField.Polygon> myLatLongs = new ArrayList<>();
    private ObjectCreateField createField;
    //    private TextView tvShowPlaceType;
//    private TextView tvShowPlaceName;
    private EditText edtPlaceName;
    private ImageView imvDeletePlace;
    private Button btnFinish;
    private ThreeStepCreateField fourStepCreateField;
    private EKAddFieldStepFourInterface.Presenter mPresenter;
    private CustomScrollView scrollView;
    private TextView tvField, tvGreenhouse;


    public static void startActivity(Context context, ObjectCreateField createField) {
        Intent intent = new Intent(context, EKAddFieldStepFourActivity.class);
        intent.putExtra(Utils.Name.CREATE_FILED, createField);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field_greenhouse_step3);

        mPresenter = new EKAddFieldStepFourPresenter(this);

        fourStepCreateField = new ThreeStepCreateField((RelativeLayout) findViewById(R.id.layoutContainFourStep), this);
        fourStepCreateField.stepTwo();
        scrollView = findViewById(R.id.scrollView);
        edtPlaceName = findViewById(R.id.edt_Place_Name);
        imvDeletePlace = findViewById(R.id.imv_Delete_Place);
        btnFinish = findViewById(R.id.btnFinish);
        tvField = findViewById(R.id.tvField);
        tvGreenhouse = findViewById(R.id.tvGreenhouse);
//        tvShowPlaceName = findViewById(R.id.tvShowPlaceName);
//        tvShowPlaceType = findViewById(R.id.tvShowPlaceType);
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.CREATE_FILED);
            if (createField != null) {
                myLatLongs.clear();
                createField.setPlaceType("0");
                listPolygon = createField.getListPolygon();
                myLatLongs.add(new ResponseDetailField.Polygon(listPolygon.get(0).getLatLng().latitude, listPolygon.get(0).getLatLng().longitude));

                createField.setPolygon(myLatLongs);
//                tvShowPlaceType.setText(createField.getPlaceType());
//                tvShowPlaceName.setText(createField.getPlaceName());
            }
        }
        btnFinish.setOnClickListener(this);
        btnFinish.setEnabled(false);
        tvField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFieldClick();
            }
        });
        tvGreenhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGreenhouseClick();
            }
        });
        edtPlaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (imvDeletePlace.getVisibility() == View.GONE && !edtPlaceName.getText().toString().trim().isEmpty()) {
                    imvDeletePlace.setVisibility(View.VISIBLE);
                    btnFinish.setEnabled(true);
                    setButtonBlue();

                } else {
                    if (edtPlaceName.getText().toString().isEmpty()) {
                        imvDeletePlace.setVisibility(View.GONE);
                        btnFinish.setEnabled(false);
                        btnFinish.setBackgroundResource(R.drawable.bg_disable_btn);
                        setButtonDefault();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imvDeletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPlaceName.getText().toString().trim().isEmpty()) {
                    edtPlaceName.setText("");
                    imvDeletePlace.setVisibility(View.GONE);
                }
            }
        });


        toolbar = findViewById(R.id.toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_google_map);
        mapFragment.getMapAsync(this);
        (toolbar.findViewById(R.id.tvToolbarRight)).setOnClickListener(this);
        toolbar.findViewById(R.id.imgBack).setOnClickListener(this);
        ((TextView) toolbar.findViewById(R.id.tvTitleMain)).setText(getString(R.string.txt_add_field_greenhouse));
        llContainRecipes = findViewById(R.id.llContainRecipes);
        scrollView = findViewById(R.id.scrollView);
        findViewById(R.id.llMain).setOnClickListener(this);
        findViewById(R.id.fm_google_map).setOnClickListener(this);
        addElementRecipe();

    }

    public void setButtonDefault() {
        btnFinish.setBackgroundColor(getResources().getColor(R.color.bg_disable_btn));
    }

    public void setButtonBlue() {
        btnFinish.setBackgroundColor(getResources().getColor(R.color.text_color_blue));
    }

    private void drawPolygon() {
        if (myLatLongs != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatLongs.get(0).getLat(),
                    myLatLongs.get(0).getLng()), 16));
            mMap.addMarker(new MarkerOptions().position(new LatLng(myLatLongs.get(0).getLat(), myLatLongs.get(0).getLng())).icon(Utils.getFromDrawable(EKAddFieldStepFourActivity.this, R.drawable.new_icon_location_svg)).title(Utils.getAddressFromLatLong(getApplicationContext(),
                    myLatLongs.get(0).getLat(), myLatLongs.get(0).getLng())));
        }
        String address = createField.getAddress();
        String[] title = address.split(",");
        String firstTitle = "";
        StringBuilder secondTitle = new StringBuilder();
        for (int i = 0; i < title.length; i++) {
            if (i == 0) {
                firstTitle = title[i];
            } else if (i < title.length - 1) {
                secondTitle.append((title[i])).append(",");
            } else {
                secondTitle.append((title[i])).append(".");
            }
        }
        ((TextView) findViewById(R.id.txt_title_map)).setText(firstTitle);
        ((TextView) findViewById(R.id.txt_sub_title_map)).setText(secondTitle);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMain:
                scrollView.setScrolling(true);
                break;
            case R.id.fm_google_map:
                scrollView.setScrolling(false);
                break;
            case R.id.tvToolbarRight:
                EKMainFieldActivity.startActivity(EKAddFieldStepFourActivity.this);
                break;
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.btnFinish:
                if (edtPlaceName.getText().toString().isEmpty()) {
                    Toast.makeText(this, R.string.require_place_name, Toast.LENGTH_SHORT).show();
                } else {
                    createField.setPlaceName(edtPlaceName.getText().toString());
                    if (isNetworkOffline()) {
                        return;
                    }
                    showLoadingDialog(getString(R.string.message_please_wait));
                    findViewById(R.id.btnFinish).setEnabled(false);
                    mPresenter.addField(createField);
                    //TODO Next phase will comment cloneRecipe and uncomment addField method
//                    mPresenter.addField(createField);
                }

                break;

            default:
                break;
        }

    }

    private void addElementRecipe() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.item_element_recipe, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 15, 0, 15);
            view.setLayoutParams(lp);
            TextView tvName = view.findViewById(R.id.tvNameRecipe);
            tvName.setText(createField.getRecipeName());
            TextView tvStage = view.findViewById(R.id.tvStateOfRecipes);
            tvStage.setText(createField.getRecipeDescription());
            ImageView imageView = view.findViewById(R.id.imvRecipe);
            Glide.with(EKAddFieldStepFourActivity.this).load(createField.getRecipeImage()).into(imageView);
            llContainRecipes.addView(view);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(false);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        // If we don't use onMapLoadedCallback: java.lang.IllegalStateException: Error using newLatLngBounds(LatLngBounds, int):
        // Map size can't be 0. Most likely, layout has not yet occurred for the map view.
        // Either wait until layout has occurred or use newLatLngBounds(LatLngBounds, int, int, int) which allows you to specify the map's dimensions.
        //mMap.setOnMapLoadedCallback(this);
        onMapLoaded();

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.fm_google_map)).setListener(this);
    }

    @Override
    public void onTouch() {
        scrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onMapLoaded() {
        drawPolygon();
    }

    @Override
    public void success() {
        Toast.makeText(getApplicationContext(), R.string.toast_success, Toast.LENGTH_SHORT).show();
        hideLoadingDialog();
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                EKMainFieldActivity.startActivity(EKAddFieldStepFourActivity.this);
            }
        }.start();

    }

    @Override
    public void failed(String failed) {
        hideLoadingDialog();
        Toast.makeText(getApplicationContext(), R.string.txt_add_field, Toast.LENGTH_SHORT).show();
        findViewById(R.id.btnFinish).setEnabled(true);
    }


    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    private void btnFieldClick() {
        //type = Utils.Name.ONE;
        createField.setPlaceType("0");
        tvField.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tv_white));
        tvField.setBackgroundResource(R.drawable.button_border_corner_green);
        tvGreenhouse.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bg_green_btn));
        tvGreenhouse.setBackgroundResource(R.drawable.button_border_corner_white);
    }

    private void btnGreenhouseClick() {
        //  type = Utils.Name.TWO;
        createField.setPlaceType("1");
        tvGreenhouse.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tv_white));
        tvGreenhouse.setBackgroundResource(R.drawable.button_border_corner_green);
        tvField.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bg_green_btn));
        tvField.setBackgroundResource(R.drawable.button_border_corner_white);
    }
}
