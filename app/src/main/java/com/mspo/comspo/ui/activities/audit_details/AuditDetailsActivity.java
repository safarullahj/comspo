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
import android.widget.ProgressBar;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.data.remote.webservice.IndividualAuditDetailsService;
import com.mspo.comspo.ui.activities.audit_sheet.AuditSheetActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuditDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String KEY_AUDIT_ID = "key.auditId";
    private static final String KEY_FARM_NAME = "key.farmName";

    private ProgressBar progressBar;
    private MaterialButton record_inspection;

    private AppCompatTextView farmGroup;
    private AppCompatTextView address;
    private AppCompatTextView mobileNumber;
    private AppCompatTextView year;
    private AppCompatTextView audit_type;
    private AppCompatTextView startDate;
    private AppCompatTextView endDate;

    private int auditId;
    private String farmName;

    private IndividualAuditDetailsResponse auditDetailsResponse = null;

    public static Intent getIntent(Context context, Integer auditId, String farmName) {
        Intent intent = new Intent(context, AuditDetailsActivity.class);
        intent.putExtra(KEY_AUDIT_ID, auditId);
        intent.putExtra(KEY_FARM_NAME, farmName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_details);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        record_inspection = findViewById(R.id.btn_Record);
        record_inspection.setOnClickListener(this);

        farmGroup = findViewById(R.id.text_farmGroup);
        address = findViewById(R.id.text_address);
        mobileNumber = findViewById(R.id.text_mobileNumber);
        year = findViewById(R.id.text_year);
        audit_type = findViewById(R.id.text_audit_type);
        startDate = findViewById(R.id.text_startDate);
        endDate = findViewById(R.id.text_endDate);

        if (getIntent().getExtras() != null) {
            auditId = getIntent().getExtras().getInt(KEY_AUDIT_ID,0);
            farmName = getIntent().getExtras().getString(KEY_FARM_NAME,"");

            Log.e("de_data", "frm_name: "+farmName);
            Log.e("de_data", "audit id: "+auditId);

            getSupportActionBar().setTitle(farmName);

            getDetails();

        }


    }

    private void getDetails() {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .getAuditDetails(auditId,
                            PrefManager.getAccessToken(AuditDetailsActivity.this),
                            PrefManager.getFarmId(AuditDetailsActivity.this),
                            "")
                    .enqueue(new Callback<IndividualAuditDetailsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<IndividualAuditDetailsResponse> call, @NonNull Response<IndividualAuditDetailsResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    auditDetailsResponse = response.body();

                                    farmGroup.setText(checkText(response.body().getFarmName()));
                                    address.setText(checkText(response.body().getAddress()));
                                    mobileNumber.setText(response.body().getCountryCode()+"-"+checkText(response.body().getPhone()));
                                    year.setText(checkText(response.body().getYear()));
                                    audit_type.setText(checkText(response.body().getAuditType()));
                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));
                                }


                            } else {

                                Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<IndividualAuditDetailsResponse> call, @NonNull Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(record_inspection, "SOmething went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            Log.e("err_:" , "msg : "+t.getMessage());
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(record_inspection, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_Record:
                getAuditSheet();
                break;
        }
    }

    private void getAuditSheet() {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(AuditSheetService.class)
                    .getAuditSheet(auditId,
                            PrefManager.getAccessToken(AuditDetailsActivity.this),
                            PrefManager.getFarmId(AuditDetailsActivity.this),
                            "")
                    .enqueue(new Callback<AuditSheetResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AuditSheetResponse> call, @NonNull Response<AuditSheetResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null && auditDetailsResponse != null) {
                                    startActivity(AuditSheetActivity.getIntent(AuditDetailsActivity.this,response.body(),auditDetailsResponse));
                                }


                            } else {

                                Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<AuditSheetResponse> call, @NonNull Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(record_inspection, "SOmething went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(record_inspection, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }
}

