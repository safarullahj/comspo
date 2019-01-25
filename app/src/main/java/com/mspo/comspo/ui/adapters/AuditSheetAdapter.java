package com.mspo.comspo.ui.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Aic;
import com.mspo.comspo.ui.activities.audit_sheet.CustomSpinnerAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AuditSheetAdapter extends RecyclerView.Adapter<AuditSheetAdapter.SheetItemViewHolder> {

    private Context context;
    private List<Aic> sheetList;
    private CustomSpinnerAdapter customAdapter;
    private String version;


    public AuditSheetAdapter(Context context, List<Aic> sheetList, CustomSpinnerAdapter customAdapter, String version) {
        this.context = context;
        this.sheetList = sheetList;
        this.customAdapter = customAdapter;
        this.version = version;
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

        holder.txtIndicatorHead.setText(version+"."+sheetList.get(position).getPosition() +" INDICATORS");
        holder.txtIndicator.setText( sheetList.get(position).getIndicatorDescription());

        if (sheetList.get(position).getIndicatorType().equals("M")){
            holder.rMinorNonComplience.setVisibility(View.GONE);
        }else if (sheetList.get(position).getIndicatorType().equals("Mi")){

        }else if (sheetList.get(position).getIndicatorType().equals("O")){
            holder.rMajorNonComplience.setVisibility(View.GONE);
        }

        if (sheetList.get(position).getComplianceValue() == -2.0){
            holder.rComplies.setChecked(false);
            holder.rMajorNonComplience.setChecked(false);
            holder.rMinorNonComplience.setChecked(false);
            holder.rNotApplicable.setChecked(false);
        }else if (sheetList.get(position).getComplianceValue() == -1.0){
            holder.rNotApplicable.setChecked(true);
        }else if (sheetList.get(position).getComplianceValue() == 0.0){
            holder.rMajorNonComplience.setChecked(true);
        }else if (sheetList.get(position).getComplianceValue() == 0.5){
            holder.rMinorNonComplience.setChecked(true);
        }else if (sheetList.get(position).getComplianceValue() == 1.0){
            holder.rComplies.setChecked(true);
        }


        holder.edtObservation.setText(sheetList.get(position).getIndicatorSuggestion());


    }

    class SheetItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private AppCompatTextView txtIndicatorHead,txtIndicator;
        private AppCompatRadioButton rComplies, rMajorNonComplience,rMinorNonComplience,rNotApplicable;
        private RadioGroup scoreRadio;
        private AppCompatEditText edtObservation;
        private int f=0;

        private SheetItemViewHolder(View itemView) {
            super(itemView);

            txtIndicatorHead = itemView.findViewById(R.id.txt_indicatorHead);
            txtIndicator = itemView.findViewById(R.id.txt_indicator);
            edtObservation = itemView.findViewById(R.id.editText_observation);

            scoreRadio = itemView.findViewById(R.id.scoreRadio);

            rComplies = itemView.findViewById(R.id.rad_complies);
            rMajorNonComplience = itemView.findViewById(R.id.rad_majorNonComplience);
            rMinorNonComplience = itemView.findViewById(R.id.rad_minorNonComplience);
            rNotApplicable = itemView.findViewById(R.id.rad_notApplicable);

            rComplies.setOnClickListener(this);
            rMajorNonComplience.setOnClickListener(this);
            rMinorNonComplience.setOnClickListener(this);
            rNotApplicable.setOnClickListener(this);


            /*scoreRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(sheetList.get(getAdapterPosition()).getComplianceValue() == -2.0){
                        f=1;
                    }
                    f++;
                    *//*switch (i) {
                        case R.id.rad_complies:
                            Log.e("rad","rad_complies");
                            sheetList.get(getAdapterPosition()).setComplianceValue(1.0);
                            break;
                        case R.id.rad_majorNonComplience:
                            Log.e("rad","rad_majorNonComplience");
                            sheetList.get(getAdapterPosition()).setComplianceValue(0.0);
                            break;
                        case R.id.rad_minorNonComplience:
                            Log.e("rad","rad_minorNonComplience");
                            sheetList.get(getAdapterPosition()).setComplianceValue(0.5);
                            break;
                        case R.id.rad_notApplicable:
                            Log.e("rad","rad_notApplicable");
                            sheetList.get(getAdapterPosition()).setComplianceValue(-1.0);
                            break;
                    }*//*

                    if(f > 1) {
                        Log.e("date_:", "D : " + TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis()));
                        sheetList.get(getAdapterPosition()).setLastEditedTime(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis())));
                        Log.e("date_:", "getD : " + sheetList.get(getAdapterPosition()).getLastEditedTime());
                    }
                }
            });*/

            edtObservation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                   Log.e("obser" , "obs_: "+edtObservation.getText().toString());
                    sheetList.get(getAdapterPosition()).setIndicatorSuggestion(edtObservation.getText().toString());
                }
            });
        }


        @Override
        public void onClick(View view) {

            Double vl = sheetList.get(getAdapterPosition()).getComplianceValue();

            switch (view.getId()) {
                case R.id.rad_complies:
                    Log.e("rad", "click");
                    if(vl == 1.0) {
                        Log.e("rad", "rad_complies uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    }else {
                        Log.e("rad","rad_complies");
                        sheetList.get(getAdapterPosition()).setComplianceValue(1.0);
                    }
                    break;
                case R.id.rad_majorNonComplience:
                    Log.e("rad", "click");
                    if(vl == 0.0) {
                        Log.e("rad", "rad_majorNonComplience uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    }else {
                        Log.e("rad","rad_majorNonComplience");
                        sheetList.get(getAdapterPosition()).setComplianceValue(0.0);
                    }
                    break;
                case R.id.rad_minorNonComplience:
                    Log.e("rad", "click");
                    if(vl == 0.5) {
                        Log.e("rad", "rad_minorNonComplience uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    }else {
                        Log.e("rad","rad_minorNonComplience");
                        sheetList.get(getAdapterPosition()).setComplianceValue(0.5);
                    }
                    break;
                case R.id.rad_notApplicable:
                    Log.e("rad", "click");
                    if(vl == -1.0) {
                        Log.e("rad", "rad_notApplicable uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    }else {
                        Log.e("rad","rad_notApplicable");
                        sheetList.get(getAdapterPosition()).setComplianceValue(-1.0);
                    }
                    break;
            }
            Log.e("date_:", "D : " + TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis()));
            sheetList.get(getAdapterPosition()).setLastEditedTime(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis())));
            Log.e("date_:", "getD : " + sheetList.get(getAdapterPosition()).getLastEditedTime());
            customAdapter.notifyDataSetChanged();
        }
    }
}

