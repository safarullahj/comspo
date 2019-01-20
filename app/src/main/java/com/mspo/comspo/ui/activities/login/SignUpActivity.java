package com.mspo.comspo.ui.activities.login;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.SmallHolderSignUpRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.SmallHolderSignUpResponse;
import com.mspo.comspo.data.remote.model.responses.UserAvailabilityResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.SignupService;
import com.mspo.comspo.data.remote.webservice.UsernameAvailabilityService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText name;
    private AppCompatEditText username;
    private ProgressBar progressBar;
    private AppCompatImageView uname_Available;
    private AppCompatImageView uname_Unavailable;
    private AppCompatImageView username_search;
    private AppCompatEditText address;
    private AppCompatEditText countryCode;
    private AppCompatEditText phone;
    private AppCompatEditText email;
    private AppCompatEditText password;
    private MaterialButton sign_up;

    private Boolean isAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        progressBar = findViewById(R.id.username_progress);
        uname_Available = findViewById(R.id.username_available);
        uname_Unavailable = findViewById(R.id.username_unavailable);
        username_search = findViewById(R.id.username_search);
        address = findViewById(R.id.address);
        countryCode = findViewById(R.id.country_code);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.btnRegister);

        username_search.setOnClickListener(this);
        sign_up.setOnClickListener(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                username_search.setVisibility(View.VISIBLE);
                uname_Available.setVisibility(View.GONE);
                uname_Unavailable.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                isAvailable = false;
            }
        });

    }

    @Override
    public void onClick(final View view) {

        switch (view.getId()) {

            case R.id.username_search:

                if (Connectivity.checkInternetIsActive(SignUpActivity.this)) {

                    if (username.getText() != null && !username.getText().toString().equals("")) {

                        username_search.setVisibility(View.GONE);
                        uname_Available.setVisibility(View.GONE);
                        uname_Unavailable.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);

                        APIClient.getClient()
                                .create(UsernameAvailabilityService.class)
                                .checkAvailability(username.getText().toString())
                                .enqueue(new Callback<UserAvailabilityResponse>() {
                                    @Override
                                    public void onResponse(Call<UserAvailabilityResponse> call, Response<UserAvailabilityResponse> response) {

                                        if (response.isSuccessful()) {
                                            Log.e("logTest", "ststus = " + response.body().getStatus());
                                            if (response.body().getStatus()) {
                                                username_search.setVisibility(View.GONE);
                                                uname_Available.setVisibility(View.VISIBLE);
                                                uname_Unavailable.setVisibility(View.GONE);
                                                progressBar.setVisibility(View.GONE);

                                                isAvailable = true;
                                            } else {
                                                username_search.setVisibility(View.GONE);
                                                uname_Available.setVisibility(View.GONE);
                                                uname_Unavailable.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);
                                                isAvailable = false;
                                            }
                                        } else {
                                            Log.e("logTest", "fail");
                                            username_search.setVisibility(View.GONE);
                                            uname_Available.setVisibility(View.GONE);
                                            uname_Unavailable.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);

                                            isAvailable = false;
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<UserAvailabilityResponse> call, Throwable t) {
                                        username_search.setVisibility(View.VISIBLE);
                                        uname_Available.setVisibility(View.GONE);
                                        uname_Unavailable.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        isAvailable = false;

                                        Snackbar.make(view, "SOmething went wrong. Try again...", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                });
                    }
                } else {
                    Snackbar.make(view, "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;
            case R.id.btnRegister:

                if (Connectivity.checkInternetIsActive(SignUpActivity.this)) {
                    if (name.getText() == null || name.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Name", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        name.requestFocus();
                    } else if (username.getText() == null || username.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Username", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        username.requestFocus();
                    } else if (address.getText() == null || address.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Adress", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        address.requestFocus();
                    } else if (countryCode.getText() == null || countryCode.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Country Code", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        countryCode.requestFocus();
                    } else if (phone.getText() == null || phone.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Phone Number", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        phone.requestFocus();
                    } else if (email.getText() == null || email.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Email", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        email.requestFocus();
                    } else if (password.getText() == null || password.getText().toString().equals("")) {
                        Snackbar.make(view, "Enter Password", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        password.requestFocus();
                    } else {

                        if (isAvailable) {

                            SmallHolderSignUpRequest signUpRequest = new SmallHolderSignUpRequest(
                                    name.getText().toString(),
                                    username.getText().toString(),
                                    address.getText().toString(),
                                    phone.getText().toString(),
                                    email.getText().toString(),
                                    1,
                                    password.getText().toString(),
                                    countryCode.getText().toString(),
                                    "false");

                            APIClient.getClient()
                                    .create(SignupService.class)
                                    .doSignup(signUpRequest)
                                    .enqueue(new Callback<SmallHolderSignUpResponse>() {
                                        @Override
                                        public void onResponse(Call<SmallHolderSignUpResponse> call, Response<SmallHolderSignUpResponse> response) {

                                            if (response.isSuccessful()) {
                                                Log.e("logTest", "username = " + response.body());
                                                Toast.makeText(SignUpActivity.this, "Wait for Approval", Toast.LENGTH_LONG).show();
                                                finish();

                                            } else {
                                                Log.e("logTest", "fail");
                                                ErrorResponse error = ErrorUtils.parseError(response);
                                                Log.e("logTest", "fail : " + error.getErrorMessage());

                                                if (!error.getErrorMessage().equals("")) {
                                                    Snackbar.make(view, error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                } else {
                                                    Snackbar.make(view, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<SmallHolderSignUpResponse> call, Throwable t) {
                                            Snackbar.make(view, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    });

                        } else {
                            Snackbar.make(view, "Check Username Availability", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            username.requestFocus();
                        }
                    }
                } else {
                    Snackbar.make(view, "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;

        }
    }
}
