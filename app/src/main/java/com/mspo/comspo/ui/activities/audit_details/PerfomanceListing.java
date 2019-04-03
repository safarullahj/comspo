package com.mspo.comspo.ui.activities.audit_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;

import java.util.List;

public class PerfomanceListing {

    private Activity activity;
    private List<Chapter> chapters;


    PerfomanceListing(Activity activity, List<Chapter> chapterss, LinearLayout view) {
        this.activity = activity;
        this.chapters = chapterss;

        view.removeAllViews();
        if (chapterss.size() > 0) {
            view.addView(getHeading());
            for (int i = 0; i < chapterss.size(); i++) {
                view.addView(getView(i));
            }
        }
    }

    @SuppressLint("InflateParams")
    private View getView(int position) {

        AppCompatTextView chapterName, percentage;
        RoundCornerProgressBar roundCornerProgressBar;
        View perfomance_view;


        perfomance_view = LayoutInflater.from(activity).inflate(R.layout.item_perfomance, null);

        chapterName = perfomance_view.findViewById(R.id.txt_ChapterName);
        roundCornerProgressBar = perfomance_view.findViewById(R.id.progress_);
        percentage = perfomance_view.findViewById(R.id.textView_percentage);

        chapterName.setText(chapters.get(position).getChapterPosition() + " " + chapters.get(position).getChapterName());
        percentage.setText(String.format("%.2f", chapters.get(position).getCompliancePercentage()) + "%");


        if(chapters.get(position).getGraphColor().equals("success") ) {
            roundCornerProgressBar.setProgressColor(Color.parseColor("#a6ed8e"));
        } else {
            roundCornerProgressBar.setProgressColor(Color.parseColor("#FAFF10"));
        }

        String f = String.format("%.2f", chapters.get(position).getCompliancePercentage());
        roundCornerProgressBar.setProgress(Float.parseFloat(f));


        return perfomance_view;
    }

    @SuppressLint("InflateParams")
    private View getHeading() {
        AppCompatTextView heading;
        View v = null;
        LayoutInflater li = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li != null) {
            v = li.inflate(R.layout.list_heading, null);
            heading = v.findViewById(R.id.textView_head);
            heading.setText(R.string.principal_summary);
        }

        return v;
    }

}
