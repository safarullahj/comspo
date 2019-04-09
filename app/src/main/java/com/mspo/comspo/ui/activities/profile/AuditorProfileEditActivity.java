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
import com.mspo.comspo.data.remote.model.requests.AuditorProfileUpdateRequest;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.auditor.AuditorProfileViewResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.ProfileService;
import com.mspo.comspo.ui.widgets.CircleImageView;
import com.mspo.comspo.utils.LocaleManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuditorProfileEditActivity extends AppCompatActivity {

    private static final String KEY_PROFILE_DATA = "key.profiledata";

    private ProgressBar progressBar;
    private CircleImageView profileImage;
    private AppCompatTextView userName;
    private AppCompatEditText address;
    private AppCompatEditText email;
    private AppCompatEditText mobile;
    private AppCompatEditText landline;
    private AppCompatEditText education;
    private AppCompatEditText experiance;

    private AuditorProfileViewResponse auditorProfileViewResponse = null;

    public static Intent getIntent(Context context, AuditorProfileViewResponse auditorProfileViewResponse) {
        Intent intent = new Intent(context, AuditorProfileEditActivity.class);
        intent.putExtra(KEY_PROFILE_DATA, auditorProfileViewResponse);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditor_profile_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress);
        profileImage = findViewById(R.id.imageViewProfileImage);
        userName = findViewById(R.id.txt_user_name);
        address = findViewById(R.id.edtTxt_address);
        email = findViewById(R.id.edtTxt_email);
        mobile = findViewById(R.id.edtTxt_mobile);
        landline = findViewById(R.id.edtTxt_landline);
        education = findViewById(R.id.edtTxt_qualification);
        experiance = findViewById(R.id.edtTxt_experience);

        if (getIntent().getExtras() != null) {
            auditorProfileViewResponse = (AuditorProfileViewResponse) getIntent().getSerializableExtra(KEY_PROFILE_DATA);

            if(auditorProfileViewResponse != null) {

                if(auditorProfileViewResponse.getProfilePic()!= null && !auditorProfileViewResponse.getProfilePic().equals("")) {
                    try {
                        Glide.with(AuditorProfileEditActivity.this)
                                .load(auditorProfileViewResponse.getProfilePic())
                                .into(profileImage);
                    } catch (Exception ignored) {
                    }
                }

                userName.setText(auditorProfileViewResponse.getUsername());
                address.setText(auditorProfileViewResponse.getAddress());
                email.setText(auditorProfileViewResponse.getEmail());
                mobile.setText(auditorProfileViewResponse.getMobile());
                landline.setText(auditorProfileViewResponse.getLandline());
                education.setText(auditorProfileViewResponse.getEducation());
                experiance.setText(auditorProfileViewResponse.getExperience());

            }
        }


        /*progressBar;
        profileImage;
        userName;
        homeAddress;
        email;
        mobile;
        landline;
        education;
        experiance;*/
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

        if (Connectivity.checkInternetIsActive(AuditorProfileEditActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            AuditorProfileUpdateRequest auditorProfileUpdateRequest = new AuditorProfileUpdateRequest(auditorProfileViewResponse.getName(),
                    email.getText().toString(),
                    mobile.getText().toString(),
                    landline.getText().toString(),
                    address.getText().toString(),
                    education.getText().toString(),
                    experiance.getText().toString(),
                    auditorProfileViewResponse.getTrainingDetails());

            APIClient.getClient()
                    .create(ProfileService.class)
                    .editAuditorProfile(PrefManager.getAccessToken(AuditorProfileEditActivity.this),
                            PrefManager.getFarmId(AuditorProfileEditActivity.this),auditorProfileUpdateRequest)
                    .enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    if (response.body().getStatus()) {
                                        finish();
                                        Toast.makeText(AuditorProfileEditActivity.this,R.string.update_successfully,Toast.LENGTH_LONG).show();
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
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

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

    }
}
