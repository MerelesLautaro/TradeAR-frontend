package com.lautadev.tradear.repository;

import com.lautadev.tradear.dto.MessageDTO;
import com.lautadev.tradear.dto.SendMessageDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageAPIClient {
    @POST("/message/save")
    Call<MessageDTO> saveMessage(@Body SendMessageDTO sendMessageDTO);
}
