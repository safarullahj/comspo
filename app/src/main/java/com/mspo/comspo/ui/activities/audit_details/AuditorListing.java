package com.mspo.comspo.ui.activities.audit_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.Auditor;
import com.mspo.comspo.ui.widgets.CircleImageView;

import java.util.List;

public class AuditorListing {
    private Activity activity;
    private List<Auditor> auditors;


    AuditorListing(Activity activity, List<Auditor> auditors, LinearLayout view) {
        this.activity = activity;
        this.auditors = auditors;


        view.removeAllViews();
        if (auditors.size() > 0) {
            //view.addView(getHeading());
            for (int i = 0; i < auditors.size(); i++) {
                view.addView(getView(i));
            }
        }
    }


    @SuppressLint("InflateParams")
    private View getView(int position) {

        CircleImageView auditorImage;
        AppCompatTextView auditor_name;
        View auditor_view;


        auditor_view = LayoutInflater.from(activity).inflate(R.layout.item_auditors, null);

        auditorImage = auditor_view.findViewById(R.id.imageViewAuditorImage);
        auditor_name = auditor_view.findViewById(R.id.auditor_name);

        if (auditors.get(position).getProfilePic() != null && !auditors.get(position).getProfilePic().equals("")) {
            try {
                Glide.with(activity)
                        .load(auditors.get(position).getProfilePic())
                        .into(auditorImage);
            } catch (Exception ignored) {
            }
        }

        auditor_name.setText(auditors.get(position).getName());


        return auditor_view;
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
            heading.setText("Auditors");
        }

        return v;
    }
}

