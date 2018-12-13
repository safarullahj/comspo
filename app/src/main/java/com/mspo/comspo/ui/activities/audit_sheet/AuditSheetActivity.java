package com.mspo.comspo.ui.activities.audit_sheet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Acc;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;
import com.mspo.comspo.ui.adapters.AuditSheetAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuditSheetActivity extends AppCompatActivity {

    private static final String KEY_AUDIT_SHEET = "key.auditSheet";
    private static final String KEY_CRITERIA = "key.criteria";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    ArrayList<String> actions = new ArrayList<String>();
    private ViewPager mViewPager;

    private Spinner category;

    private Toolbar toolbar;

    private AuditSheetResponse auditSheetResponse;

    public static Intent getIntent(Context context, AuditSheetResponse auditSheetResponse) {
        Intent intent = new Intent(context, AuditSheetActivity.class);
        intent.putExtra(KEY_AUDIT_SHEET,  auditSheetResponse);
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
        mViewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        if (getIntent().getExtras() != null) {
            auditSheetResponse = (AuditSheetResponse) getIntent().getSerializableExtra(KEY_AUDIT_SHEET);


            for(Chapter chapter: auditSheetResponse.getChapters()){
                actions.add(chapter.getChapterName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, actions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(adapter);

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
            Log.e("chk","instance+"+acc.getCriterionDescription());
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

            Log.e("chk","fragment oncreate");
            Log.e("chk","description : "+acc.getCriterionDescription());


            AppCompatTextView textView = rootView.findViewById(R.id.txt_criterion_description);
            textView.setText(acc.getCriterionDescription());

            RecyclerView criteria_list = rootView.findViewById(R.id.criteria_list);

            LinearLayoutManager verticalLayoutmanager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            criteria_list.setLayoutManager(verticalLayoutmanager);
            criteria_list.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.line_spacing_normal));

            auditSheetAdapter = new AuditSheetAdapter(getContext(), acc.getAics());
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
            Log.e("chk","pager Adapter");
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
        Log.e("chk", "Accs ("+ i +"): "+ auditSheetResponse.getChapters().get(i).getChapterName());
        mSectionsPagerAdapter = null;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),auditSheetResponse.getChapters().get(i).getAccs());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
