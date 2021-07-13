package com.coinstats.app.mock

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.local.dao.CoinDao
import com.coinstats.app.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class MockDatabaseModule {
    @Provides
    @Singleton
    internal fun provideAppDatabase(): AppDatabase {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .build()
        return db
    }

    @Provides
    internal fun provideCoinDao(appDatabase: AppDatabase): CoinDao {
        return appDatabase.coinDao
    }
}