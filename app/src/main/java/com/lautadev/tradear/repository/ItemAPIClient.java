package com.lautadev.tradear.repository;

import com.lautadev.tradear.dto.ItemDTO;
import com.lautadev.tradear.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemAPIClient {
    @POST("/item/save")
    Call<ItemDTO> saveItem(@Body Item item);
    @GET("/item/get")
    Call<List<ItemDTO>> getItems();
    @GET("/item/get/{id}")
    Call<ItemDTO> findItem(@Path("id") Long id);

    @GET("/item/get/findItemsNotBelongingToUser/{id}")
    Call<List<ItemDTO>> indItemsNotBelongingToUser(@Path("id") Long id);

    @DELETE("/item/delete/{id}")
    Call<String> deleteItem(@Path("id") Long id);
    @PATCH("/item/edit/{id}")
    Call<ItemDTO> editItem(@Path("id") Long id, @Body Item item);
}