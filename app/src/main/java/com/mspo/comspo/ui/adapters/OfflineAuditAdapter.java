package com.mspo.comspo.ui.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.offline_audit_sheet.OfflineAuditSheetResponse;
import com.mspo.comspo.ui.activities.audit_sheet.AuditSheetActivity;

import org.modelmapper.ModelMapper;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class OfflineAuditAdapter extends RealmRecyclerViewAdapter<OfflineAuditSheetResponse, OfflineAuditAdapter.AuditViewHolder> {

    private Activity context;
    private List<OfflineAuditSheetResponse> data;
    private boolean autoUpdate;
    private Realm realm;
    private ModelMapper modelMapper;

    public OfflineAuditAdapter(@NonNull Activity context,
                               @Nullable OrderedRealmCollection<OfflineAuditSheetResponse> data,
                               boolean autoUpdate) {
        super(data, autoUpdate);
        this.context = context;
        this.data = data;

        Realm.init(context);
        realm = Realm.getDefaultInstance();
        modelMapper = new ModelMapper();

    }

    @NonNull
    @Override
    public AuditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_internal_audit, viewGroup, false);
        return new AuditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuditViewHolder holder, int position) {

        holder.audit_id.setText(context.getString(R.string.audit_id_)+data.get(position).getAuditId());
        holder.audit_year.setText(context.getString(R.string.year_)+data.get(position).getYear());

    }

    public class AuditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private AppCompatTextView audit_id,audit_year,audit_status,textView_audit_status_head;

        public AuditViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            audit_id = itemView.findViewById(R.id.textView_audit_id);
            audit_year = itemView.findViewById(R.id.textView_audit_year);
            audit_status = itemView.findViewById(R.id.textView_audit_status);
            audit_status.setVisibility(View.GONE);
            textView_audit_status_head = itemView.findViewById(R.id.textView_audit_status_head);
            textView_audit_status_head.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {

            AuditSheetResponse auditResponse = new AuditSheetResponse();
            OfflineAuditSheetResponse sheetResponse = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("auditId", data.get(getAdapterPosition()).getAuditId())
                    .findFirst();

            if(sheetResponse != null){
                 auditResponse = modelMapper.map(sheetResponse, AuditSheetResponse.class);
            }
            context.startActivity(AuditSheetActivity.getIntent(context, auditResponse, null, "Offline", null,data.get(getAdapterPosition()).getAuditId()));
        }
    }
}
