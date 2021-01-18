package com.example.soa.network.client

import com.example.soa.model.Product
import io.reactivex.Single
import retrofit2.http.GET

interface DataClient {

    @GET("api/item")
    fun getItems(): Single<List<Product>>
}