package com.example.soa.network.adapter

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RetrofitCallAdapterFactory : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory by lazy { RxJava2CallAdapterFactory.createAsync() }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> =
        RxCallAdapterWrapper(original.get(returnType, annotations, retrofit)!!, annotations)

    private class RxCallAdapterWrapper<R>(private val wrapped: CallAdapter<R, *>, private val annotations: Array<Annotation>) : CallAdapter<R, Any> {

        override fun responseType(): Type = wrapped.responseType()

        override fun adapt(call: Call<R>): Any {
            return when (val adaptedCall = wrapped.adapt(call)) {
                is Completable -> adaptedCall.onErrorResumeNext { throwable -> Completable.error(throwable) }
                is Single<*> -> adaptedCall.onErrorResumeNext { Single.error(it) }
                is Observable<*> -> adaptedCall.onErrorResumeNext { throwable: Throwable ->
                    Observable.error(throwable)
                }
                else -> throw RuntimeException("Observable Status not supported")
            }
        }
    }

    companion object {

        @JvmStatic
        fun create(): CallAdapter.Factory = RetrofitCallAdapterFactory()
    }
}
