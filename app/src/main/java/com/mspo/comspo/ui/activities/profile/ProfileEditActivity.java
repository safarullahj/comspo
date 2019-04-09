package com.mspo.comspo.ui.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.ProfileUpdateRequest;
import com.mspo.comspo.data.remote.model.responses.profile_view.ProfileViewResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.UpdateProfileResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.ProfileService;
import com.mspo.comspo.ui.widgets.CircleImageView;
import com.mspo.comspo.utils.LocaleManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileEditActivity extends AppCompatActivity {

    private static final String KEY_PROFILE_DATA = "key.profiledata";

    private ProgressBar progressBar;
    private CircleImageView profileImage;
    private AppCompatTextView userName;
    private AppCompatEditText homeAddress;
    private AppCompatEditText email;
    private AppCompatEditText phone;
    private AppCompatEditText farmName;
    private AppCompatEditText farmAddress;
    private AppCompatEditText district;
    private AppCompatEditText landCondetion;
    private AppCompatEditText MPOBLicenseNumber;
    private AppCompatEditText grantAreaInHa;

    private ProfileViewResponse profileViewResponse = null;


    public static Intent getIntent(Context context, ProfileViewResponse profileViewResponse) {
        Intent intent = new Intent(context, ProfileEditActivity.class);
        intent.putExtra(KEY_PROFILE_DATA, profileViewResponse);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        progressBar = findViewById(R.id.progress);
        profileImage = findViewById(R.id.imageViewProfileImage);
        userName = findViewById(R.id.txt_user_name);
        homeAddress = findViewById(R.id.edtTxt_home_address);
        email = findViewById(R.id.edtTxt_email);
        phone = findViewById(R.id.edtTxt_phone);
        farmName = findViewById(R.id.edtTxt_farm_name);
        farmAddress = findViewById(R.id.edtTxt_farm_address);
        district = findViewById(R.id.edtTxt_district);
        landCondetion = findViewById(R.id.edtTxt_land_condition);
        MPOBLicenseNumber = findViewById(R.id.edtTxt_MPOBLicenseNumber);
        grantAreaInHa = findViewById(R.id.edtTxt_GrantAreaInHa);

        if (getIntent().getExtras() != null) {
            profileViewResponse = (ProfileViewResponse) getIntent().getSerializableExtra(KEY_PROFILE_DATA);

            if(profileViewResponse != null) {

                if(profileViewResponse.getProfilePic()!= null && !profileViewResponse.getProfilePic().equals("")) {
                    try {
                        Glide.with(ProfileEditActivity.this)
                                .load(profileViewResponse.getProfilePic())
                                .into(profileImage);
                    } catch (Exception ignored) {
                    }
                }

                userName.setText(profileViewResponse.getUsername());
                homeAddress.setText(profileViewResponse.getHomeAddress());
                email.setText(profileViewResponse.getEmail());
                phone.setText(profileViewResponse.getPhone());
                farmName.setText(profileViewResponse.getName());
                farmAddress.setText(profileViewResponse.getAddress());
                district.setText(profileViewResponse.getDistrict());
                landCondetion.setText(profileViewResponse.getLandCondition());
                MPOBLicenseNumber.setText(profileViewResponse.getLicenceNo());
                grantAreaInHa.setText(profileViewResponse.getGrantArea());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_submit_profile) {
            updateProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateProfile() {

        if (Connectivity.checkInternetIsActive(ProfileEditActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(farmAddress.getText().toString(),
                    profileViewResponse.getCountryCode(),
                    district.getText().toString(),
                    email.getText().toString(),
                    profileViewResponse.getFarmerId(),
                    grantAreaInHa.getText().toString(),
                    homeAddress.getText().toString(),
                    profileViewResponse.getIcNo(),
                    landCondetion.getText().toString(),
                    MPOBLicenseNumber.getText().toString(),
                    farmName.getText().toString(),
                    phone.getText().toString());

            APIClient.getClient()
                    .create(ProfileService.class)
                    .editProfile(PrefManager.getAccessToken(ProfileEditActivity.this),
                            PrefManager.getFarmId(ProfileEditActivity.this),profileUpdateRequest)
                    .enqueue(new Callback<UpdateProfileResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    if (response.body().getStatus()) {
                                        finish();
                                        Toast.makeText(ProfileEditActivity.this,R.string.update_successfully,Toast.LENGTH_LONG).show();
                                    }else {
                                        Snackbar.make(findViewById(android.R.id.content), R.string.fail_to_update, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }

                                }


                            } else {
                                    /*ErrorResponse error = ErrorUtils.parseError(response);


                                    if (!error.getErrorMessage().equals("")) {
                                        Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    } else {*/
                                /*Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                Snackbar.make(findViewById(android.R.id.content), R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                //}

                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(findViewById(android.R.id.content), ""+t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(findViewById(android.R.id.content), R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        /*progressBar;
        profileImage;
        userName;
        homeAddress;
        email;
        phone;
        farmName;
        farmAddress;
        district;
        landCondetion;
        MPOBLicenseNumber;
        grantAreaInHa;*/
    }
}

