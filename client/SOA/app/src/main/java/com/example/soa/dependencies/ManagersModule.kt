package com.example.soa.dependencies

import android.content.Context
import com.example.soa.managers.IResourceManager
import com.example.soa.managers.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ManagersModule {

    @Provides
    @Reusable
    internal fun provideResourceManager(context: Context): IResourceManager {
        return ResourceManager(context)
    }
}