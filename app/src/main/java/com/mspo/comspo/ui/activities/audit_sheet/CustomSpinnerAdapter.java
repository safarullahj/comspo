package com.mspo.comspo.ui.activities.audit_sheet;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Acc;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Aic;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;
    private List<Chapter> chapters;

    CustomSpinnerAdapter(Context applicationContext, ArrayList<String> list, List<Chapter> chapters) {
        this.context = applicationContext;
        this.list = list;
        this.chapters = chapters;
    }


    @Override
    public int getCount() {
        return chapters.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);

        AppCompatImageView icon = view.findViewById(R.id.imgFinish);
        AppCompatTextView chapterName = view.findViewById(R.id.txt_ChapterName);

            for(Acc acc : chapters.get(i).getAccs()){
                for(Aic aic:acc.getAics()){
                    if(aic.getComplianceValue() == -2.0 ){
                        icon.setVisibility(View.GONE);
                    }
                }
            }


        chapterName.setText(chapters.get(i).getChapterName()+" ("+String.format("%.2f", chapters.get(i).getCompliancePercentage())+"%)");
        return view;
    }
}
