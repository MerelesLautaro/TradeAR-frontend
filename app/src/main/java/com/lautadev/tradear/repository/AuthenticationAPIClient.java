package com.lautadev.tradear.repository;

import com.lautadev.tradear.model.GoogleUserInfo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;
public interface AuthenticationAPIClient {


    // Endpoint para /auth/login-google
    @POST("/auth/login-google")
    Call<String> loginWithGoogle(@Body GoogleUserInfo googleUserInfo);
}
