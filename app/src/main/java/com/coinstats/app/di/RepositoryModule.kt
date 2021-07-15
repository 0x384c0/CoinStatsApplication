package com.coinstats.app.di

import com.coinstats.app.data.repository.CoinsRepositoryImpl
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.domain.repository.CoinsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class RepositoryModule {
    //region Repository
    @Singleton
    @Provides
    fun provideCoinsRepository(
        appDatabase: AppDatabase,
        coinStatsApi: CoinStatsApi
    ): CoinsRepository {
        return CoinsRepositoryImpl(appDatabase, coinStatsApi)
    }
    //endregion
}