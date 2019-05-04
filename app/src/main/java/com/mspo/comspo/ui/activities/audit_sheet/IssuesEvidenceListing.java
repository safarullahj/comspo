package com.mspo.comspo.ui.activities.audit_sheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.EvidenceToCheck;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.IssuesToCheck;

import java.util.List;

public class IssuesEvidenceListing {
    private Context context;
    private List<IssuesToCheck> issues;

    private List<EvidenceToCheck> evidence;

    public IssuesEvidenceListing(Context context, List<IssuesToCheck> issues,List<EvidenceToCheck> evidence, LinearLayout view,String head) {
        this.context = context;
        this.issues = issues;
        this.evidence = evidence;


        view.removeAllViews();

        if (issues!= null && issues.size() > 0) {
            view.addView(getHeading(head));
            for (int i = 0; i < issues.size(); i++) {
                view.addView(getView(i));
            }
        }

        if (evidence!= null && evidence.size() > 0) {
            view.addView(getHeading(head));
            for (int i = 0; i < evidence.size(); i++) {
                view.addView(getView(i));
            }
        }
    }


    @SuppressLint("InflateParams")
    private View getView(int position) {

        AppCompatTextView list_item;
        View List_view;


        List_view = LayoutInflater.from(context).inflate(R.layout.item_issues_evidence, null);


        list_item = List_view.findViewById(R.id.txt_listItem);


        if(issues != null) {
            list_item.setText(issues.get(position).getPosition() + ")." + issues.get(position).getIssuesToCheck());
        }

        if(evidence != null) {
            list_item.setText(evidence.get(position).getPosition() + ")." + evidence.get(position).getEvidenceToCheck());
        }


        return List_view;
    }

    @SuppressLint("InflateParams")
    private View getHeading(String head) {
        AppCompatTextView heading;
        View v = null;
        LayoutInflater li = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li != null) {
            v = li.inflate(R.layout.list_heading, null);
            heading = v.findViewById(R.id.textView_head);
            heading.setText(head);
        }

        return v;
    }
}
