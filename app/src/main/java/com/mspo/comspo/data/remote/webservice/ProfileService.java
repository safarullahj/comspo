package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.profile_view.ProfileViewResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.auditor.AuditorProfileViewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfileService {
    @GET(ApiConstants.ENDPOINT_PROFILE_VIEW)
    Call<ProfileViewResponse> getProfile(@Header("access-token") String access_token,
                                         @Path("farmId") Integer farmId);


    @GET(ApiConstants.ENDPOINT_AUDITOR_PROFILE_VIEW)
    Call<AuditorProfileViewResponse> getAuditorProfile(@Header("access-token") String access_token,
                                                       @Query(value = "cb_id", encoded = true) String cb_id);

}
