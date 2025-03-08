package com.example.myapplication.api

import com.example.myapplication.model.Admin
import retrofit2.Call
import retrofit2.http.GET

interface AdminApiService {
    @GET("api/getAdmin")
    fun getAdmin(): Call<Admin>
}

