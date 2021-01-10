package com.example.soa.network.client

import com.example.soa.model.Auth
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthClient {

    @POST("api/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Single<Auth>
}