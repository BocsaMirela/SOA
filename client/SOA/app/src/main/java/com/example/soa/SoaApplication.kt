package com.example.soa

import android.app.Application
import com.example.soa.dependencies.ApplicationComponent
import com.example.soa.dependencies.ApplicationModule
import com.example.soa.dependencies.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

class SoaApplication : Application() {

    private val BASE = javaClass.simpleName

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { exception ->
            if (exception is UndeliverableException) {
                // We had some sort of error when doing a request but result was not important or it was already handled(in case of Single.zip) so we don't care
                return@setErrorHandler
            }

            Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(
                Thread.currentThread(),
                exception
            )
        }

        createApplicationComponent()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    protected open fun createApplicationComponentBuilder(): DaggerApplicationComponent.Builder {
        return DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this))
    }

    private fun createApplicationComponent() {
        applicationComponent = createApplicationComponentBuilder().build()
    }
}