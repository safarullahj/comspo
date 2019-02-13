package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.auditor_home_audit_list.AuditorAuditListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AuditorAuditListService {

    @GET(ApiConstants.ENDPOINT_AUDITOR_AUDIT_LIST)
    Call<AuditorAuditListResponse> getAuditList(@Header("access-token") String access_token,
                                                @Query(value = "offset", encoded = true) String offset,
                                                @Query(value = "limit", encoded = true) String limit,
                                                @Query(value = "cb_id", encoded = true) String cb_id,
                                                @Query(value = "keyword", encoded = true) String keyword,
                                                @Query(value = "filter_by_year", encoded = true) String filter_by_year,
                                                @Query(value = "auditor_id", encoded = true) Integer auditor_id,
                                                @Query(value = "status", encoded = true) String status,
                                                @Query(value = "filter_by", encoded = true) String filter_by,
                                                @Query(value = "audit_type", encoded = true) String audit_type);
}
