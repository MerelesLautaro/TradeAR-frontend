package com.lautadev.tradear.repository;

import com.lautadev.tradear.dto.ExchangeDTO;
import com.lautadev.tradear.model.Exchange;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExchangeAPIClient {
    @POST("/exchange/save")
    Call<ExchangeDTO> saveExchange(@Body ExchangeDTO exchangeDTO);

    @GET("/exchange/get")
    Call<List<ExchangeDTO>> getExchanges();

    @GET("/exchange/get/{id}")
    Call<ExchangeDTO> findExchange(@Path("id") Long id);

    @DELETE("/exchange/delete/{id}")
    Call<String> deleteExchange(@Path("id") Long id);

    @PATCH("/exchange/edit/{id}")
    Call<ExchangeDTO> editExchange(@Path("id") Long id, @Body Exchange exchange);
}
