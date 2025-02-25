package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.api.CustomerApiService
import com.example.myapplication.model.Market
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MarketsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var customerApiService: CustomerApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_markets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.marketsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MarketsAdapter()
        recyclerView.adapter = adapter

        // Initialize Retrofit
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.109.162:8085/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        customerApiService = retrofit.create(CustomerApiService::class.java)

        // Load markets
        loadMarkets()
    }

    private fun loadMarkets() {
        val prefs = requireActivity().getSharedPreferences("customer_prefs", Context.MODE_PRIVATE)
        val token = prefs.getString("bearer_token", "") ?: ""

        Log.d("CustomerMarkets", "Using token for API call: $token")

        if (token.isNotEmpty()) {
            customerApiService.getAllMarkets(token).enqueue(object : Callback<List<Market>> {
                override fun onResponse(call: Call<List<Market>>, response: Response<List<Market>>) {
                    Log.d("CustomerMarkets", "Response code: ${response.code()}")
                    
                    if (response.isSuccessful) {
                        val markets = response.body() ?: emptyList()
                        Log.d("CustomerMarkets", "Markets received: $markets")
                        
                        (recyclerView.adapter as MarketsAdapter).updateMarkets(markets)
                    } else {
                        Log.e("CustomerMarkets", "Error loading markets: ${response.code()}")
                        Toast.makeText(context, "Failed to load markets", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Market>>, t: Throwable) {
                    Log.e("CustomerMarkets", "Network error", t)
                    Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("CustomerMarkets", "No token available")
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class MarketsAdapter : RecyclerView.Adapter<MarketsAdapter.MarketViewHolder>() {
        private var markets: List<Market> = emptyList()

        fun updateMarkets(newMarkets: List<Market>) {
            markets = newMarkets
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer_market, parent, false)
            return MarketViewHolder(view)
        }

        override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
            holder.bind(markets[position])
        }

        override fun getItemCount() = markets.size

        inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameText: TextView = itemView.findViewById(R.id.marketNameText)
            private val idText: TextView = itemView.findViewById(R.id.marketIdText)
            private val openingTimeText: TextView = itemView.findViewById(R.id.openingTimeText)
            private val closingTimeText: TextView = itemView.findViewById(R.id.closingTimeText)

            fun bind(market: Market) {
                nameText.text = market.marketName
                idText.text = "Market ID: ${market.marketId}"
                openingTimeText.text = "Opening Time: ${market.opening_time}"
                closingTimeText.text = "Closing Time: ${market.closing_time}"
            }
        }
    }
} 