package com.example.myapplication.fragments.seller

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.SellerApiService
import com.example.myapplication.model.Seller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class SellerProfileFragment : Fragment() {
    private lateinit var nameText: TextView
    private lateinit var surnameText: TextView
    private lateinit var ageText: TextView
    private lateinit var usernameText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seller_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        nameText = view.findViewById(R.id.nameText)
        surnameText = view.findViewById(R.id.surnameText)
        ageText = view.findViewById(R.id.ageText)
        usernameText = view.findViewById(R.id.usernameText)


        // Get username and token from SharedPreferences
        val prefs = requireActivity().getSharedPreferences("seller_prefs", Context.MODE_PRIVATE)
        val username = prefs.getString("seller_username", "") ?: ""
        val token = prefs.getString("bearer_token", "") ?: ""

        if (username.isNotEmpty() && token.isNotEmpty()) {
            fetchSellerProfile(username, token)
        }
    }

    private fun fetchSellerProfile(username: String, token: String) {
        Log.d("ProfileAPI", "Fetching profile for username: $username with token: $token")
        RetrofitInstance.SellerApi.getMyProfile("Bearer $token", username).enqueue(object : Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                Log.d("ProfileAPI", "Response code: ${response.code()}")
                Log.d("ProfileAPI", "Response headers: ${response.headers()}")
                Log.d("ProfileAPI", "Raw response: ${response.raw()}")
                
                if (response.isSuccessful) {
                    val seller = response.body()
                    Log.d("ProfileAPI", "Profile data received: $seller")
                    seller?.let {
                        nameText.text = it.name
                        surnameText.text = it.surname
                        ageText.text = it.age.toString()
                        usernameText.text = it.username
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfileAPI", "Failed to fetch profile: ${response.code()}, Error: $errorBody")
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Seller>, t: Throwable) {
                Log.e("ProfileAPI", "Network error: ${t.message}")
                Log.e("ProfileAPI", "Stack trace:", t)
                Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
} 