package com.lautadev.tradear.repository;

import com.lautadev.tradear.model.Chat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChatAPIClient {
    @GET("/chat/get/{id}")
    Call<Chat> findChat(@Path("id") Long id);
}
