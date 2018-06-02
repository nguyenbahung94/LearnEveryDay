package com.ekakashi.greenhouse.features.view_field;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.RoleOfUser;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectWeatherResponse;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.AddFieldGreenHouseActivity;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.view_information_field.EKViewFieldInformationActivity;
import com.ekakashi.greenhouse.features.weather.NumberOfBlock;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumWeather;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectCommonTemplateRecipe;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;
import com.ekakashi.greenhouse.features.weather.object_weather.graphData;
import com.ekakashi.greenhouse.map.CustomScrollView;
import com.ekakashi.greenhouse.map.WorkaroundMapFragment;
import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;


public class EKMainFieldFragment extends Fragment implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.CancelableCallback, EKMainFieldFragmentInterface.View, WorkaroundMapFragment.OnTouchListener {

    private GoogleMap mMap;
    private LinearLayout linearLayout;
    private Toolbar mToolBar;
    private EKMainFieldFragmentInterface.Presenter mPresenter;
    private List<LatLng> listLatLong = new ArrayList<>();
    private ObjectCreateField createField;
    private static int CODE_START_ACTIVITY = 212;
    private View mView;
    private LinearLayout llEmpty;
    private CustomScrollView scrollView;
    private List<FilterField> fieldsList = new ArrayList<>();
    private ResponseDetailField responseDetailField;
    private EKMainFieldActivity mActivity;
    private BottomSheetBehavior bottomSheetBehavior;
    private ObjectCommonTemplateRecipe objectCommonTemplateRecipe = new ObjectCommonTemplateRecipe();


    @Override
    public void onAttach(Context context) {
        mActivity = (EKMainFieldActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_field, container, false);
        scrollView = mView.findViewById(R.id.llSrollView);
        llEmpty = mView.findViewById(R.id.llEmpty);
        llEmpty.findViewById(R.id.btnAddField).setOnClickListener(this);
        RelativeLayout llBottom = mView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        initView();
        mPresenter = new EKMainFieldFragmentPresenter(this);
        if (mActivity != null && !mActivity.isNetworkOffline()) {
            mActivity.showLoadingDialog(getString(R.string.message_please_wait));
            mPresenter.getListFields();
        }
        return mView;

    }

