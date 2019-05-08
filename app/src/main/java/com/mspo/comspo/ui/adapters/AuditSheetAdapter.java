package com.mspo.comspo.ui.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Aic;
import com.mspo.comspo.ui.activities.audit_sheet.CustomSpinnerAdapter;
import com.mspo.comspo.ui.activities.audit_sheet.IssuesEvidenceListing;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AuditSheetAdapter extends RecyclerView.Adapter<AuditSheetAdapter.SheetItemViewHolder> {

    private static final int FILE_REQUEST_CODE = 101;
    private Context context;
    private List<Aic> sheetList;
    private CustomSpinnerAdapter customAdapter;
    private String version;
    private boolean statusFlag;
    private boolean offlineFlag;


    public AuditSheetAdapter(Context context, List<Aic> sheetList, CustomSpinnerAdapter customAdapter, String version, boolean statusFlag, boolean offlineFlag) {
        this.context = context;
        this.sheetList = sheetList;
        this.customAdapter = customAdapter;
        this.version = version;
        this.statusFlag = statusFlag;
        this.offlineFlag = offlineFlag;
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

        holder.txtIndicatorHead.setText(version + "." + sheetList.get(position).getPosition() + context.getString(R.string.indicators));
        holder.txtIndicator.setText(sheetList.get(position).getIndicatorDescription());

        if (sheetList.get(position).getIndicatorType().equals("M")) {
            holder.rMinorNonComplience.setVisibility(View.GONE);
        } else if (sheetList.get(position).getIndicatorType().equals("Mi")) {

        } else if (sheetList.get(position).getIndicatorType().equals("O")) {
            holder.rMajorNonComplience.setVisibility(View.GONE);
        }

        if (sheetList.get(position).getComplianceValue() == -2.0) {
            holder.rComplies.setChecked(false);
            holder.rMajorNonComplience.setChecked(false);
            holder.rMinorNonComplience.setChecked(false);
            holder.rNotApplicable.setChecked(false);
        } else if (sheetList.get(position).getComplianceValue() == -1.0) {
            holder.rNotApplicable.setChecked(true);
        } else if (sheetList.get(position).getComplianceValue() == 0.0) {
            holder.rMajorNonComplience.setChecked(true);
        } else if (sheetList.get(position).getComplianceValue() == 0.5) {
            holder.rMinorNonComplience.setChecked(true);
        } else if (sheetList.get(position).getComplianceValue() == 1.0) {
            holder.rComplies.setChecked(true);
        }


        holder.edtObservation.setText(sheetList.get(position).getIndicatorSuggestion());

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(verticalLayoutmanager);

        //List<String> list = Arrays.asList("1.jbvbbsd.png", "2.hbsafbahba.jpg" , "3.gvgskacv.doc" , "4.jbkbkvbdku.ppt" ,"5.jbvbbsd.png", "6.hbsafbahba.jpg" , "7.gvgskacv.doc" , "8.jbkbkvbdku.ppt","9.jbvbbsd.png", "10.hbsafbahba.jpg" , "11.gvgskacv.doc" , "12.jbkbkvbdku.ppt");
        holder.fileAdapter = new FileAdapter(context,sheetList.get(position).getFiles(),statusFlag,offlineFlag);

        holder.recyclerView.setAdapter(holder.fileAdapter);


    }


/*
    private void uploadFile(MediaFile mediaFile) {
        // create upload service client
        FileUploadService service =
                APIClient.getClient().create(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(mediaFile.getPath());


            RequestBody requestFile;
            requestFile = RequestBody.create(
                    MediaType.parse(mediaFile.getMimeType()),
                    file
            );

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("audit_file", file.getName(), requestFile);

            // finally, execute the request
            Call<FileUploadResponse> call = service.upload("e5a66d6a2c42491ab0f2ced675f56551", body);
            call.enqueue(new Callback<FileUploadResponse>() {
                @Override
                public void onResponse(Call<FileUploadResponse> call,
                                       Response<FileUploadResponse> response) {
                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });

    }
*/

    class SheetItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView txtIndicatorHead, txtIndicator;
        private AppCompatRadioButton rComplies, rMajorNonComplience, rMinorNonComplience, rNotApplicable;
        private MaterialButton btn_Issues, btn_Evidence,btn_Upload;
        private RadioGroup scoreRadio;
        private AppCompatEditText edtObservation;
        private RecyclerView recyclerView;
        private FileAdapter fileAdapter;
        private int f = 0;

        private SheetItemViewHolder(View itemView) {
            super(itemView);

            txtIndicatorHead = itemView.findViewById(R.id.txt_indicatorHead);
            txtIndicator = itemView.findViewById(R.id.txt_indicator);
            edtObservation = itemView.findViewById(R.id.editText_observation);
            btn_Upload = itemView.findViewById(R.id.btn_Upload);
            btn_Upload.setOnClickListener(this);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.addItemDecoration(new SpacesItemDecoration(context, R.dimen.spacing_normal));
            fileAdapter = null;


            btn_Issues = itemView.findViewById(R.id.btn_Issues);
            btn_Evidence = itemView.findViewById(R.id.btn_Evidence);
            btn_Issues.setOnClickListener(this);
            btn_Evidence.setOnClickListener(this);

            scoreRadio = itemView.findViewById(R.id.scoreRadio);

            rComplies = itemView.findViewById(R.id.rad_complies);
            rMajorNonComplience = itemView.findViewById(R.id.rad_majorNonComplience);
            rMinorNonComplience = itemView.findViewById(R.id.rad_minorNonComplience);
            rNotApplicable = itemView.findViewById(R.id.rad_notApplicable);

            if (statusFlag) {

                rComplies.setClickable(true);
                rMajorNonComplience.setClickable(true);
                rMinorNonComplience.setClickable(true);
                rNotApplicable.setClickable(true);
                edtObservation.setFocusable(true);

                rComplies.setOnClickListener(this);
                rMajorNonComplience.setOnClickListener(this);
                rMinorNonComplience.setOnClickListener(this);
                rNotApplicable.setOnClickListener(this);

                btn_Upload.setVisibility(View.VISIBLE);

            } else {

                rComplies.setClickable(false);
                rMajorNonComplience.setClickable(false);
                rMinorNonComplience.setClickable(false);
                rNotApplicable.setClickable(false);
                edtObservation.setFocusable(false);

                btn_Upload.setVisibility(View.GONE);

            }

            if(offlineFlag){
                btn_Upload.setVisibility(View.GONE);
            }


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
                    Log.e("obser", "obs_: " + edtObservation.getText().toString());
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
                    if (vl == 1.0) {
                        Log.e("rad", "rad_complies uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    } else {
                        Log.e("rad", "rad_complies");
                        sheetList.get(getAdapterPosition()).setComplianceValue(1.0);
                    }
                    break;
                case R.id.rad_majorNonComplience:
                    Log.e("rad", "click");
                    if (vl == 0.0) {
                        Log.e("rad", "rad_majorNonComplience uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    } else {
                        Log.e("rad", "rad_majorNonComplience");
                        sheetList.get(getAdapterPosition()).setComplianceValue(0.0);
                    }
                    break;
                case R.id.rad_minorNonComplience:
                    Log.e("rad", "click");
                    if (vl == 0.5) {
                        Log.e("rad", "rad_minorNonComplience uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    } else {
                        Log.e("rad", "rad_minorNonComplience");
                        sheetList.get(getAdapterPosition()).setComplianceValue(0.5);
                    }
                    break;
                case R.id.rad_notApplicable:
                    Log.e("rad", "click");
                    if (vl == -1.0) {
                        Log.e("rad", "rad_notApplicable uncheck");
                        scoreRadio.clearCheck();
                        sheetList.get(getAdapterPosition()).setComplianceValue(-2.0);
                    } else {
                        Log.e("rad", "rad_notApplicable");
                        sheetList.get(getAdapterPosition()).setComplianceValue(-1.0);
                    }
                    break;
                case R.id.btn_Issues:

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    @SuppressLint("InflateParams") final View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
                    dialogBuilder.setView(dialog);

                    AlertDialog aDialog = dialogBuilder.create();
                    aDialog.show();

                    LinearLayout list_container = dialog.findViewById(R.id.list_container);
                    new IssuesEvidenceListing(context,
                            sheetList.get(getAdapterPosition()).getIssuesToCheck(),
                            null,
                            list_container,
                            "ISSUES TO CHECK ");




                    break;
                case R.id.btn_Evidence:

                    AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(context);
                    @SuppressLint("InflateParams") final View dialog2 = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
                    dialogBuilder2.setView(dialog2);

                    AlertDialog aDialog2 = dialogBuilder2.create();
                    aDialog2.show();

                    LinearLayout list_container2 = dialog2.findViewById(R.id.list_container);
                    new IssuesEvidenceListing(context,
                            null,
                            sheetList.get(getAdapterPosition()).getEvidenceToCheck(),
                            list_container2,
                            "EVIDENCE TO CHECK");

                    break;

                case R.id.btn_Upload:
                    Log.e("File_:" , "upload");

                    showDialog(sheetList.get(getAdapterPosition()).getAuditIndicatorId());

                    /*Intent intent = new Intent(context, FilePickerActivity.class);
                    intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .enableImageCapture(true)
                            .setShowFiles(true)
                            .setMaxSelection(10)
                            .setSkipZeroSizeFiles(true)
                            .build());
                    ((Activity) context).startActivityForResult(intent, sheetList.get(getAdapterPosition()).getAuditIndicatorId());*/

                    break;
            }
            Log.e("date_:", "D : " + TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis()));
            sheetList.get(getAdapterPosition()).setLastEditedTime(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis())));
            Log.e("date_:", "getD : " + sheetList.get(getAdapterPosition()).getLastEditedTime());
            customAdapter.notifyDataSetChanged();
        }
    }

    private void showDialog(Integer auditIndicatorId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams") final View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_file, null);
        dialogBuilder.setView(dialog);

        AlertDialog bDialog = dialogBuilder.create();
        bDialog.show();

        MaterialButton btn_Image = dialog.findViewById(R.id.btn_Image);
        MaterialButton btn_Files = dialog.findViewById(R.id.btn_Files);


        btn_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(true)
                        .setShowVideos(false)
                        .setShowFiles(false)
                        .setMaxSelection(10)
                        .setSkipZeroSizeFiles(true)
                        .build());
                ((Activity) context).startActivityForResult(intent, auditIndicatorId);
                bDialog.dismiss();
            }
        });

        btn_Files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(false)
                        .enableImageCapture(false)
                        .setShowVideos(false)
                        .setShowFiles(true)
                        .setMaxSelection(10)
                        .setSkipZeroSizeFiles(true)
                        .build());
                ((Activity) context).startActivityForResult(intent, auditIndicatorId);
                bDialog.dismiss();
            }
        });


    }
}

