package com.mspo.comspo.ui.activities.audit_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.group_audit_details.GroupAuditDetailsResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.IndividualAuditDetailsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAuditDetailsActivity extends AppCompatActivity {

    private static final String KEY_AUDIT_ID = "key.auditId";
    private static final String KEY_FARM_NAME = "key.farmName";
    private static final String KEY_AUDIT_CATEGORY = "key.category";

    private ProgressBar progressBar;
    private AppCompatTextView startDate;
    private AppCompatTextView endDate;
    private AppCompatTextView audit_status;
    private AppCompatTextView group_name;
    private AppCompatTextView head_farmer;
    private AppCompatTextView No_of_farmers;
    private AppCompatTextView phone;
    private AppCompatTextView address;
    private AppCompatTextView category;

    private LinearLayout subAudits_container;

    private int auditId;
    private String farmName, auditStatus, audit_category;

    public static Intent getIntent(Context context, Integer auditId, String farmName, String category) {
        Intent intent = new Intent(context, GroupAuditDetailsActivity.class);
        intent.putExtra(KEY_AUDIT_ID, auditId);
        intent.putExtra(KEY_FARM_NAME, farmName);
        intent.putExtra(KEY_AUDIT_CATEGORY, category);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_audit_details);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        startDate = findViewById(R.id.text_startDate);
        endDate = findViewById(R.id.text_endDate);
        audit_status = findViewById(R.id.text_audit_status);
        group_name = findViewById(R.id.text_group_name);
        head_farmer = findViewById(R.id.text_head_farmer);
        No_of_farmers = findViewById(R.id.text_No_of_farmers);
        phone = findViewById(R.id.text_phone);
        address = findViewById(R.id.text_address);
        category = findViewById(R.id.text_category);

        subAudits_container = findViewById(R.id.subAudits_container);

        if (getIntent().getExtras() != null) {
            auditId = getIntent().getExtras().getInt(KEY_AUDIT_ID, 0);
            farmName = getIntent().getExtras().getString(KEY_FARM_NAME, "");
            audit_category = getIntent().getExtras().getString(KEY_AUDIT_CATEGORY, "");

            Log.e("de_data", "frm_name: " + farmName);
            Log.e("de_data", "audit id: " + auditId);

            getSupportActionBar().setTitle(farmName);

        }

        /*progressBar;
        startDate;
        endDate;
        audit_status;
        group_name;
        head_farmer;
        No_of_farmers;
        phone;
        address;
        category;*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (auditId != 0) {
            getAuditorGroupAuditDetails();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAuditorGroupAuditDetails() {

        if (Connectivity.checkInternetIsActive(GroupAuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .getAuditorGroupAuditDetails(auditId,
                            PrefManager.getAccessToken(GroupAuditDetailsActivity.this),
                            PrefManager.getFarmId(GroupAuditDetailsActivity.this),
                            "")
                    .enqueue(new Callback<GroupAuditDetailsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GroupAuditDetailsResponse> call, @NonNull Response<GroupAuditDetailsResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));
                                    audit_status.setText(checkText(response.body().getAuditStatus()));
                                    group_name.setText(checkText(response.body().getGroupName()));
                                    head_farmer.setText(checkText(response.body().getHeadFarmer()));
                                    No_of_farmers.setText(checkText(response.body().getNoOfFarmers()));
                                    phone.setText(checkText(response.body().getPhone()));
                                    address.setText(checkText(response.body().getAddress()));
                                    category.setText(checkText(response.body().getCategory()));

                                    new SubAuditListing(GroupAuditDetailsActivity.this,
                                            response.body().getSubAudits(),
                                            response.body().getAuditId(),
                                            subAudits_container,response.body().getYear(),
                                            farmName);


                                }


                            } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/

                                Snackbar.make(progressBar, "response fail", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                             progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<GroupAuditDetailsResponse> call, @NonNull Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(record_inspection, "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(progressBar, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            Log.e("err_:", "msg : " + t.getMessage());
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(progressBar, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
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
}
