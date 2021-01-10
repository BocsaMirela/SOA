package com.example.soa.dependencies

import android.content.Context
import com.example.soa.network.client.DataClient
import com.example.soa.repository.DataRepository
import com.example.soa.repository.IDataRepository
import com.example.soa.repository.IPreference
import com.example.soa.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideDataRepository(dataClient: DataClient): IDataRepository {
        return DataRepository(dataClient)
    }

    @Provides
    @Singleton
    internal fun providePreferenceRepository(context: Context): IPreference {
        return PreferenceRepository(context, PREFERENCES)
    }

    companion object {
        private const val PREFERENCES = "soa.preferences"
    }
}
