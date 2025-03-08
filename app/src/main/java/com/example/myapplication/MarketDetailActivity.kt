package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RetrofitInstance.RetrofitInstance
import com.example.myapplication.api.SellerApiService
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
import com.example.myapplication.model.Product

class MarketDetailActivity : AppCompatActivity() {
    private lateinit var marketNameTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var addProductButton: FloatingActionButton
    private var marketName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_detail)

        // Get market name from intent
        marketName = intent.getStringExtra("marketName") ?: ""
        
        // Initialize views
        marketNameTitle = findViewById(R.id.marketNameTitle)
        recyclerView = findViewById(R.id.productsRecyclerView)
        addProductButton = findViewById(R.id.addProductButton)

        marketNameTitle.text = marketName

        // Initialize RecyclerView with GridLayoutManager (2 columns)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = ProductsAdapter()
        recyclerView.adapter = adapter

        loadProducts()


        addProductButton.setOnClickListener {
            showAddProductDialog()
        }
    }

    private fun loadProducts() {
        val token = getSharedPreferences("seller_prefs", MODE_PRIVATE)
            .getString("bearer_token", "") ?: ""

        if (token.isNotEmpty()) {
            val finalToken = if (!token.startsWith("Bearer ")) "Bearer $token" else token

            RetrofitInstance.SellerApi.getMarketProducts(finalToken, marketName)
                .enqueue(object : Callback<List<Product>> {
                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                        if (response.isSuccessful) {
                            val products = response.body() ?: emptyList()
                            (recyclerView.adapter as ProductsAdapter).updateProducts(products)
                        } else {
                            Toast.makeText(this@MarketDetailActivity, 
                                "Failed to load products: ${response.code()}", 
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        Toast.makeText(this@MarketDetailActivity, 
                            "Network error: ${t.message}", 
                            Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private inner class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
        private var products: List<Product> = emptyList()

        fun updateProducts(newProducts: List<Product>) {
            products = newProducts
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.bind(products[position])
        }

        override fun getItemCount() = products.size

        inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameText: TextView = itemView.findViewById(R.id.productNameText)
            private val stockText: TextView = itemView.findViewById(R.id.stockText)

            fun bind(product: Product) {
                nameText.text = product.productName
                stockText.text = "Stock: ${product.stock_amount} | Price: ${product.price} TL"

                itemView.setOnClickListener {
                    showProductDetailDialog(product)
                }
            }
        }
    }

    private fun showProductDetailDialog(product: Product) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_product_detail, null)

        // Initialize views
        dialogView.findViewById<TextView>(R.id.productNameText).text = product.productName
        dialogView.findViewById<TextView>(R.id.stockAmountText).text = product.stock_amount.toString()
        dialogView.findViewById<TextView>(R.id.stockStatusText).text = if (product.stock_status) "In Stock" else "Out of Stock"
        dialogView.findViewById<TextView>(R.id.priceText).text = "${product.price} TL"
        dialogView.findViewById<TextView>(R.id.addedAtText).text = product.added_at
        dialogView.findViewById<TextView>(R.id.supplyDateText).text = product.supplyDate

        // Log the product details for debugging
        Log.d("ProductDetails", "Product: $product")
        Log.d("ProductDetails", "Price: ${product.price}")

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Product Details")
            .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showAddProductDialog() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_add_product, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add New Product")
            .create()

        val productNameInput = dialogView.findViewById<TextInputEditText>(R.id.productNameInput)
        val stockAmountInput = dialogView.findViewById<TextInputEditText>(R.id.stockAmountInput)
        val priceInput = dialogView.findViewById<TextInputEditText>(R.id.priceInput)
        val addButton = dialogView.findViewById<MaterialButton>(R.id.addProductButton)

        addButton.setOnClickListener {
            val productName = productNameInput.text?.toString()?.trim() ?: ""
            val stockAmount = stockAmountInput.text?.toString()?.trim() ?: ""
            val price = priceInput.text?.toString()?.trim() ?: ""

            if (productName.isEmpty() || stockAmount.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get token from SharedPreferences
            val token = getSharedPreferences("seller_prefs", MODE_PRIVATE)
                .getString("bearer_token", "") ?: ""
            val username = getSharedPreferences("seller_prefs", MODE_PRIVATE)
                .getString("seller_username", "") ?: ""

            if (token.isNotEmpty() && username.isNotEmpty()) {
                try {
                    val stockAmountInt = stockAmount.toInt()
                    val priceDouble = price.toDouble()
                    val product = Product(
                        productId = 0, // Backend will assign the real ID
                        productName = productName,
                        stock_amount = stockAmountInt,
                        stock_status = stockAmountInt > 0,
                        price = priceDouble.toInt(),
                        comments = emptyList(),
                        added_at = "",  // Backend will set this
                        supplyDate = "" // Backend will set this
                    )

                    Log.d("ProductAPI", "Request body: $product")
                    addProduct(token, username, product, dialog)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Authentication error", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun addProduct(token: String, username: String, product: Product, dialog: AlertDialog) {
        Log.d("ProductAPI", "Adding product to market: $marketName")
        Log.d("ProductAPI", "Product details: $product")
        Log.d("ProductAPI", "Using token: $token")
        
        try {
            // Check if token already has "Bearer " prefix
            val finalToken = if (!token.startsWith("Bearer ")) "Bearer $token" else token
            Log.d("ProductAPI", "Final token being sent: $finalToken")

            RetrofitInstance.SellerApi.addProduct(finalToken, marketName, product)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        try {
                            Log.d("ProductAPI", "Response code: ${response.code()}")
                            Log.d("ProductAPI", "Response headers: ${response.headers()}")
                            Log.d("ProductAPI", "Raw response: ${response.raw()}")
                            Log.d("ProductAPI", "Request URL: ${call.request().url}")
                            Log.d("ProductAPI", "Request headers: ${call.request().headers}")
                            
                            if (response.isSuccessful) {
                                runOnUiThread {
                                    Log.d("ProductAPI", "Product added successfully")
                                    Toast.makeText(this@MarketDetailActivity, "Product added successfully", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                runOnUiThread {
                                    Log.e("ProductAPI", "Failed to add product: ${response.code()}, Error: $errorBody")
                                    Log.e("ProductAPI", "Request URL: ${call.request().url}")
                                    Toast.makeText(this@MarketDetailActivity, "Failed to add product: ${response.code()}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("ProductAPI", "Error in onResponse", e)
                            runOnUiThread {
                                Toast.makeText(this@MarketDetailActivity, "Error processing response: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("ProductAPI", "Network error", t)
                        Log.e("ProductAPI", "Request URL: ${call.request().url}")
                        runOnUiThread {
                            Toast.makeText(this@MarketDetailActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        } catch (e: Exception) {
            Log.e("ProductAPI", "Error making API call", e)
            Toast.makeText(this, "Error making API call: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
} 