    private void initView() {
        linearLayout = mView.findViewById(R.id.llcontainElementRecipe);
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        mToolBar = mView.findViewById(R.id.toolbar);
        ((TextView) mToolBar.findViewById(R.id.tvTitleMain)).setText("");
        (mToolBar.findViewById(R.id.llTapToChange)).setOnClickListener(this);

        (mToolBar.findViewById(R.id.imvRight)).setOnClickListener(this);
        (mToolBar.findViewById(R.id.imgBack)).setOnClickListener(this);
        WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fm_google_map);
        mapFragment.getMapAsync(this);
        mapFragment.setListener(this);
    }

    private void setDefault() {
        ((TextView) mToolBar.findViewById(R.id.tvTitleMain)).setText(R.string.txt_field_greenhouse);
        (mToolBar.findViewById(R.id.llTapToChange)).setVisibility(GONE);
        (mToolBar.findViewById(R.id.imvRight)).setVisibility(GONE);
        (mToolBar.findViewById(R.id.imgBack)).setVisibility(GONE);
        llEmpty.setVisibility(View.VISIBLE);
        mActivity.hideBottom();
        mToolBar.setVisibility(GONE);
        scrollView.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddField:
                //  EKAddFieldStepTwoActivity.startActivity(getActivity());
                if (!mActivity.isNetworkOffline())
                    addField();
                break;
            case R.id.llTapToChange:
                showListFieldBottom();
                break;
            case R.id.imvRight:
                //check role here
                if (createField != null && RoleOfUser.Field.EditField(getContext())) {
                    Prefs.getInstance(App.getsInstance()).saveStatusCallApi(false);
                    Intent intent = new Intent(getActivity(), EKViewFieldInformationActivity.class);
                    intent.putExtra(Utils.Name.EDIT_FILED, createField);
                    startActivityForResult(intent, CODE_START_ACTIVITY);
                } else {
                    showDialogPermisionInfo();
                }
                break;
            case R.id.imgBack:
                //  EKAddFieldStepTwoActivity.startActivity(getActivity());
                if (checkListFieldSize()) {
                    showCustomDialogEvent();
                } else if (!mActivity.isNetworkOffline()) {
                    addField();
                }
                break;
            default:
                break;
        }

    }

    private void showDialogPermisionInfo() {
        new MaterialDialog.Builder(getContext())
                .content(R.string.not_permission)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).show();
    }

    private void showCustomDialogEvent() {
        new MaterialDialog.Builder(getActivity())
                .content(R.string.upgradeField)
                .cancelable(false)
                .neutralText(R.string.toolbar_cancel).neutralColor(Utils.getColor(getActivity(), R.color.bg_green_btn))
                .positiveText(R.string.viewdetail).positiveColor(Utils.getColor(getActivity(), R.color.bg_green_btn))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(mActivity, "The in-app purchase feature has not been implemented yet.", Toast.LENGTH_SHORT).show();

                    }
                })
                .show();
    }

    private boolean checkListFieldSize() {
        return fieldsList != null && fieldsList.size() >= 5;

    }

    private void addField() {
        ObjectCreateField objectFiled = new ObjectCreateField();
        objectFiled.setIdUser(String.valueOf(Prefs.getInstance(getActivity()).getUserId()));
        Intent intentRecipe = new Intent(getActivity(), AddFieldGreenHouseActivity.class);
        intentRecipe.putExtra(Utils.Name.CREATE_FILED, objectFiled);
        startActivity(intentRecipe);
    }

    private TextView tvShowCurrentState;
    private Button btnGotoNextState;

    private void addRecipeTop() {
        btnGotoNextState = mView.findViewById(R.id.btnGotoNextState);
        btnGotoNextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextStage();
            }
        });
        tvShowCurrentState = mView.findViewById(R.id.tvCurrentStage);
        TextView tvNameRecipe = mView.findViewById(R.id.tvNameRecipeField);
        ImageView imvRecipe = mView.findViewById(R.id.image_recipe);
        List<ResponseDetailField.Recipes> recipesList = responseDetailField.getData().getRecipes();
        if (recipesList != null && !recipesList.isEmpty() && recipesList.get(0) != null) {
            ResponseDetailField.Recipes recipes = recipesList.get(0);
            if (recipes.getCurrentStage() != null) {
                tvShowCurrentState.setText(recipes.getCurrentStage().getStageName());
            } else {
                tvShowCurrentState.setText("");
            }
            if (recipes.getRecipe() != null) {
                tvNameRecipe.setText(recipes.getRecipe().getRecipeName());
                Glide.with(EKMainFieldFragment.this).load(recipes.getRecipe().getImagePath()).into(imvRecipe);
            } else {
                tvNameRecipe.setText("");
            }
        } else {
            tvShowCurrentState.setText("");
        }
    }

    private void addTemplate(final ObjectCommonTemplateRecipe objectCommonTemplateRecipe) {
        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        // TODO: add code recipe
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 50, 0, 0);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.item_recipe_withweather, null);
            //   initChart();

            @SuppressLint("InflateParams") View viewItemSensor = inflater.inflate(R.layout.item_sensor_data, null);

            //
            viewItemSensor.findViewById(R.id.llrl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentActivity activity = getActivity();
                    if (activity == null) {
                        return;
                    }
                    Toast.makeText(activity, "sensor click", Toast.LENGTH_SHORT).show();
                }
            });

            // TODO:code weather

            final LinearLayout llContainWeather = view.findViewById(R.id.llContainWeather);
            llContainWeatherList = mView.findViewById(R.id.llContainWeather);
            mView.findViewById(R.id.imvClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
            // create data to test
            /*exampleData();*/
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState) {
                        case BottomSheetBehavior.STATE_HIDDEN:
                            break;
                        case BottomSheetBehavior.STATE_EXPANDED:
                            break;
                        case BottomSheetBehavior.STATE_COLLAPSED:
                            llContainWeatherList.removeAllViews();
                            break;
                        case BottomSheetBehavior.STATE_DRAGGING:
                            break;
                        case BottomSheetBehavior.STATE_SETTLING:
                            break;
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
            NumberOfBlock numberOfBlock = new NumberOfBlock(getActivity());
            View viewToAdd = numberOfBlock.createBlock(objectCommonTemplateRecipe);
            if (viewToAdd != null) {
                llContainWeather.addView(viewToAdd);
            }
            llContainWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addElement(objectCommonTemplateRecipe);
                    // ContainListWeatherActivity.start(getActivity());
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            });
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 10, 0, 10);
            viewItemSensor.setLayoutParams(lp2);
            linearLayout.addView(view);
        }
    }

    private void goToNextStage() {
        if (!mActivity.isNetworkOffline()) {
            int sortNoOfNextStage;
//            boolean found = false;
            if (responseDetailField.getData() != null && responseDetailField.getData().getRecipes() != null && !responseDetailField.getData().getRecipes().isEmpty() && responseDetailField.getData().getRecipes().get(0) != null) {
                ResponseDetailField.Recipes firstRecipe = responseDetailField.getData().getRecipes().get(0);
                if (firstRecipe.getRecipe() == null || firstRecipe.getRecipe().getRecipeStages() == null
                        || firstRecipe.getRecipe().getRecipeStages().isEmpty() || firstRecipe.getCurrentStage() == null) {
                    Toast.makeText(mActivity, R.string.cannot_go_to_next_stage, Toast.LENGTH_LONG).show();
                    return;
                }
                List<ResponseDetailField.RecipeStages> stagesList = firstRecipe.getRecipe().getRecipeStages();
                sortNoOfNextStage = firstRecipe.getCurrentStage().getSortNo();
                Collections.sort(stagesList, new Comparator<ResponseDetailField.RecipeStages>() {
                    @Override
                    public int compare(ResponseDetailField.RecipeStages recipeStages, ResponseDetailField.RecipeStages t1) {
                        return recipeStages.getSortNo() - t1.getSortNo();
                    }
                });
//                for (int i = 0; i < stagesList.size(); i++) {
//                    ResponseDetailField.RecipeStages objectRecipe = stagesList.get(i);
//                    if (objectRecipe.getSortNo() == sortNoOfNextStage) {
//                        if (i <= stagesList.size() - 2) {
//                            ResponseDetailField.RecipeStages objectRecipeNext = stagesList.get(i + 1);
                mActivity.showLoadingDialog(getString(R.string.loading));
                mPresenter.goToNextState(responseDetailField.getData().getId(), firstRecipe.getRecipe().getId(), firstRecipe.getCurrentStage().getId());
//                int fieldId = responseDetailField.getData().getId(), recipeId = firstRecipe.getRecipe().getId(), stageId = objectRecipeNext.getId();
//                        }
//                    }

            }
//            if (!found) {
//                Toast.makeText(mActivity, R.string.cannot_go_to_next_stage, Toast.LENGTH_SHORT).show();

//                btnGotoNextState.setBackgroundColor(getResources().getColor(R.color.com_facebook_button_background_color_disabled));
//            }
        }
    }


    @Override
    public void onFinish() {
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onMapLoaded() {
        if (listLatLong != null) {
            drawPolygon();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getActivity() != null && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setOnMapClickListener(null);
        // If we don't use onMapLoadedCallback: java.lang.IllegalStateException: Error using newLatLngBounds(LatLngBounds, int):
        // Map size can't be 0. Most likely, layout has not yet occurred for the map view.
        // Either wait until layout has occurred or use newLatLngBounds(LatLngBounds, int, int, int) which allows you to specify the map's dimensions.
        mMap.setOnMapLoadedCallback(this);


    }

    @Override
    public void onTouch() {
        scrollView.requestDisallowInterceptTouchEvent(true);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, 0, 0);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void drawPolygon() {
        if (mMap == null || getActivity() == null) {
            //TODO must fix issue
            return;
        }
        if (listLatLong.size() != 0) {
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLong.get(0), 16));
            mMap.addMarker(new MarkerOptions().position(listLatLong.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location)).title(Utils.getAddressFromLatLong(getActivity(), listLatLong.get(0).latitude, listLatLong.get(0).longitude)));
        }
    }

    @Override
    public void getListFieldSuccess(List<FilterField> list) {
        if (mActivity == null) {
            return;
        }
        if (list != null && !list.isEmpty()) {
            fieldsList = list;
            mToolBar.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(GONE);
            mActivity.showBottop();
            (mToolBar.findViewById(R.id.llTapToChange)).setVisibility(View.VISIBLE);
            (mToolBar.findViewById(R.id.imvRight)).setVisibility(View.VISIBLE);
            (mToolBar.findViewById(R.id.imgBack)).setVisibility(View.VISIBLE);
            int currentId;
            if (createField != null) {
                currentId = createField.getIdField();
            } else {
                currentId = Prefs.getInstance(App.getsInstance()).getCurrentID_Field();
                //remove Field ID from pref
                Prefs.getInstance(App.getsInstance()).saveCurrentId_Field(-1);
            }
            if (currentId == -1) {
                mPresenter.getDetailField(fieldsList.get(0).getId());
            } else {
                for (FilterField data : fieldsList) {
                    if (currentId == data.getId()) {
                        mPresenter.getDetailField(data.getId());
                        return;
                    }
                }
                mPresenter.getDetailField(fieldsList.get(0).getId());

            }
        } else {
            mActivity.hideLoadingDialog();
            ((TextView) mToolBar.findViewById(R.id.tvTitleMain)).setText(getString(R.string.txt_field_greenhouse));
            setDefault();
        }
    }

    @Override
    public void success(ObjectWeatherResponse objectWeatherResponse) {

    }

    @Override
    public void getListFieldFailed(String error) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        setDefault();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDetailFieldSuccess(ResponseDetailField responseDetailField) {
        if (mActivity == null) {
            return;
        }
        if (responseDetailField != null && responseDetailField.getData() != null) {

            this.responseDetailField = responseDetailField;
            Prefs.getInstance(getActivity()).setUserRoleOnDetailField(responseDetailField.getData().setUpRoleOnField(responseDetailField.getData().getUserRoleOnField()));
            Toast.makeText(getActivity(), "" + Prefs.getInstance(getActivity()).getUserRoleOnDetailField(), Toast.LENGTH_SHORT).show();
            scrollView.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(GONE);
            mActivity.showBottop();
            mToolBar.setVisibility(View.VISIBLE);
            listLatLong.clear();
            addRecipeTop();
            ((TextView) mToolBar.findViewById(R.id.tvTitleMain)).setText(responseDetailField.getData().getName());
            createField = new ObjectCreateField(responseDetailField.getData().getName(), String.valueOf(responseDetailField.getData().getFieldType()));
            createField.setPolygon(responseDetailField.getData().getPolygon());
            List<ResponseDetailField.Polygon> myLatLongs = responseDetailField.getData().getPolygon();
            listLatLong.add(new LatLng(myLatLongs.get(0).getLat(), myLatLongs.get(0).getLng()));
            createField.setPlaceName(responseDetailField.getData().getName());
            createField.setIdField(responseDetailField.getData().getId());
            Prefs.getInstance(App.getsInstance()).saveCurrentId_Field(responseDetailField.getData().getId());
            createField.setAddress(responseDetailField.getData().getLocation());
            createField.setIdUser(String.valueOf(Prefs.getInstance(App.getsInstance()).getUserId()));
            if (responseDetailField.getData().getRecipes() != null && !responseDetailField.getData().getRecipes().isEmpty()) {
                createField.setCountRecipe(responseDetailField.getData().getRecipes().size());
                if (responseDetailField.getData().getRecipes().get(0).getRecipe() != null) {
                    createField.setRecipeId(responseDetailField.getData().getRecipes().get(0).getRecipe().getId());
                }
                if (responseDetailField.getData().getRecipes().get(0).getCurrentStage() != null) {
                    createField.setCurrentStageId(responseDetailField.getData().getRecipes().get(0).getCurrentStage().getId());
                    createField.setSortNo(responseDetailField.getData().getRecipes().get(0).getCurrentStage().getSortNo());
                }
            } else {
                createField.setCountRecipe(0);
            }
            createField.setCountDevice(responseDetailField.getData().getDeviceCount());
            createField.setCountMember(responseDetailField.getData().getMemberCount());
            drawPolygon();

            //SET STATUS FOR BUTTON MOVE TO NEXT STAGE
            if (responseDetailField.getData() != null && responseDetailField.getData().getRecipes() != null
                    && !responseDetailField.getData().getRecipes().isEmpty()
                    && responseDetailField.getData().getRecipes().get(0).getCurrentStage() != null) {
                if (responseDetailField.getData().getRecipes().get(0).getCurrentStage().getStatus() == Integer.parseInt(Utils.Name.TWO)) {
                    btnGotoNextState.setVisibility(GONE);
                } else {
                    btnGotoNextState.setVisibility(View.VISIBLE);
                }
                int sizeStage = responseDetailField.getData().getRecipes().get(0).getRecipe().getRecipeStages().size();
                int sortNo = responseDetailField.getData().getRecipes().get(0).getCurrentStage().getSortNo();
                if (sizeStage == sortNo) { // ==>>> LAST STAGE
                    btnGotoNextState.setText(getString(R.string.complete_stage));
                } else {
                    btnGotoNextState.setText(getString(R.string.proceed_to_the_next_stage));
                }
            }
        } else {
            Toast.makeText(mActivity, R.string.cannot_get_details_field, Toast.LENGTH_LONG).show();
        }
        mPresenter.getTemplateRecipe(String.valueOf(createField.getRecipeId()));

    }

    @Override
    public void getDetailFailed(String error) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeStageSuccess(ChangeStageRecipeResponse response) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        if (response == null) {
            mActivity.customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
            return;
        }

        int sortNoOfCurrentStage = responseDetailField.getData().getRecipes().get(0).getCurrentStage().getSortNo();
        sortNoOfCurrentStage++;
        List<ResponseDetailField.RecipeStages> stagesList = responseDetailField.getData().getRecipes().get(0).getRecipe().getRecipeStages();
        for (ResponseDetailField.RecipeStages recipeStages : stagesList) {
            if (recipeStages.getSortNo() == sortNoOfCurrentStage) {
                createField.setCurrentStageId(recipeStages.getId());
                tvShowCurrentState.setText(recipeStages.getStageName());
                responseDetailField.getData().getRecipes().get(0).setCurrentStage(new ResponseDetailField.CurrentStage(recipeStages.getId(),
                        sortNoOfCurrentStage, recipeStages.getStageName()));
                break;
            }

        }

        if (response.getData().getStatus() == Integer.parseInt(Utils.Name.TWO)) {
            //TODO hide btnGo to next Stage
            btnGotoNextState.setVisibility(GONE);
            mActivity.showSimpleMessage(getString(R.string.last_stage_completed));
        } else {
            int size = stagesList.size();
            if ((response.getData().getSortNo()) == size) {
                btnGotoNextState.setText(R.string.complete_stage);
            } else {
                btnGotoNextState.setText(getString(R.string.proceed_to_the_next_stage));
            }
            btnGotoNextState.setVisibility(View.VISIBLE);
            mActivity.showSimpleMessage(getString(R.string.stage_has_changed));
        }
    }

    @Override
    public void changeStageFailed(String message) {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getWeatherDataSuccess(ObjectDataForWidgetGraph objectDataForWidgetGraph) {
        mActivity.hideLoadingDialog();
        if (objectDataForWidgetGraph.getData() != null && objectDataForWidgetGraph.getData().getGraphData() != null && objectDataForWidgetGraph.getData().getGraphData().size() != 0 && objectDataForWidgetGraph.getData().getWidgetDataResponse() != null && objectDataForWidgetGraph.getData().getWidgetDataResponse().size() != 0) {
            objectCommonTemplateRecipe.setdataTemplateMainField(objectDataForWidgetGraph.getData());
            objectCommonTemplateRecipe.compateTwoList();
            objectCommonTemplateRecipe.setdataTemplateRecipe(setUpTypeWeather(objectCommonTemplateRecipe.getdataTemplateRecipe()));
            addTemplate(objectCommonTemplateRecipe);
        } else {
            Toast.makeText(getActivity(), "data empty", Toast.LENGTH_SHORT).show();
        }
    }


    private List<Data> setUpTypeWeather(List<Data> listTemplate) {
        for (Data item : listTemplate) {
            if (item.getWidget() != null) {
                subMethodTypeWeather(item);
            } else {
                if (item.getGraph() != null) {

                }
            }
        }
        return listTemplate;
    }

    private void subMethodTypeWeather(Data item) {
        switch (item.getWidget().getWidgetMeasureItemId()) {
            case 1:
                item.setTypeOfWeather(EnumWeather.WEATHER_TEMPERATURE);
                break;
            case 2:
                item.setTypeOfWeather(EnumWeather.HUMIDITY);
                break;
            case 3:
                item.setTypeOfWeather(EnumWeather.WIND_DIRECTION);
                break;
            case 4:
                item.setTypeOfWeather(EnumWeather.WIND_VELOCITY);
                break;
            case 5:
                item.setTypeOfWeather(EnumWeather.PRECIPITATION);
                break;
            case 6:
                item.setTypeOfWeather(EnumWeather.AMOUNT_OF_INSOLATION);
                break;
            case 7:
                item.setTypeOfWeather(EnumWeather.WEATHER_FORECAST_2DAY);
                break;
            case 8:
                item.setTypeOfWeather(EnumWeather.WEATHER_FORECAST_8DAY);
                break;
            case 9:
                item.setTypeOfWeather(EnumWeather.SUNRISE_SUNSET);
                break;
            default:
                break;
        }
    }

    @Override
    public void getWeatherDataFailed(String error) {
        mActivity.hideLoadingDialog();
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getTemplateRecipeSuccess(List<Data> listdata) {

        if (listdata != null && listdata.size() != 0) {
            objectCommonTemplateRecipe.setdataTemplateRecipe(listdata);
            mPresenter.getWeatherData(String.valueOf(createField.getRecipeId()));
        } else {
            Toast.makeText(getActivity(), "data empty", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getTemplateRecipeFailed(String error) {
        mActivity.hideLoadingDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        if (mActivity == null) {
            return;
        }
        mActivity.hideLoadingDialog();
        Utils.tokenExpired(mActivity);
    }

    @Override
    public void onDestroy() {
        Utils.isFinishActivity(getActivity());
        mMap = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    private void showListFieldBottom() {
        if (mActivity == null) {
            return;
        }
        if (fieldsList.size() > 0) {
            int i = -1;
            String[] arr = new String[fieldsList.size()];
            for (FilterField data : fieldsList) {
                i++;
                arr[i] = data.getPlaceName();
            }
            final BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
            LayoutInflater inflater = this.getLayoutInflater();
            @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_bottom_sheet_field, null);
            final CutomNumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(arr.length - 1);
            numberPicker.setDisplayedValues(arr);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            //set wrap true or false, try it you will know the difference
            numberPicker.setWrapSelectorWheel(false);
            dialogView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            dialogView.findViewById(R.id.tvExcute).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mActivity == null) {
                        return;
                    }
                    if (mActivity.isNetworkOffline()) {
                        return;
                    }
                    mActivity.showLoadingDialog(getString(R.string.message_please_wait));
                    int pos = numberPicker.getValue();
                    dialog.cancel();
                    mPresenter.getDetailField(fieldsList.get(pos).getId());
                }
            });
            dialog.setContentView(dialogView);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), R.string.no_more_field, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_START_ACTIVITY) {
                if (mActivity == null) {
                    return;
                }
                if (Prefs.getInstance(mActivity.getApplicationContext()).getStatusCallApi()) {
                    mActivity.showLoadingDialog(getString(R.string.message_please_wait));
                    //  mPresenter.getDetailField(Prefs.getInstance(App.getsInstance()).getCurrentID_Field());
                    listLatLong.clear();
                    mPresenter.getListFields();
                }
            }
        }
    }

    private LinearLayout llContainWeatherList;

    // TODO: code list weather
    public void addElement(ObjectCommonTemplateRecipe commonTemplateRecipe) {
        List<Data> templateList = commonTemplateRecipe.getdataTemplateRecipe();
        for (int j = 0; j < templateList.size(); j++) {
            Data firstObject = templateList.get(j);
            if (j + 1 <= templateList.size() - 1) {
                if (firstObject.getNumberOfColumn() == 1) {
                    Data secondObject = templateList.get(j + 1);
                    if (secondObject.getNumberOfColumn() == 1) {
                        // TODO: add two template at the same time
                        addTwoLayout(2, firstObject, secondObject, (ObjectDataForWidgetGraph.WidgetDataResponse) commonTemplateRecipe.getListMapObject().get(firstObject.getId()), (ObjectDataForWidgetGraph.WidgetDataResponse) commonTemplateRecipe.getListMapObject().get(secondObject.getId()), null, templateList);
                        j++;
                    } else {
                        if (secondObject.getNumberOfColumn() == 2) {
                            // TODO: add one template
                            addTwoLayout(1, firstObject, null, (ObjectDataForWidgetGraph.WidgetDataResponse) commonTemplateRecipe.getListMapObject().get(firstObject.getId()), null, null, templateList);
                        }
                    }
                } else {
                    if (firstObject.getNumberOfColumn() == 2) {
                        // TODO: add graph
                        addTwoLayout(3, firstObject, null, null, null, (graphData) commonTemplateRecipe.getListMapObject().get(firstObject.getId()), templateList);
                    }
                }
            } else {
                if (firstObject.getNumberOfColumn() == 1) {
                    // TODO: add one template
                    addTwoLayout(1, firstObject, null, (ObjectDataForWidgetGraph.WidgetDataResponse) commonTemplateRecipe.getListMapObject().get(firstObject.getId()), null, null, templateList);
                }
                if (firstObject.getNumberOfColumn() == 2) {
                    // TODO: add graph
                    addTwoLayout(3, firstObject, null, null, null, (graphData) commonTemplateRecipe.getListMapObject().get(firstObject.getId()), templateList);
                }
            }
        }

    }

    @SuppressLint("InflateParams")
    public void addTwoLayout(int number, Data firstObject, Data secondObject, ObjectDataForWidgetGraph.WidgetDataResponse firstTemplate, ObjectDataForWidgetGraph.WidgetDataResponse secondTemplate, graphData graph, List<Data> list) {
        View viewContainTemplate;
        View viewItem;
        View chartView;
        if (getActivity() == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        viewContainTemplate = inflater.inflate(R.layout.item_weather_inlist, null);
        LinearLayout llItemWeather = viewContainTemplate.findViewById(R.id.llItemWeather);
        viewItem = inflater.inflate(R.layout.one_item_weather, null);
        chartView = inflater.inflate(R.layout.activity_graph, null);
        CombinedChart mChart = chartView.findViewById(R.id.CombinedChart);
        mChart.setTouchEnabled(true);
        switch (number) {
            case 1:
                if (firstObject.getWidget() != null) {
                    viewItem.findViewById(R.id.llOneItemWeather).setVisibility(View.VISIBLE);
                    viewItem.findViewById(R.id.llTwoItemWeather).setVisibility(View.GONE);
                    ((LinearLayout) viewItem.findViewById(R.id.llOneItemWeather)).addView(NumberOfBlock.getUIWeather(firstObject.getTypeOfWeather(), firstTemplate.getData(), getActivity(), firstObject.getName()));
                    llItemWeather.addView(viewItem);
                    llContainWeatherList.addView(viewContainTemplate);
                } else {
                    for (Data ss : list) {
                        if (ss.getId() == graph.getTemplatedId()) {
                            NumberOfBlock.setUpChart(mChart, graph, 3, list, getActivity(), mChart, ss);
                        }
                    }
                    llItemWeather.addView(chartView);
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp2.setMargins(0, 10, 0, 10);
                    llItemWeather.setLayoutParams(lp2);
                    llContainWeatherList.addView(viewContainTemplate);
                }

                break;
            case 2:
                viewItem.findViewById(R.id.llOneItemWeather).setVisibility(GONE);
                viewItem.findViewById(R.id.llTwoItemWeather).setVisibility(View.VISIBLE);
                if (firstTemplate.getData() != null && secondTemplate.getData() != null) {
                    ((LinearLayout) viewItem.findViewById(R.id.llLeft)).addView(NumberOfBlock.getUIWeather(firstObject.getTypeOfWeather(), firstTemplate.getData(), getActivity(), firstObject.getName()));
                    ((LinearLayout) viewItem.findViewById(R.id.llRight)).addView(NumberOfBlock.getUIWeather(secondObject.getTypeOfWeather(), secondTemplate.getData(), getActivity(), firstObject.getName()));
                    llItemWeather.addView(viewItem);
                    llContainWeatherList.addView(viewContainTemplate);
                }
                break;
            case 3:
                for (Data ss : list) {
                    if (ss.getId() == graph.getTemplatedId()) {
                        NumberOfBlock.setUpChart(mChart, graph, 3, list, getActivity(), mChart, ss);
                    }
                }
                llItemWeather.addView(chartView);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp2.setMargins(0, 10, 0, 10);
                llItemWeather.setLayoutParams(lp2);
                llContainWeatherList.addView(viewContainTemplate);
                break;
            default:
                break;
        }
    }
}
