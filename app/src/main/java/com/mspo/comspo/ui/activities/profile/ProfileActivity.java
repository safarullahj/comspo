package com.mspo.comspo.ui.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.profile_view.ProfileViewResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.ProfileService;
import com.mspo.comspo.ui.widgets.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private CircleImageView profileImage;
    private AppCompatTextView userName;
    private AppCompatTextView homeAddress;
    private AppCompatTextView email;
    private AppCompatTextView icNo;
    private AppCompatTextView phone;
    private AppCompatTextView farmName;
    private AppCompatTextView farmAddress;
    private AppCompatTextView district;
    private AppCompatTextView landCondetion;
    private AppCompatTextView MPOBLicenseNumber;
    private AppCompatTextView grantAreaInHa;
    private AppCompatTextView certificationBody;
    private AppCompatTextView ext_approved;
    private AppCompatTextView ext_completed;
    private AppCompatTextView ext_pending;
    private AppCompatTextView ext_ongoing;
    private AppCompatTextView ext_newlyAssigned;
    private AppCompatTextView int_approved;
    private AppCompatTextView int_completed;
    private AppCompatTextView int_pending;
    private AppCompatTextView int_ongoing;
    private AppCompatTextView int_newlyAssigned;

    private ProfileViewResponse profileViewResponse = null;


    public static Intent getIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress);
        profileImage = findViewById(R.id.imageViewProfileImage);
        userName = findViewById(R.id.txt_user_name);
        homeAddress = findViewById(R.id.txt_home_address);
        email = findViewById(R.id.txt_email);
        icNo = findViewById(R.id.txt_ICNo);
        phone = findViewById(R.id.txt_phone);
        farmName = findViewById(R.id.txt_farm_name);
        farmAddress = findViewById(R.id.txt_fatm_address);
        district = findViewById(R.id.txt_district);
        landCondetion = findViewById(R.id.txt_landCondetion);
        MPOBLicenseNumber = findViewById(R.id.txt_MPOBLicenseNumber);
        grantAreaInHa = findViewById(R.id.txt_GrantAreaInHa);
        certificationBody = findViewById(R.id.txt_CertificationBody);
        ext_approved = findViewById(R.id.txt_ext_approved);
        ext_completed = findViewById(R.id.txt_ext_completed);
        ext_pending = findViewById(R.id.txt_ext_pending);
        ext_ongoing = findViewById(R.id.txt_ext_ongoing);
        ext_newlyAssigned = findViewById(R.id.txt_ext_newlyAssigned);
        int_approved = findViewById(R.id.txt_int_approved);
        int_completed = findViewById(R.id.txt_int_completed);
        int_pending = findViewById(R.id.txt_int_pending);
        int_ongoing = findViewById(R.id.txt_int_ongoing);
        int_newlyAssigned = findViewById(R.id.txt_int_newlyAssigned);


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewProfileData();
    }

    private void viewProfileData() {

        if (Connectivity.checkInternetIsActive(ProfileActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(ProfileService.class)
                    .getProfile(PrefManager.getAccessToken(ProfileActivity.this),PrefManager.getFarmId(ProfileActivity.this))
                    .enqueue(new Callback<ProfileViewResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ProfileViewResponse> call, @NonNull Response<ProfileViewResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    profileViewResponse = response.body();

                                    if(response.body().getProfilePic()!= null && !response.body().getProfilePic().equals("")) {
                                        try {
                                            Glide.with(ProfileActivity.this)
                                                    .load(response.body().getProfilePic())
                                                    .into(profileImage);
                                        } catch (Exception ignored) {
                                        }
                                    }

                                    userName.setText(response.body().getUsername());
                                    homeAddress.setText(checkText(response.body().getHomeAddress()));
                                    email.setText(checkText(response.body().getEmail()));
                                    icNo.setText(checkText(response.body().getIcNo()));
                                    phone.setText(checkText(response.body().getPhone()));
                                    farmName.setText(checkText(response.body().getName()));
                                    farmAddress.setText(checkText(response.body().getAddress()));
                                    district.setText(checkText(response.body().getDistrict()));
                                    landCondetion.setText(checkText(response.body().getLandCondition()));
                                    MPOBLicenseNumber.setText(checkText(response.body().getLicenceNo()));
                                    grantAreaInHa.setText(checkText(response.body().getGrantArea()));
                                    certificationBody.setText(checkText(response.body().getCbName()));


                                    if (response.body().getExternalAuditStatus() != null && response.body().getExternalAuditStatus().size() > 0) {
                                        ext_approved.setText(String.valueOf(response.body().getExternalAuditStatus().get(0).getApprovedCount()));
                                        ext_completed.setText(String.valueOf(response.body().getExternalAuditStatus().get(0).getCompletedCount()));
                                        ext_pending.setText(String.valueOf(response.body().getExternalAuditStatus().get(0).getPendingCount()));
                                        ext_ongoing.setText(String.valueOf(response.body().getExternalAuditStatus().get(0).getOngoingCount()));
                                        ext_newlyAssigned.setText(String.valueOf(response.body().getExternalAuditStatus().get(0).getNewlyAssignedCount()));
                                    }

                                    if (response.body().getInternalAuditStatus() != null && response.body().getInternalAuditStatus().size() > 0) {
                                        int_approved.setText(String.valueOf(response.body().getInternalAuditStatus().get(0).getApprovedCount()));
                                        int_completed.setText(String.valueOf(response.body().getInternalAuditStatus().get(0).getCompletedCount()));
                                        int_pending.setText(String.valueOf(response.body().getInternalAuditStatus().get(0).getPendingCount()));
                                        int_ongoing.setText(String.valueOf(response.body().getInternalAuditStatus().get(0).getOngoingCount()));
                                        int_newlyAssigned.setText(String.valueOf(response.body().getInternalAuditStatus().get(0).getNewlyAssignedCount()));
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
                                Snackbar.make(findViewById(android.R.id.content), "Response Fail", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                //}

                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<ProfileViewResponse> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(findViewById(android.R.id.content), ""+t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private String checkText(String text) {
        if (text != null && !text.equals("")) {
            return text;
        } else {
            return "- - -";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_edit_profile) {
            if(profileViewResponse != null) {
                startActivity(ProfileEditActivity.getIntent(ProfileActivity.this, profileViewResponse));
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

