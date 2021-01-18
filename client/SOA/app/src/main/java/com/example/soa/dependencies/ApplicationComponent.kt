package com.example.soa.dependencies

import android.content.Context
import com.example.soa.ui.fragment.LoginFragment
import com.example.soa.ui.fragment.OrdersFragment
import com.example.soa.ui.fragment.ProductsFragment
import com.example.soa.ui.fragment.base.BaseFragment
import com.example.soa.ui.screen.SplashScreen
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ManagersModule::class, RepositoryModule::class])
interface ApplicationComponent {
    val context: Context
    fun inject(baseFragment: BaseFragment)
    fun inject(splashScreen: SplashScreen)
    fun inject(loginFragment: LoginFragment)
    fun inject(productsFragment: ProductsFragment)
    fun inject(ordersFragment: OrdersFragment)
}