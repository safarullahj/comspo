package com.mspo.comspo.ui.activities.audit_sheet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.AuditDetail;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.SmallHolderAuditSheetSaveRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.SmallHolderAuditSheetSaveResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Acc;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;
import com.mspo.comspo.data.remote.model.responses.offline_audit_sheet.OfflineAuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.upload.FileUploadResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.ui.adapters.AuditSheetAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;
import com.mspo.comspo.utils.LocaleManager;

import org.modelmapper.ModelMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuditSheetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_AUDIT_SHEET = "key.auditSheet";
    private static final String KEY_DETAILS = "key.details";
    private static final String KEY_CRITERIA = "key.criteria";
    private static final String KEY_AUDIT_STATUS = "key.auditStatus";
    private static final String KEY_AUDIT_STATUS_FLAG = "key.auditStatus";
    private static final String KEY_AUDIT_OFFLINE_FLAG = "key.offlineStatus";
    private static final String KEY_SUBAUDIT_ID = "key.subauditid";
    private static final String KEY_OFFAUDIT_ID = "key.offauditid";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    //ArrayList<String> actions = new ArrayList<String>();
    private ViewPager mViewPager;

    private Spinner category;

    private Toolbar toolbar;
    private ProgressBar progressBar;

    private AuditSheetResponse auditSheetResponse;
    private IndividualAuditDetailsResponse auditDetailsResponse;
    private String auditStatus, subAuditId;
    private Integer offAuditId;
    private boolean status = false, isOffline = false;

    private static CustomSpinnerAdapter customAdapter;
    private Realm realm;

    private PlaceholderFragment placeholderFragment;

    ArrayList<MediaFile> files;
    public int i = 0;
    private ProgressDialog progressDialog;


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == 0) {
                btnNext.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.GONE);
            } else if (position == size - 1) {
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.VISIBLE);
            } else {
                btnNext.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private int size;
    private Button btnPrevious, btnNext;


    public static Intent getIntent(Context context, AuditSheetResponse auditSheetResponse, IndividualAuditDetailsResponse auditDetailsResponse, String auditStatus, String subAuditId, Integer offAuditId) {
        Intent intent = new Intent(context, AuditSheetActivity.class);
        intent.putExtra(KEY_AUDIT_SHEET, auditSheetResponse);
        intent.putExtra(KEY_DETAILS, auditDetailsResponse);
        intent.putExtra(KEY_AUDIT_STATUS, auditStatus);
        intent.putExtra(KEY_SUBAUDIT_ID, subAuditId);
        intent.putExtra(KEY_OFFAUDIT_ID, offAuditId);
        return intent;
    }

    //private LinearLayout dotsLayout;
    private TextView[] dots;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.btn_next:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_sheet);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        category = findViewById(R.id.category);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        mViewPager = findViewById(R.id.viewpager);

        //dotsLayout = findViewById(R.id.layoutDots);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        if (getIntent().getExtras() != null) {
            auditSheetResponse = (AuditSheetResponse) getIntent().getSerializableExtra(KEY_AUDIT_SHEET);
            auditDetailsResponse = (IndividualAuditDetailsResponse) getIntent().getSerializableExtra(KEY_DETAILS);
            auditStatus = getIntent().getExtras().getString(KEY_AUDIT_STATUS, "");
            subAuditId = getIntent().getExtras().getString(KEY_SUBAUDIT_ID, "");
            if (subAuditId.equals("")) {
                subAuditId = null;
            }
            offAuditId = getIntent().getExtras().getInt(KEY_OFFAUDIT_ID);

            OfflineAuditSheetResponse sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", offAuditId)
                    .findFirst();

            if (sheetResponse != null) {
                isOffline = true;
            } else {
                isOffline = false;
            }

            if (auditDetailsResponse != null) {
                if (PrefManager.getUserType(AuditSheetActivity.this).equals("operator") && auditDetailsResponse.getAuditType().equals("Internal Audit")) {
                    switch (auditStatus) {
                        case "Newly Assigned Audit":
                            status = true;
                            break;
                        case "Pending Audit":
                            status = true;
                            break;
                        case "OnGoing Audit":
                            status = true;
                            break;
                        case "Offline":
                            status = true;
                            break;
                        default:
                            status = false;
                            break;
                    }
                } else if (PrefManager.getUserType(AuditSheetActivity.this).equals("auditor")) {
                    switch (auditStatus) {
                        case "Newly Assigned Audit":
                            status = true;
                            break;
                        case "Pending Audit":
                            status = true;
                            break;
                        case "OnGoing Audit":
                            status = true;
                            break;
                        case "Offline":
                            status = true;
                            break;
                        default:
                            status = false;
                            break;
                    }
                } else {
                    status = false;
                }
            } else {
                status = true;
            }

            /*for (Chapter chapter : auditSheetResponse.getChapters()) {
                actions.add(chapter.getChapterName());
            }*/
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, actions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(adapter);*/

            customAdapter = new CustomSpinnerAdapter(getApplicationContext(), auditSheetResponse.getChapters());
            category.setAdapter(customAdapter);

            //initializeViewPager(0);

            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    initializeViewPager(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initializeViewPager(int i) {
        Log.e("chk", "Accs (" + i + "): " + auditSheetResponse.getChapters().get(i).getChapterName());
        mSectionsPagerAdapter = null;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), auditSheetResponse.getChapters().get(i).getAccs());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Acc acc;
        private AuditSheetAdapter auditSheetAdapter;

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(Acc acc, boolean statusFlag, boolean isOffline) {
            Log.e("chk", "instance+" + acc.getCriterionDescription());
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(KEY_CRITERIA, acc);
            args.putBoolean(KEY_AUDIT_STATUS_FLAG, statusFlag);
            args.putBoolean(KEY_AUDIT_OFFLINE_FLAG, isOffline);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_audit_sheet, container, false);

            acc = (Acc) getArguments().getSerializable(KEY_CRITERIA);
            boolean statusFlag = getArguments().getBoolean(KEY_AUDIT_STATUS_FLAG, false);
            boolean offlineFlag = getArguments().getBoolean(KEY_AUDIT_OFFLINE_FLAG, false);

            Log.e("chk", "fragment oncreate");
            Log.e("chk", "description : " + acc.getCriterionDescription());


            AppCompatTextView textView = rootView.findViewById(R.id.txt_criterion_description);
            String version = "4." + acc.getChapterPosition() + "." + acc.getCriterionPosition();
            textView.setText(version + " " + acc.getCriterionDescription());

            RecyclerView criteria_list = rootView.findViewById(R.id.criteria_list);

            LinearLayoutManager verticalLayoutmanager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            criteria_list.setLayoutManager(verticalLayoutmanager);
            criteria_list.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.spacing_normal));


            auditSheetAdapter = new AuditSheetAdapter(getContext(), acc.getAics(), customAdapter, version, statusFlag, offlineFlag);
            criteria_list.setAdapter(auditSheetAdapter);


            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        List<Acc> criterias;

        public SectionsPagerAdapter(FragmentManager fm, List<Acc> criterias) {
            super(fm);
            this.criterias = criterias;
            Log.e("chk", "pager Adapter");
            size = criterias.size();
            //addBottomDots(0);

            if (size == 1) {
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
            } else {
                btnNext.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.GONE);
            }
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            placeholderFragment = PlaceholderFragment.newInstance(criterias.get(position), status, isOffline);
            return placeholderFragment;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return criterias.size();
        }
    }

    /*private void addBottomDots(int currentPage) {
        dots = new TextView[size];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.WHITE);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#51C709"));
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (status) {
            getMenuInflater().inflate(R.menu.menu_audit_sheet, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (status) {
                showExitDialog();
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.action_save) {

            OfflineAuditSheetResponse sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", offAuditId)
                    .findFirst();

            if (sheetResponse != null) {
                String userType = sheetResponse.getUserType();
                String year = sheetResponse.getYear();

                ModelMapper modelMapper = new ModelMapper();
                sheetResponse = modelMapper.map(auditSheetResponse, OfflineAuditSheetResponse.class);
                sheetResponse.setAuditId(offAuditId);
                sheetResponse.setUserType(userType);
                sheetResponse.setYeare(year);


                OfflineAuditSheetResponse finalSheetResponse = sheetResponse;
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm_) {
                        realm_.copyToRealmOrUpdate(finalSheetResponse);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AuditSheetActivity.this, R.string.save_uccessfully, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                        Toast.makeText(AuditSheetActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {

                if (Connectivity.checkInternetIsActive(AuditSheetActivity.this)) {

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

                    SmallHolderAuditSheetSaveRequest auditSheetSaveRequest = null;

                    if (PrefManager.getUserType(AuditSheetActivity.this).equals("operator")) {

                        auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(PrefManager.getFarmId(AuditSheetActivity.this),
                                null,
                                subAuditId,
                                "false",
                                auditSheetResponse.getChapters(),
                                auditDetailList
                        );
                    } else if (PrefManager.getUserType(AuditSheetActivity.this).equals("auditor")) {
                        auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(null,
                                PrefManager.getFarmId(AuditSheetActivity.this),
                                subAuditId,
                                "false",
                                auditSheetResponse.getChapters(),
                                auditDetailList
                        );
                    }

                    Log.e("rad_sub", "" + auditSheetResponse.getChapters().get(0).getAccs().get(0).getAics().get(0).getComplianceValue());
                    Log.e("rad_sub", "" + auditSheetResponse.getChapters().get(0).getAccs().get(0).getAics().get(0).getLastEditedTime());

                    APIClient.getClient()
                            .create(AuditSheetService.class)
                            .saveFarmerAuditSheet(auditDetailsResponse.getAuditId(), PrefManager.getAccessToken(AuditSheetActivity.this), auditSheetSaveRequest)
                            .enqueue(new Callback<SmallHolderAuditSheetSaveResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<SmallHolderAuditSheetSaveResponse> call, @NonNull Response<SmallHolderAuditSheetSaveResponse> response) {

                                    Log.e("res_:", "res : " + response.body());
                                    ErrorResponse error = ErrorUtils.parseError(response);
                                    if (error != null)
                                        Log.e("res_:", "err : " + error.getErrorMessage());

                                    if (response.isSuccessful()) {
                                        if (response.body().getStatus()) {
                                            Toast.makeText(AuditSheetActivity.this, R.string.save_uccessfully, Toast.LENGTH_LONG).show();
                                            finish();
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
                                    Snackbar.make(findViewById(android.R.id.content), "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                } else {

                    Snackbar.make(findViewById(android.R.id.content), R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            /*Log.e("rad_sub",""+auditSheetResponse.getChapters().get(0).getAccs().get(0).getAics().get(0).getComplianceValue());
            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(0).getAccs().get(1).getAics().get(0).getComplianceValue());
            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(0).getAccs().get(1).getAics().get(1).getComplianceValue());

            Log.e("rad_sub","===============================================================");

            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(1).getAccs().get(0).getAics().get(0).getComplianceValue());
            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(1).getAccs().get(0).getAics().get(1).getComplianceValue());

            Log.e("rad_sub","===============================================================");

            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(3).getAccs().get(0).getAics().get(1).getComplianceValue());
            Log.e("rad_sub",""+auditSheetResponse.getChapters().get(3).getAccs().get(3).getAics().get(0).getComplianceValue());*/


        }

        return super.onOptionsItemSelected(item);
    }


    private void showExitDialog() {
        new AlertDialog.Builder(AuditSheetActivity.this)
                .setTitle(getString(R.string.warning_exit_without_save))
                .setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.action_no), null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (status) {
            showExitDialog();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("File_: ", "onActivityResult");

        if (data != null) {
            Log.e("File_: ", "requestCode_: " + requestCode);

            files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

            /*for (MediaFile mediaFile : files) {
                Log.e("File_: ", "path : " + mediaFile.getMimeType().toString());
                Log.e("File_: ", "path : " + mediaFile.getName().toString());
                Log.e("File_: ", "path : " + mediaFile.getPath().toString());

                *//*if (placeholderFragment != null)
                    placeholderFragment.addFile(requestCode, data);*//*

                uploadFile(requestCode, mediaFile);

            }*/
            if (Connectivity.checkInternetIsActive(this) ) {
                uploadFile(requestCode, files.get(i));
            }else {
                Toast.makeText(this,
                        R.string.check_internet_connection, Toast.LENGTH_LONG).show();
            }


        }
    }

    private void showProgressDialog(String title){
        progressDialog.setTitle(title);
        progressDialog.setMessage("Uploading..., \n Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private void uploadFile(int aicId, MediaFile mediaFile) {

        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
        }
        //progressBar.setVisibility(View.VISIBLE);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        showProgressDialog(mediaFile.getName());
        // create upload service client
        AuditSheetService service =
                APIClient.getClient().create(AuditSheetService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(mediaFile.getPath());


        RequestBody requestFile;
        requestFile = RequestBody.create(
                MediaType.parse(mediaFile.getMimeType()),
                file
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("audit_file", file.getName(), requestFile);

        Log.e("Upload", "auditid : " + auditDetailsResponse.getAuditId());
        Log.e("Upload", "offId : " + offAuditId);
        // finally, execute the request
        Call<FileUploadResponse> call = service.uploadFile(aicId,
                auditDetailsResponse.getAuditId(),
                offAuditId,
                PrefManager.getAccessToken(AuditSheetActivity.this),
                body);
        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(Call<FileUploadResponse> call,
                                   Response<FileUploadResponse> response) {
                Log.e("Upload", "success");
                if (response != null && response.isSuccessful() && response.body().getFileData().size() != 0) {

                    Log.e("Upload", "success file id : " + response.body().getFileData().get(0).getId());
                    Log.e("Upload", "success file path : " + response.body().getFileData().get(0).getFile());

                    com.mspo.comspo.data.remote.model.responses.audit_sheet.File newFile = new com.mspo.comspo.data.remote.model.responses.audit_sheet.File(response.body().getFileData().get(0).getId(),
                            response.body().getFileData().get(0).getFile(),
                            response.body().getFileData().get(0).getFile().substring(response.body().getFileData().get(0).getFile().lastIndexOf('/')+1));

                    /*if (placeholderFragment != null)
                        placeholderFragment.addFile(aicId, newFile);*/

                    for(int x=0 ; x < auditSheetResponse.getChapters().size() ; x++){

                        for(int y=0 ; y < auditSheetResponse.getChapters().get(x).getAccs().size() ; y++){

                            for(int z=0 ; z < auditSheetResponse.getChapters().get(x).getAccs().get(y).getAics().size() ; z++){

                                if(auditSheetResponse.getChapters().get(x).getAccs().get(y).getAics().get(z).getAuditIndicatorId() == aicId){
                                    Log.e("dataChange:", "file add");
                                    auditSheetResponse.getChapters().get(x).getAccs().get(y).getAics().get(z).getFiles().add(newFile);
                                    mSectionsPagerAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    i = i + 1;
                    if (i < files.size()) {
                        if (Connectivity.checkInternetIsActive(AuditSheetActivity.this) ) {
                            uploadFile(aicId, files.get(i));
                        }else {
                            i = 0;
                            progressDialog.dismiss();
                            Toast.makeText(AuditSheetActivity.this,
                                    R.string.check_internet_connection, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        //progressBar.setVisibility(View.GONE);
                        i = 0;
                        progressDialog.dismiss();
                    }


                }
            }

            @Override
            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                i=0;
                //progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });

    }
}
