package com.example.myapplication.model

data class Seller(
    val name: String,
    val surname: String,
    val age: Int,
    val username: String,
    val password: String,
    val token: String? = null
) 