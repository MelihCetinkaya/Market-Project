package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if user is logged in
        val token = getSharedPreferences("customer_prefs", MODE_PRIVATE)
            .getString("bearer_token", "") ?: ""
        
        Log.d("MainActivity", "Token check on start: $token")
        
        if (token.isEmpty()) {
            Log.d("MainActivity", "No token found, redirecting to login")
            startActivity(Intent(this, CustomerLoginActivity::class.java))
            finish()
            return
        }

        // Rest of your MainActivity code...
    }
}