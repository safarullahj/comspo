package com.mspo.comspo.ui.activities.audit_details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.AuditAcceptRequest;
import com.mspo.comspo.data.remote.model.requests.EditAuditRequest;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.AuditDetail;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.SmallHolderAuditSheetSaveRequest;
import com.mspo.comspo.data.remote.model.responses.AuditorStatusResponse;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.EditAuditResponse;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.SmallHolderAuditSheetSaveResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;
import com.mspo.comspo.data.remote.model.responses.offline_audit_sheet.OfflineAuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.result_sheet.ResultSheetResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.data.remote.webservice.IndividualAuditDetailsService;
import com.mspo.comspo.ui.activities.audit_sheet.AuditSheetActivity;
import com.mspo.comspo.ui.activities.result_sheet.ResultSheetActivity;

import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuditDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_AUDIT_ID = "key.auditId";
    private static final String KEY_SUBAUDIT_ID = "key.subauditId";
    private static final String KEY_FARM_NAME = "key.farmName";
    private static final String KEY_AUDIT_CATEGORY = "key.category";

    private ProgressBar progressBar;
    private MaterialButton record_inspection, btn_Sync, btn_Submit, btn_Result, btn_edit_details, btn_edit_duration,
            btn_Accept, btn_Reject, btn_offline;

    private AppCompatTextView smallholderName;
    private AppCompatTextView MPOBLicenseNumber;
    private AppCompatTextView ICNo;
    private AppCompatTextView grantAreaInHa;
    private AppCompatTextView landUse;
    private AppCompatTextView smallHolderFarmAddress;
    private AppCompatTextView homeAddress;
    private AppCompatTextView district;
    private AppCompatTextView contactDetails;
    private AppCompatTextView generalComments;
    private AppCompatTextView startDate;
    private AppCompatTextView endDate;
    private AppCompatTextView txt_offline;

    private LinearLayout perfomance_container, auditors_container, status_container;
    private AppCompatTextView auditors_container_head;

    private int auditId;
    private String farmName, auditStatus, category, subAuditId;
    private boolean status = false;

    private AuditSheetResponse auditSheetResponse = null;

    private IndividualAuditDetailsResponse auditDetailsResponse = null;

    private AlertDialog aDialog;
    private AlertDialog bDialog;
    private DatePickerDialog datePickerDialog;
    private long startTimeStamp = 0, endTimeStamp = 0;
    private ModelMapper modelMapper;

    private Realm realm;

    public static Intent getIntent(Context context, Integer auditId, Integer subAuditId, String farmName, String category) {
        Intent intent = new Intent(context, AuditDetailsActivity.class);
        intent.putExtra(KEY_AUDIT_ID, auditId);
        intent.putExtra(KEY_SUBAUDIT_ID, subAuditId);
        intent.putExtra(KEY_FARM_NAME, farmName);
        intent.putExtra(KEY_AUDIT_CATEGORY, category);
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

        modelMapper = new ModelMapper();

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        record_inspection = findViewById(R.id.btn_Record);
        record_inspection.setOnClickListener(this);
        record_inspection.setClickable(false);

        btn_Sync = findViewById(R.id.btn_Sync);
        btn_Sync.setOnClickListener(this);

        btn_Submit = findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(this);
        // btn_Submit.setClickable(false);

        btn_Result = findViewById(R.id.btn_Result);
        btn_Result.setOnClickListener(this);

        btn_edit_details = findViewById(R.id.btn_edit_details);
        btn_edit_details.setOnClickListener(this);

        btn_edit_duration = findViewById(R.id.btn_edit_duration);
        btn_edit_duration.setOnClickListener(this);

        btn_offline = findViewById(R.id.btn_offline);
        btn_offline.setOnClickListener(this);
        btn_offline.setVisibility(View.GONE);
        txt_offline = findViewById(R.id.txt_offline);
        txt_offline.setVisibility(View.GONE);


        /*farmGroup = findViewById(R.id.text_farmGroup);
        address = findViewById(R.id.text_address);
        mobileNumber = findViewById(R.id.text_mobileNumber);
        year = findViewById(R.id.text_year);
        audit_type = findViewById(R.id.text_audit_type);
        startDate = findViewById(R.id.text_startDate);
        endDate = findViewById(R.id.text_endDate);*/

        smallholderName = findViewById(R.id.text_Smallholder_Name);
        MPOBLicenseNumber = findViewById(R.id.text_MPOB_License_Number);
        ICNo = findViewById(R.id.text_ICNo);
        grantAreaInHa = findViewById(R.id.text_Grant_Area_In_Ha);
        landUse = findViewById(R.id.text_Land_Use);
        smallHolderFarmAddress = findViewById(R.id.text_Small_Holder_Farm_Address);
        homeAddress = findViewById(R.id.text_Home_Address);
        district = findViewById(R.id.text_District);
        contactDetails = findViewById(R.id.text_Contact_Details);
        generalComments = findViewById(R.id.text_General_Comments);
        startDate = findViewById(R.id.text_startDate);
        endDate = findViewById(R.id.text_endDate);

        perfomance_container = findViewById(R.id.perfomance_container);
        perfomance_container.setVisibility(View.GONE);

        auditors_container_head = findViewById(R.id.auditors_container_head);
        auditors_container_head.setVisibility(View.GONE);
        auditors_container = findViewById(R.id.auditors_container);
        auditors_container.setVisibility(View.GONE);

        status_container = findViewById(R.id.status_container);
        status_container.setVisibility(View.GONE);
        btn_Accept = findViewById(R.id.btn_Accept);
        btn_Accept.setOnClickListener(this);
        btn_Reject = findViewById(R.id.btn_Reject);
        btn_Reject.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            auditId = getIntent().getExtras().getInt(KEY_AUDIT_ID, 0);
            subAuditId = String.valueOf(getIntent().getExtras().getInt(KEY_SUBAUDIT_ID, 0));
            if (subAuditId.equals("0")) {
                subAuditId = null;
            }
            farmName = getIntent().getExtras().getString(KEY_FARM_NAME, "");
            category = getIntent().getExtras().getString(KEY_AUDIT_CATEGORY, "");
            //auditStatus = getIntent().getExtras().getString(KEY_AUDIT_STATUS, "");
            Log.e("SUB_ADT", "subAuditId: " + subAuditId);

            Log.e("de_data", "frm_name: " + farmName);
            Log.e("de_data", "audit id: " + auditId);

            getSupportActionBar().setTitle(farmName);

        }


        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        record_inspection.setClickable(false);
        btn_offline.setVisibility(View.GONE);
        txt_offline.setVisibility(View.GONE);

        if (auditId != 0) {
            if (auditDetailsResponse == null) {
                if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {
                    getDetails();
                } else {
                    getAuditorSingleAuditDetails();

                }
            } else {

                callAuditsheet();

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            datePickerDialog.dismiss();
        }

        if (aDialog != null && aDialog.isShowing()) {
            aDialog.dismiss();
        }
        if (bDialog != null && bDialog.isShowing()) {
            bDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getDetails() {
        auditDetailsResponse = null;
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

                                    if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator") && auditDetailsResponse.getAuditType().equals("Internal Audit")) {
                                        btn_Sync.setVisibility(View.VISIBLE);
                                        btn_Submit.setVisibility(View.VISIBLE);
                                        btn_edit_details.setVisibility(View.VISIBLE);
                                        btn_edit_duration.setVisibility(View.VISIBLE);
                                        btn_offline.setVisibility(View.VISIBLE);

                                    } else {
                                        btn_Sync.setVisibility(View.GONE);
                                        btn_Submit.setVisibility(View.GONE);
                                        btn_edit_details.setVisibility(View.GONE);
                                        btn_edit_duration.setVisibility(View.GONE);
                                        btn_offline.setVisibility(View.GONE);
                                    }

                                    startDate.setText(getString(R.string.text_start_date) + checkText(response.body().getStartDate()));
                                    endDate.setText(getString(R.string.text_end_date) + checkText(response.body().getEndDate()));

                                    smallholderName.setText(checkText(response.body().getFarmName()));
                                    MPOBLicenseNumber.setText(checkText(response.body().getLicenceNo()));
                                    ICNo.setText(checkText(response.body().getIcNo()));
                                    grantAreaInHa.setText(checkText(response.body().getGrantArea()));
                                    landUse.setText(checkText(response.body().getLandCondition()));
                                    smallHolderFarmAddress.setText(checkText(response.body().getAddress()));
                                    homeAddress.setText(checkText(response.body().getHomeAddress()));
                                    district.setText(checkText(response.body().getDistrict()));
                                    contactDetails.setText(response.body().getCountryCode() + "-" + checkText(response.body().getPhone()));

                                    generalComments.setText(checkText(response.body().getComment()));

                                    /*farmGroup.setText(checkText(response.body().getFarmName()));
                                    address.setText(checkText(response.body().getAddress()));
                                    mobileNumber.setText(response.body().getCountryCode() + "-" + checkText(response.body().getPhone()));
                                    year.setText(checkText(response.body().getYear()));
                                    audit_type.setText(checkText(response.body().getAuditType()));
                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));*/

                                    auditStatus = auditDetailsResponse.getAuditStatus();

                                    switch (auditStatus) {
                                        case "Not Approved Audit":
                                            status = true;
                                            break;
                                        case "Approved Audit":
                                            status = true;
                                            break;
                                        default:
                                            status = false;
                                            break;
                                    }

                                    //getAuditSheet();
                                    callAuditsheet();

                                }


                            } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/

                                Snackbar.make(record_inspection, R.string.response_fail, Snackbar.LENGTH_LONG)
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
            Snackbar.make(record_inspection, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
    }

    private void getAuditorSingleAuditDetails() {
        Log.e("SUB_ADT", "single in: " + subAuditId);
        auditDetailsResponse = null;
        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .getAuditorSingleAuditDetails(auditId,
                            PrefManager.getAccessToken(AuditDetailsActivity.this),
                            PrefManager.getFarmId(AuditDetailsActivity.this),
                            subAuditId)
                    .enqueue(new Callback<IndividualAuditDetailsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<IndividualAuditDetailsResponse> call, @NonNull Response<IndividualAuditDetailsResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    auditDetailsResponse = response.body();

                                    if (response.body().getAuditorAuditStatus()) {
                                        status_container.setVisibility(View.GONE);
                                        btn_Sync.setVisibility(View.VISIBLE);
                                        btn_Submit.setVisibility(View.VISIBLE);
                                        btn_edit_details.setVisibility(View.VISIBLE);
                                        btn_Result.setVisibility(View.VISIBLE);
                                        record_inspection.setVisibility(View.VISIBLE);
                                        btn_offline.setVisibility(View.VISIBLE);

                                    } else {
                                        status_container.setVisibility(View.VISIBLE);
                                        btn_Sync.setVisibility(View.GONE);
                                        btn_Submit.setVisibility(View.GONE);
                                        btn_edit_details.setVisibility(View.GONE);
                                        btn_Result.setVisibility(View.GONE);
                                        record_inspection.setVisibility(View.GONE);
                                        btn_offline.setVisibility(View.GONE);
                                    }

                                    /*if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator") && auditDetailsResponse.getAuditType().equals("Internal Audit")) {
                                        btn_Sync.setVisibility(View.VISIBLE);
                                        btn_Submit.setVisibility(View.VISIBLE);
                                        btn_edit_details.setVisibility(View.VISIBLE);
                                        btn_edit_duration.setVisibility(View.VISIBLE);

                                    } else {*/
                                       /* btn_Sync.setVisibility(View.GONE);
                                        btn_Submit.setVisibility(View.GONE);
                                        btn_edit_details.setVisibility(View.GONE);*/
                                    btn_edit_duration.setVisibility(View.GONE);
                                    // }

                                    startDate.setText(getString(R.string.text_start_date) + checkText(response.body().getStartDate()));
                                    endDate.setText(getString(R.string.text_end_date) + checkText(response.body().getEndDate()));

                                    smallholderName.setText(checkText(response.body().getFarmName()));
                                    MPOBLicenseNumber.setText(checkText(response.body().getLicenceNo()));
                                    ICNo.setText(checkText(response.body().getIcNo()));
                                    grantAreaInHa.setText(checkText(response.body().getGrantArea()));
                                    landUse.setText(checkText(response.body().getLandCondition()));
                                    smallHolderFarmAddress.setText(checkText(response.body().getAddress()));
                                    homeAddress.setText(checkText(response.body().getHomeAddress()));
                                    district.setText(checkText(response.body().getDistrict()));
                                    contactDetails.setText(response.body().getCountryCode() + "-" + checkText(response.body().getPhone()));

                                    if (response.body().getAuditors() != null && response.body().getAuditors().size() > 0) {
                                        auditors_container.setVisibility(View.VISIBLE);
                                        auditors_container_head.setVisibility(View.VISIBLE);
                                        new AuditorListing(AuditDetailsActivity.this,
                                                response.body().getAuditors(),
                                                auditors_container);
                                    }

                                    generalComments.setText(checkText(response.body().getComment()));

                                    /*farmGroup.setText(checkText(response.body().getFarmName()));
                                    address.setText(checkText(response.body().getAddress()));
                                    mobileNumber.setText(response.body().getCountryCode() + "-" + checkText(response.body().getPhone()));
                                    year.setText(checkText(response.body().getYear()));
                                    audit_type.setText(checkText(response.body().getAuditType()));
                                    startDate.setText(checkText(response.body().getStartDate()));
                                    endDate.setText(checkText(response.body().getEndDate()));*/

                                    auditStatus = auditDetailsResponse.getAuditStatus();

                                    switch (auditStatus) {
                                        case "Not Approved Audit":
                                            status = true;
                                            break;
                                        case "Approved Audit":
                                            status = true;
                                            break;
                                        default:
                                            status = false;
                                            break;
                                    }

                                   // getAuditorAuditSheet();
                                    callAuditsheet();

                                }


                            } else {
                                Log.e("SUB_ADT", "fail 1: ");
                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/

                                Snackbar.make(record_inspection, R.string.response_fail, Snackbar.LENGTH_LONG)
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
            Snackbar.make(record_inspection, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
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
                    /*OfflineAuditSheetResponse sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                            .equalTo("auditId", auditId)
                            .findFirst();

                    if(sheetResponse != null){
                        auditSheetResponse = new AuditSheetResponse();
                        AuditSheetResponse auditResponse = modelMapper.map(sheetResponse, AuditSheetResponse.class);
                        auditSheetResponse = auditResponse;
                    }*/
                    if(subAuditId != null) {
                        startActivity(AuditSheetActivity.getIntent(AuditDetailsActivity.this, auditSheetResponse, auditDetailsResponse, auditStatus, subAuditId, Integer.valueOf(subAuditId)));
                    }else {
                        startActivity(AuditSheetActivity.getIntent(AuditDetailsActivity.this, auditSheetResponse, auditDetailsResponse, auditStatus, subAuditId,auditId));
                    }
                } else {
                    /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    Snackbar.make(record_inspection, R.string.response_null, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.btn_Sync:
                if (auditSheetResponse != null && auditDetailsResponse != null) {
                    if (!status) {

                            //btn_Submit.setClickable(true);
                        submitAudit("false", true);

                    } else {
                        Snackbar.make(btn_Submit, R.string.already_submit, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } else {
                    Snackbar.make(btn_Submit, R.string.something_wrong, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.btn_Submit:

                OfflineAuditSheetResponse sheetResponse = null;
                if(subAuditId != null){
                    sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                            .equalTo("auditId", Integer.valueOf(subAuditId))
                            .findFirst();
                }else {
                    sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                            .equalTo("auditId", auditId)
                            .findFirst();
                }

                if(sheetResponse != null){
                    Snackbar.make(btn_Submit, R.string.sync_first, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {


                    if (auditSheetResponse != null && auditDetailsResponse != null) {
                        if (!status) {

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
                                submitAudit("true", false);
                            } else {
                                //btn_Submit.setClickable(false);
                                Snackbar.make(btn_Submit, R.string.audit_not_complete, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(btn_Submit, R.string.already_submit, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    } else {
                        Snackbar.make(btn_Submit, R.string.something_wrong, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }


                break;

            case R.id.btn_Result:

                if (status) {
                    getResultSheet();
                } else {
                    Snackbar.make(btn_Submit, R.string.audit_not_complete, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;

            case R.id.btn_edit_details:
                if (auditDetailsResponse != null) {
                    showEditDialog();
                }
                break;

            case R.id.btn_edit_duration:
                if (auditDetailsResponse != null) {
                    if (!status) {
                        showDurationDialog();
                    } else {
                        Snackbar.make(btn_Submit, R.string.canot_change_date, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;

            case R.id.btn_Accept:
                if (auditDetailsResponse != null) {
                    auditor_audit_status("accept");
                }
                break;

            case R.id.btn_Reject:
                if (auditDetailsResponse != null) {
                    auditor_audit_status("reject");
                }
                break;

            case R.id.btn_offline:
                if (auditDetailsResponse != null && auditSheetResponse != null) {

                    if (!status) {

                        OfflineAuditSheetResponse offlineAuditSheetResponse = modelMapper.map(auditSheetResponse, OfflineAuditSheetResponse.class);
                        if(subAuditId != null){
                            offlineAuditSheetResponse.setAuditId(Integer.valueOf(subAuditId));
                        }else {
                            offlineAuditSheetResponse.setAuditId(auditDetailsResponse.getAuditId());
                        }

                        offlineAuditSheetResponse.setUserType(PrefManager.getUserType(AuditDetailsActivity.this));
                        offlineAuditSheetResponse.setYeare(auditDetailsResponse.getYear());


                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm_) {
                                realm_.copyToRealmOrUpdate(offlineAuditSheetResponse);
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(AuditDetailsActivity.this, "Offline Success", Toast.LENGTH_LONG).show();
                                btn_offline.setVisibility(View.GONE);
                                txt_offline.setVisibility(View.VISIBLE);
                                callAuditsheet();
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                error.printStackTrace();
                            }
                        });
                    }else {
                        Snackbar.make(btn_Submit, R.string.already_submit, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }else {
                    Snackbar.make(btn_Submit, R.string.something_wrong, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }

    private void auditor_audit_status(String auditor_status) {

        if (Connectivity.checkInternetIsActive(this)) {


            progressBar.setVisibility(View.VISIBLE);

            AuditAcceptRequest auditAcceptRequest = new AuditAcceptRequest(PrefManager.getFarmId(AuditDetailsActivity.this), auditor_status);


            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .auditStatus(PrefManager.getAccessToken(AuditDetailsActivity.this), auditDetailsResponse.getAuditId(), auditAcceptRequest)
                    .enqueue(new Callback<AuditorStatusResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AuditorStatusResponse> call, @NonNull Response<AuditorStatusResponse> response) {

                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess()) {
                                    if (auditor_status.equals("accept")) {
                                        Snackbar.make(btn_edit_duration, R.string.audit_accepted, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        getAuditorSingleAuditDetails();
                                    } else {
                                        Toast.makeText(AuditDetailsActivity.this, R.string.audit_rejected, Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } else {
                                        /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();*/
                                    Snackbar.make(btn_edit_duration, R.string.fail_to_update, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                            } else {
                                    /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();*/
                                Snackbar.make(btn_edit_duration, R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<AuditorStatusResponse> call, @NonNull Throwable t) {
                            progressBar.setVisibility(View.GONE);
                                /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                            Snackbar.make(btn_edit_duration, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
        } else {
            Snackbar.make(btn_edit_duration, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }

    private void showDurationDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View dialog = getLayoutInflater().inflate(R.layout.dialog_new_audit, null);
        dialogBuilder.setView(dialog);

        bDialog = dialogBuilder.create();
        bDialog.show();

        AppCompatTextView date_title = dialog.findViewById(R.id.date_title);
        date_title.setText(R.string.update_audit);
        final AppCompatTextView startDate = dialog.findViewById(R.id.textView_StartDate);
        final AppCompatTextView endtDate = dialog.findViewById(R.id.textView_endDate);
        AppCompatImageView startCalender = dialog.findViewById(R.id.startCalender);
        AppCompatImageView endCalender = dialog.findViewById(R.id.endCalender);
        AppCompatButton create = dialog.findViewById(R.id.btnCreate);
        create.setText(R.string.text_update);

        startDate.setText(checkText(auditDetailsResponse.getStartDate()));
        endtDate.setText(checkText(auditDetailsResponse.getEndDate()));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(auditDetailsResponse.getStartDate()));// all done
            startTimeStamp = TimeUnit.MILLISECONDS.toSeconds(cal.getTimeInMillis());
            cal.setTime(sdf.parse(auditDetailsResponse.getEndDate()));// all done
            endTimeStamp = TimeUnit.MILLISECONDS.toSeconds(cal.getTimeInMillis());

        } catch (ParseException e) {
            e.printStackTrace();
        }


        startCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(startDate);
            }
        });

        endCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(endtDate);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTimeStamp == 0) {
                    Toast.makeText(AuditDetailsActivity.this, R.string.select_start_date, Toast.LENGTH_LONG).show();
                } else if (endTimeStamp == 0) {
                    Toast.makeText(AuditDetailsActivity.this, R.string.select_end_date, Toast.LENGTH_LONG).show();
                } else if (startTimeStamp > endTimeStamp) {
                    Toast.makeText(AuditDetailsActivity.this, R.string.end_date_mismatch, Toast.LENGTH_LONG).show();
                } else {
                    if (auditDetailsResponse != null) {
                        updateAudit();
                        bDialog.dismiss();
                    }
                }

            }
        });
    }

    private void getDate(final AppCompatTextView txtDate) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        int month = monthOfYear + 1;
                        txtDate.setText("" + dayOfMonth + "/" + month + "/" + year);

                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        Log.e("ts", "ts : " + TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));

                        switch (txtDate.getId()) {
                            case R.id.textView_StartDate:
                                startTimeStamp = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                                break;
                            case R.id.textView_endDate:
                                endTimeStamp = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                                break;
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void updateAudit() {
        if (Connectivity.checkInternetIsActive(this)) {


            progressBar.setVisibility(View.VISIBLE);

            EditAuditRequest editAuditRequest = new EditAuditRequest(String.valueOf(startTimeStamp),
                    String.valueOf(endTimeStamp),
                    PrefManager.getFarmId(AuditDetailsActivity.this));

            APIClient.getClient()
                    .create(IndividualAuditDetailsService.class)
                    .editAudit(PrefManager.getAccessToken(AuditDetailsActivity.this), auditDetailsResponse.getAuditId(), editAuditRequest)
                    .enqueue(new Callback<EditAuditResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<EditAuditResponse> call, @NonNull Response<EditAuditResponse> response) {

                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    Snackbar.make(btn_edit_duration, R.string.duration_updated, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    getDetails();
                                } else {
                                        /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();*/
                                    Snackbar.make(btn_edit_duration, R.string.fail_to_update, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                            } else {
                                    /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();*/
                                Snackbar.make(btn_edit_duration, R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<EditAuditResponse> call, @NonNull Throwable t) {
                            progressBar.setVisibility(View.GONE);
                                /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                            Snackbar.make(btn_edit_duration, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
        } else {
            Snackbar.make(btn_edit_duration, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
    }

    private void getResultSheet() {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(AuditSheetService.class)
                    .getResultSheetData(auditId,
                            PrefManager.getAccessToken(AuditDetailsActivity.this),
                            PrefManager.getFarmId(AuditDetailsActivity.this))
                    .enqueue(new Callback<ResultSheetResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ResultSheetResponse> call, @NonNull Response<ResultSheetResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {
                                    Log.e("rSheet_:", "" + response.body().getChapters().size());
                                    startActivity(ResultSheetActivity.getIntent(AuditDetailsActivity.this, response.body()));

                                }


                            } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                Snackbar.make(record_inspection, R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResultSheetResponse> call, @NonNull Throwable t) {

                            Log.e("RetrofitErr", t.getMessage());
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
            Snackbar.make(record_inspection, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }


    }

    private void submitAudit(String save_submit, boolean loadDetail) {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            AuditDetail auditDetail = new AuditDetail();

            auditDetail.setAddress(auditDetailsResponse.getAddress());
            auditDetail.setComment(auditDetailsResponse.getComment());
            auditDetail.setCountryCode(auditDetailsResponse.getCountryCode());
            auditDetail.setDistrict(auditDetailsResponse.getDistrict());
            auditDetail.setEndDate(auditDetailsResponse.getEndDate());
            auditDetail.setFarmName(auditDetailsResponse.getFarmName());
            auditDetail.setGrantArea(auditDetailsResponse.getGrantArea());
            auditDetail.setHomeAddress(auditDetailsResponse.getHomeAddress());
            auditDetail.setIcNo(auditDetailsResponse.getIcNo());
            auditDetail.setLandCondition(auditDetailsResponse.getLandCondition());
            auditDetail.setLicenceNo(auditDetailsResponse.getLicenceNo());
            auditDetail.setName(auditDetailsResponse.getName());
            auditDetail.setPhone(auditDetailsResponse.getPhone());
            auditDetail.setStartDate(auditDetailsResponse.getStartDate());

            /*auditDetail.setRegistrationNumber("gfgdgs");
            auditDetail.setGrantAreaInHa("gghghsa");
            auditDetail.setTehsil("tehsil");
            auditDetail.setVillage("village");
            auditDetail.setOfficerName("officer_name");
            auditDetail.setCrop("crop");
            auditDetail.setTotalProductionArea("total_production_area");*/

            List<AuditDetail> auditDetailList = new ArrayList<>();
            auditDetailList.add(auditDetail);


           /* SmallHolderAuditSheetSaveRequest auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(PrefManager.getFarmId(AuditDetailsActivity.this),
                    save_submit,
                    auditSheetResponse.getChapters(),
                    auditDetailList
            );*/
            SmallHolderAuditSheetSaveRequest auditSheetSaveRequest = null;

            if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {

                auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(PrefManager.getFarmId(AuditDetailsActivity.this),
                        null,
                        subAuditId,
                        save_submit,
                        auditSheetResponse.getChapters(),
                        auditDetailList
                );
            } else if (PrefManager.getUserType(AuditDetailsActivity.this).equals("auditor")) {
                auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(null,
                        PrefManager.getFarmId(AuditDetailsActivity.this),
                        subAuditId,
                        save_submit,
                        auditSheetResponse.getChapters(),
                        auditDetailList
                );
            }


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
                                    if (!loadDetail) {
                                        Snackbar.make(btn_Submit, R.string.submit_successfully, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();

                                    } else {
                                        Snackbar.make(btn_Submit, R.string.update_successfully, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {
                                            getDetails();
                                        } else {
                                            getAuditorSingleAuditDetails();
                                        }
                                    }

                                    if(save_submit.equals("false")){
                                        deleteOffline();
                                    }else {
                                        if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {
                                            getDetails();
                                        } else {
                                            getAuditorSingleAuditDetails();
                                        }
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
                        public void onFailure(@NonNull Call<SmallHolderAuditSheetSaveResponse> call, @NonNull Throwable t) {


                                /*Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                            Snackbar.make(btn_Submit, "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {

            Snackbar.make(btn_Submit, R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void deleteOffline() {

        OfflineAuditSheetResponse sheetResponse = null;
        if(subAuditId != null){
            sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", Integer.valueOf(subAuditId))
                    .findFirst();
        }else {
            sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", auditId)
                    .findFirst();
        }

        OfflineAuditSheetResponse finalSheetResponse = sheetResponse;
        if(finalSheetResponse != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Delete all matches
                    finalSheetResponse.deleteFromRealm();
                    if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {
                        getDetails();
                    } else {
                        getAuditorSingleAuditDetails();
                    }

                }
            });
        }
    }

    private void callAuditsheet(){
        OfflineAuditSheetResponse sheetResponse = null;
        if(subAuditId != null){
             sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", Integer.valueOf(subAuditId))
                    .findFirst();
        }else {
             sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", auditId)
                    .findFirst();
        }

        if(sheetResponse != null){
            btn_offline.setVisibility(View.GONE);
            txt_offline.setVisibility(View.VISIBLE);
            auditSheetResponse = new AuditSheetResponse();
            AuditSheetResponse auditResponse = modelMapper.map(sheetResponse, AuditSheetResponse.class);
            auditSheetResponse = auditResponse;

            /*if (auditSheetResponse.getChapters().size() > 0) {
                perfomance_container.setVisibility(View.VISIBLE);
                new PerfomanceListing(AuditDetailsActivity.this, auditSheetResponse.getChapters(), perfomance_container);
            } else {
                perfomance_container.setVisibility(View.GONE);
            }*/
            record_inspection.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }else {

            if ((PrefManager.getUserType(AuditDetailsActivity.this).equals("operator") && auditDetailsResponse.getAuditType().equals("Internal Audit")) || PrefManager.getUserType(AuditDetailsActivity.this).equals("auditor")) {
                btn_offline.setVisibility(View.VISIBLE);
                txt_offline.setVisibility(View.GONE);
            }else {
                btn_offline.setVisibility(View.GONE);
                txt_offline.setVisibility(View.GONE);
            }
            if(auditDetailsResponse.getStatus().equals("Not Approved Audit") || auditDetailsResponse.getStatus().equals("Approved Audit")){
                btn_offline.setVisibility(View.GONE);
                txt_offline.setVisibility(View.GONE);
            }
            if (PrefManager.getUserType(AuditDetailsActivity.this).equals("operator")) {
                getAuditSheet();
            } else {
                getAuditorAuditSheet();
            }
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
                                Snackbar.make(record_inspection, R.string.response_fail, Snackbar.LENGTH_LONG)
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
            Snackbar.make(record_inspection, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }

    private void getAuditorAuditSheet() {

        if (Connectivity.checkInternetIsActive(AuditDetailsActivity.this)) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(AuditSheetService.class)
                    .getAuditorAuditSheet(auditId,
                            PrefManager.getAccessToken(AuditDetailsActivity.this),
                            PrefManager.getFarmId(AuditDetailsActivity.this),
                            subAuditId)
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
                                Snackbar.make(record_inspection, R.string.response_fail, Snackbar.LENGTH_LONG)
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
            Snackbar.make(record_inspection, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }

    private void showEditDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View dialog = getLayoutInflater().inflate(R.layout.dialog_edit_basic_details, null);
        dialogBuilder.setView(dialog);

        aDialog = dialogBuilder.create();
        aDialog.show();

        AppCompatTextView smallholderNameDialog = dialog.findViewById(R.id.text_Smallholder_Name);
        AppCompatEditText edt_MPOB_License_Number = dialog.findViewById(R.id.editText_MPOB_License_Number);
        AppCompatEditText edt_Grant_Area_In_Ha = dialog.findViewById(R.id.editText_Grant_Area_In_Ha);
        AppCompatEditText edt_Land_Use = dialog.findViewById(R.id.editText_Land_Use);
        AppCompatEditText edt_Small_Holder_Farm_Address = dialog.findViewById(R.id.editText_Small_Holder_Farm_Address);
        AppCompatEditText edt_Home_Address = dialog.findViewById(R.id.editText_Home_Address);
        AppCompatEditText edt_District = dialog.findViewById(R.id.editText_District);
        AppCompatEditText edt_Mobile_Number = dialog.findViewById(R.id.editText_Mobile_Number);
        AppCompatEditText edt_General_Comments = dialog.findViewById(R.id.editText_General_Comments);
        MaterialButton btnUpdateDetails = dialog.findViewById(R.id.btnUpdateDetails);

        smallholderNameDialog.setText(auditDetailsResponse.getFarmName());
        edt_MPOB_License_Number.setText(auditDetailsResponse.getLicenceNo());
        edt_Grant_Area_In_Ha.setText(auditDetailsResponse.getGrantArea());
        edt_Land_Use.setText(auditDetailsResponse.getLandCondition());
        edt_Small_Holder_Farm_Address.setText(auditDetailsResponse.getAddress());
        edt_Home_Address.setText(auditDetailsResponse.getHomeAddress());
        edt_District.setText(auditDetailsResponse.getDistrict());
        edt_Mobile_Number.setText(auditDetailsResponse.getPhone());
        edt_General_Comments.setText(auditDetailsResponse.getComment());

        btnUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auditSheetResponse != null && auditDetailsResponse != null) {

                    auditDetailsResponse.setLicenceNo(edt_MPOB_License_Number.getText().toString());
                    auditDetailsResponse.setGrantArea(edt_Grant_Area_In_Ha.getText().toString());
                    auditDetailsResponse.setLandCondition(edt_Land_Use.getText().toString());
                    auditDetailsResponse.setAddress(edt_Small_Holder_Farm_Address.getText().toString());
                    auditDetailsResponse.setHomeAddress(edt_Home_Address.getText().toString());
                    auditDetailsResponse.setDistrict(edt_District.getText().toString());
                    auditDetailsResponse.setPhone(edt_Mobile_Number.getText().toString());
                    auditDetailsResponse.setComment(edt_General_Comments.getText().toString());

                    if (status) {
                        submitAudit("true", true);
                    } else {
                        submitAudit("false", true);
                    }
                    aDialog.dismiss();
                }


            }
        });


    }
}

