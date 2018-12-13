package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IndividualAuditDetailsService {
    @GET(ApiConstants.ENDPOINT_INDIVIDUAL_AUDIT_DETAILS)
    Call<IndividualAuditDetailsResponse> getAuditDetails(@Path("auditId") Integer auditId,
                                                           @Header("access-token") String access_token,
                                                           @Query(value = "farm_id", encoded = true) Integer farm_id,
                                                           @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);
}
