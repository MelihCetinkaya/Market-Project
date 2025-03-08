package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.AdminApiService
import com.example.myapplication.api.CustomerApiService
import com.example.myapplication.model.Admin
import com.example.myapplication.model.Customer
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerAsCustomerLink: TextView
    private lateinit var registerAsSellerLink: TextView
    private lateinit var loginAsSellerLink: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerAsCustomerLink = findViewById(R.id.registerAsCustomerLink)
        registerAsSellerLink = findViewById(R.id.registerAsSellerLink)
        loginAsSellerLink = findViewById(R.id.loginAsSellerLink)

        // Set click listener for login button
        loginButton.setOnClickListener {
            val username = usernameInput.text?.toString() ?: ""
            val password = passwordInput.text?.toString() ?: ""

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Call API to check credentials
            val credentials = mapOf("username" to username, "password" to password)
            RetrofitInstance.CustomerApi.loginCustomer(credentials).enqueue(object : Callback<Customer> {
                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                    Log.d("LoginAPI", "Response Code: ${response.code()}")
                    Log.d("LoginAPI", "Response Body: ${response.body()}")
                    Log.d("LoginAPI", "Raw Response: ${response.raw()}")
                    
                    if (response.isSuccessful) {
                        val customer = response.body()
                        Log.d("LoginAPI", "Customer: $customer")
                        
                        // Login successful
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, CustomerContentActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Invalid credentials
                        Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.e("LoginAPI", "Network Error: ${t.message}")
                    Toast.makeText(this@LoginActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Set click listener for register as customer link
        registerAsCustomerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("userType", "customer")
            startActivity(intent)
        }

        // Set click listener for register as seller link
        registerAsSellerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("userType", "seller")
            startActivity(intent)
        }

        // Set click listener for login as seller link
        loginAsSellerLink.setOnClickListener {
            val intent = Intent(this, SellerLoginActivity::class.java)
            startActivity(intent)
        }
    }
} 