package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.NewInternalAuditRequest;
import com.mspo.comspo.data.remote.model.responses.NewInternalAuditResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NewInternalAuditService {
    @POST(ApiConstants.ENDPOINT_NEW_INTERNAL_AUDIT)
    Call<NewInternalAuditResponse> createAudit(@Header("access-token") String access_token,
                                            @Body NewInternalAuditRequest internalAuditRequest);
}
