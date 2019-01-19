package com.mspo.comspo.ui.activities.audit_sheet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.AuditDetail;
import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.SmallHolderAuditSheetSaveRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.SmallHolderAuditSheetSaveResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Acc;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.ui.adapters.AuditSheetAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuditSheetActivity extends AppCompatActivity {

    private static final String KEY_AUDIT_SHEET = "key.auditSheet";
    private static final String KEY_DETAILS = "key.details";
    private static final String KEY_CRITERIA = "key.criteria";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    ArrayList<String> actions = new ArrayList<String>();
    private ViewPager mViewPager;

    private Spinner category;

    private Toolbar toolbar;
    private ProgressBar progressBar;

    private AuditSheetResponse auditSheetResponse;
    private IndividualAuditDetailsResponse auditDetailsResponse;
    private static CustomSpinnerAdapter customAdapter;

    public static Intent getIntent(Context context, AuditSheetResponse auditSheetResponse, IndividualAuditDetailsResponse auditDetailsResponse) {
        Intent intent = new Intent(context, AuditSheetActivity.class);
        intent.putExtra(KEY_AUDIT_SHEET, auditSheetResponse);
        intent.putExtra(KEY_DETAILS, auditDetailsResponse);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_sheet);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        category = findViewById(R.id.category);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        mViewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        if (getIntent().getExtras() != null) {
            auditSheetResponse = (AuditSheetResponse) getIntent().getSerializableExtra(KEY_AUDIT_SHEET);
            auditDetailsResponse = (IndividualAuditDetailsResponse) getIntent().getSerializableExtra(KEY_DETAILS);

            for (Chapter chapter : auditSheetResponse.getChapters()) {
                actions.add(chapter.getChapterName());
            }
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, actions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(adapter);*/

            customAdapter=new CustomSpinnerAdapter(getApplicationContext(),actions,auditSheetResponse.getChapters());
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Acc acc;
        private AuditSheetAdapter auditSheetAdapter;

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(Acc acc) {
            Log.e("chk", "instance+" + acc.getCriterionDescription());
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(KEY_CRITERIA, acc);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_audit_sheet, container, false);

            acc = (Acc) getArguments().getSerializable(KEY_CRITERIA);

            Log.e("chk", "fragment oncreate");
            Log.e("chk", "description : " + acc.getCriterionDescription());


            AppCompatTextView textView = rootView.findViewById(R.id.txt_criterion_description);
            textView.setText(acc.getCriterionDescription());

            RecyclerView criteria_list = rootView.findViewById(R.id.criteria_list);

            LinearLayoutManager verticalLayoutmanager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            criteria_list.setLayoutManager(verticalLayoutmanager);
            criteria_list.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.line_spacing_normal));

            auditSheetAdapter = new AuditSheetAdapter(getContext(), acc.getAics(),customAdapter);
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
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(criterias.get(position));
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

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void initializeViewPager(int i) {
        Log.e("chk", "Accs (" + i + "): " + auditSheetResponse.getChapters().get(i).getChapterName());
        mSectionsPagerAdapter = null;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), auditSheetResponse.getChapters().get(i).getAccs());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_audit_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_save) {

            if (Connectivity.checkInternetIsActive(AuditSheetActivity.this)) {

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


                SmallHolderAuditSheetSaveRequest auditSheetSaveRequest = new SmallHolderAuditSheetSaveRequest(PrefManager.getFarmId(AuditSheetActivity.this),
                        "false",
                        auditSheetResponse.getChapters(),
                        auditDetailList
                );

                Log.e("rad_sub",""+auditSheetResponse.getChapters().get(0).getAccs().get(0).getAics().get(0).getComplianceValue());
                Log.e("rad_sub",""+auditSheetResponse.getChapters().get(0).getAccs().get(0).getAics().get(0).getLastEditedTime());

                APIClient.getDrinkClient()
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
                                        finish();
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
                            public void onFailure(@NonNull Call<SmallHolderAuditSheetSaveResponse> call, @NonNull Throwable t) {


                                Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            } else {

                Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
