package com.example.myapplication.fragments.seller

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MarketDetailActivity
import com.example.myapplication.R
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.SellerApiService
import com.example.myapplication.model.Market
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MyMarketsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var marketsAdapter: MarketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_markets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.marketsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 1) // Change to 1 column
        marketsAdapter = MarketsAdapter()
        recyclerView.adapter = marketsAdapter

        // Set click listener for add market button
        view.findViewById<FloatingActionButton>(R.id.addMarketButton).setOnClickListener {
            showCreateMarketDialog()
        }

        // Load markets
        loadMarkets()
    }

    private fun loadMarkets() {
        val prefs = requireActivity().getSharedPreferences("seller_prefs", Context.MODE_PRIVATE)
        val username = prefs.getString("seller_username", "") ?: ""
        val token = prefs.getString("bearer_token", "") ?: ""

        Log.d("MarketAPI", "Loading markets for user: $username")
        Log.d("MarketAPI", "Token: Bearer $token")

        if (username.isNotEmpty() && token.isNotEmpty()) {
            RetrofitInstance.SellerApi.getMyMarkets("Bearer $token", username).enqueue(object : Callback<List<Market>> {
                override fun onResponse(call: Call<List<Market>>, response: Response<List<Market>>) {
                    Log.d("MarketAPI", "Response code: ${response.code()}")
                    Log.d("MarketAPI", "Response headers: ${response.headers()}")
                    val rawResponse = response.raw()
                    Log.d("MarketAPI", "Raw response: $rawResponse")
                    
                    if (response.isSuccessful) {
                        val markets = response.body() ?: emptyList()
                        Log.d("MarketAPI", "Markets loaded successfully: $markets")
                        marketsAdapter.updateMarkets(markets)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("MarketAPI", "Failed to load markets: ${response.code()}, Error: $errorBody")
                        Toast.makeText(context, "Failed to load markets: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Market>>, t: Throwable) {
                    Log.e("MarketAPI", "Network error while loading markets", t)
                    Log.e("MarketAPI", "Error details:", t)
                    Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("MarketAPI", "Username or token is empty. Username: $username, Token empty: ${token.isEmpty()}")
            Toast.makeText(context, "Authentication error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCreateMarketDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_create_market, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Create New Market")
            .create()

        val marketNameInput = dialogView.findViewById<TextInputEditText>(R.id.marketNameInput)
        val createButton = dialogView.findViewById<MaterialButton>(R.id.createMarketButton)

        createButton.setOnClickListener {
            val marketName = marketNameInput.text?.toString()?.trim() ?: ""
            if (marketName.isEmpty()) {
                marketNameInput.error = "Please enter market name"
                return@setOnClickListener
            }

            // Get username and token from SharedPreferences
            val prefs = requireActivity().getSharedPreferences("seller_prefs", Context.MODE_PRIVATE)
            val username = prefs.getString("seller_username", "") ?: ""
            val token = prefs.getString("bearer_token", "") ?: ""

            if (username.isNotEmpty() && token.isNotEmpty()) {
                createMarket(username, token, marketName, dialog)
            } else {
                Toast.makeText(context, "Authentication error", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun createMarket(username: String, token: String, marketName: String, dialog: AlertDialog) {
        Log.d("MarketAPI", "Creating market: $marketName for user: $username")
        Log.d("MarketAPI", "Using token: Bearer $token")

        RetrofitInstance.SellerApi.createMarket("Bearer $token", username, marketName)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("MarketAPI", "Market created successfully")
                        Toast.makeText(context, "Market created successfully", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        loadMarkets() // Refresh market list
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("MarketAPI", "Failed to create market: ${response.code()}, Error: $errorBody")
                        Toast.makeText(context, "Failed to create market", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("MarketAPI", "Network error: ${t.message}")
                    Log.e("MarketAPI", "Stack trace:", t)
                    Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private inner class MarketsAdapter : RecyclerView.Adapter<MarketsAdapter.MarketViewHolder>() {
        private var markets: List<Market> = emptyList()

        fun updateMarkets(newMarkets: List<Market>) {
            markets = newMarkets
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_market, parent, false)
            return MarketViewHolder(view)
        }

        override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
            holder.bind(markets[position])
        }

        override fun getItemCount() = markets.size

        inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val marketNameText: TextView = itemView.findViewById(R.id.marketNameText)

            fun bind(market: Market) {
                marketNameText.text = market.marketName
                itemView.setOnClickListener {
                    val intent = Intent(requireContext(), MarketDetailActivity::class.java)
                    intent.putExtra("marketName", market.marketName)
                    startActivity(intent)
                }
            }
        }
    }
} 