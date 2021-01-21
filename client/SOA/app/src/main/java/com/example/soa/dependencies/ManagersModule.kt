package com.example.soa.dependencies

import android.content.Context
import com.example.soa.managers.IOrdersManager
import com.example.soa.managers.IResourceManager
import com.example.soa.managers.OrdersManager
import com.example.soa.managers.ResourceManager
import com.example.soa.repository.IPreference
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class ManagersModule {

    @Provides
    @Reusable
    internal fun provideResourceManager(context: Context): IResourceManager {
        return ResourceManager(context)
    }

    @Provides
    @Singleton
    internal fun provideOrdersManager(preference: IPreference): IOrdersManager {
        return OrdersManager(preference)
    }
}