package com.example.soa.network.client

import com.example.soa.model.Order
import com.example.soa.model.OrderPayload
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrdersClient {

    @GET("api/orders")
    fun getOrders(): Single<List<Order>>

    @POST("api/orders")
    fun createOrder(@Body order: OrderPayload): Completable

    @POST("api/orders/{id}/buy")
    fun buyOrder(@Path("id") orderId: String): Completable

    @POST("api/orders/{id}/cancel")
    fun cancelOrder(@Path("id") orderId: String): Completable

}