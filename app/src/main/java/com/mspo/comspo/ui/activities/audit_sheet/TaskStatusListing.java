package com.mspo.comspo.ui.activities.audit_sheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Aic;

import java.util.List;

public class TaskStatusListing implements View.OnClickListener {

    private Activity activity;
    private List<Aic> sheetList;
    private LinearLayout view;
    private int criterionPosition;
    private TaskStatusInterface anInterface;
    private int selectedPosition = 0;

    TaskStatusListing(Activity activity, List<Aic> sheetList, LinearLayout view, int criterionPosition,TaskStatusInterface anInterface) {
        this.activity = activity;
        //this.sheetList = sheetList;
        this.view = view;
        this.criterionPosition = criterionPosition;
        this.anInterface = anInterface;

        init(sheetList,0);
    }

    public void init(List<Aic> sheetList, int selectedPosition){

        this.sheetList = sheetList;
        this.selectedPosition = selectedPosition;

        view.removeAllViews();
        if (this.sheetList.size() > 0) {
            for (int i = 0; i < this.sheetList.size(); i++) {
                view.addView(getView(i));
            }
        }
    }


    @SuppressLint("InflateParams")
    private View getView(int position) {

        LinearLayout task_status_container;
        AppCompatImageView taskStatusImage;
        ImageView taskStatusdash;
        AppCompatTextView taskStatusText;
        View task_view;


        task_view = LayoutInflater.from(activity).inflate(R.layout.item_step_view, null);

        task_status_container = task_view.findViewById(R.id.task_status_container);
        taskStatusImage = task_view.findViewById(R.id.taskStatusImage);
        taskStatusdash = task_view.findViewById(R.id.taskStatusdash);
        taskStatusText = task_view.findViewById(R.id.taskStatusText);

        task_status_container.setOnClickListener(this);
        task_status_container.setTag(position);

        if (sheetList.get(position).getComplianceValue() == -2.0) {
            taskStatusImage.setImageResource(R.drawable.default_icon);
        }else {
            taskStatusImage.setImageResource(R.drawable.complete);
        }

        if(position == sheetList.size()-1){
            taskStatusdash.setVisibility(View.GONE);
        }

        int taskNumber = position+1;
        taskStatusText.setText(criterionPosition+"."+taskNumber);

        if(selectedPosition == position){
            taskStatusText.setTextColor(Color.parseColor("#ffa000"));
            taskStatusImage.setColorFilter(ContextCompat.getColor(activity, R.color.selectedItemColor), android.graphics.PorterDuff.Mode.SRC_IN);
        }


        return task_view;
    }

    @Override
    public void onClick(View view) {

        int position = (int) view.getTag();
        anInterface.taskSelectedPosition(position);

        init(sheetList,position);

    }
}
