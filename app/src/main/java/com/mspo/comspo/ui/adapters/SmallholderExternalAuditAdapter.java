package com.mspo.comspo.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list.Audit;
import com.mspo.comspo.ui.activities.audit_details.AuditDetailsActivity;

import java.util.List;


public class SmallholderExternalAuditAdapter extends RecyclerView.Adapter<SmallholderExternalAuditAdapter.AuditItemViewHolder> {

    private Context context;
    private List<Audit> auditList;


    public SmallholderExternalAuditAdapter(Context context, List<Audit> auditList) {
        this.context = context;
        this.auditList = auditList;
    }


    @Override
    public int getItemCount() {
        if (auditList != null)
            return auditList.size();
        else
            return 0;
    }


    @NonNull
    @Override
    public AuditItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_external_audit, parent, false);
        return new AuditItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AuditItemViewHolder holder, final int position) {

        holder.audit_id.setText("" + auditList.get(position).getAuditId());
        holder.audit_firm.setText(auditList.get(position).getFarm());
        holder.audit_year.setText(auditList.get(position).getYear());
        holder.audit_status.setText(auditList.get(position).getAuditStatus());
        holder.audit_category.setText(auditList.get(position).getCategory());

    }

    public void addAuditlist(List<Audit> audits) {
        auditList.addAll(audits);
        notifyDataSetChanged();
    }

    class AuditItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView audit_id,audit_firm,audit_year,audit_status,audit_category;

        private AuditItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            audit_id = itemView.findViewById(R.id.textView_audit_id);
            audit_firm = itemView.findViewById(R.id.textView_farm_name);
            audit_year = itemView.findViewById(R.id.textView_year);
            audit_status = itemView.findViewById(R.id.textView_audit_status);
            audit_category = itemView.findViewById(R.id.textView_audit_category);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(AuditDetailsActivity.getIntent(context,
                    auditList.get(getAdapterPosition()).getAuditId(),
                    0,
                    auditList.get(getAdapterPosition()).getName(),
                    auditList.get(getAdapterPosition()).getCategory()));

        }
    }
}


