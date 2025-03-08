package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

class CustomerRegisterActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)


        // Set click listener
        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                register(username, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun register(username: String, password: String) {
        val customer = Customer(
            name = "",           // Backend will handle empty values
            surname = "",        // Backend will handle empty values
            age = "0",          // Backend will handle default value
            username = username,
            password = password
        )

        Log.d("CustomerRegister", "Attempting registration with username: $username")

        RetrofitInstance.CustomerApi.registerCustomer(customer).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                Log.d("CustomerRegister", "Response code: ${response.code()}")
                Log.d("CustomerRegister", "Raw response: $response")
                
                if (response.isSuccessful) {
                    Log.d("CustomerRegister", "Registration successful")
                    Toast.makeText(this@CustomerRegisterActivity, 
                        "Registration successful! Please login.", 
                        Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMsg = "Registration failed: ${response.code()}"
                    Log.e("CustomerRegister", errorMsg)
                    Toast.makeText(this@CustomerRegisterActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                val errorMsg = "Network error: ${t.message}"
                Log.e("CustomerRegister", errorMsg, t)
                Toast.makeText(this@CustomerRegisterActivity, errorMsg, Toast.LENGTH_SHORT).show()
            }
        })
    }
} 