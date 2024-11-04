package com.lautadev.tradear.repository;

import com.lautadev.tradear.dto.UserSecDTO;
import com.lautadev.tradear.model.UserSec;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserSecAPIClient {
    @POST("/user/save")
    Call<String> saveUser(@Body UserSec userSec);

    @GET("/user/get")
    Call<List<UserSec>> getUsers();

    @GET("/user/get/{id}")
    Call<UserSecDTO> findUser(@Path("id") Long id);

    @GET("/user/get/user-entity/{id}")
    Call<UserSec> findUserEntity(@Path("id") Long id);

    @GET("/user/get/findByEmail")
    Call<UserSecDTO> findUserByEmail(@Query("email") String email);

    @DELETE("/user/delete/{id}")
    Call<String> deleteUser(@Path("id") Long id);

    @PATCH("/user/edit/{id}")
    Call<UserSec> editUser(@Path("id") Long id, @Body UserSec userSec);
}
