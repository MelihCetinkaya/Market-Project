package com.example.myapplication.RetrofitInstance

import com.example.myapplication.api.CustomerApiService
import com.example.myapplication.api.SellerApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {


    val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.109.162:8085/") //"http://192.168.109.162:8085/" ,"https://market-mobil.ey.r.appspot.com"
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val CustomerApi: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }

    val SellerApi: SellerApiService by lazy {
        retrofit.create(SellerApiService::class.java)
    }


}