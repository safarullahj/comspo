package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.profile_view.ProfileViewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProfileService {
    @GET(ApiConstants.ENDPOINT_PROFILE_VIEW)
    Call<ProfileViewResponse> getProfile(@Header("access-token") String access_token);
}
