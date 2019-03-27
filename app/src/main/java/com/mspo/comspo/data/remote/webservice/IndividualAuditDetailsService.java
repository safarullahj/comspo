package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.AuditAcceptRequest;
import com.mspo.comspo.data.remote.model.requests.EditAuditRequest;
import com.mspo.comspo.data.remote.model.responses.AuditorStatusResponse;
import com.mspo.comspo.data.remote.model.responses.CommonResponse;
import com.mspo.comspo.data.remote.model.responses.EditAuditResponse;
import com.mspo.comspo.data.remote.model.responses.group_audit_details.GroupAuditDetailsResponse;
import com.mspo.comspo.data.remote.model.responses.internal_audit_details.IndividualAuditDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IndividualAuditDetailsService {
    @GET(ApiConstants.ENDPOINT_INDIVIDUAL_AUDIT_DETAILS)
    Call<IndividualAuditDetailsResponse> getAuditDetails(@Path("auditId") Integer auditId,
                                                           @Header("access-token") String access_token,
                                                           @Query(value = "farm_id", encoded = true) Integer farm_id,
                                                           @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);

    @GET(ApiConstants.ENDPOINT_INDIVIDUAL_AUDIT_DETAILS)
    Call<IndividualAuditDetailsResponse> getAuditorSingleAuditDetails(@Path("auditId") Integer auditId,
                                                         @Header("access-token") String access_token,
                                                         @Query(value = "auditor_id", encoded = true) Integer farm_id,
                                                         @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);

    @GET(ApiConstants.ENDPOINT_GROUP_AUDIT_DETAILS)
    Call<GroupAuditDetailsResponse> getAuditorGroupAuditDetails(@Path("auditId") Integer auditId,
                                                                @Header("access-token") String access_token,
                                                                @Query(value = "auditor_id", encoded = true) Integer farm_id,
                                                                @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);


    @POST(ApiConstants.ENDPOINT_EDIT_AUDIT_DURATION)
    Call<EditAuditResponse> editAudit(@Header("access-token") String access_token,
                                        @Path("auditId") Integer auditId,
                                        @Body EditAuditRequest editAuditRequest);

    @POST(ApiConstants.ENDPOINT_AUDITOR_AUDIT_STATUS)
    Call<AuditorStatusResponse> auditStatus(@Header("access-token") String access_token,
                                            @Path("auditId") Integer auditId,
                                            @Body AuditAcceptRequest auditAcceptRequest);

}
