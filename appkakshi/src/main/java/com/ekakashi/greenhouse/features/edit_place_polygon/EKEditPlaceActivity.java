package com.ekakashi.greenhouse.features.edit_place_polygon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.OnItemClickListener;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditFieldRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.map.CurrentLocationCallback;
import com.ekakashi.greenhouse.map.CurrentLocationManager;
import com.ekakashi.greenhouse.map.CustomLatLngObject;
import com.ekakashi.greenhouse.map.PlaceAutocompleteAdapter;
import com.ekakashi.greenhouse.map.PlaceObject;
import com.ekakashi.greenhouse.map.PolygonCustom;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EKEditPlaceActivity extends BaseActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.CancelableCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMyLocationClickListener, EKEditPlaceInterface.View, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener, CurrentLocationCallback {
    private GoogleMap mMap;
    //count marker on map when user add marker
    private int countMarker = 0;
    private final int MY_RUNTIME_PERMISSION = 191;
    //save current polygon
    private PolygonCustom mCurrentPolygon;
    //save list big marker
    private List<Marker> markerList = new ArrayList<>();
    //save list big and small marker
    private List<Marker> marketMidPointList = new ArrayList<>();

    private Location location = null;

    //save list latLng to draw polygon on map
    private List<CustomLatLngObject> mLatLngList = new ArrayList<>();
    //save list latLng of polygon to  draw a polygon before that.
    private List<List<LatLng>> listPolyGonUndo = new ArrayList<>();
    private List<ResponseDetailField.Polygon> listLatLongPolygon;
    //check when user is draging a marker is big or small marker;
    //check when user is dragging a marker is big or small marker;
    private boolean isBigMarker = true;
    //save id of marker when user drag it.
    private String marKerDragId = "";
    //select marker when user click on marker to delete it.
    private Marker markerDelete;
    private ImageView btnUndo;
    //google place api
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    private ImageView imvDelete;
    private EditText edtAutocomplete;
    private RecyclerView recycleViewResult;
    private TextView tvDetermineTheArea;
    private Boolean isBtnDetermine = false;
    private ObjectCreateField edtField;
    private BitmapDescriptor pin_location;
    private BitmapDescriptor bigMarker;
    private BitmapDescriptor smallMarker;
    private BitmapDescriptor bigMarkerClicked;
    private List<PlaceObject> listResult = new ArrayList<>();
    private List<LatLng> listMarkerSingle = new ArrayList<>();
    private TextView tvTitle;
    private TextView tvSubTitle;
    private EKEditPlaceInterface.Presenter mPresenter;
    private SupportMapFragment supportMapFragment;
    private BitmapDescriptor pinLocation;
    private CurrentLocationManager currentLocationManager;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = null;
    //new LatLngBounds(new LatLng(10.855974, 106.631434), new LatLng(10.855974, 106.631434));

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        setContentView(R.layout.activity_edit_place);
        initView();
        handleEvent();
    }


    private void initView() {
        checkPermission();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_google_map);
        supportMapFragment.getMapAsync(this);
        createMarkerIcons();
        location = new Location("");
        //
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Utils.Name.STATE, 0);
                setResult(Activity.RESULT_OK, intent);
                onBackPressed();
            }
        });
        tvTitle = mToolbar.findViewById(R.id.tvTitleMain);
        tvSubTitle = mToolbar.findViewById(R.id.tvSubTitle);
        //
        tvDetermineTheArea = findViewById(R.id.tv_determine_the_area);
        tvDetermineTheArea.setVisibility(View.VISIBLE);
        tvDetermineTheArea.setOnClickListener(this);
        //
        recycleViewResult = findViewById(R.id.recyclerViewResult);
        btnUndo = findViewById(R.id.btnUndo);
        btnUndo = findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        imvDelete = findViewById(R.id.imvDelete);
        initGooglePlaceAPI();
        findViewById(R.id.imvGetMyLocation).setOnClickListener(this);
        findViewById(R.id.tvChangePosition).setOnClickListener(this);
        if (shouldAskPermissions()) {
            askPermissions();
        }
        mPresenter = new EKEditPlacePresenter(this);
        if (getIntent() != null) {
            findViewById(R.id.cardSearchLocation).setVisibility(View.VISIBLE);
            recycleViewResult.setVisibility(View.GONE);
            edtField = getIntent().getParcelableExtra(Utils.Name.EDIT_FILED);
            tvTitle.setText(R.string.txt_edit_place);
            tvSubTitle.setText(edtField.getPlaceName());
            listLatLongPolygon = edtField.getPolygon();
            if (listLatLongPolygon.size() != 0) {
                location.setLongitude(listLatLongPolygon.get(0).getLng());
                location.setLatitude(listLatLongPolygon.get(0).getLat());
            }

        }
        currentLocationManager = new CurrentLocationManager(EKEditPlaceActivity.this, this);

    }

    private void addPosition() {
        mMap.clear();
        if (listLatLongPolygon != null) {
            pinLocation = Utils.getFromDrawable(this, R.drawable.new_icon_location_svg);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(listLatLongPolygon.get(0).getLat(), listLatLongPolygon.get(0).getLng()), 16));
            mMap.addMarker(new MarkerOptions().position(new LatLng(listLatLongPolygon.get(0).getLat(), listLatLongPolygon.get(0).getLng())).icon(pinLocation));
        }
    }

    private void handleEvent() {
        if (mAdapter == null) {
            mAdapter = new PlaceAutocompleteAdapter(getApplicationContext(), listResult, new OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    hideKeyBroad();
                    if (mMap != null) {
                        mMap.clear();
                    }
                    edtAutocomplete.setText(String.valueOf(listResult.get(position).getPlaceName() + " " + listResult.get(position).getTitleName()));
                    edtAutocomplete.setSelection(edtAutocomplete.getText().length());
                    mGeoDataClient.getPlaceById(listResult.get(position).getPlaceId()).addOnCompleteListener(mUpdatePlaceDetailsCallback);
                }
            });
            recycleViewResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recycleViewResult.setAdapter(mAdapter);
            recycleViewResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    hideKeyBroad();
                }
            });
        }
    }

    private void createMarkerIcons() {
        if (bigMarker == null) {
            try {
                pin_location = Utils.getFromDrawable(this, R.drawable.new_icon_location_svg);
                //  bigMarker = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon);
                // bigMarkerClicked = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon_selected);
                //  smallMarker = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon_child);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    private void askPermissions() {
        String[] permissions = {
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION"
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, MY_RUNTIME_PERMISSION);
        }
    }

    private void hideKeyBroad() {
        View view = this.getCurrentFocus();
        if (view == null) {
            view = edtAutocomplete;
        }
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initGooglePlaceAPI() {
        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        edtAutocomplete = findViewById(R.id.edtAutocomplete);
        edtAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edtAutocomplete.requestFocus(); not working
                if (recycleViewResult.getVisibility() == View.GONE && !edtAutocomplete.getText().toString().trim().isEmpty() && !listResult.isEmpty()) {
                    recycleViewResult.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    imvDelete.setVisibility(View.VISIBLE);
                }
            }
        });
        edtAutocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    listResult.clear();
                    recycleViewResult.setVisibility(View.GONE);
                    imvDelete.setVisibility(View.GONE);
                } else {
                    imvDelete.setVisibility(View.VISIBLE);
                    queryLocation(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAutocomplete.setText("");//callback to onTextChanged to hide recycleViewResult
            }
        });
    }

    private void queryLocation(CharSequence charSequence) {
        final Task<AutocompletePredictionBufferResponse> results =
                mGeoDataClient.getAutocompletePredictions(charSequence.toString(), BOUNDS_GREATER_SYDNEY,
                        null);

        results.addOnSuccessListener(new OnSuccessListener<AutocompletePredictionBufferResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(AutocompletePredictionBufferResponse autocompletePredictions) {
                Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                listResult.clear();
                if (iterator.hasNext()) {
                    while (iterator.hasNext()) {
                        AutocompletePrediction prediction = iterator.next();
                        listResult.add(new PlaceObject(prediction.getPlaceId(), prediction.getPrimaryText(null).toString(),
                                prediction.getSecondaryText(null).toString()));
                    }
                }
                if (listResult.isEmpty()) {
                    recycleViewResult.setVisibility(View.GONE);
                    imvDelete.setVisibility(View.GONE);
                } else {
                    mAdapter.notifyDataSetChanged();
                    recycleViewResult.setVisibility(View.VISIBLE);
                    imvDelete.setVisibility(View.VISIBLE);
                }
                autocompletePredictions.release();
            }
        });
        results.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Tasks.await(results, 60, TimeUnit.SECONDS);
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                    Log.e("task wait", "error");
                }

            }
        }).start();
    }

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
            try {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    // Get the Place object from the buffer.
                    if (places != null) {
                        Place place = null;
                        try {
                            place = places.get(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (place != null && mMap != null) {
                            mMap.clear();
                            if (location == null) {
                                location = new Location(place.getAddress().toString());
                            } else {
                                location.setProvider(place.getAddress().toString());
                            }
                            location.setLongitude(place.getLatLng().longitude);
                            location.setLatitude(place.getLatLng().latitude);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18), EKEditPlaceActivity.this);
                            mMap.addMarker(new MarkerOptions().position(place.getLatLng()).draggable(true)
                                    .icon(pin_location).title(place.getAddress().toString()));
                            recycleViewResult.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.toast_error_when_get_place, Toast.LENGTH_SHORT).show();
                        }
                        places.release();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.toast_error_when_get_place, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast_error_when_get_place, Toast.LENGTH_SHORT).show();
                }
            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChangePosition:
                if (mMap == null) {
                    Toast.makeText(EKEditPlaceActivity.this, R.string.map_not_ready, Toast.LENGTH_LONG).show();
                    return;
                }
                ((TextView) findViewById(R.id.tvMovePositison)).setText(getString(R.string.txt_move_the_pin_to_the_position));
                isBtnDetermine = false;
                mMap.clear();
                clearTemp();
                //add marker location
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 25), EKEditPlaceActivity.this);
                    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                            .icon(pin_location).title(location.getProvider()));
                }
                findViewById(R.id.cardSearchLocation).setVisibility(View.VISIBLE);
                tvDetermineTheArea.setVisibility(View.VISIBLE);
                edtAutocomplete.setText("");
                //disable clicking on map
                mMap.setOnMapClickListener(null);
                mMap.setOnMarkerDragListener(this);
                mMap.setOnMarkerClickListener(this);
                clearPolygonPoint();
                clearPointUndo();
                break;
            case R.id.imvGetMyLocation:
                currentLocationManager.requestLocationUpdates();

                break;
            case R.id.tv_determine_the_area:

                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                //
                List<ResponseDetailField.Polygon> myLatLongs = new ArrayList<>();
                mLatLngList.clear();
                mLatLngList.add(new CustomLatLngObject(new LatLng(location.getLatitude(), location.getLongitude()), ""));
                myLatLongs.add(new ResponseDetailField.Polygon(location.getLatitude(), location.getLongitude()));
                edtField.setListPolygon(mLatLngList);
                edtField.setPolygon(myLatLongs);
                if (!isNetworkOffline()) {
                    showLoadingDialog(getString(R.string.loading));
                    mPresenter.updatePolygon(new EditFieldRequest(edtField.getPlaceName(),
                            edtField.getPolygon(), Integer.parseInt(edtField.getPlaceType()),
                            edtField.getIdField(), Integer.parseInt(edtField.getIdUser()), edtField.getAddress()));
                }
                break;
            default:
                break;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMyLocationEnabled(true);
        //   mMap.setOnMyLocationButtonClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        // If we don't use onMapLoadedCallback: java.lang.IllegalStateException: Error using newLatLngBounds(LatLngBounds, int):
        // Map size can't be 0. Most likely, layout has not yet occurred for the map view.
        // Either wait until layout has occurred or use newLatLngBounds(LatLngBounds, int, int, int) which allows you to specify the map's dimensions.

        mMap.setOnMarkerDragListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveListener(this);
        onMapLoaded();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_RUNTIME_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! do the
                    // calendar task you need to do.
                    moveToCurrentLocation();
                }
                break;
            default:
                break;
            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapLoaded() {
        createMarkerIcons();
        // NOTE: Don't set this listeners in onCreate().
        // Error will happen if user clicks on Clear or Done while map is being loaded.
        // mMap.setOnMapLoadedCallback(this);
        mMap.setOnMarkerDragListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.tv_determine_the_position).setOnClickListener(this);
        tvDetermineTheArea.setOnClickListener(this);
        // show the temp polygon after rotating.
        //set marker onclickListener
        addPosition();
    }

    @Override
    public void onFinish() {
        Log.e("test", "onFinish");
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        //Invoked if the animation (mMap.animateCamera...) goes to completion without interruption.

    }

    @Override
    public void onCancel() {
        Log.e("test", "onCancel");
        //Invoked if the animation is interrupted by calling stopAnimation() or starting a new camera movement.
        //while loading map, you long-click on map and move it.
    }

    //if countMarker <3 we need add marker on map and save latLng to mLatlnglist until count marker=3 we will draw it on map
    @Override
    public void onMapClick(LatLng latLng) {
    }


    //current polygon on map
    private Polygon mainPolygon;


    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    private List<LatLng> points = new ArrayList<>();
    private Polygon lineGon;
    private List<Marker> listMarketOnDrag = new ArrayList<>();

    //clear list marker on method OnDragMarker
    private void clearListMarketOnDrag() {
        while (!listMarketOnDrag.isEmpty()) {
            listMarketOnDrag.get(0).remove();
            listMarketOnDrag.remove(0);
        }
    }

    //while user is dragging a marker
    @Override
    public void onMarkerDrag(Marker marker) {

    }

    private void clearPoint() {
        while (!points.isEmpty()) {
            points.remove(0);
        }
    }


    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    private void clearTemp() {
        if (mCurrentPolygon != null) {
            mCurrentPolygon.removePolygon();
            mCurrentPolygon.clear();
            mCurrentPolygon = null;
        }
        if (mMap != null) {
            mMap.setOnMapClickListener(this);
            mMap.clear();
        }

        btnUndo.setVisibility(View.GONE);
        mLatLngList.clear();
        countMarker = 0;
        clearMarkerList();
        clearMakerMidPointList();
        markerDelete = null;
        marKerDragId = "";
        isBigMarker = true;
        clearPointUndo();
        clearMarkerSingle();
    }

    private void clearPolygonPoint() {
        while (!listLatLongPolygon.isEmpty()) {
            listLatLongPolygon.remove(0);
        }
    }

    private void clearMarkerSingle() {
        while (!listMarkerSingle.isEmpty()) {
            listMarkerSingle.remove(0);
        }
    }

    //clear list contain points to undo
    private void clearPointUndo() {
        while (!listPolyGonUndo.isEmpty()) {
            listPolyGonUndo.remove(0);
        }
    }

    //clear list big marker and small marker
    private void clearMakerMidPointList() {
        while (!marketMidPointList.isEmpty()) {
            marketMidPointList.get(0).remove();
            marketMidPointList.remove(0);
        }
    }

    //clear list big marker
    private void clearMarkerList() {
        while (!markerList.isEmpty()) {
            markerList.get(0).remove();
            markerList.remove(0);
        }
    }

    @Override
    protected void onDestroy() {
        clearTemp();
        if (currentLocationManager != null) {
            currentLocationManager.stopRequestLocationUpdates();
            currentLocationManager = null;
        }
        if (mMap != null) {
            mMap.clear();
            mMap = null;
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    private void moveToCurrentLocation() {
        boolean isGPSEnabled;
        // flag for network status
        boolean isNetworkEnabled;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Location myLocation = null;
        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (myLocation == null) {
                Toast.makeText(getApplicationContext(), R.string.cannot_determine_current_location, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (myLocation == null) {
                    Toast.makeText(getApplicationContext(), R.string.cannot_determine_current_location, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.txt_please_enable_internet_and_gps, Toast.LENGTH_SHORT).show();
            }
        }
        if (myLocation != null) {
            location.setProvider(myLocation.getProvider());
            location.setLatitude(myLocation.getLatitude());
            location.setLongitude(myLocation.getLongitude());
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                        location.getLongitude()), 18), EKEditPlaceActivity.this);
                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .draggable(true).icon(pin_location).title(Utils.getAddressFromLatLong(getApplicationContext(),
                                location.getLatitude(), location.getLongitude())));
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_map_null, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // show an explanation to the user
                // Good practise: don't block thread after the user sees the explanation, try again to request the permission.
            } else {
                // request the permission.
                // CALLBACK_NUMBER is a integer constants
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                // The callback method gets the result of the request.
            }
        }
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void success() {
        Prefs.getInstance(getApplicationContext()).saveStatusCallApi(true);
        hideLoadingDialog();
        Toast.makeText(getApplicationContext(), R.string.edit_field_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Utils.Name.STATE, 1);
        intent.putExtra(Utils.Name.OBJECT_RESULT, edtField);
        setResult(Activity.RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void failed(String message) {
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void onCameraIdle() {
        findViewById(R.id.imvLocation).setVisibility(View.VISIBLE);
        moveMaker();
    }

    @Override
    public void onCameraMove() {
        // TODO: Loading
    }

    private void moveMaker() {
        mMap.clear();
        if (mMap != null) {
            CameraPosition cameraPosition = mMap.getCameraPosition();
            String title = Utils.getAddressFromLatLong(getApplicationContext(), cameraPosition.target.latitude, cameraPosition.target.longitude);
          /*  mMap.addMarker(new MarkerOptions().position(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude))
                    .draggable(true).icon(pin_location));*/
            edtField.setAddress(title);
            if (location == null) {
                location = new Location("");
            }
            location.setLatitude(cameraPosition.target.latitude);
            location.setLongitude(cameraPosition.target.longitude);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void returnCurrentLocation(Location location) {
        if (mMap != null) {
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                        location.getLongitude()), 16), EKEditPlaceActivity.this);
            }
        }
    }
}
