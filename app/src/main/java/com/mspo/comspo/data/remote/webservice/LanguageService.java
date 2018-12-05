package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.ChangeLanguageRequest;
import com.mspo.comspo.data.remote.model.responses.ChangeLanguageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LanguageService {
    @POST(ApiConstants.ENDPOINT_LANGUAGE)
    Call<ChangeLanguageResponse> changeLanguage(@Header("access-token") String access_token,
                                          @Body ChangeLanguageRequest languageRequest);
}
