package com.mspo.comspo.data.remote.utils;

import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.webservice.APIClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                APIClient.getDrinkClient()
                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse error = null;

        try {
            ResponseBody errorBody = response.errorBody();
            if(errorBody!=null) {
                error = converter.convert(errorBody);
            }
        } catch (IOException e) {
            return new ErrorResponse();
        }

        return error;
    }
}

