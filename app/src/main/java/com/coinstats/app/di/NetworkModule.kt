package com.coinstats.app.di

import com.coinstats.app.data.source.remote.CoinStatsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    //region Api
    @Singleton
    @Provides
    open fun provideCoinStatsApi(retrofit: Retrofit): CoinStatsApi {
        return retrofit.create(CoinStatsApi::class.java)
    }
    //endregion
}