package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list.SmallholderAuditListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SmallholderAuditListService {

    @GET(ApiConstants.ENDPOINT_SMALLHOLDER_AUDIT_LIST)
    Call<SmallholderAuditListResponse> getAuditList(@Path("farmId") Integer farmId,
                                                    @Header("access-token") String access_token,
                                                    @Query(value = "limit", encoded = true) String limit,
                                                    @Query(value = "offset", encoded = true) String offset,
                                                    @Query(value = "keyword", encoded = true) String keyword,
                                                    @Query(value = "filter_by", encoded = true) String filter_by,
                                                    @Query(value = "status", encoded = true) String status,
                                                    @Query(value = "farm_id", encoded = true) Integer farm_id,
                                                    @Query(value = "filter_by_year", encoded = true) String filter_by_year,
                                                    @Query(value = "is_internal_audit", encoded = true) Boolean is_internal_audit);
}