package com.coinstats.app.di

import android.app.Application
import androidx.room.Room
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.local.dao.CoinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    internal fun provideCoinDao(appDatabase: AppDatabase): CoinDao {
        return appDatabase.coinDao
    }
}