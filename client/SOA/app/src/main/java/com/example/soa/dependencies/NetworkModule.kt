package com.example.soa.dependencies

import android.content.Context
import com.example.soa.BuildConfig
import com.example.soa.repository.IPreference
import com.example.soa.network.HttpAuthInterceptor
import com.example.soa.network.adapter.RetrofitCallAdapterFactory
import com.example.soa.network.client.AuthClient
import com.example.soa.network.client.DataClient
import com.example.soa.network.client.OrdersClient
import com.example.soa.network.client.SessionClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        context: Context,
        authClient: AuthClient,
        preference: IPreference
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(HttpAuthInterceptor(preference))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            builder.addNetworkInterceptor(StethoInterceptor())
            builder.addNetworkInterceptor(ChuckInterceptor(context))
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideCallAdapter(): CallAdapter.Factory {
        return RetrofitCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideAuthClient(
        context: Context,
        adapter: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): AuthClient {
        val builder = OkHttpClient.Builder()
        builder.dispatcher(Dispatcher().apply { maxRequests = 1 })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            builder.addNetworkInterceptor(StethoInterceptor())
            builder.addNetworkInterceptor(ChuckInterceptor(context))
        }
        return Retrofit.Builder().baseUrl(AUTH_ENDPOINT)
            .client(builder.build())
            .addCallAdapterFactory(adapter)
            .addConverterFactory(gsonConverterFactory)
            .build().create(AuthClient::class.java)
    }

    @Provides
    @Singleton
    internal fun provideSessionClient(
        okHttpClient: OkHttpClient,
        adapter: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): SessionClient {
        return Retrofit.Builder()
            .baseUrl(AUTH_ENDPOINT)
            .client(okHttpClient)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(gsonConverterFactory)
            .build().create(SessionClient::class.java)
    }

    @Provides
    @Singleton
    internal fun provideDataClient(
        okHttpClient: OkHttpClient,
        adapter: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): DataClient {
        return Retrofit.Builder()
            .baseUrl(CORE_ENDPOINT)
            .client(okHttpClient)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(gsonConverterFactory)
            .build().create(DataClient::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOrdersClient(
        okHttpClient: OkHttpClient,
        adapter: CallAdapter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): OrdersClient {
        return Retrofit.Builder()
            .baseUrl(ORDERS_ENDPOINT)
            .client(okHttpClient)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(gsonConverterFactory)
            .build().create(OrdersClient::class.java)
    }

    companion object {
        private const val AUTH_ENDPOINT = "http://192.168.42.50:8879/"
        private const val CORE_ENDPOINT = "http://192.168.42.50:8879/"
        private const val ORDERS_ENDPOINT = "http://192.168.42.50:8877/"
    }
}