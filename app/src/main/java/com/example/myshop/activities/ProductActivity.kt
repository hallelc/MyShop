package com.example.myshop.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myshop.R
import com.example.myshop.models.Product
import com.example.myshop.network.ShopApi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : ComponentActivity() {
    private lateinit var image : ImageView
    private lateinit var title : TextView
    private lateinit var price : TextView
    private lateinit var description : TextView
    private lateinit var category :TextView
    private lateinit var rating : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_product);
        }

        val productId = intent.extras!!.getInt("productId")
        getProductInfo(productId)
    }

    fun getProductInfo(productId : Int){
        ShopApi.retrofitService.getProductInfo(productId).enqueue(object: Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                if(response.isSuccessful){
                    initProductData(response.body()!!);
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun initProductData(product : Product) {
        image = findViewById<ImageView>(R.id.imageView)
        title = findViewById<TextView>(R.id.title)
        price = findViewById<TextView>(R.id.price)
        description = findViewById<TextView>(R.id.description)
        category = findViewById<TextView>(R.id.price)
        rating = findViewById<TextView>(R.id.rating)

        Picasso.get().load(product.image).into(image)
        title.text = product.title
        price.text = "$${product.price}"
        description.text = product.description
        category.text = product.category
        rating.text = "rates: ${product.rating.rate.toString()} stars"
    }
}
