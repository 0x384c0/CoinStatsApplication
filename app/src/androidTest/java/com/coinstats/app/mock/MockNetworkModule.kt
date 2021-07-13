package com.coinstats.app.mock

import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule {
    //region Api
    @Singleton
    @Provides
    fun provideCoinStatsApi(): CoinStatsApi {
        return MockCoinStatsApi()
    }
    //endregion
}