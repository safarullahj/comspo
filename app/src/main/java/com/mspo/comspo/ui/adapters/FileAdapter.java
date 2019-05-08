package com.mspo.comspo.ui.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.File;
import com.mspo.comspo.data.remote.utils.Connectivity;
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

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileItemViewHolder> {

    private Context context;
    private List<File> fileList;
    private boolean statusFlag;
    private boolean offlineFlag;
    private String fileUrl,FileName;

    //private final String PATH = "/data/data/mspo/";

    public FileAdapter(Context context, List<File> fileList, boolean statusFlag, boolean offlineFlag) {
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

        holder.fileName.setText(fileList.get(position).getFileName());


    }

    /*public void addAuditlist(List<Audit> audits) {
        auditList.addAll(audits);
        notifyDataSetChanged();
    }*/

    class FileItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

        private static final int WRITE_REQUEST_CODE = 300;
        private AppCompatTextView fileName;
        private AppCompatImageView close;

        private FileItemViewHolder(View itemView) {
            super(itemView);


            fileName = itemView.findViewById(R.id.text_File_Name);
            close = itemView.findViewById(R.id.file_close);

            fileName.setOnClickListener(this);
            close.setOnClickListener(this);

            if (offlineFlag) {
                close.setVisibility(View.GONE);
            } else {
                if (statusFlag) {
                    close.setVisibility(View.VISIBLE);
                } else {
                    close.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.text_File_Name:

                    if (Connectivity.checkInternetIsActive(context) ){
                        if (CheckForSDCard.isSDCardPresent()) {
                            if (EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                fileUrl = fileList.get(getAdapterPosition()).getAicFile();
                                FileName = fileList.get(getAdapterPosition()).getFileName();

                                new DownloadFile().execute(fileUrl);

                            } else {
                                EasyPermissions.requestPermissions(context, "This app needs access to your file storage so that it can write files.", WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                            }


                        } else {
                            Toast.makeText(context,
                                    "SD Card not found", Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(context,
                                R.string.check_internet_connection, Toast.LENGTH_LONG).show();
                    }


                    //downloadFromUrl(fileList.get(getAdapterPosition()).getAicFile(), fileList.get(getAdapterPosition()).getFileName());
                    break;
            }


        }

        @Override
        public void onPermissionsGranted(int requestCode, List<String> perms) {

        }

        @Override
        public void onPermissionsDenied(int requestCode, List<String> perms) {
            Log.d("", "Permission has been denied");
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
                return "Downloaded at: " + folder + FileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
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
}


