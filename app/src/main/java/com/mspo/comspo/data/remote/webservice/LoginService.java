package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.LoginRequest;
import com.mspo.comspo.data.remote.model.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST(ApiConstants.ENDPOINT_LOGIN)
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);
}
