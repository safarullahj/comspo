package com.mspo.comspo.ui.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Aic;

import java.util.List;

public class AuditSheetAdapter extends RecyclerView.Adapter<AuditSheetAdapter.SheetItemViewHolder> {

    private Context context;
    private List<Aic> sheetList;


    public AuditSheetAdapter(Context context, List<Aic> sheetList) {
        this.context = context;
        this.sheetList = sheetList;
    }


    @Override
    public int getItemCount() {
        if (sheetList != null)
            return sheetList.size();
        else
            return 0;
    }


    @NonNull
    @Override
    public SheetItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_audit_sheet, parent, false);
        return new SheetItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SheetItemViewHolder holder, final int position) {

        holder.txtIndicatorHead.setText((position+1)+". INDICATORS");
        holder.txtIndicator.setText( sheetList.get(position).getIndicatorDescription());


    }

    class SheetItemViewHolder extends RecyclerView.ViewHolder  {

        private AppCompatTextView txtIndicatorHead,txtIndicator;

        private SheetItemViewHolder(View itemView) {
            super(itemView);

            txtIndicatorHead = itemView.findViewById(R.id.txt_indicatorHead);
            txtIndicator = itemView.findViewById(R.id.txt_indicator);

        }
    }
}

