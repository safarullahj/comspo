package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.SmallHolderSignUpRequest;
import com.mspo.comspo.data.remote.model.responses.SmallHolderSignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupService {
    @POST(ApiConstants.ENDPOINT_SIGNUP)
    Call<SmallHolderSignUpResponse> doSignup(@Body SmallHolderSignUpRequest signUpRequest);
}
