package com.example.zahidmarkertest.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zahidmarkertest.R;
import com.example.zahidmarkertest.model.LocationDataModel;
import com.example.zahidmarkertest.model.general.GeneralResponseModel;
import com.example.zahidmarkertest.utils.Resource;
import com.example.zahidmarkertest.utils.Status;
import com.example.zahidmarkertest.viewmodel.LocationDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.google.gson.reflect.TypeToken.get;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    AlertDialog pleaseWaitDialog;
    Context context;
    private LocationDataViewModel mViewModel;
    private ClusterManager<LocationDataModel> mClusterManager;
    private GoogleMap googleMap;

    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_CODE = 1234;
    private boolean mLocationPermissions = false;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    public static final float DEFAULT_ZOOM = 12f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        pleaseWaitDialog = new SpotsDialog(context, R.style.SpotDialogTheme);
        mViewModel = ViewModelProviders.of(this).get(LocationDataViewModel.class);
        mViewModel.init();
        mViewModel.getLocationDataAPI();

        mViewModel.getLocationsData().observe(this, new Observer<Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>>() {
            @Override
            public void onChanged(Resource<GeneralResponseModel<ArrayList<LocationDataModel>>> response) {
                if (response.getStatus() == Status.status.SUCCESS) {
                  showHidePleaseWaitDialog(1);
                  setUpClusters(response.getData().getData());


                } else if (response.getStatus() == Status.status.ERROR) {
                  showHidePleaseWaitDialog(1);
                    Toast.makeText(context,response.getMessage(),Toast.LENGTH_SHORT).show();

                } else if (response.getStatus() == Status.status.LOADING) {
                    showHidePleaseWaitDialog(0);

                }
            }
        });

        setMap();


    }
    private void getLoctionPermissions() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissions = true;
                // goToCurrentLocation();
                goToCurrentLocation();

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, permission, LOCATION_PERMISSION_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, permission, LOCATION_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissions = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissions = false;
                        } else {
                            mLocationPermissions = true;
                            //getLocation
                            goToCurrentLocation();
                        }
                    }
                }
            }
        }
    }
    public void showHidePleaseWaitDialog(int i) {
//        if (i == 0) {
//            pleaseWaitDialog.setCancelable(false);
//            pleaseWaitDialog.show();
//        } else if (i == 1) {
//            pleaseWaitDialog.dismiss();
//        }
    }
    private void setUpClusters(ArrayList<LocationDataModel> list) {

        mClusterManager = new ClusterManager<>(this, googleMap);
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        addMarkrWithCluster(list);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);

    }
    private void addMarkrWithCluster(List<LocationDataModel> list) {

        for (int i = 0; i < list.size(); i++) {
            LocationDataModel locationDataModel = list.get(i);
            if (locationDataModel != null) {
                LatLng latLng = locationDataModel.getLatLng();
                if (latLng != null) {
                    if (i == 0) {
                        googleMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                    }
                    mClusterManager.addItem(locationDataModel);
                }
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getLoctionPermissions();
        }

    }
    private void setMap() {
        SupportMapFragment mapFrag;
        mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }


    public void goToCurrentLocation() {
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (mLocationPermissions) {
            Task loaction = mfusedLocationProviderClient.getLastLocation();
            loaction.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation == null) {
//                            defaultCamera(new LatLng(30.666121, 73.102013));
                            statusCheck();
                        } else {
                            defaultCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                        }

                    } else {
                    }
                }
            });
        } else {
            getLoctionPermissions();
        }
    }
    private void defaultCamera(LatLng cameraPosition) {

        final CameraPosition driverlalongCamera =
                new CameraPosition.Builder().target(cameraPosition)
                        .zoom(DEFAULT_ZOOM)
                        .tilt(40)
                        .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(driverlalongCamera));
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }
    private void buildAlertMessageNoGps() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, Please Enable it?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
