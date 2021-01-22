package com.example.soa.network.client

import com.example.soa.model.Product
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface DataClient {

    @GET("api/item")
    fun getItems(): Single<List<Product>>

    @POST("api/device/updateFcmToken")
    @FormUrlEncoded
    fun setFCMToken(@Field("token") token: String): Completable
}