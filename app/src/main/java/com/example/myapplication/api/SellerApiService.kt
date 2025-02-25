package com.example.myapplication.api

import com.example.myapplication.model.Market
import com.example.myapplication.model.Seller
import com.example.myapplication.model.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SellerApiService {
    @POST("login/saveSeller")
    fun registerSeller(@Body seller: Seller): Call<Seller>

    @POST("login/authSeller")
    fun loginSeller(@Body seller: Map<String, String>): Call<Seller>

    @GET("seller/getMyProfile")
    fun getMyProfile(
        @Header("Authorization") token: String,
        @Query("username") username: String
    ): Call<Seller>

    @POST("seller/createMarket")
    fun createMarket(
        @Header("Authorization") token: String,
        @Query("username") username: String,
        @Query("marketName") marketName: String
    ): Call<Void>

    @GET("seller/getMyMarkets")
    fun getMyMarkets(
        @Header("Authorization") token: String,
        @Query("username") username: String
    ): Call<List<Market>>

    @POST("seller/addProduct")
    fun addProduct(
        @Header("Authorization") token: String,
        @Query("marketName") marketName: String,
        @Body product: Product
    ): Call<Void>

    @GET("seller/productsOfMarket")
    fun getMarketProducts(
        @Header("Authorization") token: String,
        @Query("marketName") marketName: String
    ): Call<List<Product>>
} 