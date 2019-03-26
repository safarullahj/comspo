package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.AuditorProfileUpdateRequest;
import com.mspo.comspo.data.remote.model.requests.ProfileUpdateRequest;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.ProfileViewResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.UpdateProfileResponse;
import com.mspo.comspo.data.remote.model.responses.profile_view.auditor.AuditorProfileViewResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfileService {

    @GET(ApiConstants.ENDPOINT_PROFILE_VIEW)
    Call<ProfileViewResponse> getProfile(@Header("access-token") String access_token,
                                         @Path("farmId") Integer farmId);

    @POST(ApiConstants.ENDPOINT_EDIT_PROFILE_VIEW)
    Call<UpdateProfileResponse> editProfile(@Header("access-token") String access_token,
                                            @Path("farmId") Integer farmId,
                                            @Body ProfileUpdateRequest updateRequest);


    @GET(ApiConstants.ENDPOINT_AUDITOR_PROFILE_VIEW)
    Call<AuditorProfileViewResponse> getAuditorProfile(@Header("access-token") String access_token,
                                                       @Path("auditId") Integer auditId,
                                                       @Query(value = "cb_id", encoded = true) String cb_id);


    @POST(ApiConstants.ENDPOINT_EDIT_AUDITOR_PROFILE_VIEW)
    Call<CommonResponse> editAuditorProfile(@Header("access-token") String access_token,
                                            @Path("auditorId") Integer auditorId,
                                            @Body AuditorProfileUpdateRequest updateRequest);

}
