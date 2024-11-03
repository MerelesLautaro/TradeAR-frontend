package com.lautadev.tradear.repository;

import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.model.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageAPIClient {

    @POST("/message/save")
    Call<ItemDTO> saveItem(@Body Message message);
}
