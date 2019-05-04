package com.mspo.comspo.ui.activities.result_sheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.evrencoskun.tableview.TableView;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.result_sheet.Chapter;
import com.mspo.comspo.data.remote.model.responses.result_sheet.ResultSheetResponse;
import com.mspo.comspo.ui.adapters.MyTableViewAdapter;
import com.mspo.comspo.utils.LocaleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultSheetActivity extends AppCompatActivity{

    private static final String KEY_DETAILS = "key.details";

    private ResultSheetResponse resultSheetResponse;
    private TableView tableView;
    private MaterialButton btn_Control_Points, btn_Total_Score,btn_Compliance;

    private List<String> mRowHeaderList = new ArrayList<>();
    //private List<String> mColumnHeaderList;
    /*List<String> mColumnHeaderList = Arrays.asList("Principle",
            "Applicable Major Control Points",
            "Applicable Minor Control Points",
            "Total Applicable Points For Score Calculation",
            "Total Score",
            "Compliance Percentage",
            "Minor Non Conformity",
            "Major Non Conformity",
            "Major Criteria Score",
            "Major Criteria score percentage",
            "Minor Criteria Score",
            "Minor Criteria Score Percentage");*/

    private List<List<String>> mCellList = new ArrayList<>();

    public static Intent getIntent(Context context, ResultSheetResponse resultSheetResponse) {
        Intent intent = new Intent(context, ResultSheetActivity.class);
        intent.putExtra(KEY_DETAILS, resultSheetResponse);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_sheet);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        getSupportActionBar().setTitle(R.string.audit_summary);

        tableView = findViewById(R.id.tableview);

        btn_Control_Points = findViewById(R.id.btn_Control_Points);
        btn_Total_Score = findViewById(R.id.btn_Total_Score);
        btn_Compliance = findViewById(R.id.btn_Compliance);

        btn_Compliance.setClickable(false);
        btn_Total_Score.setClickable(false);
        btn_Control_Points.setClickable(false);

        List<String> mColumnHeaderList = Arrays.asList(getResources().getStringArray(R.array.headerList));
        /*List<String> mColumnHeaderList = Arrays.asList(getString(R.string.rs_Principle),
                getString(R.string.rs_Applicable_Major_Control_Points),
                getString(R.string.rs_Applicable_Minor_Control_Points),
                getString(R.string.rs_Total_Applicable_Points_For_Score_Calculation),
                getString(R.string.rs_Total_Score),
                getString(R.string.rs_Compliance_Percentage),
                getString(R.string.rs_Minor_Non_Conformity),
                getString(R.string.rs_Major_Non_Conformity),
                getString(R.string.rs_Major_Criteria_Score),
                getString(R.string.rs_Major_Criteria_score_percentage),
                getString(R.string.rs_Minor_Criteria_Score),
                getString(R.string.rs_Minor_Criteria_Score_Percentage));*/



        if (getIntent().getExtras() != null) {
            resultSheetResponse = (ResultSheetResponse) getIntent().getSerializableExtra(KEY_DETAILS);
           // Log.e("rSheet_:" , ""+resultSheetResponse);

            btn_Control_Points.setText(getString(R.string.total_applicable_control_points)+String.format("%.2f", resultSheetResponse.getTotalApplicablePointsCount()));
            btn_Total_Score.setText(getString(R.string.total_score)+String.format("%.2f", resultSheetResponse.getTotalScore()));
            btn_Compliance.setText(getString(R.string.compliance_percentage)+String.format("%.2f", resultSheetResponse.getCompliancePercentage()) + "%");

            for(Chapter chapter :resultSheetResponse.getChapters()){

                mRowHeaderList.add(String.valueOf(chapter.getChapterPosition()));

                List<String> data = new ArrayList<>();
                data.add(chapter.getChapterName());
                String MJCScore = String.format("%.2f", chapter.getMajorCriteriaScore());
                data.add(String.valueOf(MJCScore));
                String MNCScore = String.format("%.2f", chapter.getMinorCriteriaScore());
                data.add(String.valueOf(MNCScore));
                data.add(String.valueOf(chapter.getApplicablePointsCount()));
                String tScore = String.format("%.2f", chapter.getTotalScore());
                data.add(String.valueOf(tScore));
                String prCmpl = String.format("%.2f", chapter.getCompliancePercentage()) + "%";
                data.add(String.valueOf(prCmpl));
                data.add(String.valueOf(chapter.getMinorNcCount()));
                data.add(String.valueOf(chapter.getMajorNcCount()));
                data.add(String.valueOf(MJCScore));
                String prMjr = String.format("%.2f", chapter.getMajorCriteriaScorePercentage()) + "%";
                data.add(String.valueOf(prMjr));
                data.add(String.valueOf(MNCScore));
                String mncp = String.format("%.2f", chapter.getMinorCriteriaScorePercentage()) + "%";
                data.add(String.valueOf(mncp));

                mCellList.add(data);

            }

            // Create our custom TableView Adapter
            MyTableViewAdapter adapter = new MyTableViewAdapter(this,resultSheetResponse);

            // Set this adapter to the our TableView
            tableView.setAdapter(adapter);

            /*Log.e("rSheet_:" , "adpt "+adapter);
            Log.e("rSheet_:" , "L!"+mRowHeaderList.size());
            Log.e("rSheet_:" , ""+mColumnHeaderList.size());
            Log.e("rSheet_:" , ""+mCellList.size());*/

            // Let's set datas of the TableView on the Adapter
            adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

        }


    }
}
