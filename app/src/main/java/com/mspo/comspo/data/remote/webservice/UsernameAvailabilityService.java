package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.UserAvailabilityResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsernameAvailabilityService {
    @GET(ApiConstants.ENDPOINT_USERNAME_AVAILABILITY)
    Call<UserAvailabilityResponse> checkAvailability(@Query(value = "username", encoded = true) String username);
}
