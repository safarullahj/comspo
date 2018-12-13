package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuditSheetService {
    @GET(ApiConstants.ENDPOINT_AUDIT_SHEET)
    Call<AuditSheetResponse> getAuditSheet(@Path("auditId") Integer auditId,
                                           @Header("access-token") String access_token,
                                           @Query(value = "farm_id", encoded = true) Integer farm_id,
                                           @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);
}
