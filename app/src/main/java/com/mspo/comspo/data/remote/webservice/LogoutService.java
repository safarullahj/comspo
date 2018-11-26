package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.LogoutRequest;
import com.mspo.comspo.data.remote.model.responses.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LogoutService {
    @POST(ApiConstants.ENDPOINT_LOGOUT)
    Call<LogoutResponse> doLogout(@Header("access-token") String access_token,
                                  @Body LogoutRequest logoutRequest);
}
