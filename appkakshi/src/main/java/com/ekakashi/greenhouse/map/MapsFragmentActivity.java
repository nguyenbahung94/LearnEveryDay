package com.ekakashi.greenhouse.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.OnItemClickListener;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.common.stepcreatefield.ThreeStepCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.add_field_greenhouse.step4.EKAddFieldStepFourActivity;
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

public class MapsFragmentActivity extends BaseActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.CancelableCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener, CurrentLocationCallback {
    private GoogleMap mMap;
    //count marker on map when user add marker
    //  private int countMarker = 0;
    private final int MY_RUNTIME_PERMISSION = 191;
/*
    //save current polygon
    private PolygonCustom mCurrentPolygon;
    //save list big marker
    private List<Marker> markerList = new ArrayList<>();
    //save list big and small marker
    private List<Marker> marketMidPointList = new ArrayList<>();
*/

    private Location location = null;
    private List<CustomLatLngObject> mLatLngList = new ArrayList<>();
    /*
        //save list latlng to draw polygon on map
        private List<CustomLatLngObject> mLatLngList = new ArrayList<>();
        //save list latlng of polygon to  draw a polygon before that.
        private List<List<LatLng>> listPolyGonUndo = new ArrayList<>();
        //check when user is draging a marker is big or small marker;
        private boolean isBigMarker = true;
        //save id of marker when user drag it.
        private String marKerDragId = "";
        //select marker when user click on marker to delete it.
        private Marker markerDelete;
        private ImageView btnUndo;*/
    //google place api
    protected GeoDataClient mGeoDataClient;
    private CurrentLocationManager currentLocationManager;
    private PlaceAutocompleteAdapter mAdapter;
    private ImageView imvDelete;
    private EditText edtAutocomplete;
    private RecyclerView recycleViewResult;
    private TextView tvDetermineTheArea;
    private Boolean isBtnDetermineArea = false;
    private ObjectCreateField createField;
    private BitmapDescriptor pinLocation;
    private BitmapDescriptor bigMarker;
    private BitmapDescriptor smallMarker;
    private BitmapDescriptor BigMarkerClicked;
    private List<PlaceObject> listResult = new ArrayList<>();
    /*private List<LatLng> listMarkerSingle = new ArrayList<>();*/

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = null;
    //new LatLngBounds(new LatLng(10.855974, 106.631434), new LatLng(10.855974, 106.631434));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        setContentView(R.layout.activity_maps);
        initView();
        handleEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentLocationManager.requestLocationUpdates();
    }

    private void initView() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_google_map);
        supportMapFragment.getMapAsync(this);

        //   createMarkerIcons();

        new ThreeStepCreateField((RelativeLayout) findViewById(R.id.layoutContainFourStep),
                getApplicationContext()).stepOne();
        //
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) mToolbar.findViewById(R.id.tvTitleMain)).setText(R.string.txt_place_registration);
        mToolbar.findViewById(R.id.tvToolbarRight).setOnClickListener(this);
        mToolbar.findViewById(R.id.imgBack).setVisibility(View.VISIBLE);
        //
        tvDetermineTheArea = findViewById(R.id.tv_determine_the_area);
        tvDetermineTheArea.setVisibility(View.VISIBLE);
        imvDelete = findViewById(R.id.imvDelete);
        //
        recycleViewResult = findViewById(R.id.recyclerViewResult);

