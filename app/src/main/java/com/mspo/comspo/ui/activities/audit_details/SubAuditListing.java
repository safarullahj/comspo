package com.mspo.comspo.ui.activities.audit_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.group_audit_details.SubAudit;

import java.util.List;

public class SubAuditListing implements View.OnClickListener  {
    private Activity activity;
    private List<SubAudit> subAudit;
    private String year;
    private String farmname;
    private Integer auditId;


    SubAuditListing(Activity activity, List<SubAudit> subAudit, Integer auditId, LinearLayout view, String year, String farmname) {
        this.activity = activity;
        this.subAudit = subAudit;
        this.auditId = auditId;
        this.year = year;
        this.farmname = farmname;

        view.removeAllViews();
        if (subAudit.size() > 0) {
            view.addView(getHeading());
            for (int i = 0; i < subAudit.size(); i++) {
                view.addView(getView(i));
            }
        }
    }

    @SuppressLint("InflateParams")
    private View getView(int position) {

        LinearLayout container;
        AppCompatTextView audit_id,audit_year,audit_status;
        View perfomance_view;


        perfomance_view = LayoutInflater.from(activity).inflate(R.layout.item_internal_audit, null);

        container = perfomance_view.findViewById(R.id.container);
        audit_id = perfomance_view.findViewById(R.id.textView_audit_id);
        audit_year = perfomance_view.findViewById(R.id.textView_audit_year);
        audit_status = perfomance_view.findViewById(R.id.textView_audit_status);

        audit_id.setText("Audit Id : "+subAudit.get(position).getSubAuditId());
        audit_year.setText("Year : "+year);
        String status = subAudit.get(position).getStatus();
        audit_status.setText(status);

        switch (status) {
            case "Pending Audit":
                audit_status.setTextColor(Color.RED);
                break;
            case "Not Approved Audit":
                audit_status.setTextColor(Color.parseColor("#51C709"));
                break;
            default:
                audit_status.setTextColor(Color.BLACK);
                break;
        }


        container.setOnClickListener(this);
        container.setTag(subAudit.get(position));

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
            heading.setText("Sub Audit");
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        SubAudit audit = (SubAudit) view.getTag();
        Integer id = audit.getSubAuditId();
        String name = audit.getFarmName();
        name = name +"(In Audit- "+farmname+")";
        activity.startActivity(AuditDetailsActivity.getIntent(activity,
                auditId,
                id,
                name,
                ""));
    }
}
