package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save.SmallHolderAuditSheetSaveRequest;
import com.mspo.comspo.data.remote.model.responses.SmallHolderAuditSheetSaveResponse;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.AuditSheetResponse;
import com.mspo.comspo.data.remote.model.responses.result_sheet.ResultSheetResponse;
import com.mspo.comspo.data.remote.model.responses.upload.FileUploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuditSheetService {
    @GET(ApiConstants.ENDPOINT_GET_AUDIT_SHEET)
    Call<AuditSheetResponse> getAuditSheet(@Path("auditId") Integer auditId,
                                           @Header("access-token") String access_token,
                                           @Query(value = "farm_id", encoded = true) Integer farm_id,
                                           @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);

    @GET(ApiConstants.ENDPOINT_GET_AUDIT_SHEET)
    Call<AuditSheetResponse> getAuditorAuditSheet(@Path("auditId") Integer auditId,
                                                  @Header("access-token") String access_token,
                                                  @Query(value = "auditor_id", encoded = true) Integer farm_id,
                                                  @Query(value = "sub_audit_id", encoded = true) String sub_audit_id);


    @POST(ApiConstants.ENDPOINT_SAVE_SMALLHOLDER_AUDIT_SHEET)
    Call<SmallHolderAuditSheetSaveResponse> saveFarmerAuditSheet(@Path("auditId") Integer auditId,
                                                                 @Header("access-token") String access_token,
                                                                 @Body SmallHolderAuditSheetSaveRequest auditSheetSaveRequest);

    @GET(ApiConstants.ENDPOINT_RESULT_SHEET)
    Call<ResultSheetResponse> getResultSheetData(@Path("auditId") Integer auditId,
                                                 @Header("access-token") String access_token,
                                                 @Query(value = "farm_id", encoded = true) Integer farm_id);

    @GET(ApiConstants.ENDPOINT_RESULT_SHEET)
    Call<ResultSheetResponse> getAuditorResultSheetData(@Path("auditId") Integer auditId,
                                                        @Header("access-token") String access_token,
                                                        @Query(value = "auditor_id", encoded = true) Integer farm_id);

    @Multipart
    @POST(ApiConstants.ENDPOINT_FILE_UPLOAD)
    Call<FileUploadResponse> uploadFile(@Path("aic_id") Integer aic_id,
                                        @Path("audit_id") Integer audit_id,
                                        @Path("sub_audit_id") Integer sub_audit_id,
                                        @Header("access-token") String access_token,
                                        @Part MultipartBody.Part file);


}
