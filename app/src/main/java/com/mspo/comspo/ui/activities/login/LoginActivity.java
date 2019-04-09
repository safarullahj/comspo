package com.mspo.comspo.ui.activities.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.ChangeLanguageRequest;
import com.mspo.comspo.data.remote.model.requests.ForgotPasswordRequest;
import com.mspo.comspo.data.remote.model.requests.LoginRequest;
import com.mspo.comspo.data.remote.model.responses.ChangeLanguageResponse;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.LoginResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.LanguageService;
import com.mspo.comspo.data.remote.webservice.LoginService;
import com.mspo.comspo.ui.activities.MainActivity;
import com.mspo.comspo.utils.LocaleManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText username;
    private AppCompatEditText password;
    private MaterialButton log_in;
    private MaterialButton sign_up,forgot_password;
    private ProgressBar progressBar;

    private AppCompatRadioButton malay, english;
    private RadioGroup languageRadio;

    private AlertDialog fDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PrefManagerFilter managerFilter = new PrefManagerFilter(LoginActivity.this);
        managerFilter.clearFilter();

        Log.e("loging", "status: "+PrefManager.getLoginStatus(LoginActivity.this));
        Log.e("loging", "type: "+PrefManager.getUserType(LoginActivity.this));
        Log.e("loging", "token: "+PrefManager.getAccessToken(LoginActivity.this));
        Log.e("loging", "ID: "+PrefManager.getUserId(LoginActivity.this));

        if (PrefManager.getLoginStatus(LoginActivity.this)) {
            Log.e("loging", "if ");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }Log.e("loging", "else ");

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        log_in = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.signin_progress);
        sign_up = findViewById(R.id.btnLinkToRegisterScreen);

        languageRadio = findViewById(R.id.languageRadio);
        malay = findViewById(R.id.lan_malay);
        english = findViewById(R.id.lan_english);

        setRadioButton();

        log_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);

        forgot_password = findViewById(R.id.btnlinktoforgotpassword);
        forgot_password.setOnClickListener(this);

        languageRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.lan_malay:
                        setLocale("malay");
                        break;
                    case R.id.lan_english:
                        setLocale("english");
                        break;
                }
            }
        });

        progressBar.setVisibility(View.GONE);
    }

    private void setRadioButton() {
        if (PrefManager.getUserLanguage(LoginActivity.this).equals("malay")) {
            malay.setChecked(true);
        } else {
            english.setChecked(true);
        }
    }

    private void setLocale(String language) {
        if (language.equals("malay")) {
            LocaleManager.setNewLocale(LoginActivity.this, "ms");
        } else {
            LocaleManager.setNewLocale(LoginActivity.this, "en");
        }

        Intent restartIntent = new Intent(LoginActivity.this, LoginActivity.class);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(restartIntent);
        finish();
    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()) {

            case R.id.btnLogin:
                progressBar.setVisibility(View.VISIBLE);
                log_in.setClickable(false);
                if (Connectivity.checkInternetIsActive(LoginActivity.this)) {
                    if (username.getText() == null || username.getText().toString().equals("")) {
                        Snackbar.make(view, R.string.enter_username, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        progressBar.setVisibility(View.GONE);
                        log_in.setClickable(true);
                    } else if (password.getText() == null || password.getText().toString().equals("")) {
                        Snackbar.make(view, R.string.enter_password, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        progressBar.setVisibility(View.GONE);
                        log_in.setClickable(true);
                    } else {

                        Log.e("logTest", "else ");

                        LoginRequest loginRequest = new LoginRequest(username.getText().toString(), password.getText().toString());

                        APIClient.getClient()
                                .create(LoginService.class)
                                .doLogin(loginRequest)
                                .enqueue(new Callback<LoginResponse>() {
                                    @Override
                                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                                        if (response.isSuccessful()) {
                                            Log.e("logTest", "username = " + response.body().getUsername());

                                            PrefManager.saveLoginToken(LoginActivity.this, response.body().getUserType(),
                                                    response.body().getUserId(),
                                                    response.body().getAccessToken(),
                                                    response.body().getId(),
                                                    response.body().getUsername(),
                                                    response.body().getEmail(),
                                                    response.body().getProfilePic());

                                            PrefManager.setUserLanguage(LoginActivity.this , response.body().getLanguageChosen());
                                            if (PrefManager.getUserLanguage(LoginActivity.this).equals("malay")) {
                                                LocaleManager.setNewLocale(LoginActivity.this, "ms");
                                            } else {
                                                LocaleManager.setNewLocale(LoginActivity.this, "en");
                                            }


                                            if (PrefManager.getLoginStatus(LoginActivity.this)) {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                finish();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                            log_in.setClickable(true);

                                        } else {
                                            ErrorResponse error = ErrorUtils.parseError(response);
                                            Log.e("logTest", "fail" + error.getErrorMessage());

                                            if (!error.getErrorMessage().equals("")) {
                                                Snackbar.make(view, error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            } else {
                                                Snackbar.make(view, R.string.something_wrong, Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                            log_in.setClickable(true);

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                                        progressBar.setVisibility(View.GONE);
                                        log_in.setClickable(true);
                                        Snackbar.make(view, R.string.something_wrong, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        Snackbar.make(view, ""+t.getMessage(), Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                });

                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    log_in.setClickable(true);
                    Snackbar.make(view, R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;
            case R.id.btnLinkToRegisterScreen:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;

            case R.id.btnlinktoforgotpassword:
                showForgetPasswordDialog();
                break;
        }

    }

    private void showForgetPasswordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View dialog = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
        dialogBuilder.setView(dialog);

        fDialog = dialogBuilder.create();
        fDialog.show();


        AppCompatEditText editText_username = dialog.findViewById(R.id.editText_username);
        AppCompatButton ok = dialog.findViewById(R.id.btnOk);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_username.getText() == null || editText_username.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, R.string.enter_username, Toast.LENGTH_LONG).show();
                }else {
                    getUserName(editText_username.getText().toString());
                    fDialog.dismiss();
                }

            }
        });
    }

    private void getUserName(String user_name) {
        if (Connectivity.checkInternetIsActive(LoginActivity.this)) {

            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(user_name);
            progressBar.setVisibility(View.VISIBLE);

            APIClient.getClient()
                    .create(LoginService.class)
                    .forgotPassword(forgotPasswordRequest)
                    .enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus()) {
                                    Snackbar.make(findViewById(android.R.id.content), R.string.request_send_successfully, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                            } else {
                                ErrorResponse error = ErrorUtils.parseError(response);

                                if (!error.getErrorMessage().equals("")) {
                                    Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), R.string.something_wrong, Snackbar.LENGTH_LONG)
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
            Snackbar.make(findViewById(android.R.id.content), R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }
}

