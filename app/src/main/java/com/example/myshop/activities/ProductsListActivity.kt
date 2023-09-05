package com.example.myshop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.models.Product
import com.example.myshop.network.ShopApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var data : List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_products_list);
        }

        manager = LinearLayoutManager(this)
        getAllData()

    }

    fun getAllData(){
        ShopApi.retrofitService.getAllProducts().enqueue(object: Callback<List<Product>>{
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
                        data = response.body()!!
                        val myAdapter = ProductsListAdapter(data)
                        myAdapter.setOnItemClickListener(object:
                            ProductsListAdapter.onItemClickListener {
                                override fun onItemClick(position : Int) {
                                    val intent = Intent(this@ProductsListActivity,
                                        ProductActivity()::class.java)
                                    intent.putExtra("productId", data[position].id)
                                    startActivity(intent)
                                }
                            })
                        layoutManager = manager
                        adapter = myAdapter
                    }
                } else {
                    Toast.makeText(this@ProductsListActivity, "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}


