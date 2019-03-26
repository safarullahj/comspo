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
    List<String> mColumnHeaderList = Arrays.asList("Principle",
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
            "Minor Criteria Score Percentage");
    private List<List<String>> mCellList = new ArrayList<>();

    public static Intent getIntent(Context context, ResultSheetResponse resultSheetResponse) {
        Intent intent = new Intent(context, ResultSheetActivity.class);
        intent.putExtra(KEY_DETAILS, resultSheetResponse);
        return intent;
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
        getSupportActionBar().setTitle("Audit Summary");

        tableView = findViewById(R.id.tableview);

        btn_Control_Points = findViewById(R.id.btn_Control_Points);
        btn_Total_Score = findViewById(R.id.btn_Total_Score);
        btn_Compliance = findViewById(R.id.btn_Compliance);

        btn_Compliance.setClickable(false);
        btn_Total_Score.setClickable(false);
        btn_Control_Points.setClickable(false);



        if (getIntent().getExtras() != null) {
            resultSheetResponse = (ResultSheetResponse) getIntent().getSerializableExtra(KEY_DETAILS);
            Log.e("rSheet_:" , ""+resultSheetResponse);

            btn_Control_Points.setText("Total Applicable Control Points :"+resultSheetResponse.getTotalApplicablePointsCount());
            btn_Total_Score.setText("Total Score : "+resultSheetResponse.getTotalScore());
            btn_Compliance.setText("Compliance Percentage : "+String.format("%.2f", resultSheetResponse.getCompliancePercentage()) + "%");

            for(Chapter chapter :resultSheetResponse.getChapters()){

                mRowHeaderList.add(String.valueOf(chapter.getChapterPosition()));

                List<String> data = new ArrayList<>();
                data.add(chapter.getChapterName());
                data.add(String.valueOf(chapter.getMajorCriteriaScore()));
                data.add(String.valueOf(chapter.getMinorCriteriaScore()));
                data.add(String.valueOf(chapter.getApplicablePointsCount()));
                data.add(String.valueOf(chapter.getTotalScore()));
                String prCmpl = String.format("%.2f", chapter.getCompliancePercentage()) + "%";
                data.add(String.valueOf(prCmpl));
                data.add(String.valueOf(chapter.getMinorNcCount()));
                data.add(String.valueOf(chapter.getMajorNcCount()));
                data.add(String.valueOf(chapter.getMajorCriteriaScore()));
                String prMjr = String.format("%.2f", chapter.getMajorCriteriaScorePercentage()) + "%";
                data.add(String.valueOf(prMjr));
                data.add(String.valueOf(chapter.getMinorCriteriaScore()));
                data.add(String.valueOf(chapter.getMinorCriteriaScorePercentage()));

                mCellList.add(data);

            }

            // Create our custom TableView Adapter
            MyTableViewAdapter adapter = new MyTableViewAdapter(this,resultSheetResponse);

            // Set this adapter to the our TableView
            tableView.setAdapter(adapter);

            Log.e("rSheet_:" , "adpt "+adapter);
            Log.e("rSheet_:" , "L!"+mRowHeaderList.size());
            Log.e("rSheet_:" , ""+mColumnHeaderList.size());
            Log.e("rSheet_:" , ""+mCellList.size());

            // Let's set datas of the TableView on the Adapter
            adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

        }


    }
}
