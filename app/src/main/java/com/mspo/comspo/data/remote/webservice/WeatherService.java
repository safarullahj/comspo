package com.mspo.comspo.data.remote.webservice;

import com.mspo.comspo.data.remote.model.responses.weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<WeatherResponse> getWeatherData(@Query(value = "lat", encoded = true) Double lat,
                                         @Query(value = "lon", encoded = true) Double lon,
                                         @Query(value = "APPID", encoded = true) String APPID,
                                         @Query(value = "units", encoded = true) String units);
}
