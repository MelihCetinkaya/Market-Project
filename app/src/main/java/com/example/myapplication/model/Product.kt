package com.example.myapplication.model

data class Product(
    val productId: Int,
    val productName: String,
    val stock_status: Boolean,
    val stock_amount: Int,
    val comments: List<String> = emptyList(),
    val added_at: String,
    val supplyDate: String,
    val price: Int
) 