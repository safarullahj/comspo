package com.mspo.comspo.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mspo.comspo.BuildConfig;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.LogoutRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.LogoutResponse;
import com.mspo.comspo.data.remote.model.responses.weather.WeatherResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.LogoutService;
import com.mspo.comspo.data.remote.webservice.WeatherService;
import com.mspo.comspo.ui.activities.login.LoginActivity;
import com.mspo.comspo.ui.activities.profile.AuditorProfileActivity;
import com.mspo.comspo.ui.activities.profile.ProfileActivity;
import com.mspo.comspo.ui.activities.settings.SettingsActivity;
import com.mspo.comspo.ui.fragments.home_externalaudit.HomeFragmentExternalAudit;
import com.mspo.comspo.ui.fragments.home_smallholder.HomeFragmentSmallholder;
import com.mspo.comspo.ui.fragments.home_smallholder.external.SmallholderExternalFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_0 = 0;
    public static final int FRAGMENT_1 = 1;
    public static final int FRAGMENT_2 = 2;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 1002;
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<String> yearList = new ArrayList<>();
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private AppCompatTextView temperature, condition;
    private AppCompatImageView imageView_weather;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    //private BottomNavigationView bottomNavigation;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    private RadioGroup statusRadioGroup;
    private AppCompatEditText search;
    private FilterInterface filterInterfaceExternal, filterInterfaceInternal;
    private Spinner yearSpinner;
    private ArrayAdapter<String> adapter;


    public void setFilterListenerExternal(FilterInterface anInterface) {
        filterInterfaceExternal = anInterface;
    }

    public void setFilterListenerInternal(FilterInterface anInterface) {
        filterInterfaceInternal = anInterface;
    }
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bottom_nav_external:
                    fab.hide();
                    updateMainFragment(Pages.PAGE_0.getPagePosition());
                    return true;
                case R.id.bottom_nav_internal:
                   // fab.show();
                    updateMainFragment(Pages.PAGE_1.getPagePosition());
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temperature = findViewById(R.id.temperature);
        condition = findViewById(R.id.condition);
        imageView_weather = findViewById(R.id.imageView_weather);
        imageView_weather.setVisibility(View.GONE);

        ImageView refreshLocation = findViewById(R.id.refreshLocation);
        refreshLocation.setOnClickListener(view -> {
            /*if (mRequestingLocationUpdates && checkPermissions()) {
                startLocationUpdates();
            }*/
            startLocationButtonClick();
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                if (drawer.isDrawerOpen(GravityCompat.END)) {

                    PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
                    String flt = managerFilter.getFilterStatus();

                    switch (flt) {

                        case "":
                            statusRadioGroup.check(R.id.rad_showAll);
                            break;
                        case "newly_assigned":
                            statusRadioGroup.check(R.id.rad_newlyAssigned);
                            break;
                        case "pending":
                            statusRadioGroup.check(R.id.rad_pending);
                            break;
                        case "on_going":
                            statusRadioGroup.check(R.id.rad_onGoing);
                            break;
                        case "completed":
                            statusRadioGroup.check(R.id.rad_notApproved);
                            break;
                        case "approved":
                            statusRadioGroup.check(R.id.rad_approved);
                            break;

                    }

                    search.setText(managerFilter.getFilterKey());

                    if (managerFilter.getFilterYear().equals("")) {
                        yearSpinner.setSelection(0);
                    } else {
                        int spinnerPosition = adapter.getPosition(managerFilter.getFilterYear());
                        yearSpinner.setSelection(spinnerPosition);
                    }


                }
            }
        };


        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationViewFilter = findViewById(R.id.nav_view_filter);
        View headerView = navigationViewFilter.getHeaderView(0);
        statusRadioGroup = headerView.findViewById(R.id.statusRadio);
        search = headerView.findViewById(R.id.edt_search);

        MaterialButton apply = headerView.findViewById(R.id.btnApply);
        apply.setOnClickListener(view -> {

            PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);

            int id = statusRadioGroup.getCheckedRadioButtonId();
            if (id == R.id.rad_showAll) {
                //Log.e("Filter_:", "status : showAll ");
                managerFilter.setFilterStatus("");
            } else if (id == R.id.rad_newlyAssigned) {
                //Log.e("Filter_:", "status : newlyAssigned");
                managerFilter.setFilterStatus("newly_assigned");
            } else if (id == R.id.rad_pending) {
                //Log.e("Filter_:", "status : pending");
                managerFilter.setFilterStatus("pending");
            } else if (id == R.id.rad_onGoing) {
                //Log.e("Filter_:", "status : onGoing");
                managerFilter.setFilterStatus("on_going");
            } else if (id == R.id.rad_notApproved) {
                //Log.e("Filter_:", "status : notApproved");
                managerFilter.setFilterStatus("completed");
            } else if (id == R.id.rad_approved) {
                //Log.e("Filter_:", "status : approved");
                managerFilter.setFilterStatus("approved");
            }

            if (search.getText() != null)
                managerFilter.setFilterKey(search.getText().toString());

            if (String.valueOf(yearSpinner.getSelectedItem()).equals("Select")) {
                managerFilter.setFilterYear("");
            } else {
                managerFilter.setFilterYear(String.valueOf(yearSpinner.getSelectedItem()));
            }

            filterInterfaceExternal.filter();
            filterInterfaceInternal.filter();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            drawer.closeDrawer(GravityCompat.END);
        });

        MaterialButton reset = headerView.findViewById(R.id.reset);
        reset.setOnClickListener(view -> {
            PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
            managerFilter.clearFilter();
            filterInterfaceExternal.filter();
            filterInterfaceInternal.filter();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            drawer.closeDrawer(GravityCompat.END);
        });


        yearSpinner = headerView.findViewById(R.id.spnYear);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        yearList.add("Select");
        for (int i = currentYear; i >= 2000; i--) {
            yearList.add("" + i);
        }

        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, yearList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

        if (PrefManager.getUserType(MainActivity.this).equals("admin")) {
            updateMainFragment(Pages.PAGE_2.getPagePosition());
        } else {
            /*if(currentFragment instanceof SmallholderExternalFragment) {
                updateMainFragment(Pages.PAGE_0.getPagePosition());
            }else if(currentFragment instanceof HomeFragmentSmallholder){
                updateMainFragment(Pages.PAGE_1.getPagePosition());
            }else {*/
            updateMainFragment(Pages.PAGE_1.getPagePosition());
            //}
        }

        init();

        //startLocationButtonClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e("loging", "main : " + PrefManager.getUserType(MainActivity.this));
        if (PrefManager.getUserType(MainActivity.this).equals("admin")) {
            navigationView.getMenu().findItem(R.id.nav_audits).setVisible(false);
            //bottomNavigation.setVisibility(View.GONE);
            fab.hide();
            navigationView.getMenu().getItem(1).setChecked(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_external_audits).setVisible(false);
            //bottomNavigation.setVisibility(View.VISIBLE);
            fab.hide();
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (currentFragment instanceof HomeFragmentSmallholder) {
                PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
                managerFilter.clearFilter();
                finish();
                super.onBackPressed();
            }/*else if(currentFragment instanceof HomeFragmentSmallholder){
                updateMainFragment(Pages.PAGE_0.getPagePosition());
            }*/ else if (currentFragment instanceof HomeFragmentExternalAudit) {
                finish();
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_audits) {
            //bottomNavigation.setVisibility(View.VISIBLE);
            fab.hide();
            //bottomNavigation.setSelectedItemId(R.id.bottom_nav_external);
            updateMainFragment(Pages.PAGE_1.getPagePosition());
        } else if (id == R.id.nav_profile) {
            if (PrefManager.getUserType(MainActivity.this).equals("admin")) {
                startActivity(AuditorProfileActivity.getIntent(MainActivity.this));
            } else {
                startActivity(ProfileActivity.getIntent(MainActivity.this));
            }

        } else if (id == R.id.nav_settings) {
            startActivity(SettingsActivity.getIntent(MainActivity.this));
        } else if (id == R.id.nav_signout) {

            if (Connectivity.checkInternetIsActive(MainActivity.this)) {
                if (PrefManager.getUserId(MainActivity.this) != 0) {

                    //Log.e("logTest", "user_id : " + PrefManager.getUserId(MainActivity.this));
                    //Log.e("logTest", "user_accessToken : " + PrefManager.getAccessToken(MainActivity.this));
                    LogoutRequest logoutRequest = new LogoutRequest(PrefManager.getUserId(MainActivity.this));

                    APIClient.getClient()
                            .create(LogoutService.class)
                            .doLogout(PrefManager.getAccessToken(MainActivity.this), logoutRequest)
                            .enqueue(new Callback<LogoutResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<LogoutResponse> call, @NonNull Response<LogoutResponse> response) {

                                    if (response.isSuccessful()) {
                                        if (response.body() != null) {
                                            if (response.body().getStatus()) {
                                                PrefManager.clearLoginShared(MainActivity.this);
                                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                finish();
                                            } else {
                                                Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }
                                        }

                                    } else {
                                        ErrorResponse error = ErrorUtils.parseError(response);
                                        //Log.e("logTest", "fail" + error.getErrorMessage());

                                        if (error.getErrorMessage() != null && !error.getErrorMessage().equals("")) {
                                            Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }

                                    }

                                }

                                @Override
                                public void onFailure(@NonNull Call<LogoutResponse> call, @NonNull Throwable t) {
                                    Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            });
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        } else if (id == R.id.nav_external_audits) {
            //bottomNavigation.setVisibility(View.GONE);
            fab.hide();
            updateMainFragment(Pages.PAGE_2.getPagePosition());
        }

        //DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateMainFragment(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(position);

        currentFragment = getFragment(position);

        //Log.e("TEST", "position_:" + position + " , frg_:" + fragment);
        if (fragment != null) {
            transaction.replace(R.id.fragment, fragment);
        }
        transaction.commit();
    }

    private Fragment getFragment(int position) {
        if (position == Pages.PAGE_0.getPagePosition()) {
            return Pages.PAGE_0.getFragment();
        } else if (position == Pages.PAGE_1.getPagePosition()) {
            return Pages.PAGE_1.getFragment();
        } else if (position == Pages.PAGE_2.getPagePosition()) {
            return Pages.PAGE_2.getFragment();
        }
        return null;
    }

    /*private String getFragmentTitle(int position) {
        if (position == Pages.PAGE_0.getPagePosition()) {
            return "HOME";
        } else if (position == Pages.PAGE_1.getPagePosition()) {
            return "HOME";
        } else if (position == Pages.PAGE_2.getPagePosition()) {
            return "HOME";
        }
        return null;
    }*/

    private void init() {
        //Log.e("LOCTEST", "init");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                updateLocationUI(location);
                //Log.e("LOCTEST", "updateLocationUI: " + location.getLatitude());
                //get location
            }
        };

        mRequestingLocationUpdates = false;
        //mRequestingLocationUpdates = true;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        PrefManager prefManager = new PrefManager(MainActivity.this);

        if (!prefManager.getLocationLat().equals("") && !prefManager.getLocationLon().equals("")) {
            invalidateAndFetchWeather(Double.valueOf(prefManager.getLocationLat()), Double.valueOf(prefManager.getLocationLon()));
        }
    }

    private void updateLocationUI(Location currentLocation) {
        if (currentLocation != null) {
            stopLocationButtonClick();

            Toast.makeText(this, "Location: " + currentLocation.getLatitude() + ", " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

            PrefManager prefManager = new PrefManager(MainActivity.this);

            prefManager.setLocationLat(String.valueOf(currentLocation.getLatitude()));
            prefManager.setLocationLon(String.valueOf(currentLocation.getLongitude()));

            invalidateAndFetchWeather(currentLocation.getLatitude(), currentLocation.getLongitude());
            //Log.e("LOCTEST", "lat: " + currentLocation.getLatitude() + " long: " + currentLocation.getLongitude());
        }
    }

    private void invalidateAndFetchWeather(double latitude, double longitude) {

        //Log.e("LOCTEST", "fetch: ");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mRetrofit.create(WeatherService.class)
                .getWeatherData(latitude,
                        longitude,
                        "d0372bd419bbe3feea2c991161d774ef",
                        "metric")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {

                        //Log.e("LOCTEST", "response: ");
                        if (response.body() != null) {
                            //Log.e("LOCTEST", "not null: ");
                            /*Log.e("LOCTEST", "description : " + response.body().getWeather().get(0).getDescription() +
                                    " " + response.body().getMain().getTemp() + "°C");*/

                            temperature.setVisibility(View.VISIBLE);
                            condition.setVisibility(View.VISIBLE);
                            String temp = String.format(Locale.getDefault(), "%.0f °C", response.body().getMain().getTemp());
                            temperature.setText(temp);
                            condition.setText(response.body().getWeather().get(0).getDescription());

                            try {
                                imageView_weather.setVisibility(View.VISIBLE);
                                Glide.with(MainActivity.this)
                                        .load("http://openweathermap.org/img/w/" + response.body().getWeather().get(0).getIcon() + ".png")
                                        .into(imageView_weather);
                            } catch (Exception e) {
                                imageView_weather.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                        //Log.e("LOCTEST", "fail: " + t.getMessage());
                        // daily_container.setVisibility(View.GONE);
                        //customViewMarketingWidget.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        //Log.e("LOCTEST", "startLocationUpdates");
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, locationSettingsResponse -> {
                    //Log.e(TAG, "All location settings are satisfied.");

                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, Looper.myLooper());

                })
                .addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();

                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            /*Log.e(TAG, "Location settings are not satisfied. " +
                                    "Attempting to upgrade location settings ");*/
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                //Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, " +
                                    "and cannot be fixed here. Fix in Settings.";
                            //Log.e(TAG, errorMessage);

                            Toast.makeText(this, errorMessage,
                                    Toast.LENGTH_LONG).show();
                    }

                });
    }

    public void startLocationButtonClick() {
        //Log.e("LOCTEST", "button click");
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        //Log.e("LOCTEST", "onPermissionGranted");
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            //Log.e("LOCTEST", "onPermissionDenied");
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void stopLocationButtonClick() {
        mRequestingLocationUpdates = false;
        stopLocationUpdates();
    }

    public void stopLocationUpdates() {
        //Log.e("LOCTEST", "stopLocationUpdates: ");
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, task -> Log.i(TAG,
                        "Location updates stopped!"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        //Log.e(TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        /*Log.e(TAG, "User chose not to make required " +
                                "location settings changes.");*/
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    private enum Pages {
        PAGE_0(FRAGMENT_0, SmallholderExternalFragment.newInstance()),
        PAGE_1(FRAGMENT_1, HomeFragmentSmallholder.newInstance()),
        PAGE_2(FRAGMENT_2, HomeFragmentExternalAudit.newInstance());

        int position;
        Fragment fragment;

        Pages(int position, Fragment fragment) {
            this.position = position;
            this.fragment = fragment;
        }

        int getPagePosition() {
            return position;
        }

        Fragment getFragment() {
            return fragment;
        }
    }
}
