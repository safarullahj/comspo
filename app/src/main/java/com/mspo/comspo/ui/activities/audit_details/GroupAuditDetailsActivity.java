package com.mspo.comspo.ui.activities.audit_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.AuditAcceptRequest;
import com.mspo.comspo.data.remote.model.responses.AuditorStatusResponse;
import com.mspo.comspo.data.remote.model.responses.group_audit_details.GroupAuditDetailsResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.IndividualAuditDetailsService;
import com.mspo.comspo.utils.LocaleManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAuditDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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

    private MaterialButton btn_Accept,btn_Reject;

    private LinearLayout subAudits_container,auditors_container,status_container;
    private AppCompatTextView auditors_container_head;

    private int auditId;
    private String farmName, auditStatus, audit_category;

    private GroupAuditDetailsResponse groupAuditDetailsResponse = null;

    public static Intent getIntent(Context context, Integer auditId, String farmName, String category) {
        Intent intent = new Intent(context, GroupAuditDetailsActivity.class);
        intent.putExtra(KEY_AUDIT_ID, auditId);
        intent.putExtra(KEY_FARM_NAME, farmName);
        intent.putExtra(KEY_AUDIT_CATEGORY, category);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
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

        auditors_container_head = findViewById(R.id.auditors_container_head);
        auditors_container_head.setVisibility(View.GONE);
        auditors_container = findViewById(R.id.auditors_container);
        auditors_container.setVisibility(View.GONE);

        subAudits_container = findViewById(R.id.subAudits_container);

        status_container = findViewById(R.id.status_container);
        status_container.setVisibility(View.GONE);
        btn_Accept = findViewById(R.id.btn_Accept);
        btn_Accept.setOnClickListener(this);
        btn_Reject = findViewById(R.id.btn_Reject);
        btn_Reject.setOnClickListener(this);

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
        groupAuditDetailsResponse = null;

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

                                    groupAuditDetailsResponse = response.body();

                                    if(response.body().getAuditorAuditStatus()){
                                        status_container.setVisibility(View.GONE);

                                    }else {
                                        status_container.setVisibility(View.VISIBLE);
                                    }

                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));
                                    audit_status.setText(checkText(response.body().getAuditStatus()));
                                    group_name.setText(checkText(response.body().getGroupName()));
                                    head_farmer.setText(checkText(response.body().getHeadFarmer()));
                                    No_of_farmers.setText(checkText(response.body().getNoOfFarmers()));
                                    phone.setText(checkText(response.body().getPhone()));
                                    address.setText(checkText(response.body().getAddress()));
                                    category.setText(checkText(response.body().getCategory()));

                                    if(response.body().getAuditors() != null && response.body().getAuditors().size() > 0) {
                                        auditors_container.setVisibility(View.VISIBLE);
                                        auditors_container_head.setVisibility(View.VISIBLE);
                                        new GroupAuditorListing(GroupAuditDetailsActivity.this,
                                                response.body().getAuditors(),
                                                auditors_container);
                                    }

                                    if(response.body().getSubAudits() != null && response.body().getSubAudits().size() > 0) {
                                        new SubAuditListing(GroupAuditDetailsActivity.this,
                                                response.body().getSubAudits(),
                                                response.body().getAuditId(),
                                                subAudits_container, response.body().getYear(),
                                                farmName,
                                                response.body().getAuditorAuditStatus());
                                    }


                                }


                            } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/

                                Snackbar.make(progressBar, R.string.response_fail, Snackbar.LENGTH_LONG)
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
            Snackbar.make(progressBar, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Accept:
                if (groupAuditDetailsResponse != null) {
                    auditor_audit_status("accept");
                }
                break;

            case R.id.btn_Reject:
                if (groupAuditDetailsResponse != null) {
                    auditor_audit_status("reject");
                }
                break;
        }
    }

    private void auditor_audit_status(String auditor_status) {

        if (Connectivity.checkInternetIsActive(this)) {


            progressBar.setVisibility(View.VISIBLE);

            AuditAcceptRequest auditAcceptRequest = new AuditAcceptRequest(PrefManager.getFarmId(GroupAuditDetailsActivity.this), auditor_status);


            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .auditStatus(PrefManager.getAccessToken(GroupAuditDetailsActivity.this), groupAuditDetailsResponse.getAuditId(), auditAcceptRequest)
                    .enqueue(new Callback<AuditorStatusResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AuditorStatusResponse> call, @NonNull Response<AuditorStatusResponse> response) {

                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess()) {
                                    if(auditor_status.equals("accept")) {
                                        Snackbar.make(startDate, R.string.audit_accepted, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        getAuditorGroupAuditDetails();
                                    }else {
                                        Toast.makeText(GroupAuditDetailsActivity.this , R.string.audit_rejected , Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } else {
                                        /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();*/
                                    Snackbar.make(startDate, R.string.fail_to_update, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                            } else {
                                    /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();*/
                                Snackbar.make(startDate, R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<AuditorStatusResponse> call, @NonNull Throwable t) {
                            progressBar.setVisibility(View.GONE);
                                /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                            Snackbar.make(startDate, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
        } else {
            Snackbar.make(startDate, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }
}
