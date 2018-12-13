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


public class InternalAuditAdapter extends RecyclerView.Adapter<InternalAuditAdapter.AuditItemViewHolder> {

    private Context context;
    private List<Audit> auditList;


    public InternalAuditAdapter(Context context, List<Audit> auditList) {
        this.context = context;
        this.auditList = auditList;
    }


    @Override
    public int getItemCount() {
        if(auditList!= null)
            return auditList.size();
        else
            return 0;
    }


    @NonNull
    @Override
    public AuditItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_internal_audit, parent, false);
        return new AuditItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AuditItemViewHolder holder, final int position) {

        holder.audit_id.setText("Audit Id : "+auditList.get(position).getAuditId());
        holder.audit_year.setText("Year : "+auditList.get(position).getYear());
        holder.audit_duration.setText("Status : "+auditList.get(position).getAuditStatus());


    }

    class AuditItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView audit_id,audit_year,audit_duration;

        private AuditItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            audit_id = itemView.findViewById(R.id.textView_audit_id);
            audit_year = itemView.findViewById(R.id.textView_audit_year);
            audit_duration = itemView.findViewById(R.id.textView_audit_duration);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(AuditDetailsActivity.getIntent(context,
                    auditList.get(getAdapterPosition()).getAuditId(),
                    auditList.get(getAdapterPosition()).getName()));
        }
    }
}