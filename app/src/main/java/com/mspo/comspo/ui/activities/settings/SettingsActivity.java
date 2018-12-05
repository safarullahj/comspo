package com.mspo.comspo.ui.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.ChangeLanguageRequest;
import com.mspo.comspo.data.remote.model.responses.ChangeLanguageResponse;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.LanguageService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    AppCompatRadioButton malay, english;
    RadioGroup languageRadio;

    public static Intent getIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        languageRadio = findViewById(R.id.languageRadio);
        malay = findViewById(R.id.lan_malay);
        english = findViewById(R.id.lan_english);

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

            APIClient.getDrinkClient()
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
                        }

                        @Override
                        public void onFailure(@NonNull Call<ChangeLanguageResponse> call, @NonNull Throwable t) {

                            setRadioButton();
                            Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
        }else {
            setRadioButton();
            Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