/*        btnUndo = findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(this);*/

        initGooglePlaceAPI();
        findViewById(R.id.imvGetMyLocation).setOnClickListener(this);
        if (shouldAskPermissions()) {
            if (isNetworkOffline()) {
                Toast.makeText(this, getString(R.string.txt_please_enable_internet_and_gps), Toast.LENGTH_SHORT).show();
            }
            askPermissions();
        }
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.CREATE_FILED);
        }
        currentLocationManager = new CurrentLocationManager(MapsFragmentActivity.this, this);
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

    /*private void createMarkerIcons() {
        if (bigMarker == null) {
            try {
                pinLocation = Utils.getFromDrawable(this, R.drawable.ic_svg_location_map);
                bigMarker = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon);
                BigMarkerClicked = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon_selected);
                smallMarker = Utils.getFromDrawable(this, R.drawable.ic_svg_polygon_child);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

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
                @SuppressLint("RestrictedApi") Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
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
                            findViewById(R.id.imvLocation).setVisibility(View.GONE);
                            location.setLongitude(place.getLatLng().longitude);
                            location.setLatitude(place.getLatLng().latitude);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18), MapsFragmentActivity.this);
//                            mMap.addMarker(new MarkerOptions().position(place.getLatLng()).draggable(true)
//                                    .icon(Utils.getFromDrawable(MapsFragmentActivity.this, R.drawable.new_icon_location_svg)).title(place.getAddress().toString()));
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
            case R.id.tvToolbarRight:
                EKMainFieldActivity.startActivity(MapsFragmentActivity.this);
                break;
            case R.id.tvChangePosition:
                ((TextView) findViewById(R.id.tvMovePositison)).setText(getString(R.string.txt_move_the_pin_to_the_position));
                isBtnDetermineArea = false;
                mMap.clear();
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 25), MapsFragmentActivity.this);
                    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                            .icon(pinLocation).title(location.getProvider()));
                }
                findViewById(R.id.cardSearchLocation).setVisibility(View.VISIBLE);
                tvDetermineTheArea.setVisibility(View.VISIBLE);
                edtAutocomplete.setText("");
                mMap.setOnMapClickListener(null);
                mMap.setOnMarkerDragListener(this);
                mMap.setOnMarkerClickListener(this);

                break;
            case R.id.imvGetMyLocation:
                currentLocationManager.requestLocationUpdates();
                break;
            case R.id.tv_determine_the_area:
                mLatLngList.clear();
                mLatLngList.add(new CustomLatLngObject(new LatLng(location.getLatitude(), location.getLongitude()), ""));
                createField.setListPolygon(mLatLngList);
                EKAddFieldStepFourActivity.startActivity(this, createField);
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
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        // mMap.setOnMyLocationButtonClickListener(this);

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        // If we don't use onMapLoadedCallback: java.lang.IllegalStateException: Error using newLatLngBounds(LatLngBounds, int):
        // Map size can't be 0. Most likely, layout has not yet occurred for the map view.
        // Either wait until layout has occurred or use newLatLngBounds(LatLngBounds, int, int, int) which allows you to specify the map's dimensions.
        mMap.setOnMapLoadedCallback(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_RUNTIME_PERMISSION:


                //else
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                break;
            default:
                break;
        }
    }

    private void showDialogEnableGPS() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Permissions Required")
                .setMessage("You have forcefully denied some of the required permissions " +
                        "for this action. Please open settings, go to permissions and allow them.")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        moveToCurrentLocation();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onMapLoaded() {

        //    createMarkerIcons();

        // NOTE: Don't set this listeners in onCreate().
        // Error will happen if user clicks on Clear or Done while map is being loaded.
        tvDetermineTheArea.setOnClickListener(this);
        // show the temp polygon after rotating.
        //set marker onclickListener

        location = new Location("");
    }

    @Override
    public void onFinish() {
        mMap.getUiSettings().setScrollGesturesEnabled(true);

    }

    @Override
    public void onCancel() {

    }

    //if countmarker <3 we need add marker on map and save latLng to mLatlnglist until count marker=3 we will draw it on map
    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    //while user is dragging a marker
    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    @Override
    protected void onDestroy() {
        //  clearTemp();
        if (currentLocationManager != null) {
            currentLocationManager.stopRequestLocationUpdates();
            currentLocationManager = null;
        }
        if (mMap != null) {
            mMap.clear();
            mMap = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    private Location lo = null;

    private void moveToCurrentLocation() {
        boolean isGPSEnabled;
        // flag for network status
        boolean isNetworkEnabled;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lo = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lo == null) {
                if (isGPSEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lo = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }
        if (lo != null) {
            if (location == null) {
                location = new Location("");
            }
            location = lo;
        /*    location.setLongitude(lo.getLongitude());
            location.setLatitude(lo.getLatitude());*/
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                        location.getLongitude()), 16), MapsFragmentActivity.this);
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // show an explanation to the user
                // Good practise: don't block thread after the user sees the explanation, try again to request the permission.
            } else {
                // request the permission.
                // CALLBACK_NUMBER is a integer constants
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_RUNTIME_PERMISSION);
                // The callback method gets the result of the request.
            }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onCameraIdle() {
        findViewById(R.id.imvLocation).setVisibility(View.VISIBLE);
        mMap.clear();
        CameraPosition cameraPosition = mMap.getCameraPosition();
        String title = Utils.getAddressFromLatLong(getApplicationContext(), cameraPosition.target.latitude, cameraPosition.target.longitude);
        if (location == null) {
            location = new Location("");
        }
        location.setLatitude(cameraPosition.target.latitude);
        location.setLongitude(cameraPosition.target.longitude);
        createField.setAddress(title);
        ((TextView) findViewById(R.id.tvNameAddress)).setText(title);
     /*   mMap.addMarker(new MarkerOptions().position(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude))
                .icon(pinLocation).title(Utils.getAddressFromLatLong(getApplicationContext(),
                        cameraPosition.target.latitude, cameraPosition.target.longitude)));*/
    }

    @Override
    public void onCameraMove() {
        ((TextView) findViewById(R.id.tvNameAddress)).setText(getString(R.string.loading));
    }

    @Override
    public void returnCurrentLocation(Location location) {
        if (mMap != null) {
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                        location.getLongitude()), 16), MapsFragmentActivity.this);
            }
        }
    }
}
