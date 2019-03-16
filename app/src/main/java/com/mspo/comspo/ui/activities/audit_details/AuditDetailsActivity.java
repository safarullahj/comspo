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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.AuditDetail;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.SmallHolderAuditSheetSaveRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.SmallHolderAuditSheetSaveResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.data.remote.webservice.IndividualAuditDetailsService;
import com.mspo.comspo.ui.activities.audit_sheet.AuditSheetActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuditDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_AUDIT_ID = "key.auditId";
    private static final String KEY_FARM_NAME = "key.farmName";

    private ProgressBar progressBar;
    private MaterialButton record_inspection, btn_Submit;

    private AppCompatTextView farmGroup;
    private AppCompatTextView address;
    private AppCompatTextView mobileNumber;
    private AppCompatTextView year;
    private AppCompatTextView audit_type;
    private AppCompatTextView startDate;
    private AppCompatTextView endDate;

    private LinearLayout perfomance_container;

    private int auditId;
    private String farmName;

    private AuditSheetResponse auditSheetResponse;

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
        record_inspection.setClickable(false);

        btn_Submit = findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(this);
       // btn_Submit.setClickable(false);

        farmGroup = findViewById(R.id.text_farmGroup);
        address = findViewById(R.id.text_address);
        mobileNumber = findViewById(R.id.text_mobileNumber);
        year = findViewById(R.id.text_year);
        audit_type = findViewById(R.id.text_audit_type);
        startDate = findViewById(R.id.text_startDate);
        endDate = findViewById(R.id.text_endDate);

        perfomance_container = findViewById(R.id.perfomance_container);
        perfomance_container.setVisibility(View.GONE);

        if (getIntent().getExtras() != null) {
            auditId = getIntent().getExtras().getInt(KEY_AUDIT_ID, 0);
            farmName = getIntent().getExtras().getString(KEY_FARM_NAME, "");

            Log.e("de_data", "frm_name: " + farmName);
            Log.e("de_data", "audit id: " + auditId);

            getSupportActionBar().setTitle(farmName);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        record_inspection.setClickable(false);

        if (auditId != 0) {
            if (auditDetailsResponse == null) {
                getDetails();
            } else {
                getAuditSheet();
            }
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
                                    mobileNumber.setText(response.body().getCountryCode() + "-" + checkText(response.body().getPhone()));
                                    year.setText(checkText(response.body().getYear()));
                                    audit_type.setText(checkText(response.body().getAuditType()));
                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));

                                    getAuditSheet();
                                }


                            } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/

                                Snackbar.make(record_inspection, "response fail", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            // progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<IndividualAuditDetailsResponse> call, @NonNull Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(record_inspection, "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(record_inspection, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            Log.e("err_:", "msg : " + t.getMessage());
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

        switch (view.getId()) {
            case R.id.btn_Record:
                //getAuditSheet();

                if (auditSheetResponse != null && auditDetailsResponse != null) {
                    startActivity(AuditSheetActivity.getIntent(AuditDetailsActivity.this, auditSheetResponse, auditDetailsResponse));
                } else {
                    /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    Snackbar.make(record_inspection, "Auditsheet response null", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.btn_Submit:

                if (auditSheetResponse != null && auditDetailsResponse != null) {

                    boolean f = true;
                    for (int i = 0; i < auditSheetResponse.getChapters().size(); i++) {
                        if (auditSheetResponse.getChapters().get(i).getGraphColor().equals("info") ||
                                auditSheetResponse.getChapters().get(i).getGraphColor().equals("primary")) {
                            f = false;
                            break;
                        }
                    }
                    if (f) {
                        //btn_Submit.setClickable(true);
                        submitAudit();
                    } else {
                        //btn_Submit.setClickable(false);
                        Snackbar.make(btn_Submit, "Audit not Completed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }else {
                    Snackbar.make(btn_Submit, "Something Went Wrong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;
        }
    }

    private void submitAudit() {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            AuditDetail auditDetail = new AuditDetail();

            auditDetail.setAddress(auditDetailsResponse.getAddress());
            auditDetail.setDistrict(auditDetailsResponse.getDistrict());
            auditDetail.setPhone(auditDetailsResponse.getPhone());

            auditDetail.setRegistrationNumber("gfgdgs");
            auditDetail.setGrantAreaInHa("gghghsa");
            auditDetail.setTehsil("tehsil");
            auditDetail.setVillage("village");
            auditDetail.setOfficerName("officer_name");
            auditDetail.setCrop("crop");
            auditDetail.setTotalProductionArea("total_production_area");

            List<AuditDetail> auditDetailList = new ArrayList<>();
            auditDetailList.add(auditDetail);


            SmallHolderAuditSheetSaveRequest auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(PrefManager.getFarmId(AuditDetailsActivity.this),
                    "true",
                    auditSheetResponse.getChapters(),
                    auditDetailList
            );


            APIClient.getClient()
                    .create(AuditSheetService.class)
                    .saveFarmerAuditSheet(auditDetailsResponse.getAuditId(), PrefManager.getAccessToken(AuditDetailsActivity.this), auditSheetSaveRequest)
                    .enqueue(new Callback<SmallHolderAuditSheetSaveResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SmallHolderAuditSheetSaveResponse> call, @NonNull Response<SmallHolderAuditSheetSaveResponse> response) {

                            Log.e("res_:", "res : " + response.body());
                            ErrorResponse error = ErrorUtils.parseError(response);
                            if (error != null)
                                Log.e("res_:", "err : " + error.getErrorMessage());

                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    //finish();
                                    Snackbar.make(btn_Submit, "Submit Successfully", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
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
                        public void onFailure(@NonNull Call<SmallHolderAuditSheetSaveResponse> call, @NonNull Throwable t) {


                                /*Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                            Snackbar.make(btn_Submit, ""+t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {

            Snackbar.make(btn_Submit, "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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

                                    if (response.body().getChapters().size() > 0) {
                                        perfomance_container.setVisibility(View.VISIBLE);
                                        new PerfomanceListing(AuditDetailsActivity.this, response.body().getChapters(), perfomance_container);


                                        /*boolean f = true;
                                        for (int i = 0; i < response.body().getChapters().size(); i++) {
                                            if (response.body().getChapters().get(i).getGraphColor().equals("info")) {
                                                f = false;
                                                break;
                                            }
                                        }
                                        if (f) {
                                            btn_Submit.setClickable(true);
                                        } else {
                                            btn_Submit.setClickable(false);
                                            *//*Snackbar.make(btn_Submit, "Audit not Completed", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();*//*
                                        }*/

                                    } else {
                                        perfomance_container.setVisibility(View.GONE);
                                    }

                                    auditSheetResponse = response.body();
                                    record_inspection.setClickable(true);
                                } else {
                                    perfomance_container.setVisibility(View.GONE);
                                }


                            } else {
                                perfomance_container.setVisibility(View.GONE);

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                Snackbar.make(record_inspection, "response fail", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<AuditSheetResponse> call, @NonNull Throwable t) {

                            Log.e("RetrofitErr", t.getMessage());
                            perfomance_container.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(record_inspection, "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(record_inspection, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            perfomance_container.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Snackbar.make(record_inspection, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }
}

