package com.mspo.comspo.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.ui.activities.audit_details.AuditDetailsActivity;


public class ExternalAuditAdapter extends RecyclerView.Adapter<ExternalAuditAdapter.AuditItemViewHolder> {

    private Context context;


    public ExternalAuditAdapter(Context context) {
        this.context = context;

    }


    @Override
    public int getItemCount() {
        return 4;
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

        holder.audit_id.setText(""+position);

    }

    class AuditItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView audit_id;

        private AuditItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            audit_id = itemView.findViewById(R.id.textView_audit_id);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(AuditDetailsActivity.getIntent(context));
        }
    }
}


