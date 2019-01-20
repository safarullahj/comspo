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
import com.mspo.comspo.data.remote.model.responses.profile_view.auditor.AuditorProfileViewResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.ProfileService;
import com.mspo.comspo.ui.widgets.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuditorProfileActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private CircleImageView profileImage;
    private AppCompatTextView userName;
    private AppCompatTextView homeAddress;
    private AppCompatTextView email;
    private AppCompatTextView phone;
    private AppCompatTextView education;
    private AppCompatTextView trainingYear;
    private AppCompatTextView trainingDetails;
    private AppCompatTextView experiance;
    private AppCompatTextView per_approved;
    private AppCompatTextView per_completed;
    private AppCompatTextView per_pending;
    private AppCompatTextView per_ongoing;
    private AppCompatTextView per_newlyAssigned;
    private AppCompatTextView cnt_approved;
    private AppCompatTextView cnt_completed;
    private AppCompatTextView cnt_pending;
    private AppCompatTextView cnt_ongoing;
    private AppCompatTextView cnt_newlyAssigned;

    public static Intent getIntent(Context context) {
        return new Intent(context, AuditorProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditor_profile);

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
        phone = findViewById(R.id.txt_phone);
        education = findViewById(R.id.txt_education);
        trainingYear = findViewById(R.id.txt_training_year);
        trainingDetails = findViewById(R.id.txt_trainingDetails);
        experiance = findViewById(R.id.txt_experience);
        per_approved = findViewById(R.id.txt_per_approved);
        per_completed = findViewById(R.id.txt_per_completed);
        per_pending = findViewById(R.id.txt_per_pending);
        per_ongoing = findViewById(R.id.txt_per_ongoing);
        per_newlyAssigned = findViewById(R.id.txt_per_newlyAssigned);
        cnt_approved = findViewById(R.id.txt_cnt_approved);
        cnt_completed = findViewById(R.id.txt_cnt_completed);
        cnt_pending = findViewById(R.id.txt_cnt_pending);
        cnt_ongoing = findViewById(R.id.txt_cnt_ongoing);
        cnt_newlyAssigned = findViewById(R.id.txt_cnt_newlyAssigned);


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewProfileData();
    }

    private void viewProfileData() {

        if (Connectivity.checkInternetIsActive(AuditorProfileActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(ProfileService.class)
                    .getAuditorProfile(PrefManager.getAccessToken(AuditorProfileActivity.this), "1")
                    .enqueue(new Callback<AuditorProfileViewResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AuditorProfileViewResponse> call, @NonNull Response<AuditorProfileViewResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    if (response.body().getProfilePic() != null && !response.body().getProfilePic().equals("")) {
                                        try {
                                            Glide.with(AuditorProfileActivity.this)
                                                    .load(response.body().getProfilePic())
                                                    .into(profileImage);
                                        } catch (Exception ignored) {
                                        }
                                    }

                                    userName.setText(response.body().getName());
                                    homeAddress.setText(checkText(response.body().getAddress()));
                                    email.setText(checkText(response.body().getEmail()));
                                    phone.setText(checkText(response.body().getMobile()));
                                    education.setText(checkText(response.body().getEducation()));
                                    trainingYear.setText(checkText(response.body().getTrainingYear()));
                                    trainingDetails.setText(checkText(response.body().getTrainingDetails()));
                                    experiance.setText(checkText(response.body().getExperience()));


                                    per_approved.setText(String.valueOf(response.body().getApprovedPercentage()));
                                    per_completed.setText(String.valueOf(response.body().getCompletedPercentage()));
                                    per_pending.setText(String.valueOf(response.body().getPendingPercentage()));
                                    per_ongoing.setText(String.valueOf(response.body().getOngoingPercentage()));
                                    per_newlyAssigned.setText(String.valueOf(response.body().getNewlyAssignedPercentage()));


                                    cnt_approved.setText(String.valueOf(response.body().getApprovedCount()));
                                    cnt_completed.setText(String.valueOf(response.body().getCompletedCount()));
                                    cnt_pending.setText(String.valueOf(response.body().getPendingCount()));
                                    cnt_ongoing.setText(String.valueOf(response.body().getOngoingCount()));
                                    cnt_newlyAssigned.setText(String.valueOf(response.body().getNewlyAssignedCount()));


                                }


                            } else {
                                    /*ErrorResponse error = ErrorUtils.parseError(response);


                                    if (!error.getErrorMessage().equals("")) {
                                        Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    } else {*/
                                Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                //}

                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<AuditorProfileViewResponse> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(findViewById(android.R.id.content), "SOmething went wrong. Try again...", Snackbar.LENGTH_LONG)
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
            startActivity(ProfileEditActivity.getIntent(AuditorProfileActivity.this));
        }

        return super.onOptionsItemSelected(item);
    }
}
