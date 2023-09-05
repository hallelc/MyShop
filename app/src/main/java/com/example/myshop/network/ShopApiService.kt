package com.example.myshop.network

import com.example.myshop.models.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private const val BASE_URL = "https://fakestoreapi.com/";
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build();

interface ShopApiService {
    @GET("products")
    fun getAllProducts() : Call<List<Product>>

    @GET("products/{id}")
    fun getProductInfo(@Path("id") id: Int) : Call<Product>
}

object ShopApi {
    val retrofitService : ShopApiService by lazy {
        retrofit.create(ShopApiService::class.java) }
}