package com.lautadev.tradear.repository;

import com.lautadev.tradear.model.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountAPIClient {
    @POST("/account/save")
    Call<String> saveAccount(@Body Account account);

    @GET("/account/get")
    Call<List<Account>> getAccounts();

    @GET("/account/get/{id}")
    Call<Account> findAccount(@Path("id") Long id);

    @GET("/account/get/{username}")
    Call<Account> findUserEntityByUsername(@Path("username") String username);

    @DELETE("/account/delete/{id}")
    Call<String> deleteAccount(@Path("id") Long id);

    @PATCH("/account/edit/{id}")
    Call<Account> editAccount(@Path("id") Long id, @Body Account account);
}
