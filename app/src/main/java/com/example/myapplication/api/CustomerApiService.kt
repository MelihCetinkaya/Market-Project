package com.example.myapplication.api

import com.example.myapplication.model.Customer
import com.example.myapplication.model.Market
import retrofit2.Call
import retrofit2.http.*

interface CustomerApiService {
    @POST("login/saveCustomer")
    fun registerCustomer(@Body customer: Customer): Call<Customer>

    @POST("login/authCustomer")
    fun loginCustomer(@Body customer: Map<String, String>): Call<Customer>

    @GET("customer/markets")
    fun getAllMarkets(@Header("Authorization") token: String): Call<List<Market>>
} 