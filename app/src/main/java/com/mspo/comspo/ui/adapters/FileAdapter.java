package com.mspo.comspo.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.File;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileItemViewHolder> {

    private Context context;
    private List<String> fileList;
    private boolean statusFlag;
    private boolean offlineFlag;

    public FileAdapter(Context context, List<String> fileList, boolean statusFlag, boolean offlineFlag) {
        this.context = context;
        this.fileList = fileList;
        this.statusFlag = statusFlag;
        this.offlineFlag = offlineFlag;
    }


    @Override
    public int getItemCount() {
        if (fileList != null)
            return fileList.size();
        else
            return 0;
    }


    @NonNull
    @Override
    public FileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);
        return new FileItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileItemViewHolder holder, final int position) {

        holder.fileName.setText(fileList.get(position));


    }

    /*public void addAuditlist(List<Audit> audits) {
        auditList.addAll(audits);
        notifyDataSetChanged();
    }*/

    class FileItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView fileName;
        private AppCompatImageView close;

        private FileItemViewHolder(View itemView) {
            super(itemView);


            fileName = itemView.findViewById(R.id.text_File_Name);
            close = itemView.findViewById(R.id.file_close);

            close.setOnClickListener(this);

            if(offlineFlag){
                close.setVisibility(View.GONE);
            }else {
                if (statusFlag) {
                    close.setVisibility(View.VISIBLE);
                } else {
                    close.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View view) {


        }
    }
}


