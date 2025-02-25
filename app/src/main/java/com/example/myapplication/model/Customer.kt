package com.example.myapplication.model

data class Customer(
    val name: String,
    val surname: String,
    val age: String,
    val username: String,
    val password: String,
    val token: String? = null
) 