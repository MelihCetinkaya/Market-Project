package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.CustomerApiService
import com.example.myapplication.api.SellerApiService
import com.example.myapplication.model.Customer
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

class RegisterActivity : AppCompatActivity() {
    private lateinit var titleText: TextView
    private lateinit var nameInput: TextInputEditText
    private lateinit var surnameInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var registerButton: MaterialButton
    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Get user type from intent
        userType = intent.getStringExtra("userType") ?: "customer"

        // Initialize views
        titleText = findViewById(R.id.titleText)
        nameInput = findViewById(R.id.nameInput)
        surnameInput = findViewById(R.id.surnameInput)
        ageInput = findViewById(R.id.ageInput)
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)

        // Update title based on user type
        titleText.text = if (userType == "seller") "Register as Seller" else "Register as Customer"

        // Set click listener for register button
        registerButton.setOnClickListener {
            val name = nameInput.text?.toString()?.trim() ?: ""
            val surname = surnameInput.text?.toString()?.trim() ?: ""
            val age = ageInput.text?.toString()?.trim() ?: ""
            val username = usernameInput.text?.toString()?.trim() ?: ""
            val password = passwordInput.text?.toString() ?: ""
            val confirmPassword = confirmPasswordInput.text?.toString() ?: ""

            // Validate input fields
            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                    nameInput.requestFocus()
                }
                surname.isEmpty() -> {
                    Toast.makeText(this, "Please enter your surname", Toast.LENGTH_SHORT).show()
                    surnameInput.requestFocus()
                }
                age.isEmpty() -> {
                    Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                    ageInput.requestFocus()
                }
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                    usernameInput.requestFocus()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                    passwordInput.requestFocus()
                }
                confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
                    confirmPasswordInput.requestFocus()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    confirmPasswordInput.requestFocus()
                }
                else -> {
                    try {
                        val ageInt = age.toInt()
                        if (userType == "seller") {
                            // Register seller
                            val seller = Seller(name, surname, ageInt, username, password)
                            
                            Log.d("RegisterAPI", "Registering seller: $seller")
                            RetrofitInstance.SellerApi.registerSeller(seller).enqueue(object : Callback<Seller> {
                                override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                                    Log.d("RegisterAPI", "Response Code: ${response.code()}")
                                    Log.d("RegisterAPI", "Response Body: ${response.body()}")
                                    
                                    if (response.isSuccessful) {
                                        Toast.makeText(this@RegisterActivity, "Seller registration successful", Toast.LENGTH_SHORT).show()
                                        finish()
                                    } else {
                                        Toast.makeText(this@RegisterActivity, "Registration failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Seller>, t: Throwable) {
                                    Log.e("RegisterAPI", "Network Error: ${t.message}")
                                    Toast.makeText(this@RegisterActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } else {
                            // Register customer
                            val customer = Customer(name, surname,
                                ageInt.toString(), username, password)
                            
                            Log.d("RegisterAPI", "Registering customer: $customer")
                            RetrofitInstance.CustomerApi.registerCustomer(customer).enqueue(object : Callback<Customer> {
                                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                                    Log.d("RegisterAPI", "Response Code: ${response.code()}")
                                    Log.d("RegisterAPI", "Response Body: ${response.body()}")
                                    
                                    if (response.isSuccessful) {
                                        Toast.makeText(this@RegisterActivity, "Customer registration successful", Toast.LENGTH_SHORT).show()
                                        finish()
                                    } else {
                                        Toast.makeText(this@RegisterActivity, "Registration failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Customer>, t: Throwable) {
                                    Log.e("RegisterAPI", "Network Error: ${t.message}")
                                    Toast.makeText(this@RegisterActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    } catch (e: NumberFormatException) {
                        Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
                        ageInput.requestFocus()
                    }
                }
            }
        }
    }
} 