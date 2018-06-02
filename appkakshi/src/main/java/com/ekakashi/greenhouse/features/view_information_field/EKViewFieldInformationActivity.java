package com.ekakashi.greenhouse.features.view_information_field;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.RoleOfUser;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.features.edit_place_name.EKEditNameActivity;
import com.ekakashi.greenhouse.features.edit_place_polygon.EKEditPlaceActivity;
import com.ekakashi.greenhouse.features.member_list.MemberListActivity;
import com.ekakashi.greenhouse.features.recipe.edit_recipe.EditRecipeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class EKViewFieldInformationActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.CancelableCallback, EKViewFieldInformationInterface.View {
    private static final int EDIT_RECIPES_CODE = 12;
    private static final int EDIT_FILED_REQUEST_CODE = 222;
    private static final int EDIT_NAME_FILED_REQUEST_CODE = 223;
    private TextView tvField;
    private TextView tvGreenhouse;
    private TextView tvCountRecipes;
    private String type = "";
    private EditText edtPlaceName;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private RelativeLayout layoutRecipe;
    private List<LatLng> listPolygon = new ArrayList<>();
    private ObjectCreateField createField;
    private MaterialDialog mLoadingDialog;
    private ObjectCreateField createFieldResult;
    private EKViewFieldInformationPresenter mPresenter;
    private int state = 0;
    private boolean clickChangeRecipe = false;
    private AppCompatImageView appCompatImageView;
    private BitmapDescriptor pinLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_field_information);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RoleOfUser.Field.DeleteField(this)) {
            findViewById(R.id.btnDelete).setVisibility(View.GONE);
        } else findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
    }

    private void initView() {
        listPolygon.clear();
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.EDIT_FILED);
            tvCountRecipes = findViewById(R.id.tvCountRecipes);
            tvCountRecipes.setText(String.valueOf(createField.getCountRecipe()));
            ((TextView) findViewById(R.id.tvCountMembers)).setText(String.valueOf(createField.getCountMember()));
            ((TextView) findViewById(R.id.tvCountDevices)).setText(String.valueOf(createField.getCountDevice()));
            ((TextView) findViewById(R.id.tvTapToChange)).setText(createField.getPlaceName());
            ((TextView) findViewById(R.id.tvTitleMain)).setText(R.string.txt_view_information);
            List<ResponseDetailField.Polygon> list = createField.getPolygon();
            if (list != null && list.size() != 0) {
                listPolygon.add(new LatLng(list.get(0).getLat(), list.get(0).getLng()));
            }
            int type = Integer.parseInt(createField.getPlaceType());
            if (type == 0) {
                ((TextView) findViewById(R.id.tvShowPlaceType)).setText(getString(R.string.txt_field));
            } else {
                ((TextView) findViewById(R.id.tvShowPlaceType)).setText(getString(R.string.txt_greenhouse));
            }
            ((TextView) findViewById(R.id.tvShowPlaceName)).setText(createField.getPlaceName());
        }
        findViewById(R.id.llEditPlaceName).setOnClickListener(this);
        mPresenter = new EKViewFieldInformationPresenter(this);
        layoutRecipe = findViewById(R.id.layoutRecipe);
        layoutRecipe.setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_google_map);
        mapFragment.getMapAsync(this);
        findViewById(R.id.tvEdtPlace).setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.imgBack).setOnClickListener(this);
        appCompatImageView = toolbar.findViewById(R.id.imgBack);
        appCompatImageView.setImageResource(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.findViewById(R.id.imgBack).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.imvRight).setVisibility(View.GONE);
        toolbar.findViewById(R.id.imvTapToChange).setVisibility(View.GONE);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.llMembers).setOnClickListener(this);
        findViewById(R.id.llDataSource).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intentNew = new Intent();
        intentNew.putExtra(Utils.Name.EDIT_FILED, createField);
        setResult(RESULT_OK, intentNew);
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMembers:
                //check role here
                if (checkPermissionRoleUser()) {
                    return;
                }
                Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
                Intent memberIntent = new Intent(this, MemberListActivity.class);
                memberIntent.putExtra(Utils.Constant.EK_FIELDS_ID, createField.getIdField());
                memberIntent.putExtra(Utils.Constant.EK_FIELDS_NAME, createField.getPlaceName());
                startActivity(memberIntent);
                break;
            case R.id.btnDelete:
                if (isNetworkOffline()) {
                    return;
                }
                //check role here
                if (checkPermissionRoleUser()) {
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mPresenter.deleteField(createField.getIdField());
                    //  showCustomDialogEvent();
                }
                break;
            case R.id.llEditPlaceName:
                //check role here
                if (checkPermissionRoleUser()) {
                    Intent intentName = new Intent(EKViewFieldInformationActivity.this, EKEditNameActivity.class);
                    intentName.putExtra(Utils.Name.EDIT_NAME, createField);
                    startActivityForResult(intentName, EDIT_NAME_FILED_REQUEST_CODE);


                    break;
                }
            case R.id.tvToolbarRight:
                break;
            case R.id.imgBack:
                Intent intentNew = new Intent();
                intentNew.putExtra(Utils.Name.OBJECT_RESULT, createField);
                setResult(Activity.RESULT_OK, intentNew);
                onBackPressed();
                break;

            case R.id.tvEdtPlace:
                //check role here
                if (checkPermissionRoleUser()) {
                    Intent intent = new Intent(EKViewFieldInformationActivity.this, EKEditPlaceActivity.class);
                    intent.putExtra(Utils.Name.EDIT_FILED, createField);
                    startActivityForResult(intent, EDIT_FILED_REQUEST_CODE);
                }
                break;

            case R.id.layoutRecipe:
                //check role here
                if (checkPermissionRoleUser())

                {
                    Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
                    onRecipePressed();
                } else return;
                break;
            case R.id.llDataSource:
                break;

            default:
                break;
        }
    }

    private void showCustomDialogEvent() {
        new MaterialDialog.Builder(this)
                .content(R.string.txt_dialog_delete)
                .cancelable(false)
                .neutralText(R.string.toolbar_cancel).neutralColor(Utils.getColor(this, R.color.bg_green_btn))
                .positiveText(R.string.txt_ok).positiveColor(Utils.getColor(this, R.color.bg_green_btn))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showLoadingDialog(getString(R.string.message_please_wait));
                        mPresenter.deleteField(createField.getIdField());
                    }
                })
                .show();
    }

    private void onRecipePressed() {
        clickChangeRecipe = true;
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra(Utils.Name.EDIT_FILED, createField);
        startActivityForResult(intent, EDIT_RECIPES_CODE);
    }

    private Boolean checkPermissionRoleUser() {
        return RoleOfUser.Field.EditField(this);
    }


    private void drawPolygon() {
        mMap.clear();
        if (listPolygon != null && listPolygon.size() != 0) {
            pinLocation = Utils.getFromDrawable(this, R.drawable.new_icon_location_svg);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(listPolygon.get(0)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listPolygon.get(0), 16));
            // mMap.addMarker(new MarkerOptions().position(listPolygon.get(0)).icon(pinLocation));
            String address = createField.getAddress();
            if (address != null) {
                String[] title = address.split(",");
                String firstTitle = "";
                StringBuilder secondTitle = new StringBuilder();
                for (int i = 0; i < title.length; i++) {
                    if (i == 0) {
                        firstTitle = title[i];
                    } else if (0 < i && i < title.length - 1) {
                        secondTitle.append((title[i])).append(",");
                    } else {
                        secondTitle.append((title[i])).append(".");
                    }
                }
                ((TextView) findViewById(R.id.txt_title_map)).setText(firstTitle);
                ((TextView) findViewById(R.id.txt_sub_title_map)).setText(secondTitle);

            }

        }


    }

    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = Utils.createProgressBar(this, message);
        }
        mLoadingDialog.show();
    }

    /**
     * Hide loading dialog
     */
    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        // If we don't use onMapLoadedCallback: java.lang.IllegalStateException: Error using newLatLngBounds(LatLngBounds, int):
        // Map size can't be 0. Most likely, layout has not yet occurred for the map view.
        // Either wait until layout has occurred or use newLatLngBounds(LatLngBounds, int, int, int) which allows you to specify the map's dimensions.
        //mMap.setOnMapLoadedCallback(this);
        onMapLoaded();
    }

    @Override
    public void onFinish() {
        mMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onMapLoaded() {
        drawPolygon();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_RECIPES_CODE) {
                ObjectCreateField newCreateField = data.getParcelableExtra(Utils.Name.EDIT_FILED);
                if (newCreateField != null) {
                    createField = newCreateField;
                    tvCountRecipes.setText(String.valueOf(createField.getCountRecipe()));
                }
            } else if (requestCode == EDIT_FILED_REQUEST_CODE) {
                Bundle b = data.getExtras();
                if (b != null) {
                    int tam = b.getInt(Utils.Name.STATE);
                    if (tam == 1) {
                        createFieldResult = b.getParcelable(Utils.Name.OBJECT_RESULT);
                        if (createFieldResult != null) {
                            List<ResponseDetailField.Polygon> list = createFieldResult.getPolygon();
                            listPolygon.clear();
                            for (ResponseDetailField.Polygon ss : list) {
                                listPolygon.add(new LatLng(ss.getLat(), ss.getLng()));
                            }
                            createField = createFieldResult;
                            drawPolygon();
                        }
                    }
                }
            } else if (requestCode == EDIT_NAME_FILED_REQUEST_CODE) {
                createFieldResult = data.getParcelableExtra(Utils.Name.EDIT_NAME);
                ((TextView) findViewById(R.id.tvShowPlaceName)).setText(createFieldResult.getPlaceName());
                ((TextView) findViewById(R.id.tvTapToChange)).setText(createFieldResult.getPlaceName());
                createField = createFieldResult;
            }
        }
    }


    @Override
    public void failed(String message) {
        hideLoadingDialog();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void deleteSuccess() {
        hideLoadingDialog();
        Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
        Prefs.getInstance(this).saveReloadTimeline(true);
        Toast.makeText(getApplicationContext(), getString(R.string.text_deletesuccess), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void deleteFailed() {
        hideLoadingDialog();
        Toast.makeText(getApplicationContext(), getString(R.string.txt_deletefailed), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null)
            mLoadingDialog = null;
        if (mPresenter != null)
            mPresenter.onDestroy();
        super.onDestroy();
    }
}
