package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.api.SellerApiService
import com.example.myapplication.model.Seller
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class SellerLoginActivity : AppCompatActivity() {
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var sellerApiService: SellerApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_login)

        // Create OkHttpClient
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.109.162:8085/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        sellerApiService = retrofit.create(SellerApiService::class.java)

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)

        // Set click listener for login button
        loginButton.setOnClickListener {
            val username = usernameInput.text?.toString() ?: ""
            val password = passwordInput.text?.toString() ?: ""

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("LoginAPI", "Making API call to: ${retrofit.baseUrl()}login/authSeller")
            
            // Call API to check credentials
            val credentials = mapOf("username" to username, "password" to password)
            sellerApiService.loginSeller(credentials).enqueue(object : Callback<Seller> {
                override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                    Log.d("LoginAPI", "Response Code: ${response.code()}")
                    Log.d("LoginAPI", "Response Body: ${response.body()}")
                    Log.d("LoginAPI", "Raw Response: ${response.raw()}")
                    
                    handleLoginResponse(response, username)
                }

                override fun onFailure(call: Call<Seller>, t: Throwable) {
                    Log.e("LoginAPI", "Network Error: ${t.message}")
                    Toast.makeText(this@SellerLoginActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun handleLoginResponse(response: Response<Seller>, username: String) {
        if (response.isSuccessful) {
            val seller = response.body()
            // Get token from response body instead of headers
            val token = seller?.token ?: ""
            
            Log.d("SellerLogin", "Login successful")
            Log.d("SellerLogin", "Seller response: $seller")
            Log.d("SellerLogin", "Token: $token")
            
            // Save username and token
            getSharedPreferences("seller_prefs", MODE_PRIVATE).edit().apply {
                putString("seller_username", username)
                putString("bearer_token", token) // Save token without Bearer prefix
                apply()
            }

            // Fetch profile data immediately after login
            fetchSellerProfile(username, token)

            // Navigate to content activity
            startActivity(Intent(this, SellerContentActivity::class.java))
            finish()
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("SellerLogin", "Login failed: ${response.code()}, Error: $errorBody")
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchSellerProfile(username: String, token: String) {
        Log.d("ProfileAPI", "Fetching profile for user: $username")
        Log.d("ProfileAPI", "Using token: Bearer $token")
        
        sellerApiService.getMyProfile("Bearer $token", username).enqueue(object : Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                Log.d("ProfileAPI", "Response code: ${response.code()}")
                Log.d("ProfileAPI", "Response headers: ${response.headers()}")
                val rawResponse = response.raw()
                Log.d("ProfileAPI", "Raw response: $rawResponse")
                
                if (response.isSuccessful) {
                    val seller = response.body()
                    Log.d("ProfileAPI", "Profile fetched successfully: $seller")
                    
                    // Save seller profile data to SharedPreferences
                    getSharedPreferences("seller_prefs", MODE_PRIVATE).edit().apply {
                        putString("seller_name", seller?.name)
                        putString("seller_surname", seller?.surname)
                        putInt("seller_age", seller?.age ?: 0)
                        apply()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileAPI", "Failed to fetch profile: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<Seller>, t: Throwable) {
                Log.e("ProfileAPI", "Network error while fetching profile", t)
                Log.e("ProfileAPI", "Error details:", t)
            }
        })
    }
} 