package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.CustomerApiService
import com.example.myapplication.model.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class CustomerLoginActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)



        // Set click listeners
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(username, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, CustomerRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val loginData = mapOf(
            "username" to username,
            "password" to password
        )

        Log.d("CustomerLogin", "Attempting login with username: $username")
        Log.d("CustomerLogin", "Request data: $loginData")

        RetrofitInstance.CustomerApi.loginCustomer(loginData).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                Log.d("CustomerLogin", "Response code: ${response.code()}")
                
                if (response.isSuccessful) {
                    val customer = response.body()
                    Log.d("CustomerLogin", "Login successful, customer: $customer")
                    
                    // Get token from response body
                    val token = customer?.token
                    Log.d("CustomerLogin", "Token from response: $token")
                    
                    if (!token.isNullOrEmpty()) {
                        // Add Bearer prefix if not present
                        val finalToken = if (!token.startsWith("Bearer ")) "Bearer $token" else token
                        Log.d("CustomerLogin", "Final token to save: $finalToken")
                        
                        // Save token and username to SharedPreferences
                        val prefs = getSharedPreferences("customer_prefs", MODE_PRIVATE)
                        prefs.edit().apply {
                            putString("bearer_token", finalToken)
                            putString("customer_username", username)
                            apply()
                        }

                        // Verify saved token
                        val savedToken = prefs.getString("bearer_token", "")
                        Log.d("CustomerLogin", "Verified saved token: $savedToken")
                        
                        if (savedToken.isNullOrEmpty()) {
                            Log.e("CustomerLogin", "Failed to save token")
                            Toast.makeText(this@CustomerLoginActivity, "Error saving login info", Toast.LENGTH_SHORT).show()
                            return@onResponse
                        }

                        // Navigate to MainActivity
                        startActivity(Intent(this@CustomerLoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Log.e("CustomerLogin", "No token received in response")
                        Toast.makeText(this@CustomerLoginActivity, "Login failed: No token received", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CustomerLoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Log.e("CustomerLogin", "Login failed", t)
                Toast.makeText(this@CustomerLoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
} 