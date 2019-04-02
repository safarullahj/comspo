package com.mspo.comspo.ui.activities.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.ChangeLanguageRequest;
import com.mspo.comspo.data.remote.model.requests.ChangePasswordRequest;
import com.mspo.comspo.data.remote.model.responses.ChangeLanguageResponse;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.LanguageService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private AppCompatRadioButton malay, english;
    private RadioGroup languageRadio;
    private MaterialButton btn_change_password;
    private AlertDialog pDialog;


    public static Intent getIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        languageRadio = findViewById(R.id.languageRadio);
        malay = findViewById(R.id.lan_malay);
        english = findViewById(R.id.lan_english);

        btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(this);

        setRadioButton();

        languageRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.lan_malay:
                        changeLanguage("malay");
                        break;
                    case R.id.lan_english:
                        changeLanguage("english");
                        break;
                }
            }
        });
    }

    private void setRadioButton() {
        if (PrefManager.getUserLanguage(SettingsActivity.this).equals("malay")) {
            malay.setChecked(true);
        } else {
            english.setChecked(true);
        }
    }

    private void changeLanguage(final String language) {

        if (Connectivity.checkInternetIsActive(SettingsActivity.this)) {

            ChangeLanguageRequest languageRequest = new ChangeLanguageRequest(language);
            progressBar.setVisibility(View.VISIBLE);
            APIClient.getClient()
                    .create(LanguageService.class)
                    .changeLanguage(PrefManager.getAccessToken(SettingsActivity.this), languageRequest)
                    .enqueue(new Callback<ChangeLanguageResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ChangeLanguageResponse> call, @NonNull Response<ChangeLanguageResponse> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    PrefManager.setUserLanguage(SettingsActivity.this, language);
                                    setRadioButton();
                                }

                            } else {
                                setRadioButton();
                                ErrorResponse error = ErrorUtils.parseError(response);

                                if (!error.getErrorMessage().equals("")) {
                                    Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<ChangeLanguageResponse> call, @NonNull Throwable t) {

                            setRadioButton();
                            /*Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(findViewById(android.R.id.content), "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            setRadioButton();
            Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_change_password:
                showPasswordDialog();
                break;
        }
    }

    private void showPasswordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View dialog = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        dialogBuilder.setView(dialog);

        pDialog = dialogBuilder.create();
        pDialog.show();


        AppCompatEditText editText_old_psw = dialog.findViewById(R.id.editText_old_psw);
        AppCompatEditText editText_new_psw = dialog.findViewById(R.id.editText_new_psw);
        AppCompatEditText editText_retype_psw = dialog.findViewById(R.id.editText_retype_psw);
        AppCompatButton change = dialog.findViewById(R.id.btnCgange);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(editText_old_psw.getText() == null || editText_old_psw.getText().toString().equals("")){
                   Toast.makeText(SettingsActivity.this, "Enter Old Password", Toast.LENGTH_LONG).show();
               }else if(editText_new_psw.getText() == null || editText_new_psw.getText().toString().equals("")){
                   Toast.makeText(SettingsActivity.this, "Enter New Password", Toast.LENGTH_LONG).show();
               }else if(editText_retype_psw.getText() == null || editText_retype_psw.getText().toString().equals("")){
                   Toast.makeText(SettingsActivity.this, "Retype New Password", Toast.LENGTH_LONG).show();
               }else if(!editText_new_psw.getText().toString().equals(editText_retype_psw.getText().toString())){
                   Toast.makeText(SettingsActivity.this, "Miss match Retype Password", Toast.LENGTH_LONG).show();
               }else {
                   /*Toast.makeText(SettingsActivity.this, ""+editText_old_psw.getText().toString()
                           +"-"+editText_new_psw.getText().toString()
                           +"-"+editText_retype_psw.getText().toString(), Toast.LENGTH_LONG).show();*/
                   changePassword(editText_old_psw.getText().toString() , editText_new_psw.getText().toString());
                   pDialog.dismiss();
               }

            }
        });
    }

    private void changePassword(String oldPsw , String newPsw) {

        if (Connectivity.checkInternetIsActive(SettingsActivity.this)) {

            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPsw,newPsw);
            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(LanguageService.class)
                    .changePassword(PrefManager.getAccessToken(SettingsActivity.this), changePasswordRequest)
                    .enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    Snackbar.make(findViewById(android.R.id.content), "Change Password Successfully", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                            } else {
                                ErrorResponse error = ErrorUtils.parseError(response);

                                if (!error.getErrorMessage().equals("")) {
                                    Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                            /*Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(findViewById(android.R.id.content), "" + t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}
