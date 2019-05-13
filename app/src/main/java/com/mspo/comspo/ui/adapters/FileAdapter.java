package com.mspo.comspo.ui.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.AuditorFileDeleteRequest;
import com.mspo.comspo.data.remote.model.requests.FileDeleteRequest;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.File;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditSheetService;
import com.mspo.comspo.ui.activities.MainActivity;
import com.mspo.comspo.utils.CheckForSDCard;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileItemViewHolder> {

    private Context context;
    private List<File> fileList;
    private boolean statusFlag;
    private boolean offlineFlag;
    private String fileUrl, FileName;
    private int chapter_audit_id;
    private int audit_id;

    //private final String PATH = "/data/data/mspo/";

    public FileAdapter(Context context, List<File> fileList, boolean statusFlag, boolean offlineFlag, int chapter_audit_id, int audit_id) {
        this.context = context;
        this.fileList = fileList;
        this.statusFlag = statusFlag;
        this.offlineFlag = offlineFlag;
        this.chapter_audit_id = chapter_audit_id;
        this.audit_id = audit_id;
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

        holder.fileName.setText(fileList.get(position).getFileName());


    }

    /*public void addAuditlist(List<Audit> audits) {
        auditList.addAll(audits);
        notifyDataSetChanged();
    }*/

    class FileItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

        private static final int WRITE_REQUEST_CODE = 300;
        private AppCompatTextView fileName;
        private AppCompatImageView file_delete;

        private FileItemViewHolder(View itemView) {
            super(itemView);


            fileName = itemView.findViewById(R.id.text_File_Name);
            file_delete = itemView.findViewById(R.id.file_delete);

            fileName.setOnClickListener(this);
            file_delete.setOnClickListener(this);

            if (offlineFlag) {
                file_delete.setVisibility(View.GONE);
            } else {
                if (statusFlag) {
                    file_delete.setVisibility(View.VISIBLE);
                } else {
                    file_delete.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.text_File_Name:

                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.downloadRequest))
                            .setPositiveButton(context.getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (Connectivity.checkInternetIsActive(context)) {
                                        if (CheckForSDCard.isSDCardPresent()) {
                                            if (EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                fileUrl = fileList.get(getAdapterPosition()).getAicFile();
                                                FileName = fileList.get(getAdapterPosition()).getFileName();

                                                new DownloadFile().execute(fileUrl);

                                            } else {
                                                EasyPermissions.requestPermissions(context, context.getString(R.string.storageRequest), WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                                            }


                                        } else {
                                            Toast.makeText(context,
                                                    R.string.SdNotFound, Toast.LENGTH_LONG).show();

                                        }
                                    } else {
                                        Toast.makeText(context,
                                                R.string.check_internet_connection, Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .setNegativeButton(context.getString(R.string.action_no), null)
                            .create()
                            .show();


                    //downloadFromUrl(fileList.get(getAdapterPosition()).getAicFile(), fileList.get(getAdapterPosition()).getFileName());
                    break;

                case R.id.file_delete:

                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.delete_file))
                            .setPositiveButton(context.getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (Connectivity.checkInternetIsActive(context)) {

                                        ProgressDialog progressDialog = new ProgressDialog(context);
                                        showProgressDialog(progressDialog, fileList.get(getAdapterPosition()).getFileName());

                                        Log.e("file_delete_:", "aic_file_id : " + fileList.get(getAdapterPosition()).getAicFileId());
                                        Log.e("file_delete_:", "chapter_audit_id : " + chapter_audit_id);
                                        Log.e("file_delete_:", "audit_id : " + audit_id);

                                        Log.e("file_delete_:", "acc_toc : " + PrefManager.getAccessToken(context));
                                        Log.e("file_delete_:", "farm id : " + PrefManager.getFarmId(context));

                                       if( PrefManager.getUserType(context).equals("auditor")){

                                           AuditorFileDeleteRequest auditorFileDeleteRequest = new AuditorFileDeleteRequest(PrefManager.getFarmId(context));

                                           APIClient.getClient()
                                                   .create(AuditSheetService.class)
                                                   .deleteAuditorFile(fileList.get(getAdapterPosition()).getAicFileId(),
                                                           audit_id,
                                                           chapter_audit_id,
                                                           PrefManager.getAccessToken(context),
                                                           auditorFileDeleteRequest)
                                                   .enqueue(new Callback<CommonResponse>() {
                                                       @Override
                                                       public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                                                           if (response.isSuccessful()) {

                                                               if (response.body().getStatus()) {
                                                                   Toast.makeText(context, "File Deleted", Toast.LENGTH_LONG).show();

                                                                   fileList.remove(getAdapterPosition());
                                                                   notifyDataSetChanged();

                                                               } else {
                                                                   Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_LONG).show();
                                                               }


                                                           } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                                               Toast.makeText(context, R.string.response_fail, Toast.LENGTH_LONG).show();
                                                               Log.e("file_delete_:", "message: " + response.message());
                                                               //Log.e("file_delete_:", "audit_id : " + response.errorBody().);


                                                           }
                                                           progressDialog.dismiss();

                                                       }

                                                       @Override
                                                       public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                                                           progressDialog.dismiss();
                                                           Log.e("RetrofitErr", t.getMessage());
                                                           Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                                       }
                                                   });

                                       }else {

                                           FileDeleteRequest fileDeleteRequest = new FileDeleteRequest(PrefManager.getFarmId(context));

                                           APIClient.getClient()
                                                   .create(AuditSheetService.class)
                                                   .deleteFile(fileList.get(getAdapterPosition()).getAicFileId(),
                                                           audit_id,
                                                           chapter_audit_id,
                                                           PrefManager.getAccessToken(context),
                                                           fileDeleteRequest)
                                                   .enqueue(new Callback<CommonResponse>() {
                                                       @Override
                                                       public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                                                           if (response.isSuccessful()) {

                                                               if (response.body().getStatus()) {
                                                                   Toast.makeText(context, "File Deleted", Toast.LENGTH_LONG).show();

                                                                   fileList.remove(getAdapterPosition());
                                                                   notifyDataSetChanged();

                                                               } else {
                                                                   Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_LONG).show();
                                                               }


                                                           } else {

                                /*Snackbar.make(record_inspection, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                                               Toast.makeText(context, R.string.response_fail, Toast.LENGTH_LONG).show();
                                                               Log.e("file_delete_:", "message: " + response.message());
                                                               //Log.e("file_delete_:", "audit_id : " + response.errorBody().);


                                                           }
                                                           progressDialog.dismiss();

                                                       }

                                                       @Override
                                                       public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                                                           progressDialog.dismiss();
                                                           Log.e("RetrofitErr", t.getMessage());
                                                           Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                                       }
                                                   });
                                       }


                                    } else {
                                        Toast.makeText(context,
                                                R.string.check_internet_connection, Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .setNegativeButton(context.getString(R.string.action_no), null)
                            .create()
                            .show();
                    break;
            }


        }

        @Override
        public void onPermissionsGranted(int requestCode, List<String> perms) {

        }

        @Override
        public void onPermissionsDenied(int requestCode, List<String> perms) {
            Log.d("", context.getString(R.string.permission_denied));
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

        }
    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String folder;
        private boolean isDownloaded;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lengthOfFile = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                FileName = timestamp + "_" + FileName;

                folder = Environment.getExternalStorageDirectory() + java.io.File.separator + "MSPO/";

                java.io.File directory = new java.io.File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                OutputStream output = new FileOutputStream(folder + FileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d("", "Progress: " + (int) ((total * 100) / lengthOfFile));

                    output.write(data, 0, count);
                }

                output.flush();

                output.close();
                input.close();
                return context.getString(R.string.download_at) + folder + FileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return context.getString(R.string.something_wrong);
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(context,
                    message, Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressDialog(ProgressDialog progressDialog,String title){
        progressDialog.setTitle(title);
        progressDialog.setMessage(context.getString(R.string.wait_delete));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}


