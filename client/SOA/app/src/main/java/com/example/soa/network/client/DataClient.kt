package com.example.soa.network.client

import com.example.soa.model.Order
import com.example.soa.model.OrderPayload
import com.example.soa.model.Product
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DataClient {

    @GET("api/item")
    fun getItems(): Single<List<Product>>

    @GET("api/orders")
    fun getOrders(): Single<List<Order>>

    @POST("api/orders")
    fun createOrder(@Body order: OrderPayload): Completable

    @POST("api/orders/buy")
    fun buyOrder(@Query("id") orderId: String): Completable

}