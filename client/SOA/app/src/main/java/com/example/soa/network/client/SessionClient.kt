package com.example.soa.network.client

import com.example.soa.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface SessionClient {

    @GET("api/profile")
    fun getUser(): Single<User>
